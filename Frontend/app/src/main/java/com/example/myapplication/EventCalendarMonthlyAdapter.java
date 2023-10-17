// Author: Tristan Nono
package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

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
        This holds all the variables in place for the events.
         */
        EventViewHolder(View item_view) {
            super(item_view);
            event_name = item_view.findViewById(R.id.event_title);
            event_start_time = item_view.findViewById(R.id.event_start_time);
            event_end_time = item_view.findViewById(R.id.event_end_time);
        }
    }
}
