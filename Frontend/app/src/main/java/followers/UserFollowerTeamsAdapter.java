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

import websockets.WebSocketManager;


/**
 * The adapter items for the users.
 *
 * @author Tristan Nono
 */
public class UserFollowerTeamsAdapter extends RecyclerView.Adapter<UserFollowerTeamsAdapter.UserFollowerViewHolder> {
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


    public UserFollowerTeamsAdapter(Context context, List<UserFollower> user_list, String group_id) {
        this.context = context;
        this.user_list = user_list;
        this.group_id = group_id;
    }

    @NonNull
    @Override
    public UserFollowerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_follower_team_item, parent, false);
        return new UserFollowerTeamsAdapter.UserFollowerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserFollowerViewHolder holder, int position) {
        UserFollower userFollower = user_list.get(position);
        holder.username.setText(userFollower.getUserName());

        holder.followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                follow_user = userFollower.getUserName();
                String follow_url = ADD_FOLLOWERS_TO_TEAM_URL + group_id + "/add-user/" + follow_user;
                addFollowers(follow_url);
                Log.d("User", "Item clicked: " + userFollower.getUserName());
            }
        });

        holder.unfollowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                follow_user = userFollower.getUserName();
                String unfollow_url = UNFOLLOW_FOLLOWERS_URL + group_id + "/remove-user/" + follow_user;
                unfollowFollowers(unfollow_url);
                Log.d("User", "Item clicked: " + userFollower.getUserName());
            }
        });

    }

    public void filterUserList(ArrayList<UserFollower> filterList) {
        user_list = filterList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return user_list.size();
    }

    private void addFollowers(String URL_GUY) {
        // Find the values of each field

        // Create JSON object
        JSONObject requestBody = new JSONObject();

        // Making the request
        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(
                Request.Method.PUT,  // Use PUT method instead of POST
                URL_GUY,  // Specify the specific group ID in the URL
                requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Server response", response.toString());
                        Toast.makeText(context.getApplicationContext(), "Followed" + " " + follow_user, Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Server error", "Error: " + error.getMessage());
                    }
                }
        ) {

        };

        // Add the request to the RequestQueue
        Volley.newRequestQueue(context).add(jsonObjectReq);
    }

    private void unfollowFollowers(String URL_GUY) {
        // Find the values of each field

        // Create JSON object
        JSONObject requestBody = new JSONObject();

        // Making the request
        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(
                Request.Method.PUT,  // Use PUT method instead of POST
                URL_GUY,  // Specify the specific group ID in the URL
                requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Server response", response.toString());
                        Toast.makeText(context.getApplicationContext(), "Unfollowed" + " " + follow_user, Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Server error", "Error: " + error.getMessage());
                    }
                }
        ) {

        };

        // Add the request to the RequestQueue
        Volley.newRequestQueue(context).add(jsonObjectReq);
    }

    /**
     * This class is for holding the variables in place for the group members.
     */
    static class UserFollowerViewHolder extends RecyclerView.ViewHolder {
        TextView username;

        ImageButton followButton;

        ImageButton unfollowButton;
        public UserFollowerViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.user_name);
            followButton = itemView.findViewById(R.id.follow_button);
            unfollowButton = itemView.findViewById(R.id.unfollow_button);
        }
    }
}

