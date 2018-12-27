package com.example.diffa.chattime.adapter;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.example.diffa.chattime.R;
import com.example.diffa.chattime.model.User;
import com.qiscus.nirmana.Nirmana;

import java.util.ArrayList;
import java.util.List;


public class ChatGroupAdapter extends SortedRecyclerViewAdapter<ChatGroupAdapter.SelectableContact, ChatGroupAdapter.Holder> {

    private Context context;

    public ChatGroupAdapter(Context context) {
        this.context = context;
    }

    @Override
    protected Class<SelectableContact> getItemClass() {
        return SelectableContact.class;
    }

    @Override
    protected int compare(SelectableContact item1, SelectableContact item2) {
        return item1.user.compareTo(item2.user);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.item_group_chat, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.bind(getData().get(position));
    }

    public void addOrUpdate(List<User> contacts) {
        for (User contact : contacts) {
            SelectableContact selectableContact = new SelectableContact(contact);
            int index = findPosition(selectableContact);
            if (index == -1) {
                getData().add(selectableContact);
            } else {
                selectableContact.selected = getData().get(index).selected;
                getData().updateItemAt(index, selectableContact);
            }
        }
        notifyDataSetChanged();
    }

    public List<User> getSelectedContacts() {
        List<User> contacts = new ArrayList<>();
        int size = getData().size();
        for (int i = 0; i < size; i++) {
            if (getData().get(i).selected) {
                contacts.add(getData().get(i).user);
            }
        }
        return contacts;
    }

    static class Holder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {
        private ImageView avatar;
        private TextView name;
        private CheckBox checkBox;

        private SelectableContact selectableContact;

        Holder(View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.group_avatar);
            name = itemView.findViewById(R.id.group_name);
            checkBox = itemView.findViewById(R.id.group_selected);

            checkBox.setOnCheckedChangeListener(this);
        }

        void bind(SelectableContact selectableContact) {
            this.selectableContact = selectableContact;
            Nirmana.getInstance().get()
                    .setDefaultRequestOptions(new RequestOptions()
                            .placeholder(R.drawable.ic_qiscus_avatar)
                            .error(R.drawable.ic_qiscus_avatar)
                            .dontAnimate())
                    .load(selectableContact.user.getAvatarUrl())
                    .into(avatar);
            name.setText(selectableContact.user.getName());
            checkBox.setSelected(selectableContact.selected);
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            selectableContact.selected = isChecked;
        }
    }

    static class SelectableContact implements Parcelable {
        public static final Creator<SelectableContact> CREATOR = new Creator<SelectableContact>() {
            @Override
            public SelectableContact createFromParcel(Parcel in) {
                return new SelectableContact(in);
            }

            @Override
            public SelectableContact[] newArray(int size) {
                return new SelectableContact[size];
            }
        };
        private User user;
        private boolean selected;

        SelectableContact(User user) {
            this.user = user;
        }

        private SelectableContact(Parcel in) {
            user = in.readParcelable(User.class.getClassLoader());
            selected = in.readByte() != 0;
        }

        @Override
        public int describeContents() {
            return hashCode();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(user, flags);
            dest.writeByte((byte) (selected ? 1 : 0));
        }
    }
}
