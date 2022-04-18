import org.telegram.telegrambots.meta.api.objects.MessageEntity;

import java.util.List;

public class MessageEntities {
    public static String getCommand(List<MessageEntity> entities) {
        for (MessageEntity m : entities) {
            if (m.getType().equals("bot_command")) {
                return m.getText();
            }
        }

        return null;
    }

    public static boolean hasCommand(List<MessageEntity> entities) {
        return getCommand(entities) != null;
    }
}
