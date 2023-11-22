package planIT.WebSockets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

/**
 * WebSocket endpoint for managing active users.
 * This class handles user connections, disconnections, and broadcasts the number of active users.
 *
 * @author Melani Hodge
 *
 */
@ServerEndpoint(value = "/active/{username}")
@Component
public class ActiveUserServer {

    // Store all socket session and their corresponding username.
    private static Map<Session, String> sessionUsernameMap = new Hashtable<>();
    private static Map<String, Session> usernameSessionMap = new Hashtable<>();

    private final Logger logger = LoggerFactory.getLogger(ActiveUserServer.class);

    /**
     * Broadcasts the number of active users to all connected sessions.
     */
    private void broadcastActiveUsers() {
        sessionUsernameMap.forEach((session, username) -> {
            try {
                session.getBasicRemote().sendText("" + usernameSessionMap.size());
            } catch (IOException e) {
                logger.info("[Broadcast Exception] " + e.getMessage());
            }
        });
    }

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

    /**
     * Handles the reception of a WebSocket message.
     *
     * @param session The WebSocket session.
     * @param message The received message.
     * @throws IOException If an I/O error occurs.
     */
    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        broadcastActiveUsers();
    }

    /**
     * Handles the closing of a WebSocket session.
     *
     * @param session The WebSocket session.
     * @throws IOException If an I/O error occurs.
     */
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

    /**
     * Handles WebSocket errors.
     *
     * @param session   The WebSocket session.
     * @param throwable The Throwable representing the error.
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
        logger.info("[OnError]");
        throwable.printStackTrace();
    }

}
