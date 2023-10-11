// Author: Tristan Nono
package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.Calendar;
import com.example.myapplication.NavBarView;

/*
The calendar page, basically the monthly view to be more precise,
this is where the user can see the entire month of the calendar and they can
see their events for the day they have clicked.
Also, this is a work in progress at the moment uh let me just idk pull something out to insta finish lol idk.
 */
public class CalendarMonthlyPage extends AppCompatActivity implements NavBarView.OnButtonClickListener {
    /*
    The calendar for displaying the calendar (I mean, I don't know what else to say).
    */
    private CalendarView calendar_display;

    /*
    The text for displaying the current date.
     */
    private TextView date_view;

    /*
    I don't wanna do this man. :(
     */
    private Button back_button;

    /*
    It's our navbar.
     */
    private NavBarView navbar_view;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_monthly_page);

        // Initialize
        calendar_display = findViewById(R.id.calendar_view);
        date_view = findViewById(R.id.date_change);
        back_button = findViewById(R.id.button_back);
        navbar_view = findViewById(R.id.navbar);
        navbar_view.setOnButtonClickListener(this);

        navbar_view.setSelectedButton(navbar_view.getCalendarButton());

        // :(
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarMonthlyPage.this, NavBar.class);
                startActivity(intent);
            }
        });


        // Set the on date change listener (so when the user clicks on the date, it does something).
        calendar_display.setOnDateChangeListener(
                new CalendarView.OnDateChangeListener() {

                    // This changes the date depending on what the user picks.
                    // For example if the user clicks 21 and it's on September (do you remember what happened during the night though?)
                    // and the year is 2023 the date should display 9/21/2023.
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView calendar_view, int year, int month, int day_of_month) {
                        String date =  (month + 1) + "/" + day_of_month + "/" + year;
                        date_view.setText(date);
                    }
                }
        );

        // Get the current date and set it as the selected date in the CalendarView.
        // Yeah, this basically just highlights the current day text in the calendar.
        Calendar current_date = Calendar.getInstance();
        long current_time_in_millis = current_date.getTimeInMillis();
        calendar_display.setDate(current_time_in_millis);

    }

    @Override
    public void onCalendarButtonClick() {
        /*Intent intent = new Intent(this, CalendarMonthlyPage.class);
        startActivity(intent);*/
    }

    @Override
    public void onHomeButtonClick() {

    }

    @Override
    public void onMessagesButtonClick() {
        Intent intent = new Intent(CalendarMonthlyPage.this, MemberViewer.class);
        startActivity(intent);
    }

    @Override
    public void onProfileButtonClick() {
        Intent intent = new Intent(CalendarMonthlyPage.this, ProfilePage.class);
        startActivity(intent);
    }

    @Override
    public void onCreateEventButtonClick() {
        // Navigate to Create Events page
        Intent intent = new Intent(CalendarMonthlyPage.this, CreateEventPage.class);
        startActivity(intent);
    }
}