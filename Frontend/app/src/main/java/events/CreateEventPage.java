package events;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import utilities.DateAndTimeHelper;
import websockets.NotificationWebSocketManager;
import websockets.WebSocketManager;


/**
 * @author Joshua Gutierrez
 * The CreateEventPage class is an AppCompatActivity responsible for creating a new event
 * and sending the event details to the server via a POST request.
 */
public class CreateEventPage extends AppCompatActivity {
    private static final String URL_POST_REQUEST = "http://coms-309-024.class.las.iastate.edu:8080/users/";
    // Form fields
    private Spinner event_type;
    private EditText event_name;
    private EditText event_start_date;
    private EditText event_end_date;
    private EditText event_start_time;
    private EditText event_end_time;
    private EditText event_location;
    private EditText event_description;
    private EditText event_add_people;

    private final Context createEventPageContext = this;

    /**
     * Called when the activity is starting. This is where most initialization should go.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event_form);

        ImageButton back_button = findViewById(R.id.back_button);
        Button create_event_button = findViewById(R.id.create_event_button);

        // Get input values from the user
        event_type = findViewById(R.id.event_type_dropdown);
        event_name = findViewById(R.id.event_name_text);
        event_start_date = findViewById(R.id.start_date_input);
        event_end_date = findViewById(R.id.end_date_input);
        event_start_time = findViewById(R.id.start_time_input);
        event_end_time = findViewById(R.id.end_time_input);
        event_location = findViewById(R.id.event_location_input);
        event_description = findViewById(R.id.event_description_input);
        event_add_people = findViewById(R.id.event_add_people);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        event_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateAndTimeHelper helper = new DateAndTimeHelper(createEventPageContext);
                helper.showDatePicker(event_start_date);
            }
        });

        event_end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateAndTimeHelper helper = new DateAndTimeHelper(createEventPageContext);
                helper.showDatePicker(event_end_date);
            }
        });

        event_start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateAndTimeHelper helper = new DateAndTimeHelper(createEventPageContext);
                helper.showTimePicker(event_start_time);
            }
        });

        event_end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateAndTimeHelper helper = new DateAndTimeHelper(createEventPageContext);
                helper.showTimePicker(event_end_time);
            }
        });


        // Creates event
        create_event_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String event_type_value = event_type.getSelectedItem().toString();
                String event_name_value = event_name.getText().toString();

                String event_start_date_value = event_start_date.getText().toString();
                String event_end_date_value = event_end_date.getText().toString();
                String event_start_time_value = event_start_time.getText().toString();
                String event_end_time_value = event_end_time.getText().toString();

                String event_location_value = event_location.getText().toString();
                String event_description_value = event_description.getText().toString();
                String event_add_people_value = event_add_people.getText().toString();

                String event_start_date_final = DateAndTimeHelper.combineDateAndTime(event_start_date_value, event_start_time_value);
                String event_end_date_final = DateAndTimeHelper.combineDateAndTime(event_end_date_value, event_end_time_value);

                sendPostRequest(event_type_value, event_name_value, event_start_date_final, event_end_date_final, event_location_value, event_description_value, event_add_people_value);
            }
        });
    }

    private void sendPostRequest(String eventTypeValue, String eventNameValue, String eventStartDateValue, String eventEndDateValue, String eventDescriptionValue, String eventLocationValue, String eventAddPeopleValue) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject body = new JSONObject();

        try {
            body.put("type", eventTypeValue);
            body.put("name", eventNameValue);
            body.put("startDate", eventStartDateValue);
            body.put("endDate", eventEndDateValue);
            body.put("description", eventDescriptionValue);
            body.put("location", eventLocationValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String username = WebSocketManager.getInstance().getUsername();

        // Make the request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                URL_POST_REQUEST + username + "/events",
                body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), "New event created", Toast.LENGTH_SHORT).show();

                        try {
                            JSONObject json = new JSONObject();
                            json.put("type", "events");
                            json.put("title", "Event Invite");
                            json.put("typeId", response.get("id"));
                            json.put("description", "You have been invited to " + eventNameValue + "! Please accept or reject.");
                            NotificationWebSocketManager.getInstance().sendMessage(eventAddPeopleValue + "   " + json);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                        Intent intent = new Intent(CreateEventPage.this, EventsListViewer.class);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Something went wrong. Please try again", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }
}
