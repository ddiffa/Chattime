package com.example.diffa.chattime.ui.chatroom;

import com.example.diffa.chattime.model.User;
import com.example.diffa.chattime.model.repository.ChatRoomRepository;
import com.example.diffa.chattime.model.repository.UserRepository;


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
    public void createChat(User user) {
        chatRoomRepository.createChatRoom(user,
                chatRoom -> {
                    view.showChatRooms(Collections.singletonList(chatRoom));
                },
                throwable -> {
                    view.showErrorMEssage(throwable.getMessage().toString());
                });

        userRepository.openChat(user,
                intent -> view.showChatRoomPage(intent),
                throwable -> view.showErrorMEssage(throwable.getMessage()));
    }

    @Override
    public void openChat(User user) {
        userRepository.openChat(user,
                user1 -> view.showChatRoomPage(user1),
                throwable -> view.showErrorMEssage(throwable.getMessage()));
    }

    @Override
    public void logout() {
        userRepository.logout();
    }

    @Override
    public void loadChatRooms() {
        chatRoomRepository.getChatRooms(chatRooms -> view.showChatRooms(chatRooms),
                throwable -> view.showErrorMEssage(throwable.getMessage()));
    }


}
