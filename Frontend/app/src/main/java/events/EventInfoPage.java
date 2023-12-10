package events;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
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
import calendar.EventCalendarMonthlyAdapter;
import groups.GroupInfo;
import groups.MemberViewer;
import websockets.WebSocketManager;

/**
 * The event info page. Where the information for events can be seen.
 */
public class EventInfoPage extends AppCompatActivity {
    /**
     * URL request for the users mainly used for making the api calls and method requests.
     */
    private static final String URL_STRING_REQ = "http://coms-309-024.class.las.iastate.edu:8080/users/";

    /**
     * Array list.
     */
    private List<Event> event_list;

    /**
     * The event adapter for the event list.
     */
    private EventCalendarMonthlyAdapter adapter;

    /**
     * Gets the group name.
     */
    private String getting_event_name;

    /**
     * Gets the group description.
     */
    private String getting_event_desc;

    /**
     * Gets the group ID.
     */
    private String getting_event_id;

    /**
     * Gets the stuff.
     */
    private String getting_event_location, getting_event_start_date, getting_event_end_date, getting_event_type;

    /**
     * Attributes of the group.
     */
    private TextView event_name, event_description, event_location, event_type, event_start_date, event_end_date;

    /**
     * Back button.
     */
    private ImageButton back_button;

    /**
     * Called when the activity is first created. Initializes UI components, adapters,
     * and performs a request to the server to get the list of events.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state, or null
     *                           if the activity is being started fresh.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info_page);

        event_list = new ArrayList<>();
        adapter = new EventCalendarMonthlyAdapter(this, event_list);
        event_name = findViewById(R.id.eventTitle);
        event_description = findViewById(R.id.eventDesc);
        event_location = findViewById(R.id.eventLocation);
        event_type = findViewById(R.id.eventType);
        event_start_date = findViewById(R.id.eventTimes);
        event_end_date = findViewById(R.id.eventTimes2);
        back_button = findViewById(R.id.back_button_two);
        getting_event_name = getIntent().getStringExtra("name");
        getting_event_desc = getIntent().getStringExtra("description");
        getting_event_id = getIntent().getStringExtra("id");
        getting_event_location = getIntent().getStringExtra("location");
        getting_event_type = getIntent().getStringExtra("type");
        getting_event_start_date = getIntent().getStringExtra("start_date");
        getting_event_end_date = getIntent().getStringExtra("end_date");

        event_name.setText(getting_event_name);
        event_description.setText(getting_event_desc);
        event_location.setText("Location: " + getting_event_location);
        event_type.setText("Type: " + getting_event_type);
        event_start_date.setText(getting_event_start_date);
        event_end_date.setText(getting_event_end_date);
        getEventsRequest();

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.empty_anim, R.anim.empty_anim);
            }
        });

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

                        // Iterate
                        for (int i = 0; i < responseArray.length(); i++) {
                            try {
                                JSONObject jsonObject = responseArray.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String name = jsonObject.getString("name");
                                String description = jsonObject.getString("description");
                                String location = jsonObject.getString("location");
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

                                event_list.add(new Event(id, name, description, eventType, startTime, endTime, location));

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