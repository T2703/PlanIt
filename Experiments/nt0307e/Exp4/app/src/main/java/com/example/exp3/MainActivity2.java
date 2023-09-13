package com.example.exp3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.SharedPreferences;
import android.widget.Button;

import example.subpages.MyAccount;

// The settings/user profile stuff.
public class MainActivity2 extends AppCompatActivity {
    private Button lightModeButton;
    private Button userProfileButton;
    private SharedPreferences sharedPreferences;
    private boolean isDarkMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Set the theme based on the stored preference
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        isDarkMode = sharedPreferences.getBoolean("isDarkMode", false);
        // For saving purposes.
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Initialize the stuff.
        lightModeButton = findViewById(R.id.lightmodebutton);
        userProfileButton = findViewById(R.id.accountbutton);
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // Load the current mode preference (default to light mode)
        isDarkMode = sharedPreferences.getBoolean("isDarkMode", false);
        applyTheme();

        // Set a click listener for the light/dark button
        lightModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // This will handle the button click.

                // Toggle the dark mode state
                isDarkMode = !isDarkMode;

                // Apply the selected mode
                applyTheme();

                // Save the mode preference
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isDarkMode", isDarkMode);
                editor.apply();

            }
        });

        // Set a click listener for the my account button
        userProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // This will handle the button click.
                Intent intent = new Intent(MainActivity2.this, MyAccount.class);
                startActivity(intent); // Start MainActivity2 need this other wise im just calling an object lol

            }
        });

    }

    // This pretty much toggles between the light or dark mode themes on the app.
    private void applyTheme() {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        recreate();
    }

    // Define the method to be called when the FloatingActionButton is clicked same thing but we just use the "home" button
    public void onHomeButtonClick(View view) {
        Log.d("BUTTONS", "User tapped");
        Intent intent = new Intent(MainActivity2.this, MainActivity.class);
        startActivity(intent); // Start MainActivity2 need this other wise im just calling an object lol
    }
}