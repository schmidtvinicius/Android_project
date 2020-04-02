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

public class AddSubjectDialog extends DialogFragment {

    private AddSubjectDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (AddSubjectDialogListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = getLayoutInflater().inflate(R.layout.add_subject_dialog, container, false);

        Button confirmButton = rootView.findViewById(R.id.btn_confirm_add_subject);
        Button cancelButton = rootView.findViewById(R.id.btn_cancel_add_subject);
        final EditText editTextSubject = rootView.findViewById(R.id.editText_new_subject);
        final EditText editTextGrade = rootView.findViewById(R.id.editText_grade);


        editTextGrade.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0){
                    if(Double.parseDouble(s.toString()) > 10){
                        editTextGrade.setTextColor(Color.RED);
                    }else{
                        editTextGrade.setTextColor(Color.BLACK);
                    }
                }

            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextGrade.getCurrentTextColor() == Color.RED || editTextGrade.getText().length() == 0){
                    Toast.makeText(getContext(), R.string.toast_invalid_grade, Toast.LENGTH_SHORT).show();
                }else if(editTextSubject.getText().length() == 0){
                    Toast.makeText(getContext(), R.string.toast_missing_subject_name, Toast.LENGTH_SHORT).show();
                }else{
                    listener.onAddSubjectDialogComplete(editTextSubject.getText().toString(), Double.parseDouble(editTextGrade.getText().toString()));
                    dismiss();
                }
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return rootView;
    }

    public interface AddSubjectDialogListener {
        public void onAddSubjectDialogComplete(String subjectName, double grade);
    }
}
