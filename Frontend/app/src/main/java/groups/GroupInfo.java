// Author: Tristan Nono

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

/*
This is where the group info is shown like their profile picture and the just
the general info.
 */
public class GroupInfo extends AppCompatActivity {
    private static final int EDIT_GROUP_REQUEST_CODE = 1;
    /*
    At this point we should know what it does. It goes back.
    */
    private ImageButton back_button;

    /*
    Name of the group.
     */
    private TextView group_name;

    /*
    Description of the group.
     */
    private TextView group_description;

    /*
    The button that brings up the popup menu displaying:
    Going to the chat or if you're admin deleting the group or editing the info.
     */
    private ImageButton menu_button;

    /*
    The list of the groups.
    */
    private List<Member> member_list;

    /*
    Context: context
     */
    private Context context;

    /*
    Gets the group name.
    */
    private String getting_group_name;

    /*
    Gets the group description.
     */
    private String getting_group_desc;

    /*
    Gets the group id.
     */
    private String getting_group_id;

    /*
    Member adapter for the member list.
     */
    private MemberAdapter adapter;

    private String TEAMS_URL = "http://coms-309-024.class.las.iastate.edu:8080/teams";

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
                            Log.d("Name", getting_group_name);
                            Log.d("DESC", getting_group_desc);
                        }
                        else if (menuItem.getItemId() == R.id.edit_option) {
                            Intent editIntent = new Intent(GroupInfo.this, EditGroup.class);
                            editIntent.putExtra("group_id", getting_group_id);
                            editIntent.putExtra("group_name", getting_group_name);
                            editIntent.putExtra("group_description", getting_group_desc);
                            startActivity(editIntent);

                        }

                        else if (menuItem.getItemId() == R.id.delete_option) {
                            String delete_url = "http://coms-309-024.class.las.iastate.edu:8080/teams/" + getting_group_id;
                            makeDeleteRequest(delete_url, getting_group_id);
                            Log.d("GID", getting_group_id);

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



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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

    /*
    Makes the delete request call. That's all it does really should be
    self explanatory.
    */
    private void makeDeleteRequest(String deleteUrl, String group_id) {
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

    private void updateUIWithGroupData() {
        // Update your UI components with the new group data
        group_name.setText(getting_group_name);
        group_description.setText(getting_group_desc);
        adapter.notifyDataSetChanged(); // Update the RecyclerView if you have one
    }

}

