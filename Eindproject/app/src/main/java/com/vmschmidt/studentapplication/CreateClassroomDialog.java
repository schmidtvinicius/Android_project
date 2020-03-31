package com.vmschmidt.studentapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class CreateClassroomDialog extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.create_classroom_dialog, container, false);

        //EditText editTextClassroomCode = rootView.findViewById(R.id.editText_dialog_new_classroomcode);
        Button cancelButton = rootView.findViewById(R.id.btn_cancel);
        Button addButton = rootView.findViewById(R.id.btn_add);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        };

        cancelButton.setOnClickListener(listener);
        addButton.setOnClickListener(listener);

        return rootView;
    }
}
