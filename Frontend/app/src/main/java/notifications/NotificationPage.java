package notifications;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;

import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import homepage.HomePage;
import websockets.WebSocketListener;
import websockets.WebSocketManager;

/**
 * The {@code NotificationPage} class represents the activity for displaying a list of notifications.
 * It extends the AppCompatActivity and provides functionality for handling notifications,
 * including selecting all notifications and navigating back to the home page.
 * <p>
 * The activity uses a RecyclerView to display a list of notifications, and it utilizes the
 * {@code NotificationAdapter} class to manage the adapter for the RecyclerView.
 * </p>
 *
 * @author Joshua Gutierrez
 * @version 1.0
 * @see AppCompatActivity
 * @see NotificationAdapter
 * @see Notification
 */
public class NotificationPage extends AppCompatActivity implements WebSocketListener {
    private ImageButton backButton;
    private RecyclerView notificationItemRecycler;
    private NotificationAdapter adapter;

    /**
     * Called when the activity is starting. This is where most initialization should go:
     * calling {@code setContentView(int)} to inflate the activity's UI, using
     * {@code findViewById(int)} to programmatically interact with widgets in the UI,
     * and configuring the activity's behavior as needed.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                           shut down, then this Bundle contains the data it most recently
     *                           supplied in {@link #onSaveInstanceState}. Note: Otherwise, it is
     *                           null.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_page);

        backButton = findViewById(R.id.back_button);

        notificationItemRecycler = findViewById(R.id.notificationsRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        notificationItemRecycler.setLayoutManager(layoutManager);

        List<Notification> notificationsList = createNotificationList();
        adapter = new NotificationAdapter(notificationsList);
        notificationItemRecycler.setAdapter(adapter);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationPage.this, HomePage.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Creates a list of sample notifications for testing purposes.
     *
     * @return A list of {@code Notification} objects.
     */
    private List<Notification> createNotificationList() {
        List<Notification> notifications = new ArrayList<>();
        final String BASE_URL = "http://coms-309-024.class.las.iastate.edu:8080/users/";

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String username = WebSocketManager.getInstance().getUsername();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, BASE_URL + username + "/notifications", null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);

                                notifications.add(new Notification(jsonObject.getString("title"), jsonObject.getString("description")));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        adapter.notifyDataSetChanged();
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("An error occurred while getting notifications", error.toString());
            }
        });

        requestQueue.add(jsonArrayRequest);

        return notifications;
    }

    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {

    }

    @Override
    public void onWebSocketMessage(String message) {

    }

    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onWebSocketError(Exception ex) {

    }
}
