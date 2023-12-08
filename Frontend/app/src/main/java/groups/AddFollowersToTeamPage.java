package groups;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import api.VolleySingleton;
import followers.UserFollower;
import followers.UserFollowerAdapter;
import followers.UserFollowerTeamsAdapter;
import homepage.HomePage;
import websockets.WebSocketManager;

/**
 * This is the followers page.
 *
 * @author Tristan Nono.
 */
public class AddFollowersToTeamPage extends AppCompatActivity {
    private ImageButton back_button;
    private RecyclerView recycler_view;
    private LinearLayoutManager layout_manager;
    private List<UserFollower> user_list;
    private SearchView search_bar;
    private static final String URL_STRING_REQ = "http://coms-309-024.class.las.iastate.edu:8080/users/" + WebSocketManager.getInstance().getUsername() + "/following";
    private UserFollowerTeamsAdapter adapter;
    private Toolbar toolbar;
    private ImageButton menu_button;
    private SharedPreferences sharedPreferences;
    private Set<String> eventTextTypes = new HashSet<>();
    private SearchView search_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following_page2);

        // Initialize
        back_button = findViewById(R.id.back_button);
        recycler_view = findViewById(R.id.recyclerView);
        user_list = new ArrayList<>();
        String group_id = getIntent().getStringExtra("group_id");
        adapter = new UserFollowerTeamsAdapter(this, user_list, group_id);
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
                finish();
                overridePendingTransition(R.anim.empty_anim, R.anim.empty_anim);
            }
        });
    }

    /**
     * Performs a request to the server to get the list of events for the current user.
     */
    private void getUsersRequest() {
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