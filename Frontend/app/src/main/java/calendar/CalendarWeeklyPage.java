package calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.AnalyzeSchedule;
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
import profile.CreateAccountPage;
import profile.LoginFormPage;
import profile.ProfilePage;
import websockets.WebSocketManager;

/**
 * Activity class for displaying a weekly view of the calendar with user events.
 * Users can navigate through weeks using gesture detection and view events for each day.
 *
 * <p>
 * UI components:
 * - TextView: sunDate, monDate, tueDate, wedDate, thuDate, friDate, satDate (Displays dates for each day of the week)
 * - TextView: currentMonth, currentYear (Displays the current month and year)
 * - TextView: weekButtonNext, weekButtonPrev (Buttons for navigating to the next or previous week)
 * - ImageButton: menu_button (Button to show a popup menu with calendar view options)
 * - NavBarView: navbar_view (Custom navigation bar for switching between app sections)
 * - RecyclerView: recycler_view (Displays a list of events for the selected date)
 * - Button: analyzeWeek (Button to go to the Analyze Week page)
 * </p>
 *
 * <p>
 * Functionalities:
 * - Displays a weekly view of the calendar with dates for each day of the week.
 * - Allows users to navigate to the next or previous week.
 * - Provides options to switch to a monthly view, daily view, or view all events through a popup menu.
 * - Uses gesture detection for navigating between weeks.
 * - Uses a RecyclerView to display a list of events for the selected week.
 * - Implements the NavBarView.OnButtonClickListener interface for handling navigation button clicks.
 * - Makes API requests to get the user's events for the selected week.
 * </p>
 *
 * <p>
 * The class also uses the EventCalendarMonthlyAdapter for managing the event list in the RecyclerView.
 * </p>
 *
 * @author Tristan Nono
 */
public class CalendarWeeklyPage extends AppCompatActivity implements NavBarView.OnButtonClickListener {
    /**
     * The buttons for going to the next or previous week.
     */
    private TextView weekButtonNext, weekButtonPrev;

    /**
     * The dates of the day from Mon-Sun.
     */
    private TextView sunDate, monDate, tueDate, wedDate, thuDate, friDate, satDate;

    /**
     * The current month.
     */
    private TextView currentMonth;

    /**
     * The current year.
     */
    private TextView currentYear;

    /**
     * Gesture Detector.
     */
    private GestureDetector gestureDetector;

    /**
     * Calendar instance.
     */
    private Calendar calendar, currentWeek;

    /**
     * It's our navbar.
     */
    private NavBarView navbar_view;

    /**
     * Recycler view, used to display a list of items.
     */
    private RecyclerView recycler_view;

    /**
     * Array event list.
     */
    private List<Event> event_list;

    /**
     * Manages the layout for the RecyclerView.
     */
    private LinearLayoutManager layout_manager;

    /**
     * Event adapter for the event list.
     */
    private EventCalendarMonthlyAdapter adapter;

    /**
     * Grabs the date.
     */
    private static String date_getter;

    /**
     * The button that brings up the popup menu displaying the views of the calendars.
     */
    private ImageButton menu_button;


    /**
     * Button to go to the analyze week page.
     */
    private Button analyzeWeek;

    /**
     * URL request for the users mainly used for making the api calls and method requests.
     */
    private static final String URL_STRING_REQ = "http://coms-309-024.class.las.iastate.edu:8080/users/";

    /**
     * Initializes the activity, sets up UI components, and retrieves events for the current week.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state, or null if there was none.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_weekly_page);

        // Initialize
        analyzeWeek = findViewById(R.id.analyzeWeek);
        sunDate = findViewById(R.id.sunDate);
        monDate = findViewById(R.id.monDate);
        tueDate = findViewById(R.id.tueDate);
        wedDate = findViewById(R.id.wedDate);
        thuDate = findViewById(R.id.thuDate);
        friDate = findViewById(R.id.friDate);
        satDate = findViewById(R.id.satDate);
        weekButtonNext = findViewById(R.id.nextWeek);
        weekButtonPrev = findViewById(R.id.prevWeek);
        currentMonth = findViewById(R.id.month_text_view);
        currentYear = findViewById(R.id.year_text_view);
        menu_button = findViewById(R.id.menu_calendar_button);
        navbar_view = findViewById(R.id.navbar);
        navbar_view.setOnButtonClickListener(this);
        navbar_view.setSelectedButton(navbar_view.getCalendarButton());
        event_list = new ArrayList<>();
        adapter = new EventCalendarMonthlyAdapter(this, event_list);
        layout_manager = new LinearLayoutManager(this);

        recycler_view = findViewById(R.id.event_item_list_view);
        recycler_view.setLayoutManager(layout_manager);
        recycler_view.setAdapter(adapter);

        navbar_view.setSelectedButton(navbar_view.getCalendarButton());

        // Get the current date
        calendar = Calendar.getInstance();
        currentWeek = (Calendar) calendar.clone();
        SimpleDateFormat sdf = new SimpleDateFormat("d", Locale.getDefault());
        SimpleDateFormat monthSdf = new SimpleDateFormat("MMMM", Locale.getDefault());
        SimpleDateFormat yearSdf = new SimpleDateFormat("yyyy", Locale.getDefault());

        int currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        while (currentDayOfWeek != Calendar.SUNDAY) {
            currentWeek.add(Calendar.DAY_OF_MONTH, -1);
            currentDayOfWeek = (currentDayOfWeek - 1) % 7;
        }

        // Calculate days of the week and month using the 'calendar' object
        for (int i = Calendar.SUNDAY; i <= Calendar.SATURDAY; i++) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            if (i == Calendar.SUNDAY) {
                sunDate.setText(sdf.format(calendar.getTime()));
            } else if (i == Calendar.MONDAY) {
                monDate.setText(sdf.format(calendar.getTime()));
            } else if (i == Calendar.TUESDAY) {
                tueDate.setText(sdf.format(calendar.getTime()));
            } else if (i == Calendar.WEDNESDAY) {
                wedDate.setText(sdf.format(calendar.getTime()));
            } else if (i == Calendar.THURSDAY) {
                thuDate.setText(sdf.format(calendar.getTime()));
            } else if (i == Calendar.FRIDAY) {
                friDate.setText(sdf.format(calendar.getTime()));
            } else if (i == Calendar.SATURDAY) {
                satDate.setText(sdf.format(calendar.getTime()));
            }
        }

        String monthYear = monthSdf.format(calendar.getTime()) + " " + yearSdf.format(calendar.getTime());
        currentMonth.setText(monthYear);
        currentYear.setText(yearSdf.format(calendar.getTime()));

        date_getter = getCurrentDateForDay(Calendar.DAY_OF_MONTH - 1);


        menu_button.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when the specified view is clicked. Creates and displays a {@link PopupMenu}
             * anchored to the clicked view, inflates the options menu for the calendar, and shows the popup menu.
             *
             * @param view The view that was clicked.
             *             It can be used to identify which view triggered the click event.
             */
            @Override
            public void onClick(View view) {
                PopupMenu popup_menu = new PopupMenu(CalendarWeeklyPage.this, view);
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
                            Intent intent = new Intent(CalendarWeeklyPage.this, CalendarMonthlyPage.class);
                            ActivityOptions options = ActivityOptions.makeCustomAnimation(CalendarWeeklyPage.this, R.anim.empty_anim, R.anim.empty_anim);
                            startActivity(intent, options.toBundle());
                        }

                        else if (menuItem.getItemId() == R.id.all_events) {
                            Intent intent = new Intent(CalendarWeeklyPage.this, EventsListViewer.class);
                            ActivityOptions options = ActivityOptions.makeCustomAnimation(CalendarWeeklyPage.this, R.anim.empty_anim, R.anim.empty_anim);
                            startActivity(intent, options.toBundle());
                        }

                        else if (menuItem.getItemId() == R.id.daily_view) {
                            Log.d("THING", date_getter);
                        }

                        return true;
                    }
                });

                popup_menu.show();
            }
        });

        weekButtonNext.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when the specified code v (view) is clicked. Increments the current week by one,
             * updates the date getter, updates the calendar view, and requests events for the updated date.
             *
             * @param v The view that was clicked.
             *          It can be used to identify which view triggered the click event.
             */
            @Override
            public void onClick(View v) {
                currentWeek.add(Calendar.WEEK_OF_YEAR, 1);
                Calendar date = (Calendar) currentWeek.clone();
                // This pretty much updates the date_getter.
                date_getter = getDateForDayOfWeek(Calendar.DAY_OF_MONTH, currentWeek);
                updateCalendarView();
                getEventsRequest();
            }
        });

        weekButtonPrev.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when the specified v (view) is clicked. Decrements the current week by one,
             * updates the date getter, updates the calendar view, and requests events for the updated date.
             *
             * @param v The view that was clicked.
             *          It can be used to identify which view triggered the click event.
             */
            @Override
            public void onClick(View v) {
                currentWeek.add(Calendar.WEEK_OF_YEAR, -1);
                Calendar date = (Calendar) currentWeek.clone();
                date_getter = getDateForDayOfWeek(Calendar.DAY_OF_MONTH - 1, currentWeek);
                updateCalendarView();
                getEventsRequest();
            }
        });

        sunDate.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when the specified view is clicked. Updates the date getter with the date
             * of the current week's Sunday, requests events for the updated date, and logs the selected Sunday's date.
             *
             * @param view The view that was clicked.
             *             It can be used to identify which view triggered the click event.
             */
            @Override
            public void onClick(View view) {
                date_getter = getDateForDayOfWeek(Calendar.SUNDAY, currentWeek);
                getEventsRequest();
                Log.d("Sunday", date_getter);
            }
        });

        monDate.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when the specified view is clicked. Updates the date getter with the date
             * of the current week's Monday, requests events for the updated date, and logs the selected Monday's date.
             *
             * @param view The view that was clicked.
             *             It can be used to identify which view triggered the click event.
             */
            @Override
            public void onClick(View view) {
                date_getter = getDateForDayOfWeek(Calendar.MONDAY, currentWeek);
                getEventsRequest();
                Log.d("Monday", date_getter);
            }
        });

        tueDate.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when the specified view is clicked. Updates the date getter with the date
             * of the current week's Tuesday, requests events for the updated date, and logs the selected Tuesday's date.
             *
             * @param view The view that was clicked.
             *             It can be used to identify which view triggered the click event.
             */
            @Override
            public void onClick(View view) {
                date_getter = getDateForDayOfWeek(Calendar.TUESDAY, currentWeek);
                getEventsRequest();
                Log.d("Day", "Tue");
            }
        });

        wedDate.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when the specified view is clicked. Updates the date getter with the date
             * of the current week's Wednesday requests events for the updated date, and logs the selected Wednesday's date.
             *
             * @param view The view that was clicked.
             *             It can be used to identify which view triggered the click event.
             */
            @Override
            public void onClick(View view) {
                date_getter = getDateForDayOfWeek(Calendar.WEDNESDAY, currentWeek);
                getEventsRequest();
                Log.d("Wednesday", date_getter);
            }
        });

        thuDate.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when the specified view is clicked. Updates the date getter with the date
             * of the current week's Thursday, requests events for the updated date, and logs the selected Thursday's date.
             *
             * @param view The view that was clicked.
             *             It can be used to identify which view triggered the click event.
             */
            @Override
            public void onClick(View view) {
                date_getter = getDateForDayOfWeek(Calendar.THURSDAY, currentWeek);
                getEventsRequest();
                Log.d("Thursday", date_getter);
            }
        });

        friDate.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when the specified view is clicked. Updates the date getter with the date
             * of the current week's Friday, requests events for the updated date, and logs the selected Friday's date.
             *
             * @param view The view that was clicked.
             *             It can be used to identify which view triggered the click event.
             */
            @Override
            public void onClick(View view) {
                date_getter = getDateForDayOfWeek(Calendar.FRIDAY, currentWeek);
                getEventsRequest();
                Log.d("Friday", date_getter);
            }
        });

        satDate.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when the specified view is clicked. Updates the date getter with the date
             * of the current week's Saturday, requests events for the updated date, and logs the selected Saturday's date.
             *
             * @param view The view that was clicked.
             *             It can be used to identify which view triggered the click event.
             */
            @Override
            public void onClick(View view) {
                date_getter = getDateForDayOfWeek(Calendar.SATURDAY, currentWeek);
                getEventsRequest();
                Log.d("Saturday", date_getter);
            }
        });

        analyzeWeek.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when the specified view is clicked. Launches an Intent to navigate
             * from the current CalendarWeeklyPage to the AnalyzeSchedule activity.
             *
             * @param view The view that was clicked.
             *             It can be used to identify which view triggered the click event.
             */
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(CalendarWeeklyPage.this, AnalyzeSchedule.class);
               startActivity(intent);
                //Log.d("TEST", "TEST2");
            }
        });

        getEventsRequest();
        updateCalendarView();

    }


    /**
    Updates the calendar view.
     */
    private void updateCalendarView() {
        SimpleDateFormat sdf = new SimpleDateFormat("d", Locale.getDefault());
        SimpleDateFormat monthSdf = new SimpleDateFormat("MMMM", Locale.getDefault());
        SimpleDateFormat yearSdf = new SimpleDateFormat("yyyy", Locale.getDefault());

        calendar.setTime(currentWeek.getTime());

        sunDate.setText(sdf.format(calendar.getTime()));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        monDate.setText(sdf.format(calendar.getTime()));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        tueDate.setText(sdf.format(calendar.getTime()));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        wedDate.setText(sdf.format(calendar.getTime()));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        thuDate.setText(sdf.format(calendar.getTime()));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        friDate.setText(sdf.format(calendar.getTime()));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        satDate.setText(sdf.format(calendar.getTime()));

        String monthYear = monthSdf.format(currentWeek.getTime()) + " " + yearSdf.format(calendar.getTime());
        currentMonth.setText(monthYear);
        currentYear.setText(yearSdf.format(currentWeek.getTime()));

    }

    /**
     * Handles the click event on the calendar button in the navigation bar.
     * This method is part of the NavBarView.OnButtonClickListener interface.
     */
    @Override
    public void onCalendarButtonClick() {
        // Does nothing.
    }

    /**
     * Handles the click event on the home button in the navigation bar.
     * This method is part of the NavBarView.OnButtonClickListener interface.
     */
    @Override
    public void onHomeButtonClick() {
        Intent intent = new Intent(CalendarWeeklyPage.this, HomePage.class);
        ActivityOptions options = ActivityOptions.makeCustomAnimation(CalendarWeeklyPage.this, R.anim.empty_anim, R.anim.empty_anim);
        startActivity(intent, options.toBundle());
    }

    /**
     * Handles the click event on the messages button in the navigation bar.
     * This method is part of the NavBarView.OnButtonClickListener interface.
     */
    @Override
    public void onMessagesButtonClick() {
        Intent intent = new Intent(CalendarWeeklyPage.this, MemberViewer.class);
        ActivityOptions options = ActivityOptions.makeCustomAnimation(CalendarWeeklyPage.this, R.anim.empty_anim, R.anim.empty_anim);
        startActivity(intent, options.toBundle());
    }

    /**
     * Handles the click event on the profile button in the navigation bar.
     * This method is part of the NavBarView.OnButtonClickListener interface.
     */
    @Override
    public void onProfileButtonClick() {
        Intent intent = new Intent(CalendarWeeklyPage.this, ProfilePage.class);
        ActivityOptions options = ActivityOptions.makeCustomAnimation(CalendarWeeklyPage.this, R.anim.empty_anim, R.anim.empty_anim);
        startActivity(intent, options.toBundle());
    }

    /**
     * Handles the click event on the create button in the navigation bar.
     * This method is part of the NavBarView.OnButtonClickListener interface.
     */
    @Override
    public void onCreateEventButtonClick() {
        // Navigate to Create Events page
        Intent intent = new Intent(CalendarWeeklyPage.this, CreateEventPage.class);
        startActivity(intent);
    }

    /**
     * Gets the current date for a specified day of the week.
     *
     * @param dayOfWeek The day of the week (Calendar.DAY_OF_WEEK) for which to get the current date.
     * @return A formatted date string (yyyy-MM-dd) for the specified day.
     */
    private String getCurrentDateForDay(int dayOfWeek) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek); // Set to the selected day of the week
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }

    /**
     * Makes a request to the server to get the list of events for the user.
     * Populates the event_list and updates the adapter to reflect the changes.
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

    /**
     * Gets the date for a specific day of the week relative to a base date.
     *
     * @param dayOfWeek The day of the week (Calendar.DAY_OF_WEEK) to get the date for.
     * @param baseDate   The base date from which the calculation starts.
     * @return A formatted date string (yyyy-MM-dd) for the specified day of the week.
     */
    private String getDateForDayOfWeek(int dayOfWeek, Calendar baseDate) {
        Calendar date = (Calendar) baseDate.clone();

        while (date.get(Calendar.DAY_OF_WEEK) != dayOfWeek) {
            date.add(Calendar.DAY_OF_MONTH, 1);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(date.getTime());
    }

}



