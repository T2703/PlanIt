package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class CreateEventPage extends AppCompatActivity {

    private ImageButton back_button;

    private Button create_event_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event_form);

        back_button = findViewById(R.id.back_button);
        create_event_button = findViewById(R.id.create_event_button);

        // Return to homepage button
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an intent to navigate to home page
                Intent intent = new Intent(CreateEventPage.this, NavBar.class);
                startActivity(intent);
            }
        });

        // Creates event
        create_event_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG", "Event created");
            }
        });

    }
}
