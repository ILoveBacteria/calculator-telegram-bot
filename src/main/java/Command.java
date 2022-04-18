import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class Command {
    private static final String START = "/start";

    public static SendMessage doAction(String command) {
        if (command == null) {
            return null;
        }

        SendMessage sendMessage;

        switch (command) {
            case START:
                sendMessage = new SendMessage();
                sendMessage.setText("Welcome to Calculator bot");
                break;
            default:
                sendMessage = new SendMessage();
                sendMessage.setText("Invalid command!");
        }

        return sendMessage;
    }
}
