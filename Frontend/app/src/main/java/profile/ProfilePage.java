package profile;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import api.VolleySingleton;
import groups.MemberViewer;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.NavBarView;
import com.example.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import calendar.CalendarMonthlyPage;
import events.CreateEventPage;
import homepage.HomePage;
import websockets.WebSocketManager;

/**
 * The account/profile page where the user can change/edit their account info like their name, email, password, or profile picture,
 * and delete their account. This class extends AppCompatActivity and implements NavBarView.OnButtonClickListener for handling
 * navigation bar button clicks.
 *
 * <p>The activity includes functionality for changing the profile picture, saving changes, deleting the account,
 * and navigating to other pages through the navigation bar.
 *
 * @author Tristan Nono
 */
public class ProfilePage extends AppCompatActivity implements NavBarView.OnButtonClickListener {
    /**
     * The button for changing the pfp.
     */
    private Button change_pfp_button;

    /**
     * The button for saving the changes in the profile.
     */
    private Button save_changes_button;

    /**
     * The button for deleting the profile.
     */
    private Button delete_account_button;

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
    private EditText editName, editEmail, editPass;

    private String ID;

    /*
    The image loader.
     */
    private static final int RESULT_LOAD_IMG = 1;

    private static final String URL_USERS = "http://coms-309-024.class.las.iastate.edu:8080/users/";

    /**
     * Initializes the activity, sets up UI components.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state, or null if there was none.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        // Initialize
        change_pfp_button = findViewById(R.id.changeImageButton);
        profile_image_view = findViewById(R.id.profileImageView);
        save_changes_button = findViewById(R.id.save_changes_button);
        delete_account_button = findViewById(R.id.delete_account_button);
        editName = findViewById(R.id.editTextName);
        editPass = findViewById(R.id.editTextPassword);
        editEmail = findViewById(R.id.editTextEmail);
        settings_button = findViewById(R.id.gear5);
        navbar_view = findViewById(R.id.navbar);
        navbar_view.setOnButtonClickListener(this);
        UserManager userManager = UserManager.getInstance();
        ID = userManager.getUserID();

        editName.setText(WebSocketManager.getInstance().getUsername());

        navbar_view.setSelectedButton(navbar_view.getProfileButton());

        // Load the default square profile picture
        profile_image_view.setImageResource(R.drawable.default_profile_image);
        // Apply the circular mask
        profile_image_view.setBackgroundResource(R.drawable.circular_background);

        getUserInfoRequest(ID);


        // Set a click listener for the change pfp button
        change_pfp_button.setOnClickListener(new View.OnClickListener() {
            /**
             * Handles the click event for changing the profile picture button. Initiates the image picker activity,
             * allowing the user to select a new profile picture from the gallery or another source.
             *
             * @param view The View that was clicked (in this case, the change profile picture button).
             * @see View.OnClickListener#onClick(View)
             */
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                // Start the image picker activity
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
            }
        });

        // Set a click listener for the save changes button
        save_changes_button.setOnClickListener(new View.OnClickListener() {
            /**
             * Handles the click event for saving changes in the profile. This method will be invoked when the user clicks
             * the "Save Changes" button. It logs a message indicating that changes have been saved.
             *
             * @param view The View that was clicked (in this case, the save changes button).
             */
            @Override
            public void onClick(View view) {
                // This will handle the button click.
                Log.d("TAG", "Saved");
            }
        });

        // Set a click listener for the delete button
        delete_account_button.setOnClickListener(new View.OnClickListener() {
            /**
             * Handles the click event for deleting the user's account. This method will be invoked when the user clicks
             * the "Delete Account" button. It logs a message indicating that the account deletion process has been initiated.
             *
             * @param view The View that was clicked (in this case, the delete account button).
             */
            @Override
            public void onClick(View view) {
                // This will handle the button click.
                Log.d("TAG", "Delete");
            }
        });

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
                Intent intent = new Intent(ProfilePage.this, Settings.class);
                ActivityOptions options = ActivityOptions.makeCustomAnimation(ProfilePage.this, R.anim.empty_anim, R.anim.empty_anim);
                startActivity(intent, options.toBundle());
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
                            String password = jsonObject.getString("password");

                            editEmail.setText(email);
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
     * Handles the click event on the calendar button in the navigation bar.
     * This method is part of the NavBarView.OnButtonClickListener interface.
     */
    @Override
    public void onCalendarButtonClick() {
        Intent intent = new Intent(ProfilePage.this, CalendarMonthlyPage.class);
        options = ActivityOptions.makeCustomAnimation(ProfilePage.this, R.anim.empty_anim, R.anim.empty_anim);
        startActivity(intent, options.toBundle());
    }

    /**
     * Handles the click event on the home button in the navigation bar.
     * This method is part of the NavBarView.OnButtonClickListener interface.
     */
    @Override
    public void onHomeButtonClick() {
        Intent intent = new Intent(ProfilePage.this, HomePage.class);
        options = ActivityOptions.makeCustomAnimation(ProfilePage.this, R.anim.empty_anim, R.anim.empty_anim);
        startActivity(intent, options.toBundle());
    }

    /**
     * Handles the click event on the messages button in the navigation bar.
     * This method is part of the NavBarView.OnButtonClickListener interface.
     */
    @Override
    public void onMessagesButtonClick() {
        Intent intent = new Intent(ProfilePage.this, MemberViewer.class);
        options = ActivityOptions.makeCustomAnimation(ProfilePage.this, R.anim.empty_anim, R.anim.empty_anim);
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
        Intent intent = new Intent(ProfilePage.this, CreateEventPage.class);
        startActivity(intent);
    }

}
