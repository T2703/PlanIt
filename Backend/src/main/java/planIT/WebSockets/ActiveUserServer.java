package planIT.WebSockets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import planIT.Notifications.Notification;
import planIT.Notifications.NotificationRepository;
import planIT.Users.UserRepository;

@ServerEndpoint(value = "/active/{username}")
@Component
public class ActiveUserServer {

    // cannot autowire static directly (instead we do it by the below
    // method
    private static UserRepository userRepository;

    /*
     * Grabs the MessageRepository singleton from the Spring Application
     * Context.  This works because of the @Controller annotation on this
     * class and because the variable is declared as static.
     * There are other ways to set this. However, this approach is
     * easiest.
     */
    @Autowired
    public void setUserRepository(UserRepository repo) {
        userRepository = repo;  // we are setting the static variable
    }

    // Store all socket session and their corresponding username.
    private static Map<Session, String> sessionUsernameMap = new Hashtable<>();
    private static Map<String, Session> usernameSessionMap = new Hashtable<>();

    private Set<String> activeUsers = new HashSet<>();

    private final Logger logger = LoggerFactory.getLogger(ActiveUserServer.class);

    public void addActiveUser(String username) {
        if (!activeUsers.contains(username)) {
            activeUsers.add(username);
            broadcastActiveUsers(activeUsers);
        }
    }

    public void removeActiveUser(String username) {
        if (activeUsers.contains(username)) {
            activeUsers.remove(username);
            broadcastActiveUsers(activeUsers);
        }
    }

    private void broadcastActiveUsers(Set<String> activeUsers) {
        sessionUsernameMap.forEach((session, username) -> {
            try {
                session.getBasicRemote().sendText(activeUsers.toString());
            } catch (IOException e) {
                logger.info("[Broadcast Exception] " + e.getMessage());
            }
        });
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username)
            throws IOException {

        logger.info("Entered into Open");

        addActiveUser(username);

        // store connecting user information
        sessionUsernameMap.put(session, username);
        usernameSessionMap.put(username, session);

        broadcastActiveUsers(activeUsers);
    }

    @OnMessage
    public void onMessage(Session session, String user) throws IOException { }

    @OnClose
    public void onClose(Session session) throws IOException {
        logger.info("Entered into Close");

        // get the user from the user session
        String username = sessionUsernameMap.get(session);

        // remove the user from active users
        removeActiveUser(username);

        // remove the user connection information
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(username);

        // broadcast that the user disconnected
        broadcastActiveUsers(activeUsers);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
        logger.info("Entered into Error");
        throwable.printStackTrace();
    }

}
