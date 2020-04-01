package com.vmschmidt.studentapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddFriendDialog extends DialogFragment {

    private AddFriendDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (AddFriendDialogListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = getLayoutInflater().inflate(R.layout.add_friend_dialog, container, false);

        Button confirmButton = rootView.findViewById(R.id.btn_confirm_add_student);
        Button cancelButton = rootView.findViewById(R.id.btn_cancel_add_friend);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAddFriendDialogComplete(true);
                dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAddFriendDialogComplete(false);
                dismiss();
            }
        });
        return rootView;
    }

    public interface AddFriendDialogListener{
        public void onAddFriendDialogComplete(boolean friendAdded);
    }
}
