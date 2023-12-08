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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import api.VolleySingleton;
import events.CreateEventPage;
import events.Event;
import events.EventsListViewer;
import groups.MemberViewer;
import homepage.HomePage;
import profile.ProfilePage;
import profile.ProfilePageViewer;
import websockets.WebSocketManager;

/**
 * This class represents the daily view for the calendar page in a mobile application.
 * It extends AppCompatActivity and implements NavBarView.OnButtonClickListener for button click events.
 *
 * @author Tristan Nono
 */
public class CalendarDailyPage extends AppCompatActivity implements NavBarView.OnButtonClickListener {
    /**
     * The buttons for going to the next or previous day.
     */
    private ImageButton dayButtonNext, dayButtonPrev;

    /**
     * The day it is.
     */
    private TextView dayOfMonth;

    /**
     * This grabs the date.
     */
    private static String date_getter;

    /**
     * Calendar thing.
     */
    private Calendar calendar, currentWeek;

    /**
     * It's our navbar.
     */
    private NavBarView navbar_view;

    /**
     * The event adapter for the event list.
     */
    private EventCalendarMonthlyAdapter adapter;

    /**
     * Array list.
     */
    private List<Event> event_list;

    /**
     * This manages the layout.
     */
    private LinearLayoutManager layout_manager;

    /**
     * Recycler view aka from what I know it's how we display the list of items.
     */
    private RecyclerView recycler_view;

    /**
     * The button that brings up the popup menu displaying the views of the calendars.
     */
    private ImageButton menu_button;

    /**
     * Url request for making the calls to the api.
     */
    private static final String URL_STRING_REQ = "http://coms-309-024.class.las.iastate.edu:8080/users/";

    /**
     * Method called when the activity is created.
     * Initializes components, sets up listeners, and makes an initial request for events.
     *
     * @param savedInstanceState Bundle object containing the activity's previously saved state.
     */
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
            /**
             * This onClick advances the day forward.
             *
             * @param v view that has been clicked
             */
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
            /**
             * This onClick advances the day backward.
             *
             * @param v view that has been clicked
             */
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
            /**
             * Called when the specified {@code view} is clicked. Creates and displays a PopupMenu
             * anchored to the clicked view, inflates the options menu for the calendar, and shows the popup menu.
             *
             * @param view The view that was clicked.
             *             It can be used to identify which view triggered the click event.
             */
            @Override
            public void onClick(View view) {
                PopupMenu popup_menu = new PopupMenu(CalendarDailyPage.this, view);
                popup_menu.getMenuInflater().inflate(R.menu.options_menu_calendar, popup_menu.getMenu());
                popup_menu.show();

                popup_menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    /**
                     * Called when a menu item in the options menu is clicked.
                     * Performs actions based on the selected menu item, such as starting a new activity.
                     *
                     * @param menuItem The menu item that was clicked.
                     *                 It can be used to identify which menu item triggered the click event.
                     * @return true if the menu item click has been handled, false otherwise.
                     */
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

    /**
     * Updates the displayed date text on the UI.
     */
    private void updateDateText() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy", Locale.getDefault());
        String formattedDate = sdf.format(calendar.getTime());
        dayOfMonth.setText(formattedDate);
    }

