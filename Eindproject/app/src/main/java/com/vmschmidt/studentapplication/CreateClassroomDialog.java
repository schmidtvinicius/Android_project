package com.vmschmidt.studentapplication;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Set;

public class CreateClassroomDialog extends DialogFragment {

    private static final String KEY_KEYS = "keys";
    private static final String KEY_REGEX = "regex";

    private CreateClassroomListener listener;

    public static CreateClassroomDialog newInstance(ArrayList<String> keys, String regex){
        Bundle args = new Bundle();

        args.putStringArrayList(KEY_KEYS, keys);
        args.putString(KEY_REGEX, regex);
        CreateClassroomDialog fragment = new CreateClassroomDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (CreateClassroomListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.create_classroom_dialog, container, false);

        final ArrayList<String> keys = getArguments().getStringArrayList(KEY_KEYS);
        final String regex = getArguments().getString(KEY_REGEX);

        final EditText editTextClassroomCode = rootView.findViewById(R.id.editText_dialog_new_classroomcode);
        Button cancelButton = rootView.findViewById(R.id.btn_cancel);
        Button addButton = rootView.findViewById(R.id.btn_add);

        editTextClassroomCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().matches(regex) || keys.contains(s.toString())){
                    editTextClassroomCode.setTextColor(Color.RED);
                }else{
                    editTextClassroomCode.setTextColor(Color.BLACK);
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextClassroomCode.getCurrentTextColor() == Color.RED || editTextClassroomCode.getText().length() == 0){
                    Toast.makeText(getContext(), R.string.toast_cannot_create_classroom, Toast.LENGTH_LONG).show();
                }else{
                    listener.onCreateClassroomComplete(editTextClassroomCode.getText().toString());
                    dismiss();
                }
            }
        });

        return rootView;
    }

    public interface CreateClassroomListener{
        void onCreateClassroomComplete(String newClassroomCode);
    }
}
