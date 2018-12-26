package com.example.diffa.chattime.ui.chatroom;

import android.content.Intent;

import com.example.diffa.chattime.model.User;
import com.qiscus.sdk.chat.core.data.model.QiscusChatRoom;

import java.util.List;

public class MainContract {

    public interface View {
        void showChatRooms(List<QiscusChatRoom> chatRooms);

        void showChatRoomPage(Intent intent);

        void showErrorMEssage(String errorMessage);
    }

    interface Presenter {
        void openChat(User user);
        void createChat(User user);
        void logout();
        void loadChatRooms();
    }
}
