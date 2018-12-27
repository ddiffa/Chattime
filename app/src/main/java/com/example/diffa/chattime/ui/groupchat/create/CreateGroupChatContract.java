package com.example.diffa.chattime.ui.groupchat.create;

import com.example.diffa.chattime.model.User;
import com.qiscus.sdk.chat.core.data.model.QiscusChatRoom;

import java.util.List;

public class CreateGroupChatContract {
    interface Presenter{
        void createGroup(String name, List<User> members);
    }
    interface View {
        void showGroupChatPage(QiscusChatRoom qiscusChatRoom);
        void showLoading();
        void hideLoading();
        void showErrorMessage(String errorMessage);

    }
}
