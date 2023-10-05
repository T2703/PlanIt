// Author: Tristan Nono

package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

/*
Welcome to the add group page where we can add groups by creating them ofc!
Overall, this is where the new groups gets created.
 */
public class AddGroup extends AppCompatActivity {
    /*
    This button creates the group.
     */
    private Button create_group_button;

    /*
    The group name input from the user.
     */
    private EditText group_name;

    /*
    The description that the use inputs
     */
    private EditText group_description;

    /*
    The URL for making the calls.
    */
    private static final String TEAMS_URL = "http://coms-309-024.class.las.iastate.edu:8080/teams";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);

        // Initialization
        create_group_button = findViewById(R.id.create_button);
        group_name = findViewById(R.id.input_group_name);
        group_description = findViewById(R.id.input_description);
        create_group_button.setEnabled(false); // Set the initial state to disabled

        // Sets the on click listener for the creating group button. So, we can
        // an epic group.
        create_group_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createGroup();
                Intent intent = new Intent(AddGroup.this, MemberViewer.class);
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
            create_group_button.setEnabled(true);
        }

        else {
            create_group_button.setEnabled(false);
        }
    }

    /*
    This is the request for creating a group (well we call it teams or whatever).
    This POSTs the group on to the server.
    */
    private void createGroup() {
        // Find the values of each field
        EditText input_group_name = findViewById(R.id.input_group_name);
        EditText input_group_description = findViewById(R.id.input_description);

        String input_group_value = input_group_name.getText().toString();
        String input_group_description_value = input_group_description.getText().toString();

        // Create JSON object
        JSONObject requestBody = new JSONObject();

        // Puts in the values of these variables.
        try {
            requestBody.put("name", input_group_value);
            requestBody.put("description", input_group_description_value);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Making the request
        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(
                Request.Method.POST,
                TEAMS_URL,
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

        // Add to volley request queue
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectReq);
    }
}