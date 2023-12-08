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

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import planIT.Entity.Notifications.Notification;
import planIT.Entity.Notifications.NotificationRepository;
import planIT.Entity.Users.User;
import planIT.Entity.Users.UserRepository;

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

    private static UserRepository userRepository;

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

    @Autowired
    public void setUserRepository(UserRepository repo) {
        userRepository = repo;  // we are setting the static variable
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

        logger.info("[onOpen:Notification]", username);

        // store connecting user information
        sessionUsernameMap.put(session, username);
        usernameSessionMap.put(username, session);

        broadcastNotifications();

    }

    /**
     * Handles the reception of a WebSocket message.
     *
     * @param session      The WebSocket session.
     * @param notifications The received notification.
     */
    @OnMessage
    public void onMessage(Session session, String notifications) throws JSONException {

        String parts[] = notifications.split("   ");

        String usernames[] = parts[0].split(" ");

        for (String username : usernames) {
            logger.info("[onMessage:Notification]" + parts[0]);
            logger.info("[onMessage:Notification]" + parts[1]);
            User user = userRepository.findByUsername(username);
            JSONObject json = new JSONObject(parts[1]);
            Notification notification = new Notification(json.getString("title"), json.getString("description"), "/users/" + username + "/events/" + json.getString("typeId"), Integer.parseInt(json.getString("typeId")));
            notification.setUser(user);
            notificationRepository.save(notification);
        }

        broadcastNotifications();

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

        logger.info("[onClose:Notification]", username);

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
        logger.info("[onError:Notification]");
        throwable.printStackTrace();
    }

    /**
     * Sends a notification to a specific user.
     */
    private void broadcastNotifications() {
        sessionUsernameMap.forEach((session, username) -> {
            try {
                session.getBasicRemote().sendText("notification");
            } catch (IOException e) {
                logger.info("[Broadcast Exception] " + e.getMessage());
            }

        });
    }

}
