import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class Command {
    private static final String START = "/start";
    private static final String HELP = "/help";

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
