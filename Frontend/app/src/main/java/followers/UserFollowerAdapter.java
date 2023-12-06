package followers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.List;

import api.VolleySingleton;
import events.EditEventPage;
import events.Event;
import events.EventAdapter;
import groups.Member;
import homepage.User;


/**
 * The adapter items for the users.
 *
 * @author Tristan Nono
 */
public class UserFollowerAdapter extends RecyclerView.Adapter<UserFollowerAdapter.UserFollowerViewHolder> {
    /**
     * The list of users.
     */
    private List<UserFollower> user_list;

    /**
     * The context variable.
     */
    private Context context;

    public UserFollowerAdapter(Context context, List<UserFollower> user_list) {
        this.context = context;
        this.user_list = user_list;
    }

    @NonNull
    @Override
    public UserFollowerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_follower_item, parent, false);
        return new UserFollowerAdapter.UserFollowerViewHolder(view);
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

