package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class EditEventPage extends AppCompatActivity {
    private Spinner event_type;
    private EditText event_name;
    private EditText event_date;
    private EditText event_start_time;
    private EditText event_end_time;
    private EditText event_location;
    private EditText event_description;
    private String event_id;
    private Button edit_event_button;

    private ImageButton back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        // Instantiate
        event_type = findViewById(R.id.edit_event_type_dropdown);
        event_name = findViewById(R.id.edit_event_name_text);
        event_date = findViewById(R.id.edit_event_date);
        event_start_time = findViewById(R.id.edit_event_start_time);
        event_end_time = findViewById(R.id.edit_event_end_time);
        event_location = findViewById(R.id.edit_event_location);
        event_description = findViewById(R.id.edit_event_description);
        edit_event_button = findViewById(R.id.edit_event_button);
        back_button = findViewById(R.id.back_button);
        // Auto fill fields
        Intent intent = getIntent();

        String type = intent.getStringExtra("type");
        String name = intent.getStringExtra("name");
        String date = intent.getStringExtra("date");
        String startTime = intent.getStringExtra("startTime");
        String endTime = intent.getStringExtra("endTime");
        String location = intent.getStringExtra("location");
        String description = intent.getStringExtra("description");
        event_id = intent.getStringExtra("id");

        ArrayAdapter<String> adapter = (ArrayAdapter<String>) event_type.getAdapter();
        int positionToSelect = adapter.getPosition(type);

        event_type.setSelection(positionToSelect);

        event_name.setText(name);
        event_date.setText(date);
        event_start_time.setText(startTime);
        event_end_time.setText(endTime);
        event_location.setText(location);
        event_description.setText(description);

        edit_event_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String event_name_text = event_name.getText().toString();
                String event_description_text = event_description.getText().toString();
                String event_location_text = event_location.getText().toString();
                String event_type_selection = event_type.getSelectedItem().toString();
                String event_date_text = event_date.getText().toString();
                String event_start_time_text = event_start_time.getText().toString();
                String event_end_time_text = event_end_time.getText().toString();

                sendPutRequest(event_name_text, event_description_text, event_location_text, event_type_selection, event_date_text, event_start_time_text, event_end_time_text);

                Intent intent = new Intent(EditEventPage.this, EventsListViewer.class);
                startActivity(intent);
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditEventPage.this, EventsListViewer.class);
                startActivity(intent);
            }
        });

    }

    private void sendPutRequest(String eventNameValue, String eventDescriptionValue, String eventLocationValue, String eventTypeValue, String eventDateValue, String eventStartTimeValue, String eventEndTimeValue) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        HashMap<String, String> body = new HashMap<String, String>();

        body.put("id", event_id);
        body.put("name", eventNameValue);
        body.put("description", eventDescriptionValue);
        body.put("location", eventLocationValue);
        body.put("type", eventTypeValue);
        body.put("date", eventDateValue);
        body.put("startTime", eventStartTimeValue);
        body.put("endTime", eventEndTimeValue);

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
