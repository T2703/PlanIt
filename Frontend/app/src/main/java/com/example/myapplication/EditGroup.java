// Author: Tristan Nono

package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

/*
Where the user or someone can edit the group stuff.
 */
public class EditGroup extends AppCompatActivity {
    /*
    This button edits the group.
     */
    private Button edit_group_button;

    /*
    We go back.
    */
    private ImageButton back_button;

    /*
    The group name input from the user.
     */
    private EditText group_name;

    /*
    The description that the use inputs
     */
    private EditText group_description;

    /*
    Group id number.
     */
    private String group_id;

    /*
    The URL for making the calls.
    */
    private static final String TEAMS_URL = "http://coms-309-024.class.las.iastate.edu:8080/teams";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);

        // Initialization
        edit_group_button = findViewById(R.id.edit_button);
        group_name = findViewById(R.id.group_name);
        group_description = findViewById(R.id.group_description);
        back_button = findViewById(R.id.back_button);
        edit_group_button.setEnabled(false); // Set the initial state to disabled

        // How we retrieve the group id.
        Intent intent = getIntent();
        if (intent != null) {
            String name = intent.getStringExtra("name");
            String description = intent.getStringExtra("description");
            group_id = intent.getStringExtra("group_id"); // Use getStringExtra for String values

            TextView group_name_view = findViewById(R.id.group_name);
            TextView group_description_view = findViewById(R.id.group_description);

            group_name_view.setText(name);
            group_description_view.setText(description);
        }

        // Sets the on click listener for the creating group button. So, we can
        // an epic group.
        edit_group_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateGroup();
                Intent intent = new Intent(EditGroup.this, MemberViewer.class);
                startActivity(intent);
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditGroup.this, MemberViewer.class);
                startActivity(intent);
            }
        });

        // This is for the user's name to see if anything is in it or not.
        group_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkFieldsForEmptyValues();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        // This is for the user's email to see if anything is in it or not.
        group_description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkFieldsForEmptyValues();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

    }

    /*
    This checks for empty values in the edit text variables so in other words,
    if there's nothing in both the group name and description then, it shouldn't go through.
    */
    private void checkFieldsForEmptyValues() {
        String group_name_input = group_name.getText().toString();
        String group_description_input = group_description.getText().toString();

        if (!group_name_input.isEmpty() && !group_description_input.isEmpty()) {
            edit_group_button.setEnabled(true);
        }

        else {
            edit_group_button.setEnabled(false);
        }
    }

    /*
    This is the request for updating a group (well we call it teams or whatever).
    This PUTs the group on to the server.
    */
    private void updateGroup() {
        // Find the values of each field
        EditText input_group_name = findViewById(R.id.group_name);
        EditText input_group_description = findViewById(R.id.group_description);

        String input_group_value = input_group_name.getText().toString();
        String input_group_description_value = input_group_description.getText().toString();

        // Create JSON object
        JSONObject requestBody = new JSONObject();

        // Puts in the values of these variables.
        try {
            requestBody.put("id", group_id);
            requestBody.put("name", input_group_value);
            requestBody.put("description", input_group_description_value);
            Log.d("TAG",requestBody.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Making the request
        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(
                Request.Method.PUT,  // Use PUT method instead of POST
                TEAMS_URL + "/" + group_id,  // Specify the specific group ID in the URL
                requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Server response", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Server error", "Error: " + error.getMessage());
                    }
                }
        ) {

        };

        // Add the request to the RequestQueue
        Volley.newRequestQueue(this).add(jsonObjectReq);
    }

    /*
    Fetches the group id.
     */
    /*private void fetchGroupId(String groupName) {
        // Create a request to fetch the group_id based on the group's name
        String fetchUrl = TEAMS_URL + groupName; // Adjust the URL accordingly

        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(
                Request.Method.GET,
                fetchUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Parse the response to get the group_id
                            int fetchedGroupId = response.getInt("group_id");

                            // Now you have the correct group_id
                            group_id = fetchedGroupId;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Server error", "Error: " + error.getMessage());
                    }
                }

        );

        // Add the request to the RequestQueue
        Volley.newRequestQueue(this).add(jsonObjectReq);
    }*/


}