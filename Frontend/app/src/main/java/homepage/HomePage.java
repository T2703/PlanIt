// Author: Tristan Nono

package homepage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Date;
import java.util.List;
import api.VolleySingleton;
import calendar.CalendarMonthlyPage;
import java.util.Calendar;
import events.CreateEventPage;
import events.Event;
import groups.MemberViewer;
import profile.ProfilePage;

/*
This is the homepage the main page of the app where the user can see their events and friends/other users
that they are in contact with. Basically the main hub.
 */
public class HomePage extends AppCompatActivity implements NavBarView.OnButtonClickListener {
    /*
    It's our navbar once more.
    */
    private NavBarView navbar_view;

    /*
    This is for the transitioning between pages.
    */
    private ActivityOptions options;

    /*
    Recycler view aka from what I know it's how we display the list of items.
    */
    private RecyclerView recycler_view;

    /*
    The event adapter for the event list.
    */
    private HomePageEventAdapter adapter;

    /*
    This manages the layout.
    */
    private LinearLayoutManager layout_manager;

    /*
    The list of the events.
    */
    private List<Event> event_list;

    /*
    The text that greets.
     */
    private TextView greeting_text_view;

    private static final String URL_STRING_REQ = "http://coms-309-024.class.las.iastate.edu:8080/events";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // Initialization
        event_list = new ArrayList<>();
        adapter = new HomePageEventAdapter(this, event_list);
        layout_manager = new LinearLayoutManager(this);
        recycler_view = findViewById(R.id.upcoming_event);
        recycler_view.setLayoutManager(layout_manager);
        recycler_view.setAdapter(adapter);

        navbar_view = findViewById(R.id.navbar);
        navbar_view.setOnButtonClickListener(this);
        navbar_view.setSelectedButton(navbar_view.getHomeButton());

        greeting_text_view = findViewById(R.id.greeting_text);
        setGreetingTextView();

        //getEventsRequest();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCalendarButtonClick() {
        Intent intent = new Intent(HomePage.this, CalendarMonthlyPage.class);
        options = ActivityOptions.makeCustomAnimation(HomePage.this, R.anim.empty_anim, R.anim.empty_anim);
        startActivity(intent, options.toBundle());
    }

    @Override
    public void onHomeButtonClick() {
        // Do nothing.
    }

    @Override
    public void onMessagesButtonClick() {
        Intent intent = new Intent(HomePage.this, MemberViewer.class);
        options = ActivityOptions.makeCustomAnimation(HomePage.this, R.anim.empty_anim, R.anim.empty_anim);
        startActivity(intent, options.toBundle());
    }

    @Override
    public void onProfileButtonClick() {
        Intent intent = new Intent(HomePage.this, ProfilePage.class);
        options = ActivityOptions.makeCustomAnimation(HomePage.this, R.anim.empty_anim, R.anim.empty_anim);
        startActivity(intent, options.toBundle());
    }

    @Override
    public void onCreateEventButtonClick() {
        // Navigate to Create Events page
        Intent intent = new Intent(HomePage.this, CreateEventPage.class);
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

                                event_list.add(new Event(id, name, description, startTime, endTime));

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

    /*
    This method changes the text based on the time of day. Should
    greet the users with a good morning if it's the morning, good afternoon
    if it's the afternoon, and good night if it's the night time.
     */
    private void setGreetingTextView() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        String greeting;

        // Morning
        if (hour >= 0 && hour < 12) {
            greeting = "Good morning.";
        }
        // Afternoon
        else if (hour >=12 && hour < 18) {
            greeting = "Good afternoon";
        }
        // Night
        else {
            greeting = "Good night.";
        }

        greeting_text_view.setText(greeting);
    }

}