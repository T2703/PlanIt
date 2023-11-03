package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import api.VolleySingleton;
import events.Event;


public class AnalyzeSchedule extends AppCompatActivity {

    private TextView textView;

    private static final String URL= "http://10.0.2.2:8080/scheduleAnalysis/1";  //userId

    private String result ="";

    private void getRequest(){
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //JSONArray responseArray;

                        Log.d("result", response);

                        try {
                            textView = findViewById(R.id.response);
                            textView.setText(response);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                        // Iterate
                        /*
                        for (int i = 0; i < responseArray.length(); i++) {
                            try {
                                JSONObject jsonObject = responseArray.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String name = jsonObject.getString("name");
                                String description = jsonObject.getString("description");
                                String start_date = jsonObject.getString("startDate");
                                String end_date = jsonObject.getString("endDate");

                                event_list.add(new Event(id, name, description, start_date, end_date));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                         */

                        //adapter.notifyDataSetChanged();
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
        getRequest();
        //textView.setText(result);
    }






    
}