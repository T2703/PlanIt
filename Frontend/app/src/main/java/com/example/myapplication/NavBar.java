// Author: Tristan Nono
package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

/*
The main navbar for directing the user to certain locations on the app such as home, calendar, messages/chats, & profile.
There is also a center button used for creating the events.
 */
public class NavBar extends AppCompatActivity {
    private ImageButton calendar_button;
    private ImageButton messages_button;
    private ImageButton home_button;
    private ImageButton profile_button;
    private ImageButton create_event_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navbar);

        // Initialize
        calendar_button = findViewById(R.id.calendar_button_nav);
        messages_button = findViewById(R.id.message_button);
        create_event_button = findViewById(R.id.create_events_button);
        home_button = findViewById(R.id.home_button);
        profile_button = findViewById(R.id.profile_button);

        // Set a click listeners for the corresponding buttons
        calendar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG", "Calendar");
                setSelectedButton(calendar_button);

            }
        });

        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG", "Home");
                setSelectedButton(home_button);
            }
        });

        messages_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG", "Messages");
                setSelectedButton(messages_button);
            }
        });

        profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG", "Profile");
                setSelectedButton(profile_button);
            }
        });

        create_event_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG", "Create event");
            }
        });
    }

    /*
    This method here sets the color of the selected button on the navbar depending on what is clicked.
    Example: if calendar button is clicked then the calendar button is lit up.
     */
    private void setSelectedButton(ImageButton button) {
        // Deselect all buttons
        calendar_button.setSelected(false);
        messages_button.setSelected(false);
        home_button.setSelected(false);
        profile_button.setSelected(false);

        // Select the clicked button
        button.setSelected(true);

        // Set tints based on the selected state
        calendar_button.setColorFilter(getResources().getColor(
                calendar_button.isSelected() ? R.color.selected_tint : R.color.deselected_tint));

        messages_button.setColorFilter(getResources().getColor(
                messages_button.isSelected() ? R.color.selected_tint : R.color.deselected_tint));

        home_button.setColorFilter(getResources().getColor(
                home_button.isSelected() ? R.color.selected_tint : R.color.deselected_tint));

        profile_button.setColorFilter(getResources().getColor(
                profile_button.isSelected() ? R.color.selected_tint : R.color.deselected_tint));
    }
}