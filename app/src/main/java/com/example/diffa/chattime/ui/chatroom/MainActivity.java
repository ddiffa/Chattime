package com.example.diffa.chattime.ui.chatroom;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.diffa.chattime.R;
import com.example.diffa.chattime.adapter.ChatRoomAdapter;
import com.example.diffa.chattime.adapter.OnItemClickListener;
import com.example.diffa.chattime.model.User;
import com.example.diffa.chattime.other.ChattimeApp;
import com.example.diffa.chattime.ui.login.LoginActivity;
import com.example.diffa.chattime.ui.newchat.NewChatDialog;
import com.example.diffa.chattime.utils.AvatarUtil;
import com.qiscus.sdk.chat.core.data.model.QiscusChatRoom;
import com.qiscus.sdk.chat.core.event.QiscusCommentReceivedEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View, OnItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView recyclerView;
    private MainContract.Presenter presenter;
    private ChatRoomAdapter chatRoomAdapter;
    private List<QiscusChatRoom> chatRoom;
    private User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rvChat);
        presenter = new MainPresenter(this, ChattimeApp.getInstance().getCompat().getUserRepository()
                , ChattimeApp.getInstance().getCompat().getChatRoomRepository());
        chatRoomAdapter = new ChatRoomAdapter(this);
        chatRoomAdapter.setOnItemClickListener(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(chatRoomAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadChatRooms();
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void onCommentReceivedEvent(QiscusCommentReceivedEvent event) {
        presenter.loadChatRooms();
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.rooms, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            new AlertDialog.Builder(this)
                    .setTitle("Logout")
                    .setMessage("Are you sure wants to logout?")
                    .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            presenter.logout();
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            finish();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void createNewChat(View view) {
        NewChatDialog.newInstance(new NewChatDialog.Listener() {
            @Override
            public void onSubmit(String name, String email) {
                presenter.createChat(new User(email, name, AvatarUtil.generateAvatar(name)));
            }
        }).show(getSupportFragmentManager(), TAG);
    }

    @Override
    public void onItemClick(int position) {
        user.setName(chatRoom.get(position).getName());
        for (int i = 0; i < chatRoom.get(position).getMember().size(); i++) {
            if (chatRoom.get(position).getMember().get(i).getUsername().equals(chatRoom.get(position).getName())) {
                user.setId(chatRoom.get(position).getMember().get(i).getEmail());
            }
        }
        user.setAvatarUrl(chatRoom.get(position).getAvatarUrl());
        presenter.openChat(user);
    }

    @Override
    public void showChatRooms(List<QiscusChatRoom> chatRooms) {
        chatRoom = chatRooms;
        chatRoomAdapter.addOrUpdate(chatRooms);
    }

    @Override
    public void showChatRoomPage(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void showErrorMEssage(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }
}
