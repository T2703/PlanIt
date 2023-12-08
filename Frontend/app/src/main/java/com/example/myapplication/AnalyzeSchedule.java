package com.example.myapplication;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import api.VolleySingleton;
import calendar.CalendarWeeklyPage;
import events.Event;
import groups.GroupInfo;
import groups.MemberViewer;
import profile.UserManager;


public class AnalyzeSchedule extends AppCompatActivity {

    /**
     * Back button.
     */
    private ImageButton back_button;
    private TextView textView;

    private String result ="";

    private static final String URL= "http://coms-309-024.class.las.iastate.edu:8080/scheduleAnalysis/";  //userId

    UserManager userManager = UserManager.getInstance();

    private void getRequest(){
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL + userManager.getUserID(),
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
        //textView = findViewById(R.id.textView);
        setContentView(R.layout.activity_analyze_schedule);
        back_button = findViewById(R.id.back_button_two2);
        getRequest();
        //textView.setText(result);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AnalyzeSchedule.this, CalendarWeeklyPage.class);
                startActivity(intent);
            }
        });
    }






    
}