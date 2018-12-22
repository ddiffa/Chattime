package com.example.diffa.chattime.model.impl;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.diffa.chattime.model.User;
import com.example.diffa.chattime.model.repository.UserRepository;
import com.example.diffa.chattime.ui.chatroom.MainContract;
import com.example.diffa.chattime.utils.Action;
import com.example.diffa.chattime.utils.AvatarUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.chat.core.data.model.QiscusAccount;

import java.io.IOException;
import java.util.List;

import retrofit2.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserRepositoryImpl implements UserRepository {

    private Context context;
    private Gson gson;
    private SharedPreferences sharedPreferences;

    public UserRepositoryImpl(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("rooms", Context.MODE_PRIVATE);
        gson = new Gson();
    }


    @Override
    public void login(String userId, String password, String displayName, Action<User> onSuccess, Action<Throwable> onError) {
        Qiscus.setUser(userId, password)
                .withUsername(displayName)
                .withAvatarUrl(AvatarUtil.generateAvatar(displayName))
                .save()
                .map(this::mapFromQiscusAccount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccess::call, onError::call);
    }

    @Override
    public void openChat(User user, MainContract.View view) {
        Qiscus.buildChatWith(user.getId())
                .withTitle(user.getName())
                .build(context, new Qiscus.ChatActivityBuilderListener() {
                    @Override
                    public void onSuccess(Intent intent) {
                        view.onSuccess(user);
                        context.startActivity(intent);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        if (throwable instanceof HttpException) {
                            HttpException e = (HttpException) throwable;
                            try {
                                String errorMessage = e.response().errorBody().string();
                                view.onError(errorMessage);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        } else if (throwable instanceof IOException) {
                            view.onError("Can not connect to qiscus server!");
                        } else { //Unknown error
                            view.onError("Unexpected error!");
                        }
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    public void updateContacts(List<User> contacts) {
        sharedPreferences.edit()
                .putString("contacts", gson.toJson(contacts))
                .apply();
    }

    @Override
    public List<User> getUser() {
        String json = sharedPreferences.getString("contacts","");
        return gson.fromJson(json, new TypeToken<List<User>>() {
        }.getType());
    }

    @Override
    public void logout() {
        Qiscus.clearUser();
        sharedPreferences.edit().clear().apply();
    }

    private User mapFromQiscusAccount(QiscusAccount qiscusAccount) {
        User user = new User();
        user.setId(qiscusAccount.getEmail());
        user.setName(qiscusAccount.getUsername());
        user.setAvatarUrl(qiscusAccount.getAvatar());
        return user;
    }
}
