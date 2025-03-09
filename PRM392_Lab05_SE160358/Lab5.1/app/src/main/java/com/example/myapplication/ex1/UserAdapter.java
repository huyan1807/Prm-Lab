package com.example.myapplication.ex1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.R;

import java.util.List;

// Adapter class for managing a list of User objects in a RecyclerView
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private final List<User> users;

    // Constructor to initialize the user list
    public UserAdapter(List<User> users) {
        this.users = users;
    }

    // Create and return a new ViewHolder for displaying a user item
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the layout for a user list item
        View userView = inflater.inflate(R.layout.row_userlist, parent, false);
        ViewHolder viewHolder = new ViewHolder(userView);
        return viewHolder;
    }

    // Bind user data to the ViewHolder's views for display in the RecyclerView
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = users.get(position);
        holder.tvUsername.setText("Username: " + user.getUsername());
        holder.tvFullName.setText("Fullname: " + user.getFullname());
        holder.tvEmail.setText("Email: " + user.getEmail());
    }

    // Return the total number of user items to be displayed
    @Override
    public int getItemCount() {
        return users.size();
    }

    // ViewHolder class that holds references to the views for each user item
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvUsername;
        TextView tvFullName;
        TextView tvEmail;

        // Constructor to bind the views in the layout
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            tvEmail = itemView.findViewById(R.id.tvEmail);
        }
    }


}