package groups;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.NavBar;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import api.VolleySingleton;
import messages.MessageView;
import websockets.WebSocketManager;

/**
 * Activity class for displaying group information including the group's profile picture and general details.
 * This class also allows users, especially group administrators, to perform various actions such as editing,
 * deleting, and navigating to the group's chat.
 *
 * <p>
 * Intent Extras required to launch this activity:
 * - "group_name": The name of the group.
 * - "group_description": The description of the group.
 * - "group_id": The unique identifier for the group.
 * </p>
 *
 * @author Tristan Nono
 */
public class GroupInfo extends AppCompatActivity {
    /**
     * The request code for editing the group.
     */
    public static final int EDIT_GROUP_REQUEST_CODE = 1;

    /**
     * Back button.
     */
    private ImageButton back_button;

    /**
     * Name of the group.
     */
    private TextView group_name;

    /**
     * Description of the group.
     */
    private TextView group_description;

    /**
     * The button that brings up the popup menu displaying:
     * Going to the chat or if you're admin deleting the group or editing the info.
     */
    private ImageButton menu_button;

    /**
     * The list of groups.
     */
    public List<Member> member_list;

    /**
     * Context: context
     */
    private Context context;

    /**
     * Gets the group name.
     */
    private String getting_group_name;

    /**
     * Gets the group description.
     */
    private String getting_group_desc;

    /**
     * Gets the group ID.
     */
    private String getting_group_id;

    /**
     * Group adapter for the groups (I have forgotten to rename this).
     */
    private MemberAdapter adapter;

    /**
     * The teams url for making the request.
     */
    private String TEAMS_URL = "http://coms-309-024.class.las.iastate.edu:8080/users/" + WebSocketManager.getInstance().getUsername() + "/teams";

    private Button team_Availability_Button;

    /**
     * Initializes the activity and sets up UI components.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state, or null if there was none.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);

        // Initialize
        context = this;
        back_button = findViewById(R.id.back_button_two);
        menu_button = findViewById(R.id.menu_button_two);
        group_name = findViewById(R.id.group_name_two);
        group_description = findViewById(R.id.group_desc);
        member_list = new ArrayList<>();
        adapter = new MemberAdapter(member_list, this);
        getting_group_name = getIntent().getStringExtra("group_name");
        getting_group_desc = getIntent().getStringExtra("group_description");
        getting_group_id = getIntent().getStringExtra("group_id");
        team_Availability_Button = findViewById(R.id.button_tav);

        group_name.setText(getting_group_name);
        group_description.setText(getting_group_desc);

        getGroupsRequest();


        menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup_menu = new PopupMenu(GroupInfo.this, view);
                popup_menu.getMenuInflater().inflate(R.menu.options_menu_two, popup_menu.getMenu());
                popup_menu.show();
                popup_menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.to_chat_option) {
                            Intent intent = new Intent(GroupInfo.this, MessageView.class);
                            startActivity(intent);
                        } else if (menuItem.getItemId() == R.id.edit_option) {
                            Intent editIntent = new Intent(GroupInfo.this, EditGroup.class);
                            editIntent.putExtra("group_id", getting_group_id);
                            editIntent.putExtra("group_name", getting_group_name);
                            editIntent.putExtra("group_description", getting_group_desc);
                            startActivity(editIntent);

                        } else if (menuItem.getItemId() == R.id.delete_option) {
                            String delete_url = "http://coms-309-024.class.las.iastate.edu:8080/users/" + WebSocketManager.getInstance().getUsername() + "/teams" + "/" + getting_group_id;
                            makeDeleteRequest(delete_url, getting_group_id);

                            Intent intent = new Intent(GroupInfo.this, MemberViewer.class);
                            startActivity(intent);

                        }
                        return true;
                    }

                });

                popup_menu.show();

            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(GroupInfo.this, MemberViewer.class);
                startActivity(intent);
            }
        });


        team_Availability_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Intent intent = new Intent(GroupInfo.this, TeamAvailability.class);
                intent.putExtra("groupId", getting_group_id);
                startActivity(intent);
            }
        });

    }

    /**
     * Handles the result of the activity started for editing the group.
     *
     * @param requestCode The request code passed to startActivityForResult().
     * @param resultCode  The result code returned by the child activity.
     * @param data        An Intent, which can return result data to the caller.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_GROUP_REQUEST_CODE) {
            if (resultCode == RESULT_OK && data != null) {
                // Update the UI with the edited group information
                String updatedGroupName = data.getStringExtra("group_name");
                String updatedGroupDescription = data.getStringExtra("group_description");

                // Update your UI elements with the new data
                group_name.setText(updatedGroupName);
                group_description.setText(updatedGroupDescription);
            }
        }
    }

    /**
     * This is the request for getting the data for groups.
     * This GETs the groups from the server.
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

    /**
     * Initiates a delete request for the group with the specified group ID.
     * Removes the deleted group from the list and updates the UI accordingly.
     *
     * @param deleteUrl The URL for the delete request.
     * @param group_id  The unique identifier of the group to be deleted.
     */
    public void makeDeleteRequest(String deleteUrl, String group_id) {
        // Find the position of the item with the given group_id
        Log.d("TAG", String.valueOf(member_list.size()));
        int positionToDelete = -1;
        for (int i = 0; i < member_list.size(); i++) {
            Log.d("ID", "Jp");
            if (member_list.get(i).getGroupId().equals(group_id)) {
                positionToDelete = i;
                break; // Stop searching once found
            }
        }

        if (positionToDelete != -1) {
            int finalPositionToDelete = positionToDelete;
            StringRequest stringRequest = new StringRequest(
                    Request.Method.DELETE,
                    deleteUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("response", response);

                            // Remove the item from the list based on its position
                            member_list.remove(finalPositionToDelete);

                            // Notify the adapter that the item has been removed
                            adapter.notifyItemRemoved(finalPositionToDelete);

                            Toast.makeText(getApplicationContext(), "Group Deleted", Toast.LENGTH_SHORT).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Handle any errors that occur during the request
                            Log.e("A server error has occurred", error.toString());
                            Toast.makeText(getApplicationContext(), "Uh-oh something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
            );

            VolleySingleton.getInstance(context.getApplicationContext()).addToRequestQueue(stringRequest);
        }
    }
}

