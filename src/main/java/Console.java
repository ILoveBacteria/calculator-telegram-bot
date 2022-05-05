import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.locks.ReadWriteLock;

public class Console implements Runnable {
    private final Map<User, Chat> userChatMap;
    private final ReadWriteLock userChatMapLock;

    public Console(Map<User, Chat> userChatMap, ReadWriteLock userChatMapLock) {
        this.userChatMap = userChatMap;
        this.userChatMapLock = userChatMapLock;
    }

    @Override
    public void run() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String command = scanner.nextLine();

            if (command.equalsIgnoreCase("show users")) {
                userChatMapLock.readLock().lock();
                for (User u : userChatMap.keySet()) {
                    System.out.println(u);
                }
                userChatMapLock.readLock().unlock();
            }
        }
    }
}
