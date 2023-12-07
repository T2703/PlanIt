package messages;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import websockets.WebSocketListener;
import websockets.WebSocketManager;
import websockets.WebSocketManagerChat;

import org.java_websocket.handshake.ServerHandshake;

/**
 * The MessageView class represents the message view for the messaging feature of the app,
 * including direct messages (DMs) and group chats (GCs). It provides a user interface for sending
 * and receiving messages through a WebSocket connection.
 *
 * This class implements the WebSocketListener interface to handle WebSocket events.
 *
 * @author Tristan Nono
 */
public class MessageView extends AppCompatActivity implements WebSocketListener {
    /**
     * The url for the messaging.
     */
    private String MESSAGE_URL = "ws://10.0.2.2:8080/chatSocket/" + WebSocketManager.getInstance().getUsername();
    //private String MESSAGE_URL = "ws://10.0.2.2:8080/chat/"; // DEFAULT

    /**
     * The button for sending message.
     */
    private ImageButton send_button;

    /**
     * The button for sending message.
     */
    private Button connect;

    /**
     * Where the user can send your message.
     */
    private EditText user_message;

    /**
     * This is just a place holder.
     */
    private EditText username_placeholder;

    /**
     * This is where the messages appear on this little box.
     */
    private TextView message_appear_screen;

    /**
     * Initializes the MessageView activity, setting up the UI components, and handling user interactions.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state, if available.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_view);

        // Initialize! (Music reference)
        send_button = findViewById(R.id.send_button);
        user_message = findViewById(R.id.user_message);
        message_appear_screen = findViewById(R.id.message_tv);

        String username = WebSocketManager.getInstance().getUsername();
        WebSocketManagerChat.getInstance().connectWebSocket(MESSAGE_URL + username);
        WebSocketManagerChat.getInstance().setWebSocketListener(MessageView.this);

        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // How the message is sent.
                    WebSocketManagerChat.getInstance().sendMessage(user_message.getText().toString());
                }
                catch (Exception e) {
                    Log.d("ExceptionSendMessage:", e.getMessage().toString());
                }
            }
        });

       /* connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String serverUrl = MESSAGE_URL + username_placeholder.getText().toString();

                    // Establish WebSocket connection and set listener
                    WebSocketManagerChat.getInstance().connectWebSocket(serverUrl);
                    WebSocketManagerChat.getInstance().setWebSocketListener(MessageView.this);
                }
                catch (Exception e) {
                    Log.d("ExceptionSendMessage:", e.getMessage().toString());
                }
            }
        }); */
    }

    /**
     * Callback method invoked when the WebSocket connection is opened.
     *
     * @param handshakedata Information about the WebSocket handshake.
     */
    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {

    }

    /**
     * Callback method invoked when a message is received through the WebSocket connection.
     *
     * @param message The received message.
     */
    @Override
    public void onWebSocketMessage(String message) {
        runOnUiThread(() -> {
            String m = message_appear_screen.getText().toString();
            message_appear_screen.setText(m + "\n" + message);
        });
    }

    /**
     * Callback method invoked when the WebSocket connection is closed.
     *
     * @param code   The status code of the closure.
     * @param reason A description of the reason for the closure.
     * @param remote Indicates whether the connection was closed remotely.
     */
    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {
        String closed_by = remote ? "server" : "local";
        runOnUiThread(() -> {
            String s = message_appear_screen.getText().toString();
            message_appear_screen.setText(s + "---\nconnection closed by " + closed_by + "\nreason: " + reason);
        });
    }

    /**
     * Callback method invoked when an error occurs in the WebSocket connection.
     *
     * @param ex The exception representing the error.
     */
    @Override
    public void onWebSocketError(Exception ex) {

    }
}