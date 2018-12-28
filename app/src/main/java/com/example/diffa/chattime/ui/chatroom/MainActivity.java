package com.example.diffa.chattime.ui.chatroom;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.diffa.chattime.ChattimeApp;
import com.example.diffa.chattime.R;
import com.example.diffa.chattime.adapter.ChatRoomAdapter;
import com.example.diffa.chattime.adapter.OnItemClickListener;
import com.example.diffa.chattime.ui.contact.ContactActivity;
import com.example.diffa.chattime.ui.groupchat.ChatGroupActivity;
import com.example.diffa.chattime.ui.login.LoginActivity;
import com.qiscus.sdk.chat.core.data.model.QiscusChatRoom;
import com.qiscus.sdk.chat.core.event.QiscusCommentReceivedEvent;
import com.qiscus.sdk.ui.QiscusChannelActivity;
import com.qiscus.sdk.ui.QiscusChatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View, OnItemClickListener {

    private RecyclerView recyclerView;
    private MainContract.Presenter presenter;
    private ChatRoomAdapter chatRoomAdapter;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
        if (item.getItemId() == R.id.contact) {
            startActivity(new Intent(this, ContactActivity.class));
        }
        if (item.getItemId() == R.id.chatGroup) {
            startActivity(new Intent(this, ChatGroupActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int position) {
        presenter.openChat(chatRoomAdapter.getData().get(position));
    }

    @Override
    public void showChatRooms(List<QiscusChatRoom> chatRooms) {
        chatRoomAdapter.addOrUpdate(chatRooms);
    }

    @Override
    public void showChatRoomPage(QiscusChatRoom chatRoom) {
        startActivity(QiscusChatActivity.generateIntent(this, chatRoom));
    }

    @Override
    public void showChatRoomPageGroup(QiscusChatRoom qiscusChatRoom) {
        startActivity(QiscusChannelActivity.generateIntent(this, qiscusChatRoom));
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }
}
