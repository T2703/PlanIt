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

import homepage.User;
import profile.UserManager;
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

/**
 * The LoginFormPage class represents the activity where users can log in to the application.
 * It allows users to input their username and password, sends a POST request to the server for authentication,
 * and navigates to the HomePage upon successful login.
 *
 * <p>The class includes methods for handling user interactions, such as clicking the create account button,
 * logging in, checking for empty values in the input fields, and sending a POST request for authentication.
 *
 * <p>This class uses Volley for network requests and interacts with a backend server for user authentication.
 *
 * @author Tristan Nono
 */
public class LoginFormPage extends AppCompatActivity {
    /**
     * The URL for the POST request to authenticate user login.
     */
    private static final String URL_POST_REQUEST = "http://coms-309-024.class.las.iastate.edu:8080/login";

    /**
     * WebSocket manager instance.
     */
    private WebSocketManager webSocketManager;

    /**
     * Create account button.
     */
    private TextView create_account_button;

    /**
     * Login button.
     */
    private Button login_button;

    /**
     * The email that the user will use for logging in.
     */
    private EditText user_username;

    /**
     * The password that the user will use.
     */
    private EditText user_password;
    private String userID;

    /**
     * Called when the activity is first created. Responsible for initializing the activity,
     * setting up UI components, and defining event listeners for user interactions.
     *
     * <p>This method is part of the Android Activity lifecycle and is invoked when the activity is launched.
     * It is used to perform one-time setup tasks, such as inflating the layout, initializing variables,
     * and setting up event listeners for user interactions.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state,
     *                           or null if no saved state exists. This parameter is used to restore the activity's state
     *                           when it is recreated after being destroyed. If the activity has never existed
     *                           or has been explicitly set to null, this parameter is null.
     */
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
            /**
             * Called when the specified {@code v} is clicked. Initiates the creation of an Intent to navigate
             * from the LoginFormPage to the CreateAccountPage activity and starts the activity.
             *
             * @param v The view that was clicked, triggering the onClick event.
             *          It can be used to identify which view triggered the click event.
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginFormPage.this, CreateAccountPage.class);
                startActivity(intent);
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when the specified {@code v} is clicked. Retrieves the text entered by the user
             * in the username and password input fields, sends a POST request to the server for authentication,
             * and sets the username in the WebSocketManager instance.
             *
             * @param v The view that was clicked, triggering the onClick event.
             *          It can be used to identify which view triggered the click event.
             */
            @Override
            public void onClick(View v) {
                String username = user_username.getText().toString();
                String password = user_password.getText().toString();

                sendPostRequest(username, password);

                WebSocketManager.getInstance().setUsername(username);
            }
        });

        // This is for the user's email to see if anything is in it or not.
        user_username.addTextChangedListener(new TextWatcher() {
            /**
             * Called to notify you that the characters within {@code charSequence} are about to be replaced
             * with new text with a specified range.
             *
             * @param charSequence The sequence of characters before the change.
             * @param i The starting position of the characters to be replaced.
             * @param i1 The number of characters to be replaced.
             * @param i2 The length of the new text that will replace the characters.
             */
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            /**
             * Called to notify you that somewhere within {@code charSequence}, the text has been changed.
             * It is legitimate to make further changes to {@code charSequence} from this callback.
             *
             * @param charSequence The sequence of characters after the change.
             * @param i The starting position of the changed text.
             * @param i1 The length of the text that has been changed.
             * @param i2 The length of the new text that has replaced the old text.
             */
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkFieldsForEmptyValues();
            }

            /**
             * Called to notify you that somewhere within {@code editable}, the text has been changed.
             * It is legitimate to make further changes to {@code editable} from this callback.
             *
             * @param editable The Editable containing the modified text.
             */
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        // This is for the user's password to see if anything is in it or not.
        user_password.addTextChangedListener(new TextWatcher() {
            /**
             * Called to notify you that the characters within {@code charSequence} are about to be replaced
             * with new text with a specified range.
             *
             * @param charSequence The sequence of characters before the change.
             * @param i The starting position of the characters to be replaced.
             * @param i1 The number of characters to be replaced.
             * @param i2 The length of the new text that will replace the characters.
             */
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            /**
             * Called to notify you that somewhere within {@code charSequence}, the text has been changed.
             * It is legitimate to make further changes to {@code charSequence} from this callback.
             *
             * @param charSequence The sequence of characters after the change.
             * @param i The starting position of the changed text.
             * @param i1 The length of the text that has been changed.
             * @param i2 The length of the new text that has replaced the old text.
             */
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkFieldsForEmptyValues();
            }

            /**
             * Called to notify you that somewhere within {@code editable}, the text has been changed.
             * It is legitimate to make further changes to {@code editable} from this callback.
             *
             * @param editable The Editable containing the modified text.
             */
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    /**
     * Sends a POST request to the server to authenticate user login.
     *
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     */
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
                    /**
                     * Callback method that is invoked when a network request succeeds and returns a response.
                     *
                     * @param response The response received from the network request.
                     *                 It is expected to be a JSON string representing an array.
                     * @throws RuntimeException If there is an error parsing the response as a JSON array.
                     */
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String userId = response.getString("id");

                            UserManager userManager = UserManager.getInstance();
                            userManager.setUserID(userId);

                            Intent intent = new Intent(LoginFormPage.this, HomePage.class);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error parsing server response", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    /**
                     * Callback method that is invoked when a network request encounters an error.
                     *
                     * @param error The VolleyError object containing information about the error.
                     *              This can include details such as the error message, network response, and more.
                     *              It can be used for debugging and handling specific error scenarios.
                     */
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Username or password is incorrect.", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    /**
     * Checks for empty values in the input fields, enabling or disabling the login button accordingly.
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

    public String getUserID() { return userID; }

    public void setUserID(String userID) {
        this.userID = userID;
    }

}
