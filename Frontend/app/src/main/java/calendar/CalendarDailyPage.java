package calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.NavBarView;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import api.VolleySingleton;
import events.CreateEventPage;
import events.Event;
import events.EventsListViewer;
import groups.MemberViewer;
import homepage.HomePage;
import profile.ProfilePage;
import websockets.WebSocketManager;

/*
Daily view for the calendar page.
 */
public class CalendarDailyPage extends AppCompatActivity implements NavBarView.OnButtonClickListener {
    /*
    The buttons for going to the next or previous day.
    */
    private ImageButton dayButtonNext, dayButtonPrev;

    /*
    The day it is.
    */
    private TextView dayOfMonth;

    /*
    This grabs the date.
    */
    private static String date_getter;

    /*
    Calendar thing.
    */
    private Calendar calendar, currentWeek;

    /*
    It's our navbar.
    */
    private NavBarView navbar_view;

    /*
    The event adapter for the event list.
    */
    private EventCalendarMonthlyAdapter adapter;

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
    The button that brings up the popup menu displaying the views of the calendars.
    */
    private ImageButton menu_button;


    private static final String URL_STRING_REQ = "http://coms-309-024.class.las.iastate.edu:8080/users/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_daily_page);

        //Initialize the components
        dayButtonNext = findViewById(R.id.nextDayButton);
        dayButtonPrev = findViewById(R.id.prevDayButton);
        dayOfMonth = findViewById(R.id.dayOfMonthText);
        navbar_view = findViewById(R.id.navbar);
        navbar_view.setSelectedButton(navbar_view.getCalendarButton());
        menu_button = findViewById(R.id.menu_calendar_button);
        navbar_view.setOnButtonClickListener(this);
        navbar_view.setSelectedButton(navbar_view.getCalendarButton());
        event_list = new ArrayList<>();
        adapter = new EventCalendarMonthlyAdapter(this, event_list);
        layout_manager = new LinearLayoutManager(this);

        recycler_view = findViewById(R.id.liste);
        recycler_view.setLayoutManager(layout_manager);
        recycler_view.setAdapter(adapter);

        calendar = Calendar.getInstance();
        currentWeek = (Calendar) calendar.clone();


        SimpleDateFormat sdf = new SimpleDateFormat("d", Locale.getDefault());
        SimpleDateFormat monthSdf = new SimpleDateFormat("MMMM", Locale.getDefault());
        SimpleDateFormat yearSdf = new SimpleDateFormat("yyyy", Locale.getDefault());
        dayOfMonth.setText(monthSdf.format(calendar.getTime()) + " " + sdf.format(calendar.getTime()) + "," + " " + yearSdf.format(calendar.getTime()));

        getEventsRequest();

        // Buttons
        dayButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                //currentWeek.add(Calendar.WEEK_OF_YEAR, 1);
                date_getter = getDateForDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK), calendar);
                getEventsRequest();
                Log.d("DATE", date_getter);
                updateDateText();
            }
        });

        dayButtonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                //currentWeek.add(Calendar.WEEK_OF_YEAR, -1);
                date_getter = getDateForDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK), calendar);
                getEventsRequest();
                Log.d("DATE", date_getter);
                updateDateText();
            }
        });

        menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup_menu = new PopupMenu(CalendarDailyPage.this, view);
                popup_menu.getMenuInflater().inflate(R.menu.options_menu_calendar, popup_menu.getMenu());
                popup_menu.show();

                popup_menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.monthly_view) {
                            Intent intent = new Intent(CalendarDailyPage.this, CalendarMonthlyPage.class);
                            ActivityOptions options = ActivityOptions.makeCustomAnimation(CalendarDailyPage.this, R.anim.empty_anim, R.anim.empty_anim);
                            startActivity(intent, options.toBundle());
                        }

                        else if (menuItem.getItemId() == R.id.weekly_view) {
                            Intent intent = new Intent(CalendarDailyPage.this, CalendarWeeklyPage.class);
                            ActivityOptions options = ActivityOptions.makeCustomAnimation(CalendarDailyPage.this, R.anim.empty_anim, R.anim.empty_anim);
                            startActivity(intent, options.toBundle());
                        }

                        else if (menuItem.getItemId() == R.id.all_events) {
                            Intent intent = new Intent(CalendarDailyPage.this, EventsListViewer.class);
                            ActivityOptions options = ActivityOptions.makeCustomAnimation(CalendarDailyPage.this, R.anim.empty_anim, R.anim.empty_anim);
                            startActivity(intent, options.toBundle());
                        }

                        return true;
                    }
                });

                popup_menu.show();
            }
        });

        //calendar.add(Calendar.DAY_OF_MONTH, 0);
        date_getter = getDateForDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK), calendar);
    }

    /*
    Method to update the date text.
    So, when user clicks the button it updates.
     */
    private void updateDateText() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy", Locale.getDefault());
        String formattedDate = sdf.format(calendar.getTime());
        dayOfMonth.setText(formattedDate);
    }

    /*
    Gets the current date for the day.
    */
    private String getCurrentDateForDay(int dayOfWeek) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek); // Set to the selected day of the week
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }

    /*
    Method to get the date for the day of the week.
    */
    private String getDateForDayOfWeek(int dayOfWeek, Calendar baseDate) {
        Calendar date = (Calendar) baseDate.clone();

        while (date.get(Calendar.DAY_OF_WEEK) != dayOfWeek) {
            date.add(Calendar.DAY_OF_MONTH, 1);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(date.getTime());
    }

    @Override
    public void onCalendarButtonClick() {
        /*Intent intent = new Intent(this, CalendarMonthlyPage.class);
        startActivity(intent);*/
        //Log.d("Date", date_getter);
    }

    @Override
    public void onHomeButtonClick() {
        Intent intent = new Intent(CalendarDailyPage.this, HomePage.class);
        ActivityOptions options = ActivityOptions.makeCustomAnimation(CalendarDailyPage.this, R.anim.empty_anim, R.anim.empty_anim);
        startActivity(intent, options.toBundle());
    }

    @Override
    public void onMessagesButtonClick() {
        Intent intent = new Intent(CalendarDailyPage.this, MemberViewer.class);
        ActivityOptions options = ActivityOptions.makeCustomAnimation(CalendarDailyPage.this, R.anim.empty_anim, R.anim.empty_anim);
        startActivity(intent, options.toBundle());
    }

    @Override
    public void onProfileButtonClick() {
        Intent intent = new Intent(CalendarDailyPage.this, ProfilePage.class);
        ActivityOptions options = ActivityOptions.makeCustomAnimation(CalendarDailyPage.this, R.anim.empty_anim, R.anim.empty_anim);
        startActivity(intent, options.toBundle());
    }

    @Override
    public void onCreateEventButtonClick() {
        // Navigate to Create Events page
        Intent intent = new Intent(CalendarDailyPage.this, CreateEventPage.class);
        startActivity(intent);
    }

    private void getEventsRequest() {
        String username = WebSocketManager.getInstance().getUsername();
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL_STRING_REQ + username + "/events",
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
                                String eventType = jsonObject.getString("type");
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
                                    event_list.add(new Event(id, name, description, eventType, startTime, endTime));
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

