import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class CalculatorBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            System.out.println(update.getMessage().getChat().toString());
            System.out.println(update.getMessage().getText());

            sendCustomKeyboard(update.getMessage().getChatId().toString());
        }
    }

    public void sendCustomKeyboard(String chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Custom message text");

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
        message.setReplyMarkup(keyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendText(String text, String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, text);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
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
