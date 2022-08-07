import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;

/**
 * This class execute the bot
 *
 * @author Moein Arabi
 */
public class Main {
    public static void main(String[] args) {
        try {
            new HerokuHttpServer(Integer.parseInt(args[0])).run();
            
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
