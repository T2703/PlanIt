package followers;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * The adapter items for the users.
 *
 * @author Tristan Nono
 */
public class UserFollowerTeamsMemberAdapter extends RecyclerView.Adapter<UserFollowerTeamsMemberAdapter.UserFollowerViewHolder> {
    /**
     * The list of users.
     */
    private List<UserFollower> user_list;

    /**
     * The context variable.
     */
    private Context context;

    /**
     * The user that will be followed.
     */
    private String follow_user;

    /**
     * ID.
     */
    private String group_id;

    /**
     * The url for adding followers.
     */
    private static final String ADD_FOLLOWERS_TO_TEAM_URL = "http://coms-309-024.class.las.iastate.edu:8080/teams/";

    /**
     * The url for unfollowing followers.
     */
    private static final String UNFOLLOW_FOLLOWERS_URL = "http://coms-309-024.class.las.iastate.edu:8080/teams/";


    public UserFollowerTeamsMemberAdapter(Context context, List<UserFollower> user_list) {
        this.context = context;
        this.user_list = user_list;
    }

    @NonNull
    @Override
    public UserFollowerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_follower_team_item2, parent, false);
        return new UserFollowerTeamsMemberAdapter.UserFollowerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserFollowerViewHolder holder, int position) {
        UserFollower userFollower = user_list.get(position);
        holder.username.setText(userFollower.getUserName());


    }

    public void filterUserList(ArrayList<UserFollower> filterList) {
        user_list = filterList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return user_list.size();
    }


    /**
     * This class is for holding the variables in place for the group members.
     */
    static class UserFollowerViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        public UserFollowerViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.user_name);
        }
    }
}

