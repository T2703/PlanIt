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
 * The item adapter for creating individual items for the calendar events.
 * Each item represents an event in the list.
 *
 * This adapter is responsible for binding event data to the corresponding views
 * in the RecyclerView, handling option button clicks (Edit or Delete), and making
 * network requests for event details or deleting events.
 *
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
     *
     * @param context The context of the calling activity or fragment.
     * @param event_list The list of events to be displayed.
     */
    public EventCalendarMonthlyAdapter(Context context, List<Event> event_list) {
        this.context = context;
        this.event_list = event_list;
    }

    /**
     * Sets the events for the event list.
     *
     * @param eventList The list of events to be set in the adapter.
     */
    public void setEvents(List<Event> eventList) {
        this.event_list = eventList;
        notifyDataSetChanged();
    }

    /**
     * Called when RecyclerView needs a new EventViewHolder of the given type to represent
     * an item.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new {@link EventViewHolder} that holds a View of the given view type.
     */
    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item_calendar_monthly, parent, false);
        return new EventCalendarMonthlyAdapter.EventViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * This method updates the contents of the EventViewHolder#event_name,
     * EventViewHolder#event_start_time, and EventViewHolder#event_end_time
     * based on the data at the given position in the event list.
     *
     * @param holder The EventViewHolder to bind data to.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull EventCalendarMonthlyAdapter.EventViewHolder holder, int position) {
        Event event = event_list.get(position);
        holder.event_name.setText(event.getName());
        holder.event_start_time.setText(event.getStartTime());
        holder.event_end_time.setText(event.getEndTime());

        holder.options_button.setOnClickListener(new View.OnClickListener() {
            /**
             * Callback method that is invoked when a view is clicked.
             *
             * @param v The view that was clicked.
             *          It can be used to identify which view triggered the click event.
             *          For example, you can compare it with view IDs to determine the source of the click.
             */
            @Override
            public void onClick(View v) {
                PopupMenu popup_menu = new PopupMenu(context, v);
                popup_menu.getMenuInflater().inflate(R.menu.options_menu, popup_menu.getMenu());

                popup_menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    /**
                     * Callback method that is invoked when a context menu item is clicked.
                     *
                     * @param menuItem The clicked MenuItem.
                     * @return true if the click event was handled, false otherwise.
                     */
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
     *
     * @param deleteUrl The URL for deleting the event.
     * @param eventId   The ID of the event to be deleted.
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

                            event_list.remove(finalPositionToDelete);

                            notifyItemRemoved(finalPositionToDelete);
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
                        }
                    }
            );
            VolleySingleton.getInstance(context.getApplicationContext()).addToRequestQueue(stringRequest);
        }

    }

    /**
     * Method for getting the details of a specific event.
     *
     * @param eventId The ID of the event to retrieve details for.
     */
    private void getEvent(String eventId) {
        String URL_STRING_REQ = "http://coms-309-024.class.las.iastate.edu:8080/events/" + eventId;

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL_STRING_REQ,
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
                    }
                }
        );

        // Adding request to request queue
        VolleySingleton.getInstance(context.getApplicationContext()).addToRequestQueue(stringRequest);
    }

    /**
     * Gets the item count for the event list.
     *
     * @return event_list size, the amount
     */
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
         * Holds all the attributes for the item.
         *
         * @param item_view The view for the item.
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
