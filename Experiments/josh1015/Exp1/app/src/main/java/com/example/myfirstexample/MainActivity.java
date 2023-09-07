package com.example.myfirstexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Button and form
        Button createEventBtn = findViewById(R.id.create_event_button);
        final LinearLayout createNewEventForm = findViewById(R.id.new_event_form);

        // Select form fields
        EditText eventTitle = findViewById(R.id.event_title);
        String eventTitleInput = eventTitle.getText().toString();

        EditText eventLocation = findViewById(R.id.event_location);
        String eventLocationInput = eventTitle.getText().toString();

        // Add an event listener
        createEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show form
                if (createNewEventForm.getVisibility() == View.VISIBLE) {
                    createNewEventForm.setVisibility(View.GONE);
                } else {
                    createNewEventForm.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}