package com.example.diffa.chattime.ui.chatroom;

import com.example.diffa.chattime.model.User;
import com.example.diffa.chattime.model.repository.UserRepository;

import java.util.List;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;
    private UserRepository userRepository;

    public MainPresenter(MainContract.View view, UserRepository userRepository) {
        this.view = view;
        this.userRepository = userRepository;
    }

    @Override
    public void openChat(User user) {

        userRepository.openChat(user,
                user1 -> {
                    view.onSuccess(user);
                },
                throwable -> {
                    view.onError(throwable.getMessage().toString());
                });

    }

    @Override
    public void updateContacts(List<User> contacts) {
        userRepository.updateContacts(contacts);
    }

    @Override
    public List<User> getUser() {
        return userRepository.getUser();
    }

    @Override
    public void logout() {
        userRepository.logout();
    }
}
