package com.example.real;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

// Could use this as a settings menu for the user that's what I'm thinking.
public class MainActivity2 extends AppCompatActivity {
    private GestureDetector gestureDetector;
    private Button lightModeButton;
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
        gestureDetector = new GestureDetector(this, new MainActivity2.MyGestureListener());
        lightModeButton = findViewById(R.id.lightmodebutton);
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // Load the current mode preference (default to light mode)
        isDarkMode = sharedPreferences.getBoolean("isDarkMode", false);
        applyTheme();

        // Set a click listener for the button
        lightModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // This will handle the button click.
                //Log.d(TAG, "Button Clicked!");
                //System.out.println("This is a test message for debugging.");

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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Pass the touch event to the GestureDetector
        return gestureDetector.onTouchEvent(event);
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            // Implement your logic for swipe here
            if (e1.getX() < e2.getX()) {
                // Right swipe
                Toast.makeText(MainActivity2.this, "Right Swipe", Toast.LENGTH_SHORT).show();

                // Start MainActivity
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);

            } else {
                // Left swipe
                Toast.makeText(MainActivity2.this, "Left Swipe", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    }
}