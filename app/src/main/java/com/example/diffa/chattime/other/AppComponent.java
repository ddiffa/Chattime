package com.example.diffa.chattime.other;

import android.content.Context;

import com.example.diffa.chattime.model.impl.ChatRoomRepositoryImpl;
import com.example.diffa.chattime.model.repository.ChatRoomRepository;
import com.example.diffa.chattime.model.repository.UserRepository;
import com.example.diffa.chattime.model.impl.UserRepositoryImpl;

public class AppComponent {

    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;

    public AppComponent(Context context) {
        userRepository = new UserRepositoryImpl(context);
        chatRoomRepository = new ChatRoomRepositoryImpl();
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public ChatRoomRepository getChatRoomRepository() {
        return chatRoomRepository;
    }
}
