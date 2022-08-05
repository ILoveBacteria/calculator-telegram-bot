import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Scanner;

/**
 * This class provides commands to manage the bot
 * show users: Prints all users that have used the bot so far
 * send message: Manually sends a message to a specific user
 */
public class Console implements Runnable {
    private final CalculatorBot bot;

    public Console(CalculatorBot bot) {
        this.bot = bot;
    }

    @Override
    public void run() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String command = scanner.nextLine();

            if (command.equalsIgnoreCase("show users")) {
                bot.getUserChatMapLock().readLock().lock();
                for (User u : bot.getUserChatMap().keySet()) {
                    System.out.println(u);
                }
                bot.getUserChatMapLock().readLock().unlock();

            } else if (command.startsWith("send message")) {
                String[] data = command.split(" ", 4);

                if (data.length <= 3) {
                    System.err.println("No id or message provided!");

                } else {
                    User user = findUserById(Long.valueOf(data[2]));

                    if (user == null) {
                        System.err.println("No such user found!");

                    } else {
                        bot.getUserChatMapLock().readLock().lock();
                        bot.sendText(data[3], String.valueOf(bot.getUserChatMap().get(user).getId()));
                        bot.getUserChatMapLock().readLock().unlock();
                    }
                }
            }
        }
    }

    private User findUserById(Long id) {
        bot.getUserChatMapLock().readLock().lock();

        try {
            for (User u : bot.getUserChatMap().keySet()) {
                if (u.getId().equals(id))
                    return u;
            }
            return null;

        } finally {
            bot.getUserChatMapLock().readLock().unlock();
        }
    }
}
