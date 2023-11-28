package calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.PopupMenu;
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
import events.EventsListViewer;
import groups.MemberViewer;
import com.example.myapplication.NavBarView;

import homepage.HomePage;
import profile.ProfilePage;
import websockets.WebSocketManager;

import com.example.myapplication.R;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Activity class for displaying a monthly view of the calendar with user events.
 * Users can navigate through months using the CalendarView and view events for selected dates.
 *
 * <p>
 * UI components:
 * - CalendarView: calendar_display (Displays the calendar for selecting dates)
 * - TextView: date_view (Displays the selected date)
 * - NavBarView: navbar_view (Custom navigation bar for switching between app sections)
 * - ImageButton: menu_button (Button to show a popup menu with calendar view options)
 * - RecyclerView: recycler_view (Displays a list of events for the selected date)
 * </p>
 *
 * <p>
 * Functionalities:
 * - Displays a monthly calendar using CalendarView.
 * - Updates the displayed events based on the selected date.
 * - Provides options to switch to a weekly view or view all events through a popup menu.
 * - Uses a RecyclerView to display a list of events for the selected date.
 * - Implements the NavBarView.OnButtonClickListener interface for handling navigation button clicks.
 * - Makes API requests to get the user's events for the selected date.
 * </p>
 *
 * <p>
 * The class also uses the EventCalendarMonthlyAdapter for managing the event list in the RecyclerView.
 * </p>
 *
 * @author Tristan Nono
 */
public class CalendarMonthlyPage extends AppCompatActivity implements NavBarView.OnButtonClickListener {
    /**
     * The calendar for displaying the calendar (I mean, I don't know what else to say).
     */
    private CalendarView calendar_display;

    /**
     * This grabs the date from the onSelectedDayChange.
     */
    private static String date_getter;

    /**
     * It's our navbar.
     */
    private NavBarView navbar_view;

    /**
     * This is for the transitioning between pages.
     */
    private ActivityOptions options;

    /**
     * Array list for the event list.
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
     * The event adapter for the event list.
     */
    private EventCalendarMonthlyAdapter adapter;

    /**
     * The button that brings up the popup menu displaying the views of the calendars.
     */
    private ImageButton menu_button;

    /**
     * URL request for the users mainly used for making the api calls and method requests.
     */
    private static final String URL_STRING_REQ = "http://coms-309-024.class.las.iastate.edu:8080/users/";

    /**
     * Initializes the activity, sets up UI components, and retrieves events for the current date.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state, or null if there was none.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_monthly_page);

        // Initialize
        calendar_display = findViewById(R.id.calendar_view);
        navbar_view = findViewById(R.id.navbar);
        navbar_view.setOnButtonClickListener(this);
        event_list = new ArrayList<>();
        adapter = new EventCalendarMonthlyAdapter(this, event_list);
        layout_manager = new LinearLayoutManager(this);
        menu_button = findViewById(R.id.menu_calendar_button);
        recycler_view = findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(layout_manager);
        recycler_view.setAdapter(adapter);
        navbar_view.setSelectedButton(navbar_view.getCalendarButton());


        // Set the on date change listener (so when the user clicks on the date, it does something).
        calendar_display.setOnDateChangeListener(
                new CalendarView.OnDateChangeListener() {

                    /**
                     * Called when the user selects a day on the {@code calendar_view}.
                     * Updates the selected date, displays it in the {@code date_view}, and requests events for the selected date.
                     *
                     * @param calendar_view The CalendarView that triggered the change event.
                     * @param year The selected year.
                     * @param month The selected month (0-indexed, i.e., January is 0).
                     * @param day_of_month The selected day of the month.
                     */
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView calendar_view, int year, int month, int day_of_month) {
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(year, month, day_of_month);

                        date_getter = String.format("%04d-%02d-%02d", year, month + 1, day_of_month);
                        // Request here everytime when a day is selected.
                        getEventsRequest();

                    }
                }
        );

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
                PopupMenu popup_menu = new PopupMenu(CalendarMonthlyPage.this, view);
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
                        if (menuItem.getItemId() == R.id.weekly_view) {
                            Intent intent = new Intent(CalendarMonthlyPage.this, CalendarWeeklyPage.class);
                            ActivityOptions options = ActivityOptions.makeCustomAnimation(CalendarMonthlyPage.this, R.anim.empty_anim, R.anim.empty_anim);
                            startActivity(intent, options.toBundle());
                        }

                        else if (menuItem.getItemId() == R.id.daily_view) {
                            Intent intent = new Intent(CalendarMonthlyPage.this, CalendarDailyPage.class);
                            ActivityOptions options = ActivityOptions.makeCustomAnimation(CalendarMonthlyPage.this, R.anim.empty_anim, R.anim.empty_anim);
                            startActivity(intent, options.toBundle());
                        }

                        else if (menuItem.getItemId() == R.id.all_events) {
                            Intent intent = new Intent(CalendarMonthlyPage.this, EventsListViewer.class);
                            ActivityOptions options = ActivityOptions.makeCustomAnimation(CalendarMonthlyPage.this, R.anim.empty_anim, R.anim.empty_anim);
                            startActivity(intent, options.toBundle());
                        }

                        return true;
                    }
                });

                popup_menu.show();
            }
        });

        // Get the current date just so it has the current date right away.
        Calendar currentDate = Calendar.getInstance();
        int year = currentDate.get(Calendar.YEAR);
        int month = currentDate.get(Calendar.MONTH) + 1;
        int day = currentDate.get(Calendar.DAY_OF_MONTH);

        date_getter = String.format("%04d-%02d-%02d", year, month, day);

        // Get the current date and set it as the selected date in the CalendarView.
        // Yeah, this basically just highlights the current day text in the calendar.
        Calendar current_date = Calendar.getInstance();
        long current_time_in_millis = current_date.getTimeInMillis();
        calendar_display.setDate(current_time_in_millis);

        // Request events from server
        getEventsRequest();
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
        Intent intent = new Intent(CalendarMonthlyPage.this, HomePage.class);
        ActivityOptions options = ActivityOptions.makeCustomAnimation(CalendarMonthlyPage.this, R.anim.empty_anim, R.anim.empty_anim);
        startActivity(intent, options.toBundle());
    }

    /**
     * Handles the click event on the messages button in the navigation bar.
     * This method is part of the NavBarView.OnButtonClickListener interface.
     */
    @Override
    public void onMessagesButtonClick() {
        Intent intent = new Intent(CalendarMonthlyPage.this, MemberViewer.class);
        options = ActivityOptions.makeCustomAnimation(CalendarMonthlyPage.this, R.anim.empty_anim, R.anim.empty_anim);
        startActivity(intent, options.toBundle());
    }

    /**
     * Handles the click event on the profile button in the navigation bar.
     * This method is part of the NavBarView.OnButtonClickListener interface.
     */
    @Override
    public void onProfileButtonClick() {
        Intent intent = new Intent(CalendarMonthlyPage.this, ProfilePage.class);
        options = ActivityOptions.makeCustomAnimation(CalendarMonthlyPage.this, R.anim.empty_anim, R.anim.empty_anim);
        startActivity(intent, options.toBundle());
    }

    /**
     * Handles the click event on the create button in the navigation bar.
     * This method is part of the NavBarView.OnButtonClickListener interface.
     */
    @Override
    public void onCreateEventButtonClick() {
        // Navigate to Create Events page
        Intent intent = new Intent(CalendarMonthlyPage.this, CreateEventPage.class);
        startActivity(intent);
    }

    /**
     * Makes an API request to get the user's events for the selected date and updates the event list.
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