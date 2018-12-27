package com.example.diffa.chattime.ui.groupchat;

import com.example.diffa.chattime.model.User;
import com.example.diffa.chattime.model.repository.UserRepository;

import java.util.List;

public class ChatGroupPresenter implements ChatGroupContract.Presenter {
    private ChatGroupContract.View view;
    private UserRepository userRepository;

    public ChatGroupPresenter(ChatGroupContract.View view, UserRepository userRepository) {
        this.view = view;
        this.userRepository = userRepository;
    }

    @Override
    public void loadContacts() {
        userRepository.getUser(
                users -> view.showContacts(users),
                throwable -> view.showErrorMessage(throwable.getMessage())
        );
    }

    @Override
    public void selectContacts(List<User> selectedContacts) {
        if (selectedContacts.isEmpty()) {
            view.showErrorMessage("Please select at least one contact!");
            return;
        }
        view.showCreateGroupPage(selectedContacts);
    }
}
