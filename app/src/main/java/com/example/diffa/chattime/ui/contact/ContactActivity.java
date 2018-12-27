package com.example.diffa.chattime.ui.contact;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.diffa.chattime.R;
import com.example.diffa.chattime.adapter.ContactAdapter;
import com.example.diffa.chattime.adapter.OnItemClickListener;
import com.example.diffa.chattime.model.User;
import com.example.diffa.chattime.ChattimeApp;
import com.qiscus.sdk.chat.core.data.model.QiscusChatRoom;

import java.util.List;

public class ContactActivity extends AppCompatActivity implements OnItemClickListener, ContactContract.View {
    private RecyclerView recyclerView;
    private ContactAdapter contactAdapter;

    private ContactContract.Presenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.back).setOnClickListener(v -> onBackPressed());
        recyclerView = findViewById(R.id.contact_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        contactAdapter = new ContactAdapter(this);
        contactAdapter.setOnItemClickListener(this);

        recyclerView.setAdapter(contactAdapter);
        presenter = new ContactPresenter(this,
                ChattimeApp.getInstance().getCompat().getUserRepository(),
                ChattimeApp.getInstance().getCompat().getChatRoomRepository());

        presenter.loadContacts();






    }

    @Override
    public void onItemClick(int position) {
        presenter.createRoom(contactAdapter.getData().get(position));
    }

    @Override
    public void showContacts(List<User> contacts) {
        contactAdapter.addOrUpdate(contacts);
    }

    @Override
    public void showChatRoomPage(QiscusChatRoom qiscusChatRoom) {

    }

    @Override
    public void showErrorMessage(String errorMessage) {
        Toast.makeText(this,errorMessage,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRoomPage(Intent intent) {

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
