import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.telegram.telegrambots.meta.TelegramBotsApi;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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
            CalculatorBot bot = new CalculatorBot();

            Thread console = new Thread(new Console(bot));
            console.start();

            botsApi.registerBot(bot);
        } catch (TelegramApiException | IOException e) {
            e.printStackTrace();
        }
    }
}
