package com.example.diffa.chattime.other;

import android.content.Context;

import com.example.diffa.chattime.model.repository.UserRepository;
import com.example.diffa.chattime.model.impl.UserRepositoryImpl;

public class AppComponent {

    private final UserRepository userRepository;

    public AppComponent(Context context) {
        userRepository = new UserRepositoryImpl(context);
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
}
