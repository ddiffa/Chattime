package com.example.diffa.chattime.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.example.diffa.chattime.R;
import com.example.diffa.chattime.model.User;
import com.qiscus.nirmana.Nirmana;

import java.util.List;

public class ContactAdapter extends SortedRecyclerViewAdapter<User, ContactAdapter.Holder> {
    private Context context;
    private OnItemClickListener onItemClickListener;

    public ContactAdapter(Context context) {
        this.context = context;
    }

    @Override
    protected Class<User> getItemClass() {
        return User.class;
    }

    @Override
    protected int compare(User item1, User item2) {
        return item1.compareTo(item2);
    }

    @NonNull
    @Override
    public ContactAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false);
        return new Holder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.Holder holder, int position) {
        holder.bind(getData().get(position));
    }

    public void addOrUpdate(List<User> contacts) {
        for (User contact : contacts) {
            int index = findPosition(contact);
            if (index == -1) getData().add(contact);
            else getData().updateItemAt(index, contact);
        }
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView avatar;
        private TextView username;
        private OnItemClickListener onItemClickListener;

        public Holder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            avatar = itemView.findViewById(R.id.contact_avatar);
            username = itemView.findViewById(R.id.contact_name);
            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
        }

        public void bind(User user) {
            Nirmana.getInstance().get()
                    .setDefaultRequestOptions(new RequestOptions()
                            .placeholder(R.drawable.ic_qiscus_avatar)
                            .error(R.drawable.ic_qiscus_avatar)
                            .dontAnimate())
                    .load(user.getAvatarUrl())
                    .into(avatar);
            username.setText(user.getName());
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
