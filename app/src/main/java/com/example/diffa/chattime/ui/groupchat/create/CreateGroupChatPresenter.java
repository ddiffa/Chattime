package com.example.diffa.chattime.ui.groupchat.create;

import com.example.diffa.chattime.model.User;
import com.example.diffa.chattime.model.repository.ChatRoomRepository;

import java.util.List;

public class CreateGroupChatPresenter implements CreateGroupChatContract.Presenter {
    private CreateGroupChatContract.View view;
    private ChatRoomRepository chatRoomRepository;

    public CreateGroupChatPresenter(CreateGroupChatContract.View view, ChatRoomRepository chatRoomRepository) {
        this.view = view;
        this.chatRoomRepository = chatRoomRepository;
    }

    @Override
    public void createGroup(String name, List<User> members) {
        view.showLoading();
        chatRoomRepository.createGroupChatRoom(name, members,
                qiscusChatRoom -> {
                    view.hideLoading();
                    view.showGroupChatPage(qiscusChatRoom);
                },
                throwable -> {
                    view.hideLoading();
                    view.showErrorMessage(throwable.getMessage());
                });

    }
}
