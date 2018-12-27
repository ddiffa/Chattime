package com.example.diffa.chattime.ui.groupchat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.diffa.chattime.ui.groupchat.create.CreateGroupChatActivity;
import com.example.diffa.chattime.R;
import com.example.diffa.chattime.adapter.ChatGroupAdapter;
import com.example.diffa.chattime.model.User;
import com.example.diffa.chattime.ChattimeApp;

import java.util.List;

public class ChatGroupActivity extends AppCompatActivity implements ChatGroupContract.View {
    private RecyclerView recyclerView;
    private ChatGroupAdapter chatGroupAdapter;
    private ChatGroupContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_group);
        findViewById(R.id.back).setOnClickListener(v -> onBackPressed());
        recyclerView = findViewById(R.id.group_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        chatGroupAdapter = new ChatGroupAdapter(this);
        recyclerView.setAdapter(chatGroupAdapter);
        findViewById(R.id.next).setOnClickListener(v -> submit());

        presenter = new ChatGroupPresenter(this,
                ChattimeApp.getInstance().getCompat().getUserRepository());
        presenter.loadContacts();
    }

    private void submit() {
        presenter.selectContacts(chatGroupAdapter.getSelectedContacts());
    }

    @Override
    public void showContacts(List<User> contacts) {
        chatGroupAdapter.addOrUpdate(contacts);
    }

    @Override
    public void showCreateGroupPage(List<User> members) {
        finish();
        startActivity(CreateGroupChatActivity.generateIntent(this, members));
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

}
