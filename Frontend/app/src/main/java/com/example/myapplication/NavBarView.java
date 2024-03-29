package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

/**
 * Navigation bar for navigating throughout the pages and app.
 * @author Tristan Nono
 */
public class NavBarView extends RelativeLayout {
    /**
     * Button for navigating to the calendar.
     */
    private ImageButton calendar_button;

    /**
     * Button for navigating to messages.
     */
    private ImageButton messages_button;

    /**
     * Button for navigating to the home page.
     */
    private ImageButton home_button;

    /**
     * Button for navigating to the user's profile.
     */
    private ImageButton profile_button;

    /**
     * Button for creating events.
     */
    private ImageButton create_event_button;

    /**
     * Variable to check if a button has been clicked.
     */
    private OnButtonClickListener button_click_listener;


    /**
    An interface mainly used for calling these methods for the navbar
    since, we can't just use intent here it won't work.
     */
    public interface OnButtonClickListener {
        void onCalendarButtonClick();
        void onHomeButtonClick();
        void onMessagesButtonClick();
        void onProfileButtonClick();
        void onCreateEventButtonClick();
    }


    /**
     * The view for the navbar.
     * @param context
     * @param attrs
     */
    public NavBarView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.activity_navbar, this);

        // Initialize
        calendar_button = findViewById(R.id.calendar_button_nav);
        messages_button = findViewById(R.id.message_button);
        create_event_button = findViewById(R.id.create_events_button);
        home_button = findViewById(R.id.home_button);
        profile_button = findViewById(R.id.profile_button);
        create_event_button = findViewById(R.id.create_events_button);

        // Set a click listeners for the corresponding buttons.
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelectedButton(home_button);
                button_click_listener.onHomeButtonClick();
            }
        });

        calendar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelectedButton(calendar_button);
                button_click_listener.onCalendarButtonClick();
            }
        });

        create_event_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to Create Events page
                button_click_listener.onCreateEventButtonClick();
            }
        });

        messages_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelectedButton(messages_button);
                button_click_listener.onMessagesButtonClick();
            }
        });

        profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelectedButton(profile_button);
                button_click_listener.onProfileButtonClick();
            }
        });
    }

    /**
     * This method here sets the color of the selected button on the navbar depending on what is clicked.
     * Example: if calendar button is clicked then the calendar button is lit up.
     * @param button
     */
    public void setSelectedButton(ImageButton button) {
        // Deselect all buttons
        calendar_button.setSelected(false);
        messages_button.setSelected(false);
        home_button.setSelected(false);
        profile_button.setSelected(false);

        // Select the clicked button
        button.setSelected(true);

        // Set tints based on the selected state
        calendar_button.setColorFilter(getResources().getColor(
                calendar_button.isSelected() ? R.color.primary_color : R.color.deselected_tint));

        messages_button.setColorFilter(getResources().getColor(
                messages_button.isSelected() ? R.color.primary_color : R.color.deselected_tint));

        home_button.setColorFilter(getResources().getColor(
                home_button.isSelected() ? R.color.primary_color : R.color.deselected_tint));

        profile_button.setColorFilter(getResources().getColor(
                profile_button.isSelected() ? R.color.primary_color : R.color.deselected_tint));

    }

    /**
    Sets the button click listener.
     */
    public void setOnButtonClickListener(OnButtonClickListener listener) {
        this.button_click_listener = listener;
    }

    /**
     * Gets the calendar button.
     * @return calendar_button
     */
    public ImageButton getCalendarButton() {
        return calendar_button;
    }

    /**
     * Gets the message button.
     * @return message button
     */
    public ImageButton getMessagesButton() {
        return messages_button;
    }

    /**
     * Gets the profile button.
     * @return profile button
     */
    public ImageButton getProfileButton() {
        return profile_button;
    }

    /**
     * Gets the home button.
     * @return home button
     */
    public ImageButton getHomeButton() {
        return home_button;
    }
}
