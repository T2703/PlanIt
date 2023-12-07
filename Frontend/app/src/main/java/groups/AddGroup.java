package groups;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.R;
import api.VolleySingleton;
import websockets.WebSocketManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Welcome to the add group page where we can add groups by creating them ofc!
 * Overall, this is where the new groups gets created.
 *
 * @author Tristan Nono
 */
public class AddGroup extends AppCompatActivity {
    /**
     * Button that creates the group.
     */
    private Button create_group_button;

    /**
     * Button to go back.
     */
    private ImageButton back_button;

    /**
     * Input for the group name from the user.
     */
    private EditText group_name;

    /**
     * Input for the group description from the user.
     */
    private EditText group_description;

    /**
     * URL for making calls.
     */
    private static final String TEAMS_URL = "http://coms-309-024.class.las.iastate.edu:8080/users/" + WebSocketManager.getInstance().getUsername() + "/teams";

    private String CHAT_URL = "http://coms-309-024.class.las.iastate.edu:8080/createTeamChat/";

    private String teamID;


    /**
     * Initializes the activity and sets up UI components.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state, or null if there was none.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);

        // Initialization
        create_group_button = findViewById(R.id.create_group_button);
        group_name = findViewById(R.id.group_name);
        group_description = findViewById(R.id.group_description);
        back_button = findViewById(R.id.add_group_back_button);
        create_group_button.setEnabled(false); // Set the initial state to disabled
        Log.d("URL", TEAMS_URL);

        // Sets the on click listener for the creating group button. So, we can
        // an epic group.
        create_group_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createGroup();
                //getGroupsRequest();
                Intent intent = new Intent(AddGroup.this, MemberViewer.class);
                startActivity(intent);
            }
        });

        // How we go back the functionally/behind the scenes.
        // Oh yeah this just functions the same without creating the group.
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    /**
     * This checks for empty values in the edit text variables so in other words,
     * if there's nothing in both the group name and description then, it shouldn't go through.
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

    /**
     * This is the request for creating a group (well we call it teams or whatever).
     * This POSTs the group on to the server.
     */
    private void createGroup() {
        // Find the values of each field
        EditText input_group_name = findViewById(R.id.group_name);
        EditText input_group_description = findViewById(R.id.group_description);

        String input_group_value = input_group_name.getText().toString();
        String input_group_description_value = input_group_description.getText().toString();

        // Create JSON object
        JSONObject requestBody = new JSONObject();

        // Puts in the values of these variables.
        try {
            requestBody.put("name", input_group_value);
            requestBody.put("description", input_group_description_value);
            requestBody.put("chat", input_group_value);
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
                        // Log the entire response for debugging
                        Log.d("Server response", response.toString());

                        // Extract team information
                        try {
                            if (response.has("id")) {
                                String teamID = response.getString("id");
                                String teamName = response.getString("name");

                                // Log team information for debugging
                                Log.d("Team ID", teamID);
                                Log.d("Team Name", teamName);

                                // Proceed to create chat
                                createChat(teamID, teamName);
                            } else {
                                Log.e("JSON Parsing Error", "Response is missing 'id' field");
                            }
                        } catch (JSONException e) {
                            // Handle JSONException more gracefully
                            Log.e("JSON Parsing Error", "Error parsing JSON response: " + e.getMessage());
                        }
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

    private void createChat(String teamID, String name) {
        // Find the values of each field

        // Create JSON object
        JSONObject requestBody = new JSONObject();

        // Puts in the values of these variables.
        try {
            requestBody.put("chat", name);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Making the request
        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(
                Request.Method.POST,
                CHAT_URL + teamID,
                requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Server response", response.toString());
                        Toast.makeText(AddGroup.this, "Chat created!", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Uh oh not good", "Error: " + error.getMessage());
                        Log.d("URL", CHAT_URL);
                    }
                }
        ) {

        };

        // Add to volley request queue
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectReq);
    }


    /*private void getGroupsRequest() {
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
                                String id = jsonObject.getString("id");
                                teamID = id;
                                createChat(teamID, name);
                                Log.d("List", id);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle any errors that occur during the request
                        Log.e("A server error has occurred", error.toString());
                    }
                }
        );

        // Adding request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    } */


}