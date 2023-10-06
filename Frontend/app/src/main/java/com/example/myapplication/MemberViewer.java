// Author: Tristan Nono

package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
The page for viewing the group members.
 */
public class MemberViewer extends AppCompatActivity {
    /*
    this just is here for debugging.
     */
    private Button button;

    private ImageButton back_button;

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
    The URL for making the calls.
     */
    private static final String TEAMS_URL = "http://coms-309-024.class.las.iastate.edu:8080/teams";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_viewer);

        // Initialize
        button = findViewById(R.id.button);
        back_button = findViewById(R.id.back_button);
        recycler_view = findViewById(R.id.recycler_view);
        member_list = new ArrayList<>();
        adapter = new MemberAdapter(member_list, this);
        layout_manger = new LinearLayoutManager(this);

        recycler_view.setLayoutManager(layout_manger);
        recycler_view.setAdapter(adapter);

        getGroupsRequest();

        // Sample data
        //member_list.add(new Member("Group 1", "Hello"));
        //member_list.add(new Member("Tristan", "Group 1", "Hello"));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MemberViewer", "Number of members: " + member_list.size());
                Intent intent = new Intent(MemberViewer.this, AddGroup.class);
                startActivity(intent);
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // For testing purposes only
                Intent intent = new Intent(MemberViewer.this, NavBar.class);
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

}
