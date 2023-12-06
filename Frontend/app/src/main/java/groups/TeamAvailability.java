package groups;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import api.VolleySingleton;

public class TeamAvailability extends AppCompatActivity {

    private TextView textView;

    private static final String URL= "http://coms-309-024.class.las.iastate.edu:8080/compareStandard/" + group_id + "/dates";  //groupId

    private String result ="";


    /// NEED DATE PICKER FOR REQUEST BODY
    private void setDateRange(){
        event_start_date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(createEventPageContext, new DatePickerDialog.OnDateSetListener() {
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

                DatePickerDialog datePickerDialog = new DatePickerDialog(createEventPageContext, new DatePickerDialog.OnDateSetListener() {
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
                TimePickerDialog timePickerDialog = new TimePickerDialog(createEventPageContext, new TimePickerDialog.OnTimeSetListener() {
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
                TimePickerDialog timePickerDialog = new TimePickerDialog(createEventPageContext, new TimePickerDialog.OnTimeSetListener() {
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
    }

    private void getRequest(){
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("result", response);

                        try {
                            textView = findViewById(R.id.response);
                            textView.setText(response);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_availability);

        EditText event_start_date2 = findViewById(R.id.start_date_input2);
        EditText event_end_date2 = findViewById(R.id.end_date_input2);
        EditText event_start_time2 = findViewById(R.id.start_time_input2);
        EditText event_end_time2 = findViewById(R.id.end_time_input2);
    }
}