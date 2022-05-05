import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.telegram.telegrambots.meta.TelegramBotsApi;

import java.io.IOException;

/**
 * This class execute the bot
 *
 * @author Moein Arabi
 * @version 1.0.0
 */
public class Main {
    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new CalculatorBot(FileManagement.readUserAndChat()));
        } catch (TelegramApiException | IOException e) {
            e.printStackTrace();
        }
    }
}
