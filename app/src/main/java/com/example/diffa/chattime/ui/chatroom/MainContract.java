package com.example.diffa.chattime.ui.chatroom;

import android.content.Intent;

import com.example.diffa.chattime.model.User;

import java.util.List;

public class MainContract {

    public interface View {
        void onSuccess(User user);
        void onError(String errorMessage);
    }

    interface Presenter {
        void openChat(User user);
        List<User> getUser();
        void logout();
        void updateContacts(List<User> contacts);
    }
}
