package homepage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

/**
 * @author Joshua Gutierrez
 * The UserAdapter class is a RecyclerView adapter for displaying a list of users
 * with avatars and online status in a RecyclerView.
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> userList;

    /**
     * Constructs a new UserAdapter with the specified list of users.
     *
     * @param userList The list of users to be displayed in the RecyclerView.
     */
    public UserAdapter(List<User> userList) {
        this.userList = userList;
    }

    /**
     * Called when RecyclerView needs a new UserViewHolder to represent
     * an item with the given type.
     *
     * @param parent   The ViewGroup into which the new View will be added.
     * @param viewType The type of the new View.
     * @return A new UserViewHolder that holds a View representing an item.
     */
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_active_users, parent, false);
        return new UserViewHolder(itemView);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder   The UserViewHolder that holds the View for each item.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        User user = userList.get(position);

        holder.avatarImageView.setImageResource(user.getAvatarResource());

        if (user.isOnline()) {
            holder.onlineIndicatorImageView.setVisibility(View.VISIBLE);
        } else {
            holder.onlineIndicatorImageView.setVisibility(View.GONE);
        }
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in the adapter's data set.
     */
    @Override
    public int getItemCount() {
        return userList.size();
    }

    /**
     * The UserViewHolder class represents a ViewHolder for user items in the RecyclerView.
     */
    public static class UserViewHolder extends RecyclerView.ViewHolder {
        ImageView avatarImageView;
        ImageView onlineIndicatorImageView;

        /**
         * Constructs a new UserViewHolder with the specified item view.
         *
         * @param itemView The View for each item in the RecyclerView.
         */
        public UserViewHolder(View itemView) {
            super(itemView);
            avatarImageView = itemView.findViewById(R.id.userAvatar);
            onlineIndicatorImageView = itemView.findViewById(R.id.onlineIndicator);
        }
    }
}
