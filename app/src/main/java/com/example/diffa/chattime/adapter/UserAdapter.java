package com.example.diffa.chattime.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.diffa.chattime.R;
import com.example.diffa.chattime.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.Holder> {

    private Context context;
    private List<User> user;
    private OnClickListener listener;

    public UserAdapter(Context context, List<User> user) {
        this.context = context;
        if (user != null)
            this.user = user;
        else
            this.user = new ArrayList<>();
    }

    @NonNull
    @Override
    public UserAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.item_chat_message, parent, false), listener);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.Holder holder, int i) {
        holder.bind(user.get(i));
    }

    @Override
    public int getItemCount() {
        return user.size();
    }

    public List<User> getUser() {
        return user;
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView displayName;
        private OnClickListener listener;

        public Holder(@NonNull View itemView, OnClickListener listener) {
            super(itemView);
            displayName = itemView.findViewById(R.id.chatUsername);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        public void bind(User user) {
            displayName.setText(user.getName());
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onClick(getAdapterPosition());
            }
        }
    }

    public interface OnClickListener {
        void onClick(int position);
    }
}
