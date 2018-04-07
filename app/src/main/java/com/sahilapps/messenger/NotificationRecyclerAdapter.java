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

public class NotificationRecyclerAdapter extends RecyclerView.Adapter<NotificationRecyclerAdapter.viewHolder> {

    private List<Notification> notificationList;
    private Context context;

    public NotificationRecyclerAdapter(List<Notification> notificationList, Context context) {
        this.notificationList = notificationList;
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationRecyclerAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_list_item, parent, false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationRecyclerAdapter.viewHolder holder, int position) {
        holder.userNameTextView.setText(notificationList.get(position).getUser_name());
        holder.bodyTextView.setText(notificationList.get(position).getMessage());
        Picasso.get().load(notificationList.get(position).getImage_url()).placeholder(R.drawable.placeholder).into(holder.profilePicImageView);
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        private View itemView;
        private CircleImageView profilePicImageView;
        private TextView userNameTextView;
        private TextView bodyTextView;

        public viewHolder(View itemView) {
            super(itemView);

            profilePicImageView = itemView.findViewById(R.id.profile_pic_notification);
            userNameTextView = itemView.findViewById(R.id.user_name_notification);
            bodyTextView = itemView.findViewById(R.id.text_notification);
            itemView = itemView;
        }

    }
}
