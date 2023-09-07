package com.example.real;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
public class MainActivity2 extends AppCompatActivity {
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Initialize the GestureDetector
        gestureDetector = new GestureDetector(this, new MainActivity2.MyGestureListener());
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