package profile;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;

import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import homepage.HomePage;
import websockets.WebSocketListener;
import websockets.WebSocketManager;

public class LoginFormPage extends AppCompatActivity implements WebSocketListener {

    private static final String URL_POST_REQUEST = "http://coms-309-024.class.las.iastate.edu:8080/login";

    private static final String URL_ACTIVE_WEBSOCKET = "ws://coms-309-024.class.las.iastate.edu:8080/active/";

    private WebSocketManager webSocketManager;

    private TextView create_account_button;
    private Button login_button;

    /*
    The email that the user will use for logging in.
     */
    private EditText user_username;

    /*
    The password that the user will use.
     */
    private EditText user_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_form);

        create_account_button = findViewById(R.id.create_account_button);
        login_button = findViewById(R.id.login_button);
        login_button.setEnabled(false); // Set this to false for checking the inputs of the user.
        user_username = findViewById(R.id.login_username_field);
        user_password = findViewById(R.id.login_password_field);

        create_account_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginFormPage.this, CreateAccountPage.class);
                startActivity(intent);
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = user_username.getText().toString();
                String password = user_password.getText().toString();

                sendPostRequest(username, password);

                WebSocketManager.getInstance().connectWebSocket(URL_ACTIVE_WEBSOCKET + username);
                WebSocketManager.getInstance().setWebSocketListener(LoginFormPage.this);
            }
        });

        // This is for the user's email to see if anything is in it or not.
        user_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkFieldsForEmptyValues();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        // This is for the user's password to see if anything is in it or not.
        user_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkFieldsForEmptyValues();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void sendPostRequest(String username, String password) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject body = new JSONObject();

        try {
            body.put("username", username);
            body.put("password", password);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Make the request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                URL_POST_REQUEST,
                body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Intent intent = new Intent(LoginFormPage.this, HomePage.class);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Username or password is incorrect.", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onWebSocketOpen(ServerHandshake data) {
    }

    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {
    }

    @Override
    public void onWebSocketMessage(String message) {
        // TODO send notifications to the user through the web socket.
    }

    @Override
    public void onWebSocketError(Exception ex) {}

    /*
    This checks for empty values in the edit text variables so in other words,
    if there's nothing in both the email and password then, it shouldn't go through.
    */
    private void checkFieldsForEmptyValues() {
        String user_email_login = user_username.getText().toString();
        String password = user_password.getText().toString();

        if (!user_email_login.isEmpty() && !password.isEmpty()) {
            login_button.setEnabled(true);
        }

        else {
            login_button.setEnabled(false);
        }
    }


}
