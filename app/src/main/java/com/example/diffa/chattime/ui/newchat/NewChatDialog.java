package com.example.diffa.chattime.ui.newchat;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.util.PatternsCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.diffa.chattime.R;

public class NewChatDialog extends DialogFragment implements View.OnClickListener {

    private TextView name;
    private TextView email;
    private View btnSubmit;
    private View btnCancel;

    private Listener listener;

    public static NewChatDialog newInstance(Listener listener) {
        NewChatDialog dialog = new NewChatDialog();
        dialog.listener = listener;
        dialog.setCancelable(false);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_new_chat, container, false);
        name = (TextView) view.findViewById(R.id.name);
        email = (TextView) view.findViewById(R.id.email);
        btnSubmit = view.findViewById(R.id.tv_submit);
        btnCancel = view.findViewById(R.id.tv_cancel);

        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_submit:

                if (TextUtils.isEmpty(name.getText().toString())) {
                    name.setError("Please insert name!");
                    name.requestFocus();
                } else if (!PatternsCompat.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                    email.setError("Please insert a valid email!");
                    email.requestFocus();
                } else {
                    if (listener != null) {
                        listener.onSubmit( name.getText().toString().trim(),  email.getText().toString().trim());
                    }
                    dismiss();
                }
                break;
            case R.id.tv_cancel:
                dismiss();
                break;
        }
    }


    public interface Listener {
        void onSubmit(String name, String email);
    }
}
