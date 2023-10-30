// Author: Tristan Nono
package calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import api.VolleySingleton;
import events.CreateEventPage;
import events.Event;
import groups.MemberViewer;
import com.example.myapplication.NavBar;
import com.example.myapplication.NavBarView;

import homepage.HomePage;
import profile.LoginFormPage;
import profile.ProfilePage;
import com.example.myapplication.R;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    This grabs the date from the onSelectedDayChange.
     */
    private static String date_getter;

    /*
    It's our navbar.
     */
    private NavBarView navbar_view;

    /*
    This is for the transitioning between pages.
     */
    private ActivityOptions options;

    /*
    Array list.
    */
    private List<Event> event_list;

    /*
    This manages the layout.
    */
    private LinearLayoutManager layout_manager;

    /*
    Recycler view aka from what I know it's how we display the list of items.
    */
    private RecyclerView recycler_view;

    /*
    The event adapter for the event list.
    */
    private EventCalendarMonthlyAdapter adapter;

    private String formattedDate;

    private Button buttonThing;

    private static final String URL_STRING_REQ = "http://coms-309-024.class.las.iastate.edu:8080/events";

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_monthly_page);

        // Initialize
        calendar_display = findViewById(R.id.calendar_view);
        date_view = findViewById(R.id.date_change);
        navbar_view = findViewById(R.id.navbar);
        buttonThing = findViewById(R.id.button);
        navbar_view.setOnButtonClickListener(this);
        event_list = new ArrayList<>();
        adapter = new EventCalendarMonthlyAdapter(this, event_list);
        layout_manager = new LinearLayoutManager(this);

        recycler_view = findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(layout_manager);
        recycler_view.setAdapter(adapter);

        navbar_view.setSelectedButton(navbar_view.getCalendarButton());

        buttonThing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarMonthlyPage.this, CalendarWeeklyPage.class);
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
                        //month = month - 1;

                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(year, month, day_of_month);

                        date_getter = String.format("%04d-%02d-%02d", year, month + 1, day_of_month);
                        date_view.setText(date_getter);
                        // Request here everytime when a day is selected.
                        getEventsRequest();

                    }
                }
        );

        // Get the current date just so it has the current date right away.
        Calendar currentDate = Calendar.getInstance();
        int year = currentDate.get(Calendar.YEAR);
        int month = currentDate.get(Calendar.MONTH) + 1;
        int day = currentDate.get(Calendar.DAY_OF_MONTH);

        date_getter = String.format("%04d-%02d-%02d", year, month, day);

        date_view.setText(date_getter);

        // Get the current date and set it as the selected date in the CalendarView.
        // Yeah, this basically just highlights the current day text in the calendar.
        Calendar current_date = Calendar.getInstance();
        long current_time_in_millis = current_date.getTimeInMillis();
        calendar_display.setDate(current_time_in_millis);

        // Request events from server
        getEventsRequest();
    }

    @Override
    public void onCalendarButtonClick() {
        /*Intent intent = new Intent(this, CalendarMonthlyPage.class);
        startActivity(intent);*/
        //Log.d("Date", date_getter);
    }

    @Override
    public void onHomeButtonClick() {
        Intent intent = new Intent(CalendarMonthlyPage.this, HomePage.class);
        ActivityOptions options = ActivityOptions.makeCustomAnimation(CalendarMonthlyPage.this, R.anim.empty_anim, R.anim.empty_anim);
        startActivity(intent, options.toBundle());
    }

    @Override
    public void onMessagesButtonClick() {
        Intent intent = new Intent(CalendarMonthlyPage.this, MemberViewer.class);
        options = ActivityOptions.makeCustomAnimation(CalendarMonthlyPage.this, R.anim.empty_anim, R.anim.empty_anim);
        startActivity(intent, options.toBundle());
    }

    @Override
    public void onProfileButtonClick() {
        Intent intent = new Intent(CalendarMonthlyPage.this, ProfilePage.class);
        options = ActivityOptions.makeCustomAnimation(CalendarMonthlyPage.this, R.anim.empty_anim, R.anim.empty_anim);
        startActivity(intent, options.toBundle());
    }

    @Override
    public void onCreateEventButtonClick() {
        // Navigate to Create Events page
        Intent intent = new Intent(CalendarMonthlyPage.this, CreateEventPage.class);
        startActivity(intent);
    }

    private void getEventsRequest() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL_STRING_REQ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray responseArray;

                        try {
                            responseArray = new JSONArray(response);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                        // Clear the event list to refresh it
                        event_list.clear();

                        // Iterate
                        for (int i = 0; i < responseArray.length(); i++) {
                            try {
                                JSONObject jsonObject = responseArray.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String name = jsonObject.getString("name");
                                String description = jsonObject.getString("description");
                                String startDateStr  = jsonObject.getString("startDate");
                                String endDateStr  = jsonObject.getString("endDate");

                                SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
                                Date startDate = inputDateFormat.parse(startDateStr);
                                Date endDate = inputDateFormat.parse(endDateStr);
                                
                                //event_list.add(new Event(id, name, description, start_date, end_date));
                                SimpleDateFormat militaryTimeFormat = new SimpleDateFormat("HH:mm");
                                String startTime = militaryTimeFormat.format(startDate);
                                String endTime = militaryTimeFormat.format(endDate);
                                Log.d("START", startDateStr);

                                if (startDateStr.startsWith(date_getter)) {
                                    event_list.add(new Event(id, name, description, startTime, endTime));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle any errors that occur during the request
                        Log.e("A server error has occurred", error.toString());
                    }
                }
        );

        // Adding request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

}