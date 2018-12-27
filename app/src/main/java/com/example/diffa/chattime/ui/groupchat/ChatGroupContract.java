package com.example.diffa.chattime.ui.groupchat;

import com.example.diffa.chattime.model.User;

import java.util.List;

public class ChatGroupContract {
    interface View {
        void showContacts(List<User> contacts);
        void showCreateGroupPage(List<User> members);
        void showErrorMessage(String errorMessage);
    }

    interface Presenter {
        void loadContacts();
        void selectContacts(List<User> selectedContacts);
    }
}
