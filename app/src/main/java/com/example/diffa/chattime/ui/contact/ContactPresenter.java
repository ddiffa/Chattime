package com.example.diffa.chattime.ui.contact;

import com.example.diffa.chattime.model.User;
import com.example.diffa.chattime.model.repository.ChatRoomRepository;
import com.example.diffa.chattime.model.repository.UserRepository;

public class ContactPresenter implements ContactContract.Presenter {
    private ContactContract.View view;
    private UserRepository userRepository;
    private ChatRoomRepository chatRoomRepository;

    public ContactPresenter(ContactContract.View view, UserRepository userRepository, ChatRoomRepository chatRoomRepository) {
        this.view = view;
        this.userRepository = userRepository;
        this.chatRoomRepository = chatRoomRepository;
    }

    @Override
    public void loadContacts() {
        userRepository.getUser(users -> view.showContacts(users),
                throwable -> view.showErrorMessage(throwable.getMessage()));

    }

    @Override
    public void createRoom(User contact) {
        chatRoomRepository.createChatRoom(contact,
                qiscusChatRoom -> view.showChatRoomPage(qiscusChatRoom),
                throwable -> view.showErrorMessage(throwable.getMessage()));
        userRepository.openChat(contact, intent -> view.showRoomPage(intent),
                throwable -> view.showErrorMessage(throwable.getMessage()));

    }
}
