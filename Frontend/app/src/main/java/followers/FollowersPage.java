package followers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import api.VolleySingleton;
import homepage.HomePage;
import websockets.WebSocketManager;

public class FollowersPage extends AppCompatActivity {

    private ImageButton back_button;
    private RecyclerView recycler_view;
    private LinearLayoutManager layout_manager;
    private List<UserFollower> user_list;
    private SearchView search_bar;
    private static final String URL_STRING_REQ = "http://coms-309-024.class.las.iastate.edu:8080/users/" + WebSocketManager.getInstance().getUsername() + "/followers";
    private UserFollowerAdapter adapter;
    private Toolbar toolbar;
    private ImageButton menu_button;
    private SharedPreferences sharedPreferences;
    private Set<String> eventTextTypes = new HashSet<>();
    private SearchView search_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers_page);

        // Initialize
        back_button = findViewById(R.id.back_button);
        recycler_view = findViewById(R.id.recyclerView);
        user_list = new ArrayList<>();
        adapter = new UserFollowerAdapter(this, user_list);
        layout_manager = new LinearLayoutManager(this);
        toolbar = findViewById(R.id.toolbar);
        sharedPreferences = getSharedPreferences("FilterPreferences", MODE_PRIVATE);
        setSupportActionBar(toolbar);

        recycler_view.setLayoutManager(layout_manager);
        recycler_view.setAdapter(adapter);
        getSupportActionBar().setTitle("");
        getUsersRequest();

        // Set a click listeners for the corresponding buttons.
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an intent to navigate to go back to another page.
                Intent intent = new Intent(FollowersPage.this, HomePage.class);
                ActivityOptions options = ActivityOptions.makeCustomAnimation(FollowersPage.this, R.anim.empty_anim, R.anim.empty_anim);
                startActivity(intent, options.toBundle());
            }
        });
    }

    /**
     * Creates the options menu for the activity, including the search bar.
     *
     * @param menu The options menu in which the items are placed.
     * @return {@code true} if the menu is to be displayed; {@code false} otherwise.
     */
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

    /**
     * Filters the list of events based on the entered text and selected event types.
     *
     * @param text       The text to be searched in event names.
     * @param eventTypes The set of selected event types for filtering.
     */
    private void filterText(String text, Set<String> eventTypes) {
        // List to filter the data.
        ArrayList<UserFollower> filtered_user_list = new ArrayList<UserFollower>();

        Pattern pattern = Pattern.compile("\\b" + Pattern.quote(text), Pattern.CASE_INSENSITIVE);

        // Iterate we are using this to compare each event.
        // And this is how we shared.
        for (UserFollower user_item : user_list) {
            if (pattern.matcher(user_item.getUserName()).find()) {
                filtered_user_list.add(user_item);
            }
        }

        // Well I mean there is nothing lol so you'll get this.
        if (filtered_user_list.isEmpty()) {
            Log.d("We Are The Empty", "DeSense"); // This a cool song you should totally check it out!
        } else {
            adapter.filterUserList(filtered_user_list);
        }
    }

    /**
     * Performs a request to the server to get the list of events for the current user.
     */
    private void getUsersRequest() {
        String username = WebSocketManager.getInstance().getUsername();
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
                                String name = jsonObject.getString("username");

                                if (name.equals(WebSocketManager.getInstance().getUsername())) {
                                    continue;
                                }

                                user_list.add(new UserFollower(id, name));

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
                        Log.e("Fuc", error.toString());
                    }
                }
        );
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}