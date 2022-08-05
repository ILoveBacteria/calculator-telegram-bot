import org.telegram.telegrambots.meta.api.objects.MessageEntity;

import java.util.List;

/**
 * This class manages using with {@link MessageEntity}
 */
public class MessageEntities {
    /**
     * Finds a specific command in the list
     * @param entities The list of message entities
     * @return A bot command if present else null
     */
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
