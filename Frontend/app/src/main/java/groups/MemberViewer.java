// Author: Tristan Nono

package groups;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.NavBar;
import com.example.myapplication.NavBarView;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import api.VolleySingleton;
import calendar.CalendarMonthlyPage;
import events.CreateEventPage;
import events.Event;
import homepage.HomePage;
import profile.ProfilePage;

/**
 * The MemberViewer class represents the page for viewing group members.
 * It includes functionality for displaying and managing group members using a RecyclerView.
 * This class also handles interactions with the navigation bar and provides options for creating new groups.
 *
 * @author Tristan Nono
 */
public class MemberViewer extends AppCompatActivity implements NavBarView.OnButtonClickListener {
    /**
     * Create group button for creating groups
     */
    private ImageButton create_group_button;

    /**
     * Recycler view to display the list of items.
     */
    private RecyclerView recycler_view;

    /**
     * Manages the layout as stated in the name.
     */
    private LinearLayoutManager layout_manger;

    /**
     * Array list for the members to display them.
     */
    private List<Member> member_list;

    /**
     * Member adapter for the member list.
     */
    private MemberAdapter adapter;

    /**
     * Navbar.
     */
    private NavBarView navbar_view;

    /**
     * This is for the transitioning between pages.
     */
    private ActivityOptions options;

    /**
     * The URL for making the calls.
     */
    private static final String TEAMS_URL = "http://coms-309-024.class.las.iastate.edu:8080/teams";

    /**
     * Initializes the MemberViewer activity, setting up the UI components and handling user interactions.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state, if available.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_viewer);

        // Initialize
        create_group_button = findViewById(R.id.create_button_add);
        recycler_view = findViewById(R.id.recycler_view);
        member_list = new ArrayList<>();
        adapter = new MemberAdapter(member_list, this);
        layout_manger = new LinearLayoutManager(this);
        navbar_view = findViewById(R.id.navbar);
        navbar_view.setOnButtonClickListener(this);
        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recycler_view);

        recycler_view.setLayoutManager(layout_manger);
        recycler_view.setAdapter(adapter);

        navbar_view.setSelectedButton(navbar_view.getMessagesButton());

        getGroupsRequest();

        // Sample data
        //member_list.add(new Member("Group 1", "Hello", "2"));
        //member_list.add(new Member("Tristan", "Group 1", "1"));

        create_group_button.setOnClickListener(new View.OnClickListener() {
            /**
             * The button to navigate to the create event page
             *
             * @param view The View that was clicked.
             */
            @Override
            public void onClick(View view) {
                Log.d("MemberViewer", "Number of members: " + member_list.size());
                Intent intent = new Intent(MemberViewer.this, AddGroup.class);
                startActivity(intent);
            }
        });

    }

    /**
     * Creates the options menu for the activity, including a search bar for filtering group members.
     *
     * @param menu The options menu in which you place your items.
     * @return true for the menu to be displayed; false otherwise.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // So yeah basically creates the search bar.
        getMenuInflater().inflate(R.menu.event_search_bar, menu);
        MenuItem search_event = menu.findItem(R.id.searchBar);
        SearchView search_view = (SearchView) search_event.getActionView();

        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            /**
             * Called when the user submits the query.
             *
             * @param text The submitted query text.
             * @return true if the query has been handled, false otherwise.
             */
            @Override
            public boolean onQueryTextSubmit(String text) {
                return false;
            }

            /**
             * Called when the query text is changed by the user.
             *
             * @param newText The new text in the query.
             * @return true if the query text change has been handled, false otherwise.
             */
            @Override
            public boolean onQueryTextChange(String newText) {
                filterText(newText);
                return false;
            }
        });

        return true;
    }

    /**
     * Filters the displayed group members based on the provided text.
     *
     * @param text The text used for filtering group members.
     */
    private void filterText(String text) {
        // List to filter the data.
        ArrayList<Member> filtered_event_list = new ArrayList<Member>();

        Pattern pattern = Pattern.compile("\\b" + Pattern.quote(text), Pattern.CASE_INSENSITIVE);

        // Iterate we are using this to compare each event.
        // And this is how we shared.
        for (Member member_item : member_list) {
            if (pattern.matcher(member_item.getGroupName()).find()) {
                filtered_event_list.add(member_item);
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

    /**
     * Initiates a request to the server to retrieve group data and populates the member list accordingly.
     */
    private void getGroupsRequest() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                TEAMS_URL,
                new Response.Listener<String>() {
                    /**
                     * Callback method that is invoked when a network request succeeds and returns a response.
                     *
                     * @param response The response received from the network request.
                     *                 It is expected to be a JSON string representing an array.
                     * @throws RuntimeException If there is an error parsing the response as a JSON array.
                     */
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
                                String name = jsonObject.getString("name");
                                String description = jsonObject.getString("description");
                                String id = jsonObject.getString("id");

                                member_list.add(0, new Member(name, description, id));
                                Log.d("List", id);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    /**
                     * Callback method that is invoked when a network request encounters an error.
                     *
                     * @param error The VolleyError object containing information about the error.
                     *              This can include details such as the error message, network response, and more.
                     *              It can be used for debugging and handling specific error scenarios.
                     */
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

    /**
     * Handles the click event on the calendar button in the navigation bar.
     * This method is part of the NavBarView.OnButtonClickListener interface.
     */
    @Override
    public void onCalendarButtonClick() {
        Intent intent = new Intent(MemberViewer.this, CalendarMonthlyPage.class);
        options = ActivityOptions.makeCustomAnimation(MemberViewer.this, R.anim.empty_anim, R.anim.empty_anim);
        startActivity(intent, options.toBundle());
    }

    /**
     * Handles the click event on the home button in the navigation bar.
     * This method is part of the NavBarView.OnButtonClickListener interface.
     */
    @Override
    public void onHomeButtonClick() {
        Intent intent = new Intent(MemberViewer.this, HomePage.class);
        ActivityOptions options = ActivityOptions.makeCustomAnimation(MemberViewer.this, R.anim.empty_anim, R.anim.empty_anim);
        startActivity(intent, options.toBundle());
    }

    /**
     * Handles the click event on the messages button in the navigation bar.
     * This method is part of the NavBarView.OnButtonClickListener interface.
     */
    @Override
    public void onMessagesButtonClick() {
        /*
        Do nothing why should you.
         */
    }

    /**
     * Handles the click event on the profile button in the navigation bar.
     * This method is part of the NavBarView.OnButtonClickListener interface.
     */
    @Override
    public void onProfileButtonClick() {
        Intent intent = new Intent(MemberViewer.this, ProfilePage.class);
        options = ActivityOptions.makeCustomAnimation(MemberViewer.this, R.anim.empty_anim, R.anim.empty_anim);
        startActivity(intent, options.toBundle());
    }

    /**
     * Handles the click event on the create button in the navigation bar.
     * This method is part of the NavBarView.OnButtonClickListener interface.
     */
    @Override
    public void onCreateEventButtonClick() {
        //Log.d("MemberViewer", "Create Event button clicked.");
        Intent intent = new Intent(MemberViewer.this, CreateEventPage.class);
        startActivity(intent);
    }
}
