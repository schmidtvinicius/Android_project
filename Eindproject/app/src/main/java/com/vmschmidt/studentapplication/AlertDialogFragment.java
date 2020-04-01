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

public class AlertDialogFragment extends DialogFragment {

    private AlertDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (AlertDialogListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = getLayoutInflater().inflate(R.layout.alert_dialog, container, false);

        Button cancelButton = rootView.findViewById(R.id.btn_cancel_deletion);
        Button confirmButton = rootView.findViewById(R.id.btn_confirm_deletion);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAlertDialogComplete(false);
                dismiss();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAlertDialogComplete(true);
                dismiss();
            }
        });

        return rootView;
    }

    public interface AlertDialogListener{
        void onAlertDialogComplete(boolean isDeleted);
    }
}
