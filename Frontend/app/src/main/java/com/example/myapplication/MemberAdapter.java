// Author Tristan Nono

// Side note just think of member as like the teams/group I need to rename them.

package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

/*
This is responsible for implementing/inflating the item layout.
Also, treats the functionally of said individual list.
 */
public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder> {
    /*
    The list of the events.
     */
    private List<Member> member_list;

    /*
    Epic constructor. This is how we have this event list initialized.
     */
    public MemberAdapter(List<Member> member_list) {
        this.member_list = member_list;
    }

    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_item, parent, false);
        return new MemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        Member member = member_list.get(position);
        //holder.username.setText(member.getUsername());
        holder.group_name.setText(member.getGroupName());
        holder.description.setText(member.getDescription());

    }


    @Override
    public int getItemCount() {
        return member_list.size();
    }

    /*
    This is for the list or I should say the characteristics of it like what goes in it.
    Like our title and description and so on.
     */
    static class MemberViewHolder extends RecyclerView.ViewHolder {
        /*
        The user's name.
         */
        TextView username;

        /*
        Group's name.
         */
        TextView group_name;

        /*
        Information.
         */
        TextView description;

        /*
        This holds all the variables in place for the events.
         */
        MemberViewHolder(View item_view) {
            super(item_view);
            username = item_view.findViewById(R.id.username);
            group_name = item_view.findViewById(R.id.group_name);
            description = item_view.findViewById(R.id.description);
        }
    }
}