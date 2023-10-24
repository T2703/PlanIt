// Author: Tristan Nono
package calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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

import com.example.myapplication.R;

import java.util.List;

import events.EditEventPage;
import events.Event;

/*
This one is for the events but for the calendar monthly page.
 */
public class EventCalendarMonthlyAdapter extends RecyclerView.Adapter<EventCalendarMonthlyAdapter.EventViewHolder> {
    /*
    The list of the events.
    */
    private List<Event> event_list;
    private Context context;

    /*
    Constructs the apdater.
     */
    public EventCalendarMonthlyAdapter(Context context, List<Event> event_list) {
        this.context = context;
        this.event_list = event_list;
    }

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

                        }
                        else if(menuItem.getItemId() == R.id.delete_button) {

                        }
                        return true;
                    }
                });
                // Show the popup menu
                popup_menu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return event_list.size();
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {
        /*
        The event title.
         */
        TextView event_name;

        /*
        The event start time.
         */
        TextView event_start_time;

        /*
        The event end time.
        */
        TextView event_end_time;

        /*
        The button for the options for the event items (Edit or Delete).
         */
        ImageButton options_button;

        /*
        This holds all the variables in place for the events.
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
