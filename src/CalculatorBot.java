import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CalculatorBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        Scanner scanner = new Scanner(System.in);
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            System.out.println(update.getMessage().getChat().toString());
            SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
            message.setChatId(update.getMessage().getChatId().toString());
            System.out.print("enter message: ");
            message.setText(scanner.next());

            try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
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
        row.add("+");
        keyboard.add(row);

        row = new KeyboardRow();
        row.add("1");
        row.add("2");
        row.add("3");
        row.add("=");
        keyboard.add(row);

        // Set the keyboard to the markup
        keyboardMarkup.setKeyboard(keyboard);
        // Add it to the message
        message.setReplyMarkup(keyboardMarkup);

        try {
            // Send the message
            execute(message);
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
