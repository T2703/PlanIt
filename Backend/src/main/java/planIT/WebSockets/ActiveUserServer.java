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

    // Store all socket session and their corresponding username.
    private static Map<Session, String> sessionUsernameMap = new Hashtable<>();
    private static Map<String, Session> usernameSessionMap = new Hashtable<>();

    private final Logger logger = LoggerFactory.getLogger(ActiveUserServer.class);

    private void broadcastActiveUsers() {
        sessionUsernameMap.forEach((session, username) -> {
            try {
                session.getBasicRemote().sendText("" + usernameSessionMap.size());
            } catch (IOException e) {
                logger.info("[Broadcast Exception] " + e.getMessage());
            }
        });
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username)
            throws IOException {

        // server side log
        logger.info("[onOpen] " + username);

        // Handle the case of a duplicate username
        if (usernameSessionMap.containsKey(username)) {
            session.getBasicRemote().sendText("User already logged in.");
            session.close();
        }
        else {
            // map current session with username
            sessionUsernameMap.put(session, username);

            // map current username with session
            usernameSessionMap.put(username, session);
        }

        broadcastActiveUsers();
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        broadcastActiveUsers();
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        // get the username from session-username mapping
        String username = sessionUsernameMap.get(session);

        // server side log
        logger.info("[onClose] " + username);

        // remove user from memory mappings
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(username);

        broadcastActiveUsers();
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
        logger.info("[OnError]");
        throwable.printStackTrace();
    }

}
