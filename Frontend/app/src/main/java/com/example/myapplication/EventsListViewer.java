// Author: Tristan Nono
package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/*
The events list viewer page where you can see all your events.
 */
public class EventsListViewer extends AppCompatActivity  {
    /*
    The button for going back.
    */
    private ImageButton back_button;

    /*
    Recycler view aka from what I know it's how we display the list of items.
     */
    private RecyclerView recycler_view;

    /*
    This manages the layout yeah I suppose. It says in the name.
     */
    private LinearLayoutManager layout_manager;

    /*
    Array list for the event lists the list of them (List of a list please ignore this).
     */
    private List<Event> event_list;
    private static final String URL_STRING_REQ = "http://coms-309-024.class.las.iastate.edu:8080/events";

    /*
    The event adapter for the event list.
     */
    private EventAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_list_viewer);

        // Initialize
        back_button = findViewById(R.id.back_button);
        recycler_view = findViewById(R.id.recyclerView);
        event_list = new ArrayList<>();
        adapter = new EventAdapter(this, event_list);
        layout_manager = new LinearLayoutManager(this);

        recycler_view.setLayoutManager(layout_manager);
        recycler_view.setAdapter(adapter);

        // Request events from server
        getEventsRequest();

        // Set a click listeners for the corresponding buttons.
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an intent to navigate to go back to another page.
                Intent intent = new Intent(EventsListViewer.this, NavBar.class);
                startActivity(intent);
            }
        });
    }

    private void getEventsRequest() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL_STRING_REQ,
                new Response.Listener<String>() {
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

                                event_list.add(new Event(id, name, description));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        adapter.notifyDataSetChanged();
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

};