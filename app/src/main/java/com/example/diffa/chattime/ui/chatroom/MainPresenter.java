package com.example.diffa.chattime.ui.chatroom;

import com.example.diffa.chattime.model.User;
import com.example.diffa.chattime.model.repository.ChatRoomRepository;
import com.example.diffa.chattime.model.repository.UserRepository;
import com.qiscus.sdk.chat.core.data.model.QiscusChatRoom;


import java.util.Collections;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;
    private UserRepository userRepository;
    private ChatRoomRepository chatRoomRepository;

    public MainPresenter(MainContract.View view, UserRepository userRepository, ChatRoomRepository chatRoomRepository) {
        this.view = view;
        this.userRepository = userRepository;
        this.chatRoomRepository = chatRoomRepository;
    }

    @Override
    public void openChat(QiscusChatRoom chatRoom) {
        if (chatRoom.isGroup()){
            view.showChatRoomPageGroup(chatRoom);
        return;
        }
        view.showChatRoomPage(chatRoom);
    }

    @Override
    public void logout() {
        userRepository.logout();
    }

    @Override
    public void loadChatRooms() {
        chatRoomRepository.getChatRooms(chatRooms -> view.showChatRooms(chatRooms),
                throwable -> view.showErrorMessage(throwable.getMessage()));
    }


}
