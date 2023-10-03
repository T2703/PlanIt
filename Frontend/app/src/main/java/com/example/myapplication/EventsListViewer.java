// Author: Tristan Nono
package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

/*
The events list viewer page where you can see all your events.
 */
public class EventsListViewer extends AppCompatActivity {
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
        adapter = new EventAdapter(event_list);
        layout_manager = new LinearLayoutManager(this);

        recycler_view.setLayoutManager(layout_manager);
        recycler_view.setAdapter(adapter);

        // Populate eventList with event data (do some calls to the backend).
        // Add sample event data
        event_list.add(new Event("Event 1", "Description for Event 1"));
        event_list.add(new Event("Event 2", "Description for Event 2"));
        event_list.add(new Event("Event 3", "Description for Event 3"));

        // Set a click listeners for the corresponding buttons.
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG", "Back");

                // Create an intent to navigate to go back to another page.
                Intent intent = new Intent(EventsListViewer.this, NavBar.class);
                startActivity(intent);

            }
        });
    }
}