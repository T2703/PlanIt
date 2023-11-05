package websockets;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/*
Manages the websocket connections in the app.
Makes sure that there is one websocket running in the app's lifecycle.
 */
public class WebSocketManager {
    /*
    The instance of the websocket manager.
     */
    private static WebSocketManager manager_instance;

    /*
    The custom web socket.
    mywebsocket.wad.
     */
    private MyWebSocketClient web_socket_client;

    /*
    The listener for the websocket.
     */
    private WebSocketListener web_socket_listener;

    private boolean isConnected = false;

    private String username;


    /*
    Yeah, this is empty.
     */
    private WebSocketManager() {}

    /*
    This retrieves a synchronized instance of the websocket.
    Makes sure that only ONE instance is exists.
     */
    public static synchronized WebSocketManager getInstance() {
        if (manager_instance == null) {
            manager_instance = new WebSocketManager();
        }
        return manager_instance;
    }

    public String getUsername () {
        return username;
    }

    public void setUsername (String username) {
        this.username = username;
    }

    /*
    This handles the events like the messages and errors.
     */
    public void setWebSocketListener(WebSocketListener listener) {
        this.web_socket_listener = listener;
    }

    public void removeWebSocketListener() {
        this.web_socket_listener = null;
    }


    /*
    Connects a WebSocket connection to the server. So, in a nutshell it just makes
    the connection.
     */
    public void connectWebSocket(String server_url) {
        if (!isConnected) {
            try {
                URI serverUri = URI.create(server_url);
                web_socket_client = new MyWebSocketClient(serverUri);
                web_socket_client.connect();
                isConnected = true;
            } catch (Exception e) {
                e.printStackTrace();
                isConnected = false;
            }
        }
    }

    /*
    Sends the message to the connected the websocket server.
     */
    public void sendMessage(String message) {
        if (web_socket_client != null && web_socket_client.isOpen()) {
            web_socket_client.send(message);
        }
    }

    /*
    As stated in the name this disconnects the web socket connection.
     */
    public void disconnectWebSocket() {
        if (web_socket_client != null) {
            web_socket_client.close();
            isConnected = false;
        }
    }

    /*
    Custom client which handles the functionality within the WebSocketManager.
     */
    private class MyWebSocketClient extends WebSocketClient {

        public MyWebSocketClient(URI serverUri) {
            super(serverUri);
        }

        @Override
        public void onOpen(ServerHandshake handshakedata) {
            Log.d("WebSocket", "Connected");
            if (web_socket_listener != null) {
                web_socket_listener.onWebSocketOpen(handshakedata);
            }
        }

        @Override
        public void onMessage(String message) {
            Log.d("WebSocket", "Received message: " + message);
            if (web_socket_listener != null) {
                web_socket_listener.onWebSocketMessage(message);
            }
        }

        @Override
        public void onClose(int code, String reason, boolean remote) {
            Log.d("WebSocket", "Closed");
            if (web_socket_listener != null) {
                web_socket_listener.onWebSocketClose(code, reason, remote);
            }
        }

        @Override
        public void onError(Exception ex) {
            Log.d("WebSocket", "Error");
            if (web_socket_listener != null) {
                web_socket_listener.onWebSocketError(ex);
            }
        }
    }
}

