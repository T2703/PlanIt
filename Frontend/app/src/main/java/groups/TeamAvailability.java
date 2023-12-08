package groups;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import api.VolleySingleton;
import events.CreateEventPage;
import events.EventsListViewer;
import utilities.DateAndTimeHelper;

public class TeamAvailability extends AppCompatActivity {

    private TextView textView;

    private String group_id;
    private static final String URL= "http://coms-309-024.class.las.iastate.edu:8080/compareStandard/"; //+ group_id + "/dates"

    private String result ="";
    private final Context teamAvailabilityPageContext = this;

    private EditText event_start_date2;
    private EditText event_end_date2;
    private EditText event_start_time2;
    private EditText event_end_time2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_availability);

        // Initialize
        //context = this;
        group_id = getIntent().getStringExtra("groupId");
        event_start_date2 = findViewById(R.id.start_date_input2);
        event_end_date2 = findViewById(R.id.end_date_input2);
        event_start_time2 = findViewById(R.id.start_time_input2);
        event_end_time2 = findViewById(R.id.end_time_input2);

        Button see_Availability = findViewById(R.id.see_Availability);

        /// NEED DATE PICKER FOR REQUEST BODY
            event_start_date2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DateAndTimeHelper helper = new DateAndTimeHelper(teamAvailabilityPageContext);
                    helper.showDatePicker(event_start_date2);
                }
            });

            event_end_date2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DateAndTimeHelper helper = new DateAndTimeHelper(teamAvailabilityPageContext);
                    helper.showDatePicker(event_end_date2);
                }
            });

            event_start_time2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DateAndTimeHelper helper = new DateAndTimeHelper(teamAvailabilityPageContext);
                    helper.showTimePicker(event_start_time2);
                }
            });

            event_end_time2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DateAndTimeHelper helper = new DateAndTimeHelper(teamAvailabilityPageContext);
                    helper.showTimePicker(event_end_time2);
                }
            });

        see_Availability.setOnClickListener(new View.OnClickListener() {       // SEE AVAILABILITY
            @Override
            public void onClick(View view) {
                String[] dates = stripDateAndTime();
                String event_type_value2 = "private";
                String event_name_value2 = "dateRange";
                String event_location_value2 = "NA";
                String event_description_value2 = "NA";

                String event_start_date_value = event_start_date2.getText().toString();
                String event_end_date_value = event_end_date2.getText().toString();
                String event_start_time_value = event_start_time2.getText().toString();
                String event_end_time_value = event_end_time2.getText().toString();

                String event_start_Combined = DateAndTimeHelper.combineDateAndTime(event_start_date_value, event_start_time_value);
                String event_end_Combined = DateAndTimeHelper.combineDateAndTime(event_end_date_value, event_end_time_value);

                getRequest(event_name_value2, event_description_value2, event_location_value2, event_type_value2, event_start_Combined, event_end_Combined);
            }
        });
    }

    private void getRequest(String eventNameValue, String eventDescriptionValue, String eventLocationValue, String eventTypeValue, String eventStartDateValue, String eventEndDateValue){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject body = new JSONObject();

        try {
            body.put("name", eventNameValue);
            body.put("description", eventDescriptionValue);
            body.put("location", eventLocationValue);
            body.put("type", eventTypeValue);
            body.put("startDate", eventStartDateValue);
            body.put("endDate", eventEndDateValue);

            System.out.println(body);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PUT,
                URL+ group_id + "/dates",
                body,
                new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {

                        //Log.d("result", response);

                        try {
                            textView = findViewById(R.id.response);
                            textView.setText(response.get("message").toString());
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
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

    private String[] stripDateAndTime() {
        String start_date = event_start_date2.getText().toString();
        String end_date = event_end_date2.getText().toString();
        String start_time = event_start_time2.getText().toString();
        String end_time = event_end_time2.getText().toString();

        String startDate = start_date + "T" + start_time + ":00.000+00:00";
        String endDate = end_date + "T" + end_time + ":00.000+00:00";

        return new String[]{startDate, endDate};
    }


}