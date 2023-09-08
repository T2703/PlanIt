package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Main2Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_screen);

        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME_KEY");

        if (username != null) {
            String welcomeMessage = getString(R.string.welcome, username);

            TextView textView = findViewById(R.id.welcome_text);
            textView.setText(welcomeMessage);
        }
     }
}
