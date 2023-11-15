package planIT.WebSockets;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import planIT.Entity.Notifications.Notification;
import planIT.Entity.Notifications.NotificationRepository;

/**
 * WebSocket endpoint for handling user notifications.
 * This class manages the WebSocket connections, processes incoming notifications,
 * and interacts with the NotificationRepository to store chat history.
 *
 * @author Melani Hodge
 *
 */
@ServerEndpoint(value = "/notification/{username}")
@Component
public class NotificationServer {

    // cannot autowire static directly (instead we do it by the below
    // method
    private static NotificationRepository notificationRepository;

    /**
     * Sets the {@link NotificationRepository} for the NotificationServer.
     *
     * <p>This method is annotated with {@code @Autowired} to enable Spring to inject
     * the NotificationRepository bean. It sets the static {@code notificationRepository}
     * variable, allowing the NotificationServer to interact with the repository.</p>
     *
     * @param repo The NotificationRepository bean to be injected.
     * @see NotificationRepository
     */
    @Autowired
    public void setNotificationRepository(NotificationRepository repo) {
        notificationRepository = repo;  // we are setting the static variable
    }

    // Store all socket session and their corresponding username.
    private static Map<Session, String> sessionUsernameMap = new Hashtable<>();
    private static Map<String, Session> usernameSessionMap = new Hashtable<>();

    private final Logger logger = LoggerFactory.getLogger(NotificationServer.class);

    /**
     * Handles the opening of a WebSocket session.
     *
     * @param session  The WebSocket session.
     * @param username The username obtained from the path parameter.
     * @throws IOException If an I/O error occurs.
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username)
            throws IOException {

        logger.info("[onOpen]", username);

        // store connecting user information
        sessionUsernameMap.put(session, username);
        usernameSessionMap.put(username, session);
    }

    /**
     * Handles the reception of a WebSocket message.
     *
     * @param session      The WebSocket session.
     * @param notification The received notification.
     * @throws IOException If an I/O error occurs.
     */
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

    /**
     * Handles the closing of a WebSocket session.
     *
     * @param session The WebSocket session.
     * @throws IOException If an I/O error occurs.
     */
    @OnClose
    public void onClose(Session session) throws IOException {
        String username = sessionUsernameMap.get(session);

        logger.info("[onClose]", username);

        // remove the user connection information
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(username);
    }

    /**
     * Handles WebSocket errors.
     *
     * @param session   The WebSocket session.
     * @param throwable The Throwable representing the error.
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
        logger.info("[onError]");
        throwable.printStackTrace();
    }

    /**
     * Sends a notification to a specific user.
     *
     * @param username The username of the recipient.
     * @param message  The notification message.
     */
    private void sendNotification(String username, String message) {
        try {
            usernameSessionMap.get(username).getBasicRemote().sendText(message);
        } catch (IOException e) {
            logger.info("[DM Exception] " + e.getMessage());
        }
    }


    /**
     * Broadcasts a message to all connected users.
     *
     * @param message The message to broadcast.
     */
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


    /**
     * Gets the notification history from the repository.
     *
     * @return A string representing the notification history.
     */
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
