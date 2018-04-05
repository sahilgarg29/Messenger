package com.sahilapps.messenger;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserRecyclerAdapter extends RecyclerView.Adapter<UserRecyclerAdapter.viewHolder> {

    private List<User> userList;
    private Context context;

    public UserRecyclerAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserRecyclerAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_users_list_item, parent, false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserRecyclerAdapter.viewHolder holder, int position) {
        holder.mUserNameTextView.setText(userList.get(position).getUser_name());
        Picasso.get().load(userList.get(position).getImage_url()).placeholder(R.drawable.placeholder).into(holder.mProfilePicImageView);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        private CircleImageView mProfilePicImageView;
        private TextView mUserNameTextView;

        public viewHolder(View itemView) {
            super(itemView);
            mProfilePicImageView = itemView.findViewById(R.id.profile_pic_all_users);
            mUserNameTextView = itemView.findViewById(R.id.username_allusers);
        }
    }
}
