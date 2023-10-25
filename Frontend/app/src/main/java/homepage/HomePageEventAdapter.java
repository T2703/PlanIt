package homepage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import org.w3c.dom.Text;

import java.util.List;

import calendar.EventCalendarMonthlyAdapter;
import events.Event;

/*
The adpater aka the item for the list of upcoming events shown on the homepage.
 */
public class HomePageEventAdapter extends RecyclerView.Adapter<HomePageEventAdapter.EventViewHolder>{
    /*
    The list of the events.
    */
    private List<Event> event_list;
    private Context context;

    /*
    Constructs the apdater.
    */
    public HomePageEventAdapter(Context context, List<Event> event_list) {
        this.context = context;
        this.event_list = event_list;
    }

    public void setEvents(List<Event> eventList) {
        this.event_list = eventList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HomePageEventAdapter.EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_homepage_item, parent, false);
        return new HomePageEventAdapter.EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = event_list.get(position);
        holder.event_title.setText(event.getName());
    }

    @Override
    public int getItemCount() {
        return event_list.size();
    }


    static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView event_title;

        /*
        This holds all the variables in place for the events.
         */
        EventViewHolder(View item_view) {
            super(item_view);
            event_title = item_view.findViewById(R.id.event_title);

        }
    }
}


