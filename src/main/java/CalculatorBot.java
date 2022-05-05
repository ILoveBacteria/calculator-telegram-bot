import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * This class is the logic and the core of the bot
 *
 * @author Moein Arabi
 * @version 1.2.0
 */
public class CalculatorBot extends TelegramLongPollingBot {
    /**
     * Saves the numbers and operation that the user sends
     */
    private String problem = "";

    /**
     * List of users who started chatting with the bot
     */
    private final Map<User, Chat> userChatMap;

    private final ReadWriteLock userChatMapLock;

    public CalculatorBot(Map<User, Chat> userChatMap, ReadWriteLock userChatMapLock) {
        super();
        this.userChatMap = userChatMap;
        this.userChatMapLock = userChatMapLock;
    }

    /**
     * This method is executed when a new message is received
     *
     * @param update The {@code update} contains message details
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            List<MessageEntity> messageEntity = update.getMessage().getEntities();
            SendMessage sendMessage;

            String text = update.getMessage().getText();
            System.out.println(update.getMessage().getChat().toString());
            System.out.println(text);


            if (messageEntity.size() != 0 && MessageEntities.hasCommand(messageEntity)) {
                sendMessage = Command.doAction(MessageEntities.getCommand(messageEntity));
                sendMessage.setChatId(update.getMessage().getChatId().toString());
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {
                if (text.charAt(0) == '=') {
                    sendText(calculate(problem), update.getMessage().getChatId().toString());
                    problem = "";
                } else {
                    problem = problem + text;
                }
            }

            userChatMapLock.writeLock().lock();
            if (!userChatMap.containsKey(update.getMessage().getFrom())) {
                sendCustomKeyboard(update.getMessage().getChatId().toString());

                User newUser = update.getMessage().getFrom();
                Chat newChat = update.getMessage().getChat();
                userChatMap.put(newUser, newChat);
                try {
                    FileManagement.writeUser(newUser);
                    FileManagement.writeChat(newChat);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            userChatMapLock.writeLock().unlock();
        }
    }

    /**
     * This method will send a custom keyboard to user
     *
     * @param chatId Unique identifier for this chat
     */
    private void sendCustomKeyboard(String chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Use this custom keyboard");

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();

        // Set each button
        row.add("7");
        row.add("8");
        row.add("9");
        row.add("-");
        keyboard.add(row);

        row = new KeyboardRow();
        row.add("4");
        row.add("5");
        row.add("6");
        row.add("\u00D7");
        keyboard.add(row);

        row = new KeyboardRow();
        row.add("1");
        row.add("2");
        row.add("3");
        row.add("\u00F7"); // Division Sign
        keyboard.add(row);

        row = new KeyboardRow();
        row.add("0");
        row.add(".");
        row.add("+");
        row.add("=");
        keyboard.add(row);

        keyboardMarkup.setKeyboard(keyboard);
        keyboardMarkup.setInputFieldPlaceholder("Write a problem...");
        message.setReplyMarkup(keyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method sends a text message
     *
     * @param text A {@code String} text message
     * @param chatId Unique identifier for this chat
     */
    private void sendText(String text, String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, text);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called after the user sends "=" then calculates the problem
     *
     * @param text A {@code String} math problem
     * @return Solved math problem
     */
    private String calculate(String text) {
        BigDecimal result = new BigDecimal("0");
        char operator = '+';
        int firstIndent = 0;

        for (int i = 0; i < text.length(); i++) {
            if (isOperator(text.charAt(i)) || i + 1 == text.length()) {
                BigDecimal number;

                if (i + 1 == text.length())
                    number = new BigDecimal(text.substring(firstIndent, i + 1));
                else
                    number = new BigDecimal(text.substring(firstIndent, i));

                if (operator == '+')
                    result = result.add(number);
                else if (operator == '-')
                    result = result.subtract(number);
                else if (operator == '\u00D7')
                    result = result.multiply(number);
                else if (operator == '\u00F7')
                    result = result.divide(number, RoundingMode.HALF_UP);

                operator = text.charAt(i);
                firstIndent = i + 1;
            }
        }

        return result.toString();
    }

    /**
     * Checks the parameter if it is math operator or not
     *
     * @param c A character that should be checked
     * @return true or false
     */
    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '\u00F7' || c == '\u00D7';
    }

    /**
     * Username of bot
     */
    @Override
    public String getBotUsername() {
        return "CommonCalculatorBot";
    }

    /**
     * Token
     */
    @Override
    public String getBotToken() {
        return "5242332503:AAEBRjCafdvOYtLvhlZIngaoUt2WRVDhsvE";
    }
}
