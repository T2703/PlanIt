// Author Tristan Nono

package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/*
This is responsible for implementing/inflating the item layout.
Also, treats the functionally of said individual list.
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
    /*
    The list of the events.
     */
    private List<Event> event_list;
    private Context context;
    /*
    Epic constructor. This is how we have this event list initialized.
     */
    public EventAdapter(Context context, List<Event> event_list) {
        this.context = context;
        this.event_list = event_list;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = event_list.get(position);
        holder.event_name.setText(event.getName());
        holder.event_description.setText(event.getDescription());

        // Set a click listener for the delete button.
        // Also I guess nullcheckers are nice but that's just me going crazy after finding out a
        // variable wasn't initialized hahahahahaha man I love coding sometimes.
        if (holder.delete_button != null) {
            holder.delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) {
                        Event clickedEvent = event_list.get(position);

                        String eventId = clickedEvent.getId();

                        String delete_url = "http://coms-309-024.class.las.iastate.edu:8080/events/" + eventId;

                        makeDeleteRequest(delete_url, eventId);
                    }
                }
            });

            // Makes the list function as button (plus null checker).
            // Set a click listener for the entire item view (in a nutshell each item acts like button)
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("TAG", "Item clicked: " + event.getName());
                    // TO-DO: Add a page for this that show the event and such.
                }
            });
        }

    }

    private void makeDeleteRequest(String deleteUrl, String eventId) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.DELETE,
                deleteUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response);

                        event_list.remove(Integer.parseInt(eventId));

                        notifyItemRemoved(Integer.parseInt(eventId));
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

    @Override
    public int getItemCount() {
        return event_list.size();
    }

    /*
    This is for the list or I should say the characteristics of it like what goes in it.
    Like our title and description and so on.
     */
    static class EventViewHolder extends RecyclerView.ViewHolder {
        /*
        The delete button well it does as
         */
        Button delete_button;

        /*
        The event title.
         */
        TextView event_name;

        /*
        The event description (yeah).
         */
        TextView event_description;

        /*
        This holds all the variables in place for the events.
         */
        EventViewHolder(View item_view) {
            super(item_view);
            event_name = item_view.findViewById(R.id.event_title);
            event_description = item_view.findViewById(R.id.event_description);
            delete_button = item_view.findViewById(R.id.delete_button);
        }
    }
}
