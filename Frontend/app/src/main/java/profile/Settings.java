package profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.SharedPreferences;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import com.example.myapplication.NavBarView;
import com.example.myapplication.R;
import calendar.CalendarMonthlyPage;
import events.CreateEventPage;
import groups.MemberViewer;
import homepage.HomePage;

/**
 * The Settings page of the app where users can configure preferences, including toggling between dark and light modes.
 *
 * @author Tristan Nono
 */
public class Settings extends AppCompatActivity implements NavBarView.OnButtonClickListener {
    /**
     * Navbar.
     */
    private NavBarView navbar_view;

    /**
     * The switch that toggles the dark or light themes.
     */
    private Switch dark_or_light_mode_switch;

    /**
     * Saves the theme.
     */
    private SharedPreferences sharedPreferencesTheme;

    /**
     * Initializes the activity and sets up UI components.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state, or null if there was none.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        navbar_view = findViewById(R.id.navbar);
        navbar_view.setOnButtonClickListener(this);
        navbar_view.setSelectedButton(navbar_view.getProfileButton());
        dark_or_light_mode_switch = findViewById(R.id.theme_switch);

        sharedPreferencesTheme = getSharedPreferences("MyPrefs", MODE_PRIVATE);

    }

    /**
     * Resumes the activity and updates the UI based on the current night mode.
     */
    @Override
    protected void onResume() {
        super.onResume();

        // Retrieve the current night mode
        int currentNightMode = AppCompatDelegate.getDefaultNightMode();

        // Set the switch state based on the current night mode
        if (currentNightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            dark_or_light_mode_switch.setChecked(true); // Dark mode is on
        }
        else {
            dark_or_light_mode_switch.setChecked(false); // Dark mode is off
        }

        dark_or_light_mode_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /**
             * Handles the change event when the dark/light mode switch is toggled. Updates the app's theme based on the switch state
             * and saves the dark mode preference.
             *
             * @param buttonView The compound button view whose state has changed.
             * @param isChecked  True if the switch is in the checked state (user wants dark mode), false otherwise (user wants light mode).
             */
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Check if the switch is checked (user wants dark mode) or unchecked (user wants light mode).
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }

                sharedPreferencesTheme.edit().putBoolean("darkMode", isChecked).apply();
            }
        });
    }

    /**
     * Handles the click event on the calendar button in the navigation bar.
     * This method is part of the NavBarView.OnButtonClickListener interface.
     */
    @Override
    public void onCalendarButtonClick() {
        Intent intent = new Intent(Settings.this, CalendarMonthlyPage.class);
        ActivityOptions options = ActivityOptions.makeCustomAnimation(Settings.this, R.anim.empty_anim, R.anim.empty_anim);
        startActivity(intent, options.toBundle());
    }

    /**
     * Handles the click event on the home button in the navigation bar.
     * This method is part of the NavBarView.OnButtonClickListener interface.
     */
    @Override
    public void onHomeButtonClick() {
        Intent intent = new Intent(Settings.this, HomePage.class);
        ActivityOptions options = ActivityOptions.makeCustomAnimation(Settings.this, R.anim.empty_anim, R.anim.empty_anim);
        startActivity(intent, options.toBundle());
    }

    /**
     * Handles the click event on the message button in the navigation bar.
     * This method is part of the NavBarView.OnButtonClickListener interface.
     */
    @Override
    public void onMessagesButtonClick() {
        Intent intent = new Intent(Settings.this, MemberViewer.class);
        ActivityOptions options = ActivityOptions.makeCustomAnimation(Settings.this, R.anim.empty_anim, R.anim.empty_anim);
        startActivity(intent, options.toBundle());
    }

    /**
     * Handles the click event on the profile button in the navigation bar.
     * This method is part of the NavBarView.OnButtonClickListener interface.
     */
    @Override
    public void onProfileButtonClick() {
        Intent intent = new Intent(Settings.this, ProfilePage.class);
        ActivityOptions options = ActivityOptions.makeCustomAnimation(Settings.this, R.anim.empty_anim, R.anim.empty_anim);
        startActivity(intent, options.toBundle());
    }

    /**
     * Handles the click event on the create button in the navigation bar.
     * This method is part of the NavBarView.OnButtonClickListener interface.
     */
    @Override
    public void onCreateEventButtonClick() {
        // Navigate to Create Events page
        Intent intent = new Intent(Settings.this, CreateEventPage.class);
        startActivity(intent);
    }
}