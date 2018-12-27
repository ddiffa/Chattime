package com.example.diffa.chattime.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.diffa.chattime.ChattimeApp;
import com.example.diffa.chattime.R;
import com.example.diffa.chattime.ui.chatroom.MainActivity;
import com.qiscus.sdk.Qiscus;

public class LoginActivity extends AppCompatActivity implements OnClickListener, LoginContract.View {
    private EditText edtPassword, edtDisplayName, edtUserID;
    private Button btnLogin;
    private LoginPresenter loginPresenter;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (Qiscus.hasSetupUser()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        edtUserID = findViewById(R.id.userId);
        edtPassword = findViewById(R.id.password);
        edtDisplayName = findViewById(R.id.dispayName);
        loginPresenter = new LoginPresenter(this, ChattimeApp.getInstance().getCompat().getUserRepository());
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnLogin) {
            if (TextUtils.isEmpty(edtDisplayName.getText().toString())) {
                edtDisplayName.setError("Must not Empty!");
                edtDisplayName.requestFocus();
            } else if (TextUtils.isEmpty(edtUserID.getText().toString())) {
                edtUserID.setError("Must not Empty!");
                edtUserID.requestFocus();
            } else if (TextUtils.isEmpty(edtPassword.getText().toString())) {
                edtPassword.setError("Must not Empty!");
                edtPassword.requestFocus();
            } else {
                loginPresenter.login(
                        edtUserID.getText().toString().trim(),
                        edtPassword.getText().toString().trim(),
                        edtDisplayName.getText().toString().trim());
            }
        }
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showHomePage() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }
}
