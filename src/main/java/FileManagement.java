import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * Saves and loads users and their chat id in files
 */
public class FileManagement {
    private static final Path PATH_USERS = Paths.get("users.csv");
    private static final Path PATH_CHATS = Paths.get("chats.csv");
    
    /**
     * Writes a {@link User} in file
     * @param user The user that must be written in file
     * @throws IOException if any problems occur in writing to file
     */
    public static void writeUser(User user) throws IOException {
        StringBuilder str = new StringBuilder();
        str.append(user.getId()).append(",").
                append(user.getIsBot()).append(",").
                append(user.getFirstName()).append(",").
                append(user.getLastName()).append(",").
                append(user.getUserName()).append(",").
                append(user.getLanguageCode()).append(",").
                append(user.getCanJoinGroups()).append(",").
                append(user.getCanReadAllGroupMessages()).append(",").
                append(user.getSupportInlineQueries()).
                append("\n");

        Files.writeString(PATH_USERS, str, StandardCharsets.UTF_8, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
    }
    
    /**
     * Reads all users from file
     * @return {@link  List} of users
     * @throws IOException if any problems occur in reading from file
     */
    private static List<User> readUsers() throws IOException {
        List<User> users = new ArrayList<>();

        if (Files.notExists(PATH_USERS))
            return users;

        List<String> stringList = Files.readAllLines(PATH_USERS, StandardCharsets.UTF_8);
        for (String s : stringList) {
            String[] data = s.split(",");
            User user = new User(Long.parseLong(data[0]), data[2], Boolean.parseBoolean(data[1]), data[3], data[4],
                    data[5], Boolean.parseBoolean(data[6]), Boolean.parseBoolean(data[7]), Boolean.parseBoolean(data[8]));

            users.add(user);
        }

        return users;
    }
    
    /**
     * Reads all chats from file
     * @return {@link  List} of chats
     * @throws IOException if any problems occur in reading from file
     */
    private static List<Chat> readChats() throws IOException {
        List<Chat> chats = new ArrayList<>();

        if (Files.notExists(PATH_CHATS))
            return chats;

        List<String> stringList = Files.readAllLines(PATH_CHATS, StandardCharsets.UTF_8);
        for (String s : stringList) {
            String[] data = s.split(",");
            Chat newChat = new Chat(Long.parseLong(data[0]), data[1]);
            chats.add(newChat);
        }

        return chats;
    }
    
    /**
     * Writes a {@link Chat} in file
     * @param chat The chat that must be written in file
     * @throws IOException if any problems occur in writing to file
     */
    public static void writeChat(Chat chat) throws IOException {
        StringBuilder str = new StringBuilder();
        str.append(chat.getId()).append(",").
                append(chat.getType()).
                append("\n");

        Files.writeString(PATH_CHATS, str, StandardCharsets.UTF_8, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
    }
    
    /**
     * Reads both users and chats
     * @return {@link Map} of user and chat
     * @throws IOException if any problems occur in reading from file
     */
    public static Map<User, Chat> readUserAndChat() throws IOException {
        List<User> users = readUsers();
        List<Chat> chats = readChats();
        Map<User, Chat> userChatMap = new HashMap<>();

        for (int i = 0; i < users.size(); i++) {
            userChatMap.put(users.get(i), chats.get(i));
        }

        return userChatMap;
    }
}
