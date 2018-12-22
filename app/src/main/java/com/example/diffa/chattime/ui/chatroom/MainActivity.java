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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.diffa.chattime.R;
import com.example.diffa.chattime.adapter.UserAdapter;
import com.example.diffa.chattime.model.User;
import com.example.diffa.chattime.other.ChattimeApp;
import com.example.diffa.chattime.ui.login.LoginActivity;
import com.example.diffa.chattime.ui.newchat.NewChatDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private MainContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rvChat);
        presenter = new MainPresenter(this, ChattimeApp.getInstance().getCompat().getUserRepository());
        userAdapter = new UserAdapter(this, presenter.getUser());
        userAdapter.setListener(new UserAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                presenter.openChat(userAdapter.getUser().get(position));
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(userAdapter);

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
                presenter.openChat(new User(email, name, ""));
            }
        }).show(getSupportFragmentManager(), TAG);
    }

    private void updateList(User user) {
        if (!userAdapter.getUser().contains(user)) {
            userAdapter.getUser().add(user);
            userAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSuccess(User user) {
        List<User> contacts = presenter.getUser();
        if (contacts == null) {
            contacts = new ArrayList<>();
        }

        if (!contacts.contains(user)) {
            contacts.add(user);
            presenter.updateContacts(contacts);
            updateList(user);
        }
    }

    @Override
    public void onError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }


}
