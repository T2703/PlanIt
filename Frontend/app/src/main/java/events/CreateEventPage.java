package events;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.NavBar;
import com.example.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateEventPage extends AppCompatActivity {
    private static final String URL_POST_REQUEST = "http://coms-309-024.class.las.iastate.edu:8080/events";
    // Form fields
    private Spinner event_type;
    private EditText event_name;
    private EditText event_date;
    private EditText event_start_time;
    private EditText event_end_time;
    private EditText event_location;
    private EditText event_description;

    private ImageButton back_button;
    private Button create_event_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event_form);

        back_button = findViewById(R.id.back_button);
        create_event_button = findViewById(R.id.create_event_button);

        // Get input values from the user
        event_type = findViewById(R.id.event_type_dropdown);
        event_name = findViewById(R.id.event_name_text);
        event_date = findViewById(R.id.event_date);
        event_start_time = findViewById(R.id.event_start_time);
        event_end_time = findViewById(R.id.event_end_time);
        event_location = findViewById(R.id.event_location);
        event_description = findViewById(R.id.event_description);

        // Return to homepage button
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an intent to navigate to home page
                Intent intent = new Intent(CreateEventPage.this, NavBar.class);
                startActivity(intent);
            }
        });

        // Creates event
        create_event_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String event_type_value = event_type.getSelectedItem().toString();
                String event_name_value = event_name.getText().toString();
                String event_date_value = event_date.getText().toString();
                String event_start_time_value = event_start_time.getText().toString();
                String event_end_time_value = event_end_time.getText().toString();
                String event_location_value = event_location.getText().toString();
                String event_description_value = event_description.getText().toString();

                sendPostRequest(event_name_value, event_description_value, event_location_value, event_type_value, event_date_value, event_start_time_value, event_end_time_value);

                Intent intent = new Intent(CreateEventPage.this, EventsListViewer.class);
                startActivity(intent);
            }
        });
    }

    private void sendPostRequest(String eventNameValue, String eventDescriptionValue, String eventLocationValue, String eventTypeValue, String eventDateValue, String eventStartTimeValue, String eventEndTimeValue) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject body = new JSONObject();

        try {
            body.put("name", eventNameValue);
            body.put("description", eventDescriptionValue);
            body.put("location", eventLocationValue);
            body.put("type", eventTypeValue);
            body.put("date", eventDateValue);
            body.put("startTime", eventStartTimeValue);
            body.put("endTime", eventEndTimeValue);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Make the request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                URL_POST_REQUEST,
                body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), "New event created", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "POST request failed", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }
}
