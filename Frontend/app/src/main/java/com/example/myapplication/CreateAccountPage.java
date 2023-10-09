// Author: Tristan Nono
package com.example.myapplication;

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
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

/*
The create account page if the user does not have an account.
This is where the user can create their account for the app.
 */
public class CreateAccountPage extends AppCompatActivity {
    /*
    The create account button
     */
    private Button create_account_button;

    /*
    The email that the user will use.
    */
    private EditText username;

    /*
    The email that the user will use.
     */
    private EditText user_email;

    /*
    The password that the user will use.
     */
    private EditText user_password;

    private ImageButton return_login_button;

    private static final String CREATE_ACCOUNT_URL = "http://coms-309-024.class.las.iastate.edu:8080/users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        // Initialization
        create_account_button = findViewById(R.id.create_account_button);
        username = findViewById(R.id.group_description);
        user_email = findViewById(R.id.input_email);
        user_password = findViewById(R.id.input_password);
        create_account_button.setEnabled(false); // Set this to false for checking the inputs of the user.
        return_login_button = findViewById(R.id.return_login_button);

        // Navigate to home page
        return_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateAccountPage.this, Index.class);
                startActivity(intent);
            }
        });

        // This should have user be in the main app after logging in.
        create_account_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call this guy so we create account makes sense yes?
                createAccountRequest();

                Intent intent = new Intent(CreateAccountPage.this, NavBar.class);
                startActivity(intent);
            }
        });

        // This is for the user's name to see if anything is in it or not.
        username.addTextChangedListener(new TextWatcher() {
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

        // This is for the user's email to see if anything is in it or not.
        user_email.addTextChangedListener(new TextWatcher() {
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

    /*
    This is the request for creating an account.
    This POSTs the account on to the server.
     */
    private void createAccountRequest() {
        // Find the values of each field
        EditText input_email = findViewById(R.id.input_email);
        EditText input_username = findViewById(R.id.group_description);
        EditText input_password = findViewById(R.id.input_password);

        String input_email_value = input_email.getText().toString();
        String input_username_value = input_username.getText().toString();
        String input_password_value = input_password.getText().toString();

        // Create JSON object
        JSONObject requestBody = new JSONObject();

        // Puts in the values of these variables. 
        try {
            requestBody.put("email", input_email_value);
            requestBody.put("username", input_username_value);
            requestBody.put("password", input_password_value);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Making the request
        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(
                Request.Method.POST,
                CREATE_ACCOUNT_URL,
                requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Server response", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Server error", "Error: " + error.getMessage());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();

                // Add headers
                headers.put("headername", "headervalue");
                return headers;
            }
        };

        // Add to volley request queue
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectReq);
    }

    /*
   This checks for empty values in the edit text variables so in other words,
   if there's nothing in both the email and password then, it shouldn't go through.
    */
    private void checkFieldsForEmptyValues() {
        String username_login = username.getText().toString();
        String user_email_login = user_email.getText().toString();
        String password = user_password.getText().toString();

        if (!user_email_login.isEmpty() && !password.isEmpty() && !username_login.isEmpty()) {
            create_account_button.setEnabled(true);
        }

        else {
            create_account_button.setEnabled(false);
        }
    }

    /*
    This method is used for the link aka the text that says "Create an account"
    this gives the function of making the text work a like button and should take
    the user to the register page.
    */
    public void onRegisterLinkClick(View view) {
        // For logging checking the button clicks could be deleted later idk.
        Log.d(TAG, "Register Clicked!");
    }
}