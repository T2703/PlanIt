package calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.R;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import api.VolleySingleton;
import events.EditEventPage;
import events.Event;
import websockets.WebSocketManager;

/**
 * The item adapter for making the individual item for the calendar events.
 * Basically, just an individual item for the list of events.
 * @author Tristan Nono
 */
public class EventCalendarMonthlyAdapter extends RecyclerView.Adapter<EventCalendarMonthlyAdapter.EventViewHolder> {
    /**
     * List of events in the list.
     */
    private List<Event> event_list;

    /**
     * The context.
     */
    private Context context;


    /**
     * Adapter constructor for constructing the adapter.
     * @param context
     * @param event_list
     */
    public EventCalendarMonthlyAdapter(Context context, List<Event> event_list) {
        this.context = context;
        this.event_list = event_list;
    }

    /**
     * Sets the events for the event list.
     * @param eventList
     */
    public void setEvents(List<Event> eventList) {
        this.event_list = eventList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item_calendar_monthly, parent, false);
        return new EventCalendarMonthlyAdapter.EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventCalendarMonthlyAdapter.EventViewHolder holder, int position) {
        Event event = event_list.get(position);
        holder.event_name.setText(event.getName());
        holder.event_start_time.setText(event.getStartTime());
        holder.event_end_time.setText(event.getEndTime());

        holder.options_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup_menu = new PopupMenu(context, v);
                popup_menu.getMenuInflater().inflate(R.menu.options_menu, popup_menu.getMenu());

                popup_menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.edit_option) {
                            int position = holder.getAdapterPosition();

                            if (position != RecyclerView.NO_POSITION) {
                                Event clickedEvent = event_list.get(position);

                                String eventId = clickedEvent.getId();

                                getEvent(eventId);
                            }
                        }
                        else if(menuItem.getItemId() == R.id.delete_option) {
                            int position = holder.getAdapterPosition();

                            if (position != RecyclerView.NO_POSITION) {
                                Event clickedEvent = event_list.get(position);

                                String eventId = clickedEvent.getId();

                                String username = WebSocketManager.getInstance().getUsername();

                                String delete_url = "http://coms-309-024.class.las.iastate.edu:8080/users/" + username + "/events/" + eventId;

                                makeDeleteRequest(delete_url, eventId);

                                Log.d("DELETE", "Delete");
                            }
                        }
                        return true;
                    }
                });
                // Show the popup menu
                popup_menu.show();
            }
        });
    }

    /**
     * Method to make the delete request for the event.
     * @param deleteUrl
     * @param eventId
     */
    private void makeDeleteRequest(String deleteUrl, String eventId) {
        int positionToDelete = -1;

        for (int i = 0; i < event_list.size(); i++) {
            if (event_list.get(i).getId().equals(eventId)) {
                positionToDelete = i;
                break;
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

                            event_list.remove(finalPositionToDelete);

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

    /**
     * Method for getting the list of events to store the info for each item.
     * @param eventId
     */
    private void getEvent(String eventId) {
        String URL_STRING_REQ = "http://coms-309-024.class.las.iastate.edu:8080/events/" + eventId;

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL_STRING_REQ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Intent intent = new Intent(context, EditEventPage.class);

                            intent.putExtra("type", jsonObject.getString("type"));
                            intent.putExtra("name", jsonObject.getString("name"));
                            intent.putExtra("startDate", jsonObject.getString("startDate"));
                            intent.putExtra("endDate", jsonObject.getString("endDate"));
                            intent.putExtra("location", jsonObject.getString("location"));
                            intent.putExtra("description", jsonObject.getString("description"));
                            intent.putExtra("id", jsonObject.getString("id"));

                            context.startActivity(intent);

                        } catch(JSONException err) {
                            err.printStackTrace();
                        }
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
        VolleySingleton.getInstance(context.getApplicationContext()).addToRequestQueue(stringRequest);
    }

    @Override
    public int getItemCount() {
        return event_list.size();
    }

    /**
     * Class for the holding the attributes of the event item.
     */
    public static class EventViewHolder extends RecyclerView.ViewHolder {
        /**
         * The event name.
         */
        TextView event_name;

        /**
         * The event start time
         */
        TextView event_start_time;

        /**
         * The event end time.
         */
        TextView event_end_time;

        /**
         * The button for the options for the event items (Edit or Delete).
         */
        ImageButton options_button;

        /**
         * This holds all the attributes for the item.
         * @param item_view
         */
        EventViewHolder(View item_view) {
            super(item_view);
            event_name = item_view.findViewById(R.id.event_title);
            event_start_time = item_view.findViewById(R.id.event_start_time);
            event_end_time = item_view.findViewById(R.id.event_end_time);
            options_button = item_view.findViewById(R.id.menu_button);
        }
    }
}
