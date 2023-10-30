package homepage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> userList;

    public UserAdapter(List<User> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_active_users, parent, false);
        return new UserViewHolder(itemView);
    }

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

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        ImageView avatarImageView;
        ImageView onlineIndicatorImageView;

        public UserViewHolder(View itemView) {
            super(itemView);
            avatarImageView = itemView.findViewById(R.id.userAvatar);
            onlineIndicatorImageView = itemView.findViewById(R.id.onlineIndicator);
        }
    }
}
