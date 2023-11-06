// Author: Tristan Nono
package events;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import androidx.appcompat.widget.SearchView;

import android.widget.PopupMenu;
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
import websockets.WebSocketManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    private static final String URL_STRING_REQ = "http://coms-309-024.class.las.iastate.edu:8080/users/";

    /*
    The event adapter for the event list.
     */
    private EventAdapter adapter;

    /*
    Toolbar
     */
    private Toolbar toolbar;

    /*
    The button that brings up the popup menu displaying the types for the groups.
    */
    private ImageButton menu_button;

    /*
    Shared preferences.
     */
    private SharedPreferences sharedPreferences;

    /*
    Text type for retrieving said data.
     */
    private Set<String> eventTextTypes = new HashSet<>();

    /*
    How to search up the events.
     */
    private SearchView search_view;

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
        menu_button = findViewById(R.id.filter_menu);
        sharedPreferences = getSharedPreferences("FilterPreferences", MODE_PRIVATE);
        setSupportActionBar(toolbar);

        recycler_view.setLayoutManager(layout_manager);
        recycler_view.setAdapter(adapter);

        event_list.add(new Event("3", "Tank Party", "description", "Public", "start_date", "end_date"));
        event_list.add(new Event("3", "myhouse.wad", "description", "Private", "start_date", "end_date"));

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

        menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EventsListViewer.this);
                builder.setTitle("Filter");

                View dialog_view = getLayoutInflater().inflate(R.layout.group_type_filters, null);
                builder.setView(dialog_view);

                CheckBox checkbox_private = dialog_view.findViewById(R.id.checkbox_private);
                CheckBox checkbox_public = dialog_view.findViewById(R.id.checkbox_public);
                CheckBox checkbox_group = dialog_view.findViewById(R.id.checkbox_group);

                boolean privateChecked = sharedPreferences.getBoolean("privateChecked", false);
                boolean publicChecked = sharedPreferences.getBoolean("publicChecked", false);
                boolean groupChecked = sharedPreferences.getBoolean("groupChecked", false);
                checkbox_private.setChecked(privateChecked);
                checkbox_public.setChecked(publicChecked);
                checkbox_group.setChecked(groupChecked);

                AlertDialog dialog = builder.create();

                checkbox_private.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        sharedPreferences.edit().putBoolean("privateChecked", isChecked).apply();
                        if (isChecked) {
                            Log.d("PRIVATE", "PTR Checked");
                            eventTextTypes.add("Private");
                        }
                        else {
                            Log.d("PRIVATE", "PTR Unchecked");
                            eventTextTypes.remove("Private");
                        }

                        filterText(search_view.getQuery().toString(), eventTextTypes);
                    }
                });

                checkbox_public.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        sharedPreferences.edit().putBoolean("publicChecked", isChecked).apply();
                        if (isChecked) {
                            Log.d("PUBLIC", "PTR Checked");
                            eventTextTypes.add("Public");
                        }
                        else {
                            Log.d("PUBLIC", "PTR Unchecked");
                            eventTextTypes.remove("Public");
                        }
                        filterText(search_view.getQuery().toString(), eventTextTypes);
                    }
                });

                checkbox_group.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        sharedPreferences.edit().putBoolean("groupChecked", isChecked).apply();
                        if (isChecked) {
                            Log.d("GROUP", "PTR Checked");
                            eventTextTypes.add("Group");
                        }
                        else {
                            Log.d("GROUP", "PTR Unchecked");
                            eventTextTypes.remove("Group");
                        }

                        filterText(search_view.getQuery().toString(), eventTextTypes);
                    }
                });

                // Show the dialog
                dialog.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // So yeah basically creates the search bar.
        getMenuInflater().inflate(R.menu.event_search_bar, menu);
        MenuItem search_event = menu.findItem(R.id.searchBar);
        search_view = (SearchView) search_event.getActionView();

        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterText(newText, eventTextTypes);
                return false;
            }
        });

        return true;
    }

    /*
    Method that filters the text so the user can find their events without issues.
    Gotta think of the users. :D
     */
    private void filterText(String text, Set<String> eventTypes) {
        // List to filter the data.
        ArrayList<Event> filtered_event_list = new ArrayList<Event>();

        Pattern pattern = Pattern.compile("\\b" + Pattern.quote(text), Pattern.CASE_INSENSITIVE);

        // Iterate we are using this to compare each event.
        // And this is how we shared.
        for (Event event_item : event_list) {
            boolean typeMatch = eventTypes.isEmpty();
            for (String eventType : eventTypes) {
                if (event_item.getType().equalsIgnoreCase(eventType)) {
                    typeMatch = true;
                    break;
                }
            }

            if (pattern.matcher(event_item.getName()).find() && typeMatch) {
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
        String username = WebSocketManager.getInstance().getUsername();
        Log.d("GET REQUEST", URL_STRING_REQ + username + "/events");
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL_STRING_REQ + username + "/events",
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
                                String eventType = jsonObject.getString("type");
                                String start_date = jsonObject.getString("startDate");
                                String end_date = jsonObject.getString("endDate");

                                event_list.add(new Event(id, name, description, eventType, start_date, end_date));
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