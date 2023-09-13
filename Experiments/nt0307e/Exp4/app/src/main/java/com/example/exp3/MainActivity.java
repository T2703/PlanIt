package com.example.exp3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


// This is the home/calendar page.
public class MainActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_main);

    }

    // Define the method to be called when the FloatingActionButton is clicked
    public void onSettingsButtonClick(View view) {
        Log.d("BUTTONS", "User tapped");
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        startActivity(intent); // Start MainActivity2 need this other wise im just calling an object lol
    }

}