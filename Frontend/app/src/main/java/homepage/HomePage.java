package homepage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import com.android.volley.VolleyError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.NavBarView;
import com.example.myapplication.R;
import com.google.android.material.navigation.NavigationView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.java_websocket.handshake.ServerHandshake;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import calendar.CalendarMonthlyPage;
import events.CreateEventPage;
import groups.MemberViewer;
import notifications.NotificationPage;
import profile.LoginFormPage;
import profile.ProfilePage;
import utilities.NotificationsHelper;
import websockets.WebSocketListener;
import websockets.WebSocketManager;

/**
 * @author Joshua Gutierrez
 * The main activity representing the home page of the application.
 * The home page displays user greetings, active users, upcoming assignments, and provides navigation options.
 * Implements the {@link NavBarView.OnButtonClickListener} for handling bottom navigation events and
 * {@link WebSocketListener} for WebSocket communication.
 */
public class HomePage extends AppCompatActivity implements NavBarView.OnButtonClickListener, WebSocketListener {
    // Constants
    private static final String URL_ACTIVE_WEBSOCKET = "ws://coms-309-024.class.las.iastate.edu:8080/active/";

    // UI components
    private RecyclerView activeUsersRecyclerView;
    private RecyclerView assignmentsRecyclerView;
    private UserAdapter userAdapter;
    private AssignmentAdapter assignmentsAdapter;
    private List<User> userList;
    private List<Assignment> assignmentsList;
    private NavBarView navbar_view;
    private TextView homePageGreeting;
    private ImageView notificationButton;
    private ImageButton menuButton;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down, this Bundle contains the data it
     *                           most recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navView = findViewById(R.id.navView);

        MenuHeader menuHeader = new MenuHeader(this);

        View headerView = navView.getHeaderView(0);
        TextView headerUsername = headerView.findViewById(R.id.menuHeaderUsername);
        TextView headerEmailAddress = headerView.findViewById(R.id.menuHeaderEmailAddress);

        String usernameText = menuHeader.getUsername();
        menuHeader.getUserEmailAddress(usernameText, headerEmailAddress);

        headerUsername.setText(usernameText);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        String username = WebSocketManager.getInstance().getUsername();
        WebSocketManager.getInstance().connectWebSocket(URL_ACTIVE_WEBSOCKET + username);
        WebSocketManager.getInstance().setWebSocketListener(HomePage.this);
        WebSocketManager.getInstance().sendMessage("Update User List");

        TextView notificationsCount = findViewById(R.id.notificationCount);
        NotificationsHelper.setNumberOfUnreadNotifications(notificationsCount);

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.menu_logout) {
                    WebSocketManager.getInstance().disconnectWebSocket();

                    Intent intent = new Intent(HomePage.this, LoginFormPage.class);
                    startActivity(intent);
                }
                return true;
            }
        });

        menuButton = findViewById(R.id.menuButton);

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navbar_view = findViewById(R.id.navbar);
        navbar_view.setOnButtonClickListener(this);
        navbar_view.setSelectedButton(navbar_view.getHomeButton());

        notificationButton = findViewById(R.id.notificationIcon);

        homePageGreeting = findViewById(R.id.homepage_greeting);
        TextView todayDate = findViewById(R.id.homepage_today_date);

        todayDate.setText(getTodayDate());

        activeUsersRecyclerView = findViewById(R.id.activeUsersRecyclerView);
        userList = new ArrayList<>();

        userAdapter = new UserAdapter(userList);
        activeUsersRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        activeUsersRecyclerView.setAdapter(userAdapter);

        assignmentsRecyclerView = findViewById(R.id.assignments_recyclerView);
        assignmentsList = new ArrayList<>();

        assignmentsAdapter = new AssignmentAdapter(assignmentsList);
        assignmentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        assignmentsRecyclerView.setAdapter(assignmentsAdapter);

        getAssignments();

        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, NotificationPage.class);
                startActivity(intent);
            }
        });
    }

    private void getAssignments() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String URL = "http://coms-309-024.class.las.iastate.edu:8080/assignments";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject assignment = response.getJSONObject(i);

                        if (assignment.getString("isCompleted").equals("false")) {
                            Log.d("found", "yes");
                            String course = assignment.getString("course");
                            String title = assignment.getString("title");
                            String dueDate = assignment.getString("dueDate");

                            assignmentsList.add(new Assignment(course, title, dueDate));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                assignmentsAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Assignments could not be loaded", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    private String getTodayDate() {
        Date today = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);

        int year = calendar.get(Calendar.YEAR);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.getDefault());
        String monthName = monthFormat.format(today);

        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
        String dayName = dayFormat.format(today);

        displayGreeting(hour);

        return dayName + " " + monthName + " " + day + ", " + year;
    }

    private void displayGreeting(int hour) {
        String username = WebSocketManager.getInstance().getUsername();
        String greeting = "";

        if (hour > 0 && hour < 12) {
            greeting = "Good morning, " + username;
        } else if (hour >= 12 && hour < 18) {
            greeting = "Good afternoon, " + username;
        } else {
            greeting = "Good evening, " + username;
        }

        homePageGreeting.setText(greeting);
    }

    /**
     * Handles the click event for the calendar button in the {@link HomePage}.
     * Initiates the navigation to the {@link CalendarMonthlyPage} with a custom animation.
     * Uses an {@link Intent} to start the new activity and {@link ActivityOptions} to specify custom animations.
     */
    @Override
    public void onCalendarButtonClick() {
        Intent intent = new Intent(HomePage.this, CalendarMonthlyPage.class);
        ActivityOptions options = ActivityOptions.makeCustomAnimation(HomePage.this, R.anim.empty_anim, R.anim.empty_anim);
        startActivity(intent, options.toBundle());
    }

    /**
     * Placeholder method for handling the click event on the home button in the {@link HomePage}.
     * This method does not perform any specific actions and can be overridden as needed.
     */
    @Override
    public void onHomeButtonClick() {

    }

    /**
     * Handles the click event for the messages button in the {@link HomePage}.
     * Initiates the navigation to the {@link MemberViewer} with a custom animation.
     * Uses an {@link Intent} to start the new activity and {@link ActivityOptions} to specify custom animations.
     */
    @Override
    public void onMessagesButtonClick() {
        Intent intent = new Intent(HomePage.this, MemberViewer.class);
        ActivityOptions options = ActivityOptions.makeCustomAnimation(HomePage.this, R.anim.empty_anim, R.anim.empty_anim);
        startActivity(intent, options.toBundle());
    }

    /**
     * Handles the click event for the profile button in the {@link HomePage}.
     * Initiates the navigation to the {@link ProfilePage} with a custom animation.
     * Uses an {@link Intent} to start the new activity and {@link ActivityOptions} to specify custom animations.
     */
    @Override
    public void onProfileButtonClick() {
        Intent intent = new Intent(HomePage.this, ProfilePage.class);
        ActivityOptions options = ActivityOptions.makeCustomAnimation(HomePage.this, R.anim.empty_anim, R.anim.empty_anim);
        startActivity(intent, options.toBundle());
    }

    /**
     * Handles the click event for the "Create Event" button in the {@link HomePage}.
     * Initiates the navigation to the {@link CreateEventPage}.
     * Uses an {@link Intent} to start the new activity.
     */
    @Override
    public void onCreateEventButtonClick() {
        Intent intent = new Intent(HomePage.this, CreateEventPage.class);
        startActivity(intent);
    }

    /**
     * Callback method triggered when a WebSocket connection is opened.
     * Logs the information about the open event using {@link Log}.
     *
     * @param handshakedata Information about the WebSocket handshake.
     */
    @Override
    public void onWebSocketOpen(ServerHandshake handshakedata) {
        Log.d("ON OPEN", handshakedata.toString());
    }

    /**
     * Callback method triggered when a WebSocket message is received.
     * Parses the received message to determine the number of active users and updates the UI accordingly.
     *
     * @param message The WebSocket message containing information about active users.
     */
    @Override
    public void onWebSocketMessage(String message) {
        int activeUsers = Integer.parseInt(message);

        runOnUiThread(() -> {
            userList.clear();
            for (int i = 0; i < activeUsers; i++) {
                userList.add(new User(R.drawable.icons8_avatar_48, true));
            }
            userAdapter.notifyDataSetChanged();
        });
    }

    /**
     * Callback method triggered when a WebSocket connection is closed.
     *
     * @param code   The close code.
     * @param reason The reason for the close.
     * @param remote Indicates if the close was initiated by the remote party.
     */
    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {

    }

    /**
     * Callback method triggered when a WebSocket error occurs.
     *
     * @param ex The exception representing the WebSocket error.
     */
    @Override
    public void onWebSocketError(Exception ex) {
    }
}