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

public class TeamAvailability extends AppCompatActivity {

    private TextView textView;

    private static String group_id = ""; //TODO GET GROUP ID
    private static final String URL= "http://coms-309-024.class.las.iastate.edu:8080/compareStandard/"; //+ group_id + "/dates"

    private String result ="";
    private final Context teamAvailabilityPageContext = this;

    private EditText event_start_date2;
    private EditText event_end_date2;
    private EditText event_start_time2;
    private EditText event_end_time2;

    Button see_Availability = findViewById(R.id.see_Availability);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_availability);

        // Initialize
        //context = this;
        int groupId = getIntent().getIntExtra("groupId", 0);
        event_start_date2 = findViewById(R.id.start_date_input2);
        event_end_date2 = findViewById(R.id.end_date_input2);
        event_start_time2 = findViewById(R.id.start_time_input2);
        event_end_time2 = findViewById(R.id.end_time_input2);

        /// NEED DATE PICKER FOR REQUEST BODY
            event_start_date2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Calendar c = Calendar.getInstance();
                    int year = c.get(Calendar.YEAR);
                    int month = c.get(Calendar.MONTH);
                    int day = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(teamAvailabilityPageContext, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            Date date = new Date();

                            String pattern = year + "-" + ((month < 9) ? "0" + (month + 1) : (month + 1)) + "-" + ((dayOfMonth < 10) ? "0" + dayOfMonth : dayOfMonth);
                            SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.US);
                            String formattedDate = sdf.format(date);

                            event_start_date2.setText(formattedDate);
                        }
                    }, year, month, day);

                    datePickerDialog.show();
                }
            });

            event_end_date2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Calendar c = Calendar.getInstance();
                    int year = c.get(Calendar.YEAR);
                    int month = c.get(Calendar.MONTH);
                    int day = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(teamAvailabilityPageContext, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            Date date = new Date();

                            String pattern = year + "-" + ((month < 9) ? "0" + (month + 1) : (month + 1)) + "-" + ((dayOfMonth < 10) ? "0" + dayOfMonth : dayOfMonth);
                            SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.US);
                            String formattedDate = sdf.format(date);

                            event_end_date2.setText(formattedDate);
                        }
                    }, year, month, day);

                    datePickerDialog.show();
                }
            });

            event_start_time2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Calendar c = Calendar.getInstance();
                    int mHour = c.get(Calendar.HOUR_OF_DAY);
                    int mMinute = c.get(Calendar.MINUTE);
                    TimePickerDialog timePickerDialog = new TimePickerDialog(teamAvailabilityPageContext, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            String eventHour = hourOfDay < 10 ? "0" + hourOfDay : String.valueOf(hourOfDay);
                            String eventMinute = minute < 10 ? "0" + minute : String.valueOf(minute);
                            String formattedTime = eventHour + ":" + eventMinute;

                            event_start_time2.setText(formattedTime);
                        }
                    }, mHour, mMinute, false);

                    timePickerDialog.show();
                }
            });

            event_end_time2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Calendar c = Calendar.getInstance();
                    int mHour = c.get(Calendar.HOUR_OF_DAY);
                    int mMinute = c.get(Calendar.MINUTE);
                    TimePickerDialog timePickerDialog = new TimePickerDialog(teamAvailabilityPageContext, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            String eventHour = hourOfDay < 10 ? "0" + hourOfDay : String.valueOf(hourOfDay);
                            String eventMinute = minute < 10 ? "0" + minute : String.valueOf(minute);
                            String formattedTime = eventHour + ":" + eventMinute;

                            event_end_time2.setText(formattedTime);
                        }
                    }, mHour, mMinute, false);

                    timePickerDialog.show();
                }
            });

        see_Availability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] dates = stripDateAndTime();
                String event_type_value = "";
                String event_name_value = "";
                String event_start_date = dates[0];
                String event_end_date = dates[1];
                String event_location_value = "";
                String event_description_value = "";

                getRequest(event_name_value, event_description_value, event_location_value, event_type_value, event_start_date, event_end_date);
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
            //body.put("startDate", eventStartDateValue);
            //body.put("endDate", eventEndDateValue);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

            Date startDate = sdf.parse(eventStartDateValue);
            Date endDate = sdf.parse(eventEndDateValue);

            body.put("startDate", sdf.format(startDate));
            body.put("endDate", sdf.format(endDate));


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL+ group_id + "/dates",
                body,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        //Log.d("result", response);
//                        Intent intent = new Intent(TeamAvailability.this, EventsListViewer.class); //TODO
//                        startActivity(intent);

                        try {
                            textView = findViewById(R.id.response);
                            textView.setText(response.toString());
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

        // Adding request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
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