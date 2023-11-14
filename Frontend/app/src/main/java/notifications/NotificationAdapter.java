package notifications;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

/**
 * The {@code NotificationAdapter} class is a custom adapter for managing the display of
 * notifications in a RecyclerView. It extends the RecyclerView.Adapter class and provides
 * methods to bind notification data to individual views within the RecyclerView.
 * <p>
 * The adapter is designed to work with the {@code NotificationViewHolder} class, which defines
 * the layout of each notification item in the RecyclerView.
 * </p>
 *
 * @see RecyclerView.Adapter
 * @see NotificationAdapter.NotificationViewHolder
 * @author Joshua Gutierrez
 * @version 1.0
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
    private List<Notification> notificationList;

    /**
     * Constructs a new {@code NotificationAdapter} with the specified list of notifications.
     *
     * @param notificationList The list of notifications to be displayed.
     */
    public NotificationAdapter(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }

    /**
     * Called when RecyclerView needs a new {@code NotificationViewHolder} of the given type to
     * represent an item.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new {@code NotificationViewHolder} that holds a View of the given view type.
     * @see NotificationViewHolder
     */
    @NonNull
    @Override
    public NotificationAdapter.NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_notification_item, parent, false);
        return new NotificationAdapter.NotificationViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder   The {@code NotificationViewHolder} that holds the view for each notification item.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(NotificationAdapter.NotificationViewHolder holder, int position) {
        Notification notification = notificationList.get(position);

        holder.notificationTitle.setText(notification.getTitle());
        holder.notificationDescription.setText(notification.getDescription());

        if (!notification.getIsRead()) {
            holder.itemView.setBackgroundColor(Color.parseColor("#C5C5C5"));
        }

        if (notification.getIsSelected()) {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFF5E0"));
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in the data set.
     */
    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    /**
     * The {@code NotificationViewHolder} class represents a single item view within the RecyclerView.
     * It holds references to individual UI components for displaying notification information.
     */
    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        ImageView userAvatarImage;
        TextView notificationTitle;
        TextView notificationDescription;
        Button acceptButton;
        Button denyButton;

        /**
         * Constructs a new {@code NotificationViewHolder} with the specified item view.
         *
         * @param itemView The view representing a single item within the RecyclerView.
         */
        public NotificationViewHolder(View itemView) {
            super(itemView);


            userAvatarImage = itemView.findViewById(R.id.sentBy);
            notificationTitle = itemView.findViewById(R.id.notificationTitle);
            notificationDescription = itemView.findViewById(R.id.notificationDescription);
            acceptButton = itemView.findViewById(R.id.acceptButton);
            denyButton = itemView.findViewById(R.id.denyButton);
        }
    }
}