    /**
     * Gets the current date for the given day of the week.
     *
     * @param dayOfWeek Integer representing the day of the week.
     * @return String containing the formatted date.
     */
    private String getCurrentDateForDay(int dayOfWeek) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek); // Set to the selected day of the week
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }

    /**
     * Gets the date for the specified day of the week based on the provided base date.
     *
     * @param dayOfWeek Integer representing the day of the week.
     * @param baseDate  Calendar object representing the base date.
     * @return String containing the formatted date.
     */
    private String getDateForDayOfWeek(int dayOfWeek, Calendar baseDate) {
        Calendar date = (Calendar) baseDate.clone();

        while (date.get(Calendar.DAY_OF_WEEK) != dayOfWeek) {
            date.add(Calendar.DAY_OF_MONTH, 1);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(date.getTime());
    }

    /**
     * Handles the click event on the calendar button in the navigation bar.
     * This method is part of the NavBarView.OnButtonClickListener interface.
     */
    @Override
    public void onCalendarButtonClick() {
        /*Intent intent = new Intent(this, CalendarMonthlyPage.class);
        startActivity(intent);*/
        //Log.d("Date", date_getter);
    }

    /**
     * Handles the click event on the home button in the navigation bar.
     * This method is part of the NavBarView.OnButtonClickListener interface.
     */
    @Override
    public void onHomeButtonClick() {
        Intent intent = new Intent(CalendarDailyPage.this, HomePage.class);
        ActivityOptions options = ActivityOptions.makeCustomAnimation(CalendarDailyPage.this, R.anim.empty_anim, R.anim.empty_anim);
        startActivity(intent, options.toBundle());
    }

    /**
     * Handles the click event on the messages button in the navigation bar.
     * This method is part of the NavBarView.OnButtonClickListener interface.
     */
    @Override
    public void onMessagesButtonClick() {
        Intent intent = new Intent(CalendarDailyPage.this, MemberViewer.class);
        ActivityOptions options = ActivityOptions.makeCustomAnimation(CalendarDailyPage.this, R.anim.empty_anim, R.anim.empty_anim);
        startActivity(intent, options.toBundle());
    }

    /**
     * Handles the click event on the profile button in the navigation bar.
     * This method is part of the NavBarView.OnButtonClickListener interface.
     */
    @Override
    public void onProfileButtonClick() {
        Intent intent = new Intent(CalendarDailyPage.this, ProfilePageViewer.class);
        ActivityOptions options = ActivityOptions.makeCustomAnimation(CalendarDailyPage.this, R.anim.empty_anim, R.anim.empty_anim);
        startActivity(intent, options.toBundle());
    }

    /**
     * Handles the click event on the create button in the navigation bar.
     * This method is part of the NavBarView.OnButtonClickListener interface.
     */
    @Override
    public void onCreateEventButtonClick() {
        // Navigate to Create Events page
        Intent intent = new Intent(CalendarDailyPage.this, CreateEventPage.class);
        startActivity(intent);
    }

    /**
     * Makes a request to the server to get events for the currently logged-in user.
     */
    private void getEventsRequest() {
        String username = WebSocketManager.getInstance().getUsername();
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL_STRING_REQ + username + "/events",
                new Response.Listener<String>() {
                    /**
                     * Callback method that is invoked when a network request succeeds and returns a response.
                     *
                     * @param response The response received from the network request.
                     *                 It is expected to be a JSON string representing an array.
                     * @throws RuntimeException If there is an error parsing the response as a JSON array.
                     */
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

                                SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault());
                                inputDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

                                Date startDate = inputDateFormat.parse(startDateStr);
                                Date endDate = inputDateFormat.parse(endDateStr);

                                Log.d("StartDate", startDate.toString());

                                // Convert to local time zone
                                SimpleDateFormat outputFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());
                                outputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

                                String startTime = outputFormat.format(startDate);
                                String endTime = outputFormat.format(endDate);

                                Log.d("StartDate", startDate.toString());

                                if (startDateStr.startsWith(date_getter)) {
                                    event_list.add(new Event(id, name, description, eventType, startTime, endTime));
                                }

                            } catch (JSONException | ParseException e) {
                                e.printStackTrace();
                            }
                        }

                        Collections.sort(event_list, new Comparator<Event>() {
                            @Override
                            public int compare(Event event1, Event event2) {
                                SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());

                                try {
                                    Date startTime1 = timeFormat.parse(event1.getStartTime());
                                    Date startTime2 = timeFormat.parse(event2.getStartTime());

                                    return startTime1.compareTo(startTime2);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                    return 0;
                                }
                            }
                        });

                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    /**
                     * Callback method that is invoked when a network request encounters an error.
                     *
                     * @param error The VolleyError object containing information about the error.
                     *              This can include details such as the error message, network response, and more.
                     *              It can be used for debugging and handling specific error scenarios.
                     */
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

