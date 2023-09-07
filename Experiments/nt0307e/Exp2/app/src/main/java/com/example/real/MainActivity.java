package com.example.real;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class MainActivity extends AppCompatActivity  {
    private GestureDetector gestureDetector;
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

        // Initialize the GestureDetector
        gestureDetector = new GestureDetector(this, new MyGestureListener());

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
                Toast.makeText(MainActivity.this, "Right Swipe", Toast.LENGTH_SHORT).show();
            } else {
                // Left swipe
                Toast.makeText(MainActivity.this, "Left Swipe", Toast.LENGTH_SHORT).show();

                // Start MainActivity2
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
            return true;
        }
    }
}