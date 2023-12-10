package events;

import android.app.ActivityOptions;
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
import api.VolleySingleton;
import websockets.WebSocketManager;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Joshua Gutierrez
 * The {@code EventAdapter} class extends {@link RecyclerView.Adapter} and is responsible for managing
 * the display and interaction with a list of events in a {@link RecyclerView}.
 * <p>
 * It provides a bridge between the data set, represented by a list of {@link Event} objects,
 * and the UI elements displayed in each item of the {@code RecyclerView}.
 * </p>
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
    /**
     * The list of events to be displayed in the RecyclerView.
     */
    private List<Event> event_list;

    /**
     * The context associated with the adapter.
     */
    private Context context;

    /**
     * Is manager.
     */
    private Boolean isManager;


    /**
     * Constructs an {@code EventAdapter} with the specified context and list of events.
     *
     * @param context    The context associated with the adapter.
     * @param event_list The list of events to be displayed.
     */
    public EventAdapter(Context context, List<Event> event_list) {
        this.context = context;
        this.event_list = event_list;
    }

    /**
     * Called when RecyclerView needs a new {@link EventViewHolder} of the given type to represent
     * an event.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The type of the new View.
     * @return A new {@code EventViewHolder} that holds a View of the given view type.
     */
    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
        return new EventViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method updates the
     * contents of the {@code EventViewHolder} to reflect the event at the given position.
     *
     * @param holder   The {@code EventViewHolder} which should be updated to represent the contents of the item at the given position.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = event_list.get(position);
        holder.event_name.setText(event.getName());
        holder.event_description.setText(event.getDescription());
        getEventManager(event.getId());

        holder.menu_button.setOnClickListener(new View.OnClickListener() {
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
                popup_menu.getMenu().findItem(R.id.edit_option).setVisible(isManager);
                popup_menu.getMenu().findItem(R.id.delete_option).setVisible(isManager);

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
                        else if (menuItem.getItemId() == R.id.info_option) {
                            Intent intent = new Intent(v.getContext(), EventInfoPage.class);

                            //This should pass the data into the next page.
                            intent.putExtra("id", event.getId());
                            intent.putExtra("name", event.getName());
                            intent.putExtra("description", event.getDescription());
                            intent.putExtra("location", event.getLocation());
                            intent.putExtra("type", event.getType());
                            intent.putExtra("start_date", event.getStartTime());
                            intent.putExtra("end_date", event.getEndTime());

                            ActivityOptions options = ActivityOptions.makeCustomAnimation(v.getContext(), R.anim.empty_anim, R.anim.empty_anim);
                            v.getContext().startActivity(intent, options.toBundle());

                        }
                        else if (menuItem.getItemId() == R.id.member_option) {
                            Intent intent = new Intent(v.getContext(), EventMembersPage.class);
                            intent.putExtra("id", event.getId());

                            ActivityOptions options = ActivityOptions.makeCustomAnimation(v.getContext(), R.anim.empty_anim, R.anim.empty_anim);
                            v.getContext().startActivity(intent, options.toBundle());

                        }
                        return true;
                    }
                });
                // Show the popup menu
                popup_menu.show();
            }
        });

    }

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

    private void getEventManager(String eventId) {
        String URL_STRING_REQ = "http://coms-309-024.class.las.iastate.edu:8080/events/" + eventId;

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL_STRING_REQ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject eventObject = new JSONObject(response);

                            // Extract manager information
                            JSONObject managerObject = eventObject.getJSONObject("manager");
                            String managerUsername = managerObject.getString("username");

                            isManager = managerUsername.equals(WebSocketManager.getInstance().getUsername());

                            // Now you can use managerUsername as needed
                            Log.d("Manager ID", eventId);
                            Log.d("Manager Username", managerUsername);

                            // Continue with other processing if needed

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

    public void filterEventList(ArrayList<Event> filterList) {
        event_list = filterList;
        notifyDataSetChanged();
    }

    /**
     * The {@code EventViewHolder} class represents each item view in the {@link RecyclerView}.
     * It holds references to the UI elements within the item layout.
     */
    static class EventViewHolder extends RecyclerView.ViewHolder {
        ImageButton menu_button;
        TextView event_name;
        TextView event_description;
        EventViewHolder(View item_view) {
            super(item_view);
            event_name = item_view.findViewById(R.id.event_time_month);
            event_description = item_view.findViewById(R.id.event_title);
            menu_button = item_view.findViewById(R.id.menu_button);

        }
    }
}
