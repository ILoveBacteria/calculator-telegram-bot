import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

/**
 * The Command class contains bot commands.
 *
 * @author Moein Arabi
 * @version 1.0.0
 */
public class Command {
    private static final String START = "/start";
    private static final String HELP = "/help";

    /**
     * This method takes a command and executes it
     *
     * @param command A {@code String} command: "/start"
     * @return A prepared {@link org.telegram.telegrambots.meta.api.methods.send.SendMessage SendMessage} object with text
     */
    public static SendMessage doAction(String command) {
        if (command == null) {
            return null;
        }

        SendMessage sendMessage = new SendMessage();

        switch (command) {
            case START:
                sendMessage.setText("Welcome to Calculator bot");
                break;
            case HELP:
                sendMessage.setText("This calculator performs simple mathematical operations.\n" +
                        "To input numbers use the custom keyboard.\nDon't try to test this bot, " +
                        "it is baby and can't handle all exceptions!");
                break;
            default:
                sendMessage = new SendMessage();
                sendMessage.setText("Invalid command!");
        }

        return sendMessage;
    }
}
