// Author: Tristan Nono
package profile;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.myapplication.Index;
import com.example.myapplication.NavBar;
import com.example.myapplication.R;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import android.widget.Toast;


import api.VolleySingleton;
import homepage.HomePage;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

/**
 * The CreateAccountPage class represents the activity where users can create a new account.
 * It allows users to input a username, email, and password, and then sends a POST request to the server
 * to create a new user account. The class also includes functionality for checking empty values in the input fields.
 *
 * <p>The class includes methods for handling user interactions, such as clicking the create account button,
 * returning to the login page, and checking for empty values in the input fields.
 *
 * <p>This class uses Volley to handle network requests and communicates with a backend server to create user accounts.
 *
 * @author Tristan Nono
 */
public class CreateAccountPage extends AppCompatActivity {
    /**
     * URL Request for the POST.
     */
    private static final String URL_POST_REQUEST = "http://coms-309-024.class.las.iastate.edu:8080/users";

    /**
     * The create account button
     */
    private Button create_account_button;

    /**
     * The user's username that the user will use.
     */
    private EditText user_username;

    /**
     * The email that the user will use.
     */
    private EditText user_email;

    /**
     * The password that the user will use.
     */
    private EditText user_password;

    /**
     * Return login button.
     */
    private ImageButton return_login_button;

    /**
     * Create Account URL for the users.
     */
    private static final String CREATE_ACCOUNT_URL = "http://coms-309-024.class.las.iastate.edu:8080/users";

    /**
     * Initializes the activity, sets up UI components, and retrieves events for the current date.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state, or null if there was none.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        // Initialization
        create_account_button = findViewById(R.id.create_account_button);
        user_username = findViewById(R.id.group_description);
        user_email = findViewById(R.id.input_email);
        user_password = findViewById(R.id.input_password);
        create_account_button.setEnabled(false); // Set this to false for checking the inputs of the user.
        return_login_button = findViewById(R.id.return_login_button);

        // Navigate to home page
        return_login_button.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when the specified View v is clicked. Initiates the creation of an Intent to navigate
             * from the CreateAccountPage to the Index activity and starts the activity.
             *
             * @param v The view that was clicked, triggering the onClick event.
             *          It can be used to identify which view triggered the click event.
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateAccountPage.this, Index.class);
                startActivity(intent);
            }
        });

        // This should have user be in the main app after logging in.
        create_account_button.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when the specified view is clicked. Retrieves the text entered by the user
             * in the username, password, and email input fields, sends a POST request to the server to create a new user account,
             * and initiates the navigation to the LoginFormPage activity.
             *
             * @param view The view that was clicked, triggering the onClick event.
             *             It can be used to identify which view triggered the click event.
             */
            @Override
            public void onClick(View view) {
                String username = user_username.getText().toString();
                String password = user_password.getText().toString();
                String email = user_email.getText().toString();

                sendPostRequest(username, password, email);

                Intent intent = new Intent(CreateAccountPage.this, HomePage.class);
                startActivity(intent);
            }
        });

        // This is for the user's name to see if anything is in it or not.
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

        // This is for the user's email to see if anything is in it or not.
        user_email.addTextChangedListener(new TextWatcher() {
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
     * Sends a POST request to the server to create a new user account.
     *
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     * @param email The email entered by the user.
     */
    private void sendPostRequest(String username, String password, String email) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject body = new JSONObject();

        try {
            body.put("username", username);
            body.put("password", password);
            body.put("email", email);
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
                        Toast.makeText(getApplicationContext(), "New User created", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getApplicationContext(), "POST request failed", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    /**
     * This checks for empty values in the edit text variables so in other words,
     * if there's nothing in both the email and password then, it shouldn't go through.
     */
    private void checkFieldsForEmptyValues() {
        String username_login = user_username.getText().toString();
        String user_email_login = user_email.getText().toString();
        String password = user_password.getText().toString();

        if (!user_email_login.isEmpty() && !password.isEmpty() && !username_login.isEmpty()) {
            create_account_button.setEnabled(true);
        }

        else {
            create_account_button.setEnabled(false);
        }
    }

    /**
     * This method is used for the link aka the text that says "Create an account"
     * this gives the function of making the text work a like button and should take
     * the user to the register page.
     * @param view the view that was clicked on
     */
    public void onRegisterLinkClick(View view) {
        // For logging checking the button clicks could be deleted later idk.
        Log.d(TAG, "Register Clicked!");
    }
}