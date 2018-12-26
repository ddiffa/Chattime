//package com.example.diffa.chattime.adapter;
//
//import android.content.Context;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.bumptech.glide.request.RequestOptions;
//import com.example.diffa.chattime.R;
//import com.example.diffa.chattime.model.User;
//import com.qiscus.nirmana.Nirmana;
//import com.qiscus.sdk.chat.core.data.model.QiscusChatRoom;
//import com.qiscus.sdk.chat.core.data.model.QiscusComment;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class UserAdapter extends RecyclerView.Adapter<UserAdapter.Holder> {
//
//    private Context context;
//    private List<User> user;
//    private OnClickListener listener;
//
//    public UserAdapter(Context context, List<User> user) {
//        this.context = context;
//        if (user != null)
//            this.user = user;
//        else
//            this.user = new ArrayList<>();
//    }
//
//    @NonNull
//    @Override
//    public UserAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
//        return new Holder(LayoutInflater.from(context).inflate(R.layout.item_chat_message, parent, false), listener);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull UserAdapter.Holder holder, int i) {
//        holder.bind(user.get(i));
//    }
//
//    @Override
//    public int getItemCount() {
//        return user.size();
//    }
//
//    public List<User> getUser() {
//        return user;
//    }
//
//    public void setListener(OnClickListener listener) {
//        this.listener = listener;
//    }
//
//    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        private TextView displayName;
//        private TextView reviewChat;
//        private ImageView imageView;
//        private OnClickListener listener;
//
//        public Holder(@NonNull View itemView, OnClickListener listener) {
//            super(itemView);
//            displayName = itemView.findViewById(R.id.chatUsername);
//            reviewChat = itemView.findViewById(R.id.chatReview);
//            imageView = itemView.findViewById(R.id.chatImage);
//            this.listener = listener;
//            itemView.setOnClickListener(this);
//        }
//
//        public void bind(QiscusChatRoom qiscusChatRoom) {
//            displayName.setText(qiscusChatRoom.getName());
//            Nirmana.getInstance().get()
//                    .setDefaultRequestOptions(new RequestOptions()
//                            .placeholder(R.drawable.ic_qiscus_avatar)
//                            .error(R.drawable.ic_qiscus_avatar)
//                            .dontAnimate())
//                    .load(qiscusChatRoom.getAvatarUrl())
//                    .into(imageView);
//            QiscusComment lastMessage = qiscusChatRoom.getLastComment();
//            String lastMessageTxt = lastMessage.isMyComment() ? "You: " : lastMessage.getSender().split(" ")[0] + ": ";
//            lastMessageTxt += qiscusChatRoom.getLastComment().getType() == QiscusComment.Type.IMAGE ? "\uD83D\uDCF7 send an image" : lastMessage.getMessage();
//            reviewChat.setText(lastMessageTxt);
//
//
//        }
//
//        @Override
//        public void onClick(View v) {
//            if (listener != null) {
//                listener.onClick(getAdapterPosition());
//            }
//        }
//    }
//
//    public interface OnClickListener {
//        void onClick(int position);
//    }
//}
