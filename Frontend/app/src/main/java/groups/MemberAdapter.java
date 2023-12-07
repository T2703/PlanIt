package groups;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import api.VolleySingleton;
import events.Event;

/**
 * This is responsible for implementing/inflating the item layout.
 * Also, treats the functionally of said individual list.
 *
 * @author Tristan Nono
 */
public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder> implements ItemTouchHelperAdapter {
    /**
     * The list of group members.
     */
    private List<Member> member_list;

    /**
     * The context variable.
     */
    private Context context;

    private String CHAT_URL = "http://coms-309-024.class.las.iastate.edu:8080/createTeamChat/";

    private String teamUrl;

    /**
     * Constructor for the MemberAdapter class.
     *
     * @param member_list The list of group members.
     * @param context The context.
     */
    public MemberAdapter(List<Member> member_list, Context context) {
        this.member_list = member_list;
        this.context = context;
    }

    public List<Member> getMemberList() {
        return this.member_list;
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_item, parent, false);
        return new MemberViewHolder(view);
    }

    /**
     * Called to check whether two items have the same data.
     *
     * @param fromPosition The first item to compare.
     * @param toPosition The second item to compare.
     * @return True if the two items represent the same object or false if they are different.
     */
    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        // Reorder your list items when an item is dragged and dropped
        Collections.swap(member_list, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    /**
     * Called when an item has been dismissed.
     *
     * @param position The position of the item dismissed.
     */
    @Override
    public void onItemDismiss(int position) {
        // If needed.
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder The ViewHolder that should be updated to represent the contents of the item at the given position.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        Member member = member_list.get(position);
        //holder.username.setText(member.getUsername());
        holder.group_name.setText(member.getGroupName());
        //holder.description.setText(member.getDescription());


        // Makes the list function as button (plus null checker).
        // Set a click listener for the entire item view (in a nutshell each item acts like button)
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG", "Item clicked: " + member.getGroupName());
                //int group_id = Integer.parseInt(member.getGroupId());
                // This gets the position of the item.
                int position = holder.getAdapterPosition();

                if (position != RecyclerView.NO_POSITION) {
                    Member clicked_group = member_list.get(position);
                    String group_id = clicked_group.getGroupId();
                    //createChat(CHAT_URL + group_id, member.getGroupName());

                    Log.d("TAG", group_id);

                }


                Intent intent = new Intent(view.getContext(), GroupInfo.class);

                //This should pass the data into the next page.
                intent.putExtra("group_id", member.getGroupId());
                intent.putExtra("group_name", member.getGroupName());
                intent.putExtra("group_description", member.getDescription());

                view.getContext().startActivity(intent);

            }
        });
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return member_list.size();
    }

    /**
     * Filters the group member list.
     *
     * @param filterList The filtered list of group members.
     */
    public void filterEventList(ArrayList<Member> filterList) {
        member_list = filterList;
        notifyDataSetChanged();
    }

    /**
     * Makes the delete request call.
     *
     * @param deleteUrl The URL for the delete request.
     * @param group_id The ID of the group to be deleted.
     */
    private void makeDeleteRequest(String deleteUrl, String group_id) {
        // Find the position of the item with the given group_id
        int positionToDelete = -1;
        for (int i = 0; i < member_list.size(); i++) {
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
                        /**
                         * Callback method that is invoked when a network request succeeds and returns a response.
                         *
                         * @param response The response received from the network request.
                         *                 It is expected to be a JSON string representing an array.
                         * @throws RuntimeException If there is an error parsing the response as a JSON array.
                         */
                        @Override
                        public void onResponse(String response) {
                            Log.d("response", response);

                            // Remove the item from the list based on its position
                            member_list.remove(finalPositionToDelete);

                            // Notify the adapter that the item has been removed
                            notifyItemRemoved(finalPositionToDelete);

                            Toast.makeText(context.getApplicationContext(), "Group Deleted", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(context.getApplicationContext(), "Something went wrong. Please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
            );

            VolleySingleton.getInstance(context.getApplicationContext()).addToRequestQueue(stringRequest);
        }
    }

    /*private void createChat(String URL_CHAT, String name) {
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
                URL_CHAT,
                requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Server response", response.toString());
                        Toast.makeText(context.getApplicationContext(), "Chat created!", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Uh oh not good", "Error: " + error.getMessage());
                        Log.d("URL", URL_CHAT);
                    }
                }
        ) {

        };

        // Add to volley request queue
        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectReq);
    } */

    /**
     * This class is for holding the variables in place for the group members.
     */
    static class MemberViewHolder extends RecyclerView.ViewHolder {
        /**
         * The user's name.
         */
        TextView username;

        /**
         * Group's name.
         */
        TextView group_name;

        /**
         * Information.
         */
        TextView description;

        /**
         * Delete button guy.
         */
        Button delete_button;

        /**
         * Drag handle man.
         */
        ImageButton drag_handle;

        /**
         * This holds all the variables in place for the events.
         */
        MemberViewHolder(View item_view) {
            super(item_view);
            //username = item_view.findViewById(R.id.username);
            group_name = item_view.findViewById(R.id.group_name);
            //description = item_view.findViewById(R.id.description);
            //delete_button = item_view.findViewById(R.id.delete_group_button);
            drag_handle = item_view.findViewById(R.id.drag_handle);
        }
    }
}