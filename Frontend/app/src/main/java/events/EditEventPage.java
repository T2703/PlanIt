package events;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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
import com.example.myapplication.R;

import org.json.JSONObject;

import java.text.ParseException;
import java.util.HashMap;

import utilities.DateAndTimeHelper;

/**
 * @author Joshua Gutierrez
 * The EditEventPage class is an AppCompatActivity responsible for editing an existing event
 * and sending the updated event details to the server via a PUT request.
 */
public class EditEventPage extends AppCompatActivity {
    private Spinner event_type;
    private EditText event_name;
    private EditText event_start_date;
    private EditText event_end_date;
    private EditText event_start_time;
    private EditText event_end_time;
    private EditText event_location;
    private EditText event_description;
    private String event_id;
    private Button edit_event_button;

    private ImageButton back_button;
    private Context context = this;

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
        setContentView(R.layout.activity_edit_event);

        // Instantiate
        event_type = findViewById(R.id.edit_event_type_dropdown);
        event_name = findViewById(R.id.edit_event_name_text);
        event_start_date = findViewById(R.id.edit_start_date_input);
        event_end_date = findViewById(R.id.edit_end_date_input);
        event_start_time = findViewById(R.id.edit_start_time_input);
        event_end_time = findViewById(R.id.edit_end_time_input);
        event_location = findViewById(R.id.edit_event_location);
        event_description = findViewById(R.id.edit_event_description);
        edit_event_button = findViewById(R.id.edit_event_button);
        back_button = findViewById(R.id.back_button);
        // Auto fill fields
        Intent intent = getIntent();

        String type = intent.getStringExtra("type");
        String name = intent.getStringExtra("name");
        String startDate = intent.getStringExtra("startDate");
        String endDate = intent.getStringExtra("endDate");
        String location = intent.getStringExtra("location");
        String description = intent.getStringExtra("description");
        event_id = intent.getStringExtra("id");

        ArrayAdapter<String> adapter = (ArrayAdapter<String>) event_type.getAdapter();
        int positionToSelect = adapter.getPosition(type);

        event_type.setSelection(positionToSelect);
        event_name.setText(name);

        try {
            event_start_date.setText(DateAndTimeHelper.formatDate(startDate));
            event_end_date.setText(DateAndTimeHelper.formatDate(endDate));
            event_start_time.setText(DateAndTimeHelper.formatTime(startDate));
            event_end_time.setText(DateAndTimeHelper.formatTime(endDate));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        event_location.setText(location);
        event_description.setText(description);

        edit_event_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eventType = event_type.getSelectedItem().toString();
                String eventName = event_name.getText().toString();

                String eventStartDate = event_start_date.getText().toString();
                String eventEndDate = event_end_date.getText().toString();
                String eventStartTime = event_start_time.getText().toString();
                String eventEndTime = event_end_time.getText().toString();

                String newStartDate = DateAndTimeHelper.combineDateAndTime(eventStartDate, eventStartTime);
                String newEndDate = DateAndTimeHelper.combineDateAndTime(eventEndDate, eventEndTime);

                String eventLocation = event_location.getText().toString();
                String eventDescription = event_description.getText().toString();

                sendPutRequest(eventType, eventName, newStartDate, newEndDate, eventLocation, eventDescription);

                Intent intent = new Intent(EditEventPage.this, EventsListViewer.class);
                startActivity(intent);
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        event_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateAndTimeHelper helper = new DateAndTimeHelper(context);
                helper.showDatePicker(event_start_date);
            }
        });

        event_end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateAndTimeHelper helper = new DateAndTimeHelper(context);
                helper.showDatePicker(event_end_date);
            }
        });

        event_start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateAndTimeHelper helper = new DateAndTimeHelper(context);
                helper.showTimePicker(event_start_time);
            }
        });

        event_end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateAndTimeHelper helper = new DateAndTimeHelper(context);
                helper.showTimePicker(event_end_time);
            }
        });
    }

    private void sendPutRequest(String eventType, String eventName, String startDate, String endDate, String eventLocation, String eventDescription) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        HashMap<String, String> body = new HashMap<String, String>();

        body.put("id", event_id);
        body.put("name", eventName);
        body.put("description", eventDescription);
        body.put("location", eventLocation);
        body.put("type", eventType);
        body.put("startDate", startDate);
        body.put("endDate", endDate);

        // Make the request
        String URL_PUT_REQUEST = "http://coms-309-024.class.las.iastate.edu:8080/events/";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PUT,
                URL_PUT_REQUEST + event_id,
                new JSONObject(body),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), "Event updated", Toast.LENGTH_SHORT).show();
                        Log.d("response", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "PUT request failed", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }
}
