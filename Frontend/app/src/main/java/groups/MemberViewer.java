// Author: Tristan Nono

package groups;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
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

import api.VolleySingleton;
import calendar.CalendarMonthlyPage;
import events.CreateEventPage;
import homepage.HomePage;
import profile.ProfilePage;

/*
The page for viewing the group members.
 */
public class MemberViewer extends AppCompatActivity implements NavBarView.OnButtonClickListener {
    /*
    Create group button for creating groups
     */
    private ImageButton create_group_button;

    /*
    Recycler view to display the list of items.
     */
    private RecyclerView recycler_view;

    /*
    Manages the layout as stated in the name.
     */
    private LinearLayoutManager layout_manger;

    /*
    Array list for the members to display them.
     */
    private List<Member> member_list;

    /*
    Member adapter for the member list.
     */
    private MemberAdapter adapter;

    /*
    Hi there navbar, fancy seeing you here. Yeah, it's the same one.l
    */
    private NavBarView navbar_view;

    /*
    This is for the transitioning between pages.
    */
    private ActivityOptions options;

    /*
    The URL for making the calls.
     */
    private static final String TEAMS_URL = "http://coms-309-024.class.las.iastate.edu:8080/teams";

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

        recycler_view.setLayoutManager(layout_manger);
        recycler_view.setAdapter(adapter);

        navbar_view.setSelectedButton(navbar_view.getMessagesButton());

        getGroupsRequest();

        // Sample data
        //member_list.add(new Member("Group 1", "Hello", "2"));
        //member_list.add(new Member("Tristan", "Group 1", "1"));

        create_group_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MemberViewer", "Number of members: " + member_list.size());
                Intent intent = new Intent(MemberViewer.this, AddGroup.class);
                startActivity(intent);
            }
        });

    }

    /*
    This is the request for getting the data for groups.
    This GETs the groups from the server.
    */
    private void getGroupsRequest() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                TEAMS_URL,
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
                                String name = jsonObject.getString("name");
                                String description = jsonObject.getString("description");
                                String id = jsonObject.getString("id");

                                member_list.add(new Member(name, description, id));
                                Log.d("List", id);
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

    @Override
    public void onCalendarButtonClick() {
        Intent intent = new Intent(MemberViewer.this, CalendarMonthlyPage.class);
        options = ActivityOptions.makeCustomAnimation(MemberViewer.this, R.anim.empty_anim, R.anim.empty_anim);
        startActivity(intent, options.toBundle());
    }

    @Override
    public void onHomeButtonClick() {
        Intent intent = new Intent(MemberViewer.this, HomePage.class);
        ActivityOptions options = ActivityOptions.makeCustomAnimation(MemberViewer.this, R.anim.empty_anim, R.anim.empty_anim);
        startActivity(intent, options.toBundle());
    }

    @Override
    public void onMessagesButtonClick() {
        /*
        Do nothing why should you.
         */
    }

    @Override
    public void onProfileButtonClick() {
        Intent intent = new Intent(MemberViewer.this, ProfilePage.class);
        options = ActivityOptions.makeCustomAnimation(MemberViewer.this, R.anim.empty_anim, R.anim.empty_anim);
        startActivity(intent, options.toBundle());
    }

    @Override
    public void onCreateEventButtonClick() {
        //Log.d("MemberViewer", "Create Event button clicked.");
        Intent intent = new Intent(MemberViewer.this, CreateEventPage.class);
        startActivity(intent);
    }
}
