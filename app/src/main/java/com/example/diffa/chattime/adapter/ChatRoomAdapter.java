package com.example.diffa.chattime.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.example.diffa.chattime.R;
import com.example.diffa.chattime.utils.DateUtil;
import com.qiscus.nirmana.Nirmana;
import com.qiscus.sdk.chat.core.data.model.QiscusChatRoom;
import com.qiscus.sdk.chat.core.data.model.QiscusComment;

import java.util.List;


public class ChatRoomAdapter extends SortedRecyclerViewAdapter<QiscusChatRoom, ChatRoomAdapter.VH> {

    private Context context;
    private OnItemClickListener onItemClickListener;

    public ChatRoomAdapter(Context context) {
        this.context = context;
    }

    @Override
    protected Class<QiscusChatRoom> getItemClass() {
        return QiscusChatRoom.class;
    }

    @Override
    protected int compare(QiscusChatRoom item1, QiscusChatRoom item2) {
        return item2.getLastComment().getTime().compareTo(item1.getLastComment().getTime());
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chat_message, parent, false);
        return new VH(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.bind(getData().get(position));
    }

    public void addOrUpdate(List<QiscusChatRoom> chatRooms) {
        for (QiscusChatRoom chatRoom : chatRooms) {
            int index = findPosition(chatRoom);
            if (index == -1) {
                getData().add(chatRoom);
            } else {
                getData().updateItemAt(index, chatRoom);
            }
        }
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public static class VH extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView avatar;
        private TextView name;
        private TextView lastMessage;
        private TextView unread;
        private  TextView time;
        private OnItemClickListener onItemClickListener;

        VH(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            avatar = itemView.findViewById(R.id.chatImage);
            name = itemView.findViewById(R.id.chatUsername);
            lastMessage = itemView.findViewById(R.id.chatReview);
            unread = itemView.findViewById(R.id.chatUnread);
            time = itemView.findViewById(R.id.chatTime);
            this.onItemClickListener = onItemClickListener;

            itemView.setOnClickListener(this);
        }

        @SuppressLint("ResourceAsColor")
        void bind(QiscusChatRoom chatRoom) {

            Nirmana.getInstance().get()
                    .setDefaultRequestOptions(new RequestOptions()
                            .placeholder(R.drawable.ic_qiscus_avatar)
                            .error(R.drawable.ic_qiscus_avatar)
                            .dontAnimate())
                    .load(chatRoom.getAvatarUrl())
                    .into(avatar);
            time.setText(DateUtil.toTimeDate(chatRoom.getLastComment().getTime()));
            name.setText(chatRoom.getName());
            if (chatRoom.getLastComment() != null) {
                QiscusComment lastComment = chatRoom.getLastComment();
                String lastMessageText = lastComment.isMyComment() ? "You: " : lastComment.getSender().split(" ")[0] + ": ";
                lastMessageText += chatRoom.getLastComment().getType() == QiscusComment.Type.IMAGE
                        ? "\uD83D\uDCF7 send an image" : lastComment.getMessage();
                lastMessage.setText(lastMessageText);
            }
            if (chatRoom.getUnreadCount() != 0) {
                unread.setVisibility(View.VISIBLE);
                unread.setText(String.valueOf(chatRoom.getUnreadCount()));
            } else {
                unread.setVisibility(View.GONE);
            }

        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }

}
