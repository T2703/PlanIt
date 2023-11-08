package planIT.WebSockets;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import planIT.Entity.Notifications.Notification;
import planIT.Entity.Notifications.NotificationRepository;

@ServerEndpoint(value = "/notification/{username}")
@Component
public class NotificationServer {

    // cannot autowire static directly (instead we do it by the below
    // method
    private static NotificationRepository notificationRepository;

    /*
     * Grabs the MessageRepository singleton from the Spring Application
     * Context.  This works because of the @Controller annotation on this
     * class and because the variable is declared as static.
     * There are other ways to set this. However, this approach is
     * easiest.
     */
    @Autowired
    public void setNotificationRepository(NotificationRepository repo) {
        notificationRepository = repo;  // we are setting the static variable
    }

    // Store all socket session and their corresponding username.
    private static Map<Session, String> sessionUsernameMap = new Hashtable<>();
    private static Map<String, Session> usernameSessionMap = new Hashtable<>();

    private final Logger logger = LoggerFactory.getLogger(NotificationServer.class);

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username)
            throws IOException {

        logger.info("[onOpen]", username);

        // store connecting user information
        sessionUsernameMap.put(session, username);
        usernameSessionMap.put(username, session);
    }

    @OnMessage
    public void onMessage(Session session, String notification) throws IOException {

        // Handle new messages
        logger.info("Entered into Notification: Got Notification:" + notification);
        String username = sessionUsernameMap.get(session);
        String[] words = notification.split(" ");

        if (notification.startsWith("@")) {
            for (String word : words) {
                if (word.startsWith("@")) {
                    word.split("@");
                    sendNotification(word, notification);
                }
            }
        }

        // Sending Invite Notifications
        if (notification.startsWith("INVITE")) {
            for (String word : words) {
                if (word.startsWith("@")) {
                    word.split("@");
                    sendNotification(word, "Here is your notification.");
                }
            }
        }

        else if (notification.startsWith("WELCOME")) {
            sendNotification(username, "Welcome to PlanIT, " + username + "!");
            broadcast(username + ": " + "Welcome to the application.");
        }

        else if (notification.startsWith("MESSAGE")) {
            for (String word : words) {
                if (word.startsWith("@")) {
                    word.split("@");
                    sendNotification(word, "Here is your message.");
                }
            }
        }

        // Saving chat history to repository
        notificationRepository.save(new Notification("title", "description"));
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        String username = sessionUsernameMap.get(session);

        logger.info("[onClose]", username);

        // remove the user connection information
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(username);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
        logger.info("[onError]");
        throwable.printStackTrace();
    }


    private void sendNotification(String username, String message) {
        try {
            usernameSessionMap.get(username).getBasicRemote().sendText(message);
        } catch (IOException e) {
            logger.info("[DM Exception] " + e.getMessage());
        }
    }


    private void broadcast(String message) {
        sessionUsernameMap.forEach((session, username) -> {
            try {
                session.getBasicRemote().sendText(message);
            }
            catch (IOException e) {
                logger.info("Exception: " + e.getMessage().toString());
                e.printStackTrace();
            }

        });

    }


    // Gets the Chat history from the repository
    private String getNotificationHistory() {
        List<Notification> notifications = notificationRepository.findAll();

        // convert the list to a string
        StringBuilder sb = new StringBuilder();
        if(notifications != null && notifications.size() != 0) {
            for (Notification notification : notifications) {
                sb.append(notification.getTitle() + ": " + notification.getDescription() + "\n");
            }
        }
        return sb.toString();
    }
}
