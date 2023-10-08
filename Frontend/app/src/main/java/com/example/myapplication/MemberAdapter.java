// Author Tristan Nono

// Side note just think of member as like the teams/group I need to rename them.

package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.List;

/*
This is responsible for implementing/inflating the item layout.
Also, treats the functionally of said individual list.
 */
public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder> {
    /*
    The list of the events.
     */
    private List<Member> member_list;

    /*
    The context variable.
     */
    private Context context;

    /*
    Epic constructor. This is how we have this event list initialized.
     */
    public MemberAdapter(List<Member> member_list, Context context) {
        this.member_list = member_list;
        this.context = context;
    }

    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_item, parent, false);
        return new MemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        Member member = member_list.get(position);
        //holder.username.setText(member.getUsername());
        holder.group_name.setText(member.getGroupName());
        holder.description.setText(member.getDescription());

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
                    String put_url = "http://coms-309-024.class.las.iastate.edu:8080/teams/" + group_id;

                    Log.d("TAG", group_id);

                }

                Intent intent = new Intent(view.getContext(), EditGroup.class);

                // This should pass the data into the next page
                intent.putExtra("group_id", member.getGroupId());
                intent.putExtra("name", member.getGroupName());
                intent.putExtra("description", member.getDescription());

                view.getContext().startActivity(intent);

            }
        });

        // How this button functions as a delete. So, basically this button should delete
        // the group.
        holder.delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();

                if (position != RecyclerView.NO_POSITION) {
                    Member clickedEvent = member_list.get(position);

                    String group_id = clickedEvent.getGroupId();

                    String delete_url = "http://coms-309-024.class.las.iastate.edu:8080/teams/" + group_id;

                    //Log.d("TAG", delete_url);

                    makeDeleteRequest(delete_url, group_id);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return member_list.size();
    }

    /*
    Makes the delete request call. That's all it does really should be
    self explanatory.
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
                        @Override
                        public void onResponse(String response) {
                            Log.d("response", response);

                            // Remove the item from the list based on its position
                            member_list.remove(finalPositionToDelete);

                            // Notify the adapter that the item has been removed
                            notifyItemRemoved(finalPositionToDelete);
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

            VolleySingleton.getInstance(context.getApplicationContext()).addToRequestQueue(stringRequest);
        }
    }


    /*
    This is for the list or I should say the characteristics of it like what goes in it.
    Like our title and description and so on.
     */
    static class MemberViewHolder extends RecyclerView.ViewHolder {
        /*
        The user's name.
         */
        TextView username;

        /*
        Group's name.
         */
        TextView group_name;

        /*
        Information.
         */
        TextView description;

        /*
        Delete button guy.
         */
        Button delete_button;

        /*
        This holds all the variables in place for the events.
         */
        MemberViewHolder(View item_view) {
            super(item_view);
            username = item_view.findViewById(R.id.username);
            group_name = item_view.findViewById(R.id.group_name);
            description = item_view.findViewById(R.id.description);
            delete_button = item_view.findViewById(R.id.delete_group_button);
        }
    }
}