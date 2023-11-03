package homepage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.myapplication.NavBarView;
import com.example.myapplication.R;

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
import profile.ProfilePage;

/*
This is the homepage the main page of the app where the user can see their events and friends/other users
that they are in contact with. Basically the main hub.
 */
public class HomePage extends AppCompatActivity implements NavBarView.OnButtonClickListener {
    private RecyclerView activeUsersRecyclerView;
    private RecyclerView assignmentsRecyclerView;
    private UserAdapter userAdapter;
    private AssignmentAdapter assignmentsAdapter;
    private List<User> userList;
    private List<Assignment> assignmentsList;

    private NavBarView navbar_view;

    private TextView homePageGreeting;
    private ImageView notificationButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);


        // Open menu
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);







        navbar_view = findViewById(R.id.navbar);
        navbar_view.setOnButtonClickListener(this);
        navbar_view.setSelectedButton(navbar_view.getHomeButton());

        notificationButton = findViewById(R.id.notificationIcon);

        homePageGreeting = findViewById(R.id.homepage_greeting);
        TextView todayDate = findViewById(R.id.homepage_today_date);

        todayDate.setText(getTodayDate());

        // Update active users
        activeUsersRecyclerView = findViewById(R.id.activeUsersRecyclerView);
        userList = new ArrayList<>();

        // Create and set the adapter
        userAdapter = new UserAdapter(userList);
        activeUsersRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        activeUsersRecyclerView.setAdapter(userAdapter);

        // Add users to the RecyclerView
        userList.add(new User(R.drawable.icons8_avatar_48, true));
        userList.add(new User(R.drawable.icons8_avatar_48, true));
        userList.add(new User(R.drawable.icons8_avatar_48, true));
        userList.add(new User(R.drawable.icons8_avatar_48, true));
        userList.add(new User(R.drawable.icons8_avatar_48, true));
        userList.add(new User(R.drawable.icons8_avatar_48, true));
        userList.add(new User(R.drawable.icons8_avatar_48, true));
        userList.add(new User(R.drawable.icons8_avatar_48, true));
        userList.add(new User(R.drawable.icons8_avatar_48, true));
        userList.add(new User(R.drawable.icons8_avatar_48, true));
        userList.add(new User(R.drawable.add_user_icon, false));

        // Update assignments
        assignmentsRecyclerView = findViewById(R.id.assignments_recyclerView);
        assignmentsList = new ArrayList<>();

        assignmentsAdapter = new AssignmentAdapter(assignmentsList);
        assignmentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        assignmentsRecyclerView.setAdapter(assignmentsAdapter);

        assignmentsList.add(new Assignment("COM S 309", "Demo 3", "Nov 10th"));
        assignmentsList.add(new Assignment("COM S 321", "Programming Assignment 2", "Nov 30th"));
        assignmentsList.add(new Assignment("COM S 321", "Programming Assignment 2", "Nov 30th"));

        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, NotificationPage.class);
                startActivity(intent);
            }
        });

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
        if (hour > 0 && hour < 12) {
            homePageGreeting.setText("Good morning, Joshua");
        } else if (hour >= 12 && hour < 18) {
            homePageGreeting.setText("Good afternon, Joshua");
        } else {
            homePageGreeting.setText("Good evening, Joshua");
        }
    }

    @Override
    public void onCalendarButtonClick() {
        Intent intent = new Intent(HomePage.this, CalendarMonthlyPage.class);
        ActivityOptions options = ActivityOptions.makeCustomAnimation(HomePage.this, R.anim.empty_anim, R.anim.empty_anim);
        startActivity(intent, options.toBundle());
    }

    @Override
    public void onHomeButtonClick() {

    }

    @Override
    public void onMessagesButtonClick() {
        Intent intent = new Intent(HomePage.this, MemberViewer.class);
        ActivityOptions options = ActivityOptions.makeCustomAnimation(HomePage.this, R.anim.empty_anim, R.anim.empty_anim);
        startActivity(intent, options.toBundle());
    }

    @Override
    public void onProfileButtonClick() {
        Intent intent = new Intent(HomePage.this, ProfilePage.class);
        ActivityOptions options = ActivityOptions.makeCustomAnimation(HomePage.this, R.anim.empty_anim, R.anim.empty_anim);
        startActivity(intent, options.toBundle());
    }

    @Override
    public void onCreateEventButtonClick() {
        // Navigate to Create Events page
        Intent intent = new Intent(HomePage.this, CreateEventPage.class);
        startActivity(intent);
    }
}