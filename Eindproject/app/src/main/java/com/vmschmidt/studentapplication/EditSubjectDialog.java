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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Collections;

public class EditSubjectDialog extends DialogFragment{

    private EditSubjectDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (EditSubjectDialogListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = getLayoutInflater().inflate(R.layout.edit_subject_dialog, container, false);

        Bundle args = getArguments();

        String subjectName = args.getString(SubjectsListActivity.KEY_SUBJECT_NAME);
        double grade = args.getDouble(SubjectsListActivity.KEY_GRADE);

        final TextView textViewSubject = rootView.findViewById(R.id.textView_subject_name);
        textViewSubject.setText(subjectName);
        final EditText editTextGrade = rootView.findViewById(R.id.editText_new_grade);
        editTextGrade.setText(String.valueOf(grade));
        Button confirmButton = rootView.findViewById(R.id.btn_confirm_edit_subject);
        Button cancelButton = rootView.findViewById(R.id.btn_cancel_edit_subject);
        Button deleteButton = rootView.findViewById(R.id.btn_delete_subject);

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

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEditSubjectDialogComplete(true, textViewSubject.getText().toString(), Double.parseDouble(editTextGrade.getText().toString()));
                dismiss();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextGrade.getCurrentTextColor() == Color.RED || editTextGrade.getText().length() == 0){
                    Toast.makeText(getContext(), R.string.toast_invalid_grade, Toast.LENGTH_SHORT).show();
                }else{
                    double grade;
                    if(editTextGrade.getText().length() > 3){
                        grade = Double.parseDouble(editTextGrade.getText().toString().substring(0, 3));
                    }else{
                        grade = Double.parseDouble(editTextGrade.getText().toString());
                    }
                    listener.onEditSubjectDialogComplete(false, textViewSubject.getText().toString(), grade);
                    dismiss();
                }
            }
        });
        return rootView;
    }

    public interface EditSubjectDialogListener{
        public void onEditSubjectDialogComplete(boolean isDeleted, String subjectName, double grade);
    }
}
