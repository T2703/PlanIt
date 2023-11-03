// Author: Tristan Nono
package events;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.NavBar;
import com.example.myapplication.R;
import api.VolleySingleton;
import calendar.CalendarMonthlyPage;
import calendar.CalendarWeeklyPage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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

    /*
    The search bar this can be used to search things like events.
     */
    private SearchView search_bar;
    private static final String URL_STRING_REQ = "http://coms-309-024.class.las.iastate.edu:8080/events";

    /*
    The event adapter for the event list.
     */
    private EventAdapter adapter;

    /*
    Toolbar
     */
    private Toolbar toolbar;

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
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recycler_view.setLayoutManager(layout_manager);
        recycler_view.setAdapter(adapter);

        //event_list.add(new Event("3", "Tank Party", "description", "start_date", "end_date"));
        //event_list.add(new Event("3", "myhouse.wad", "description", "start_date", "end_date"));

        // Request events from server
        getEventsRequest();

        // Set a click listeners for the corresponding buttons.
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an intent to navigate to go back to another page.
                Intent intent = new Intent(EventsListViewer.this, CalendarMonthlyPage.class);
                ActivityOptions options = ActivityOptions.makeCustomAnimation(EventsListViewer.this, R.anim.empty_anim, R.anim.empty_anim);
                startActivity(intent, options.toBundle());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // So yeah basically creates the search bar.
        getMenuInflater().inflate(R.menu.event_search_bar, menu);
        MenuItem search_event = menu.findItem(R.id.searchBar);
        SearchView search_view = (SearchView) search_event.getActionView();

        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterText(newText);
                return false;
            }
        });

        return true;
    }

    /*
    Method that filters the text so the user can find their events without issues.
    Gotta think of the users. :D
     */
    private void filterText(String text) {
        // List to filter the data.
        ArrayList<Event> filtered_event_list = new ArrayList<Event>();

        Pattern pattern = Pattern.compile("\\b" + Pattern.quote(text), Pattern.CASE_INSENSITIVE);

        // Iterate we are using this to compare each event.
        // And this is how we shared.
        for (Event event_item : event_list) {
            if (pattern.matcher(event_item.getName()).find()) {
                filtered_event_list.add(event_item);
            }
        }

        // Well I mean there is nothing lol so you'll get this.
        if (filtered_event_list.isEmpty()) {
            Log.d("We Are The Empty", "DeSense"); // This a cool song you should totally check it out!
        }
        else {
            adapter.filterEventList(filtered_event_list);
        }
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
                                String start_date = jsonObject.getString("startDate");
                                String end_date = jsonObject.getString("endDate");

                                event_list.add(new Event(id, name, description, start_date, end_date));
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