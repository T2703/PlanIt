// Author: Tristan Nono
package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;
import java.util.Calendar;

/*
The calendar page, basically the monthly view to be more precise,
this is where the user can see the entire month of the calendar and they can
see their events for the day they have clicked.
Also, this is a work in progress at the moment uh let me just idk pull something out to insta finish lol idk.
 */
public class CalendarMonthlyPage extends AppCompatActivity {
    /*
    The calendar for displaying the calendar (I mean, I don't know what else to say).
    */
    private CalendarView calendar_display;

    /*
    The text for displaying the current date.
     */
    private TextView date_view;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_monthly_page);

        // Initialize
        calendar_display = findViewById(R.id.calendar_view);
        date_view = findViewById(R.id.date_change);


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
}