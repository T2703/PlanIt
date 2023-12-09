package profile;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.NavBarView;
import com.example.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import api.VolleySingleton;
import calendar.CalendarMonthlyPage;
import events.CreateEventPage;
import groups.MemberViewer;
import homepage.HomePage;
import websockets.WebSocketManager;

/**
 * This is the page where the user can view their own profile info.
 *
 * @author Tristan Nono
 */
public class ProfilePageViewer extends AppCompatActivity implements NavBarView.OnButtonClickListener {
    /**
     * The button for going to the edit page.
     */
    private Button edit_button;

    /**
     * Settings button for going into the settings page.
     */
    private ImageButton settings_button;

    /**
     * The image view for pulling up the profile pics.
     */
    private ImageView profile_image_view;

    /**
     * It's our navbar. Once again.
     */
    private NavBarView navbar_view;

    /**
     * This is for the transitioning between pages.
     */
    private ActivityOptions options;

    /*
    The variables for editing the profile info.
     */
    private TextView name, userEmail;
    private String ID;

    private String username;

    /*
    The image loader.
     */
    private static final int RESULT_LOAD_IMG = 1;

    private static final String URL_USERS = "http://coms-309-024.class.las.iastate.edu:8080/users/";

    /**
     * The URL for the POST request to authenticate user login.
     */
    private static final String URL_POST_REQUEST = "http://coms-309-024.class.las.iastate.edu:8080/login";

    /**
     * Initializes the activity, sets up UI components.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state, or null if there was none.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page_viewer);

        // Initialize
        profile_image_view = findViewById(R.id.profileImageView);
        edit_button = findViewById(R.id.editButton);
        name = findViewById(R.id.nameText);
        userEmail = findViewById(R.id.emailText);
        settings_button = findViewById(R.id.gear5);
        navbar_view = findViewById(R.id.navbar);
        navbar_view.setOnButtonClickListener(this);
        UserManager userManager = UserManager.getInstance();
        ID = userManager.getUserID();

        username = WebSocketManager.getInstance().getUsername();
        name.setText(WebSocketManager.getInstance().getUsername());

        navbar_view.setSelectedButton(navbar_view.getProfileButton());

        // Load the default square profile picture
        profile_image_view.setImageResource(R.drawable.default_profile_image);
        // Apply the circular mask
        profile_image_view.setBackgroundResource(R.drawable.circular_background);

        getUserInfoRequest(ID);

        settings_button.setOnClickListener(new View.OnClickListener() {
            /**
             * Handles the click event for navigating to the Settings page. This method is invoked when the user clicks
             * the settings button. It creates an Intent to start the Settings activity and applies custom animations
             * for a smooth transition.
             *
             * @param view The View that was clicked (in this case, the settings button).
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfilePageViewer.this, Settings.class);
                ActivityOptions options = ActivityOptions.makeCustomAnimation(ProfilePageViewer.this, R.anim.empty_anim, R.anim.empty_anim);
                startActivity(intent, options.toBundle());
            }
        });

        edit_button.setOnClickListener(new View.OnClickListener() {
            /**
             * Handles the click event for navigating to the Settings page. This method is invoked when the user clicks
             * the settings button. It creates an Intent to start the Settings activity and applies custom animations
             * for a smooth tFransition.
             *
             * @param view The View that was clicked (in this case, the settings button).
             */
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfilePageViewer.this);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.password_dialog, null);
                builder.setView(dialogView);

                EditText passwordEditText = dialogView.findViewById(R.id.passwordEditText);
                Button confirmButton = dialogView.findViewById(R.id.confirmButton);

                final AlertDialog dialog = builder.create();
                dialog.show();

                // Handle the click event for the confirm button in the dialog
                confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String enteredPassword = passwordEditText.getText().toString();

                        sendPostRequest(username, enteredPassword);

                        //dialog.dismiss();
                    }
                });
            }
        });
    }

    /**
     * Handles the result of the image picker activity. It is invoked when the user selects an image
     * from the gallery or another source. The method checks if the requestCode matches the constant RESULT_LOAD_IMG,
     * the resultCode is RESULT_OK, and the data is not null. If these conditions are met, it retrieves
     * the selected image URI and loads and displays the image in the ImageView.
     *
     * <p>This method is part of the Android Activity lifecycle and is called when an activity launched with
     * {@link #startActivityForResult(Intent, int)} finishes and returns a result. In this case, it is used to handle
     * the result of the image picker activity.
     *
     * @param requestCode An integer code that represents the request code passed to {@link #startActivityForResult(Intent, int)}.
     * @param resultCode An integer result code returned by the child activity.
     * @param data An Intent containing the result data. It may include various data depending on the activity.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Checks if the requestCode matches the constant RESULT_LOAD_IMG the resultCode is RESULT_OK, and the data is not null.
        if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && data != null) {
            // Get the selected image URI
            Uri selectedImageUri = data.getData();

            try {
                // Load and display the selected image in the ImageView
                profile_image_view.setImageURI(selectedImageUri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /*
    How to retreive the user info for this page.
     */
    private void getUserInfoRequest(String userID) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL_USERS + userID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            // Extract user information from the JSON response
                            String email = jsonObject.getString("email");

                            userEmail.setText(email);
                            //editPass.setText(password);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Handle any JSON parsing errors
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle any errors that occur during the request
                        Log.e("A server error has occurred", error.toString());
                    }
                }
        );

        // Adding the request to the request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
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
                            Intent intent = new Intent(ProfilePageViewer.this, ProfilePage.class);
                            startActivity(intent);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
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
                        Toast.makeText(ProfilePageViewer.this, "Incorrect password", Toast.LENGTH_SHORT);
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    /**
     * Handles the click event on the calendar button in the navigation bar.
     * This method is part of the NavBarView.OnButtonClickListener interface.
     */
    @Override
    public void onCalendarButtonClick() {
        Intent intent = new Intent(ProfilePageViewer.this, CalendarMonthlyPage.class);
        options = ActivityOptions.makeCustomAnimation(ProfilePageViewer.this, R.anim.empty_anim, R.anim.empty_anim);
        startActivity(intent, options.toBundle());
    }

    /**
     * Handles the click event on the home button in the navigation bar.
     * This method is part of the NavBarView.OnButtonClickListener interface.
     */
    @Override
    public void onHomeButtonClick() {
        Intent intent = new Intent(ProfilePageViewer.this, HomePage.class);
        options = ActivityOptions.makeCustomAnimation(ProfilePageViewer.this, R.anim.empty_anim, R.anim.empty_anim);
        startActivity(intent, options.toBundle());
    }

    /**
     * Handles the click event on the messages button in the navigation bar.
     * This method is part of the NavBarView.OnButtonClickListener interface.
     */
    @Override
    public void onMessagesButtonClick() {
        Intent intent = new Intent(ProfilePageViewer.this, MemberViewer.class);
        options = ActivityOptions.makeCustomAnimation(ProfilePageViewer.this, R.anim.empty_anim, R.anim.empty_anim);
        startActivity(intent, options.toBundle());
    }

    /**
     * Handles the click event on the profile button in the navigation bar.
     * This method is part of the NavBarView.OnButtonClickListener interface.
     */
    @Override
    public void onProfileButtonClick() {
        /*
        Does nothing
         */
    }

    /**
     * Handles the click event on the create button in the navigation bar.
     * This method is part of the NavBarView.OnButtonClickListener interface.
     */
    @Override
    public void onCreateEventButtonClick() {
        // Navigate to Create Events page
        Intent intent = new Intent(ProfilePageViewer.this, CreateEventPage.class);
        startActivity(intent);
    }



}