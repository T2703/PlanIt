package messages;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import websockets.WebSocketListener;
import websockets.WebSocketManager;

import org.java_websocket.handshake.ServerHandshake;

/*
The message view for the messaging of the app like DMs and GCs (group chats).
 */
public class MessageView extends AppCompatActivity implements WebSocketListener {
    /*
    The url for the messaging.
     */
    private String MESSAGE_URL = "ws://10.0.2.2:8080/chat/";

    /*
    The button for sending message.
     */
    private Button send_button;

    /*
    The button for sending message.
    */
    private Button connect;

    /*
    Where the user can send your message.
     */
    private EditText user_message;

    /*
    This is just a place holder.
    */
    private EditText username_placeholder;

    /*
    This is where the messages appear on this little box.
     */
    private TextView message_appear_screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_view);

        // Initialize! (Music reference)
        send_button = findViewById(R.id.send_button);
        connect = findViewById(R.id.connect_button);
        user_message = findViewById(R.id.user_message);
        username_placeholder = findViewById(R.id.username);
        message_appear_screen = findViewById(R.id.message_tv);

        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // How the message is sent.
                    WebSocketManager.getInstance().sendMessage(user_message.getText().toString());
                }
                catch (Exception e) {
                    Log.d("ExceptionSendMessage:", e.getMessage().toString());
                }
            }
        });

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String serverUrl = MESSAGE_URL + username_placeholder.getText().toString();

                    // Establish WebSocket connection and set listener
                    WebSocketManager.getInstance().connectWebSocket(serverUrl);
                    WebSocketManager.getInstance().setWebSocketListener(MessageView.this);
                }
                catch (Exception e) {
                    Log.d("ExceptionSendMessage:", e.getMessage().toString());
                }
            }
        });
    }

    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {

    }

    @Override
    public void onWebSocketMessage(String message) {
        runOnUiThread(() -> {
            String m = message_appear_screen.getText().toString();
            message_appear_screen.setText(m + "\n" + message);
        });
    }

    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {
        String closed_by = remote ? "server" : "local";
        runOnUiThread(() -> {
            String s = message_appear_screen.getText().toString();
            message_appear_screen.setText(s + "---\nconnection closed by " + closed_by + "\nreason: " + reason);
        });
    }

    @Override
    public void onWebSocketError(Exception ex) {

    }
}