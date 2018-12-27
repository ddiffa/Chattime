package com.example.diffa.chattime.ui.contact;

import android.content.Intent;

import com.example.diffa.chattime.model.User;
import com.qiscus.sdk.chat.core.data.model.QiscusChatRoom;

import java.util.List;

public class ContactContract {
    interface View {
        void showContacts(List<User> contacts);
        void showChatRoomPage(QiscusChatRoom qiscusChatRoom);
        void showRoomPage(Intent intent);
        void showErrorMessage(String errorMessage);
    }

    interface Presenter{
        void loadContacts();
        void createRoom(User contact);
    }
}
