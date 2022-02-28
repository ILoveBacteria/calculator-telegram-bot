import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class CalculatorBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();

            System.out.println(update.getMessage().getChat().toString());
            System.out.println(text);

            if (text.charAt(0) >= '0' || text.charAt(0) <= '9')
                sendText(calculate(text), update.getMessage().getChatId().toString());

            sendCustomKeyboard(update.getMessage().getChatId().toString());
        }
    }

    private void sendCustomKeyboard(String chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("true");

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

    private void sendText(String text, String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, text);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

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
                    result = result.divide(number);

                operator = text.charAt(i);
                firstIndent = i + 1;
            }
        }

        return result.toString();
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '\u00F7' || c == '\u00D7';
    }

    @Override
    public String getBotUsername() {
        return "CommonCalculatorBot";
    }

    @Override
    public String getBotToken() {
        return "token";
    }
}
