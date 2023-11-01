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

/*
The settings page of the app.
 */
public class Settings extends AppCompatActivity implements NavBarView.OnButtonClickListener {
    /*
    It's our navbar. Once again.
    */
    private NavBarView navbar_view;

    /*
    The switch that toggles the dark or light themes.
     */
    private Switch dark_or_light_mode_switch;

    /*
    Saves the theme.
     */
    private SharedPreferences sharedPreferencesTheme;

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

    @Override
    public void onCalendarButtonClick() {
        Intent intent = new Intent(Settings.this, CalendarMonthlyPage.class);
        ActivityOptions options = ActivityOptions.makeCustomAnimation(Settings.this, R.anim.empty_anim, R.anim.empty_anim);
        startActivity(intent, options.toBundle());
    }

    @Override
    public void onHomeButtonClick() {
        Intent intent = new Intent(Settings.this, HomePage.class);
        ActivityOptions options = ActivityOptions.makeCustomAnimation(Settings.this, R.anim.empty_anim, R.anim.empty_anim);
        startActivity(intent, options.toBundle());
    }

    @Override
    public void onMessagesButtonClick() {
        Intent intent = new Intent(Settings.this, MemberViewer.class);
        ActivityOptions options = ActivityOptions.makeCustomAnimation(Settings.this, R.anim.empty_anim, R.anim.empty_anim);
        startActivity(intent, options.toBundle());
    }

    @Override
    public void onProfileButtonClick() {
        Intent intent = new Intent(Settings.this, ProfilePage.class);
        ActivityOptions options = ActivityOptions.makeCustomAnimation(Settings.this, R.anim.empty_anim, R.anim.empty_anim);
        startActivity(intent, options.toBundle());
    }

    @Override
    public void onCreateEventButtonClick() {
        // Navigate to Create Events page
        Intent intent = new Intent(Settings.this, CreateEventPage.class);
        startActivity(intent);
    }
}