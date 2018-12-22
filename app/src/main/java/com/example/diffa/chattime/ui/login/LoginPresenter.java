package com.example.diffa.chattime.ui.login;

import com.example.diffa.chattime.model.repository.UserRepository;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View view;
    private UserRepository userRepository;

    public LoginPresenter(LoginContract.View view, UserRepository userRepository) {
        this.view = view;
        this.userRepository = userRepository;
    }

    @Override
    public void login(String userId, String password, String displayName) {
        view.showProgressBar();
        userRepository.login(userId, password, displayName,
                user -> {
                    view.showHomePage();
                },
                throwable -> {
                    view.showErrorMessage(throwable.getMessage());
                });
        view.hideProgressBar();
    }


}
