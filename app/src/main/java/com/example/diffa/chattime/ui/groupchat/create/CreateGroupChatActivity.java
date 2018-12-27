package com.example.diffa.chattime.ui.groupchat.create;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.example.diffa.chattime.R;
import com.example.diffa.chattime.model.User;
import com.example.diffa.chattime.ChattimeApp;
import com.qiscus.sdk.chat.core.data.model.QiscusChatRoom;
import com.qiscus.sdk.ui.QiscusChannelActivity;

import java.util.ArrayList;
import java.util.List;

public class CreateGroupChatActivity extends AppCompatActivity implements CreateGroupChatContract.View {
    private static final String MEMBERS_KEY = "groups";

    private ProgressDialog progressDialog;
    private EditText edtName;
    private CreateGroupChatContract.Presenter presenter;

    public static Intent generateIntent(Context context, List<User> members) {
        Intent intent = new Intent(context, CreateGroupChatActivity.class);
        intent.putParcelableArrayListExtra(MEMBERS_KEY, (ArrayList<User>) members);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group_chat);
        List<User> members = getIntent().getParcelableArrayListExtra(MEMBERS_KEY);
        if (members == null) {
            finish();
            return;
        }
        edtName = findViewById(R.id.create_name);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait!");

        presenter = new CreateGroupChatPresenter(this,
                ChattimeApp.getInstance().getCompat().getChatRoomRepository());

        findViewById(R.id.create_submit).setOnClickListener(v -> {
            if (TextUtils.isEmpty(edtName.getText().toString())) {
                edtName.setError("Please set group name!");
            } else {
                presenter.createGroup(edtName.getText().toString(), members);
            }
        });
        findViewById(R.id.create_cancel).setOnClickListener(v -> onBackPressed());
    }

    @Override
    public void showGroupChatPage(QiscusChatRoom qiscusChatRoom) {
        finish();
        startActivity(QiscusChannelActivity.generateIntent(this, qiscusChatRoom));
    }

    @Override
    public void showLoading() {
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        progressDialog.dismiss();
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }
}
