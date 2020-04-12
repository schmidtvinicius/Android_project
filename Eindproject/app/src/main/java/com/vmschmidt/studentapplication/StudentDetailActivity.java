package com.vmschmidt.studentapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vmschmidt.studentapplication.dataprovider.DataProvider;
import com.vmschmidt.studentapplication.student.Student;

import java.util.Set;

public class StudentDetailActivity extends AppCompatActivity  {

    public static final String EXTRA_FIRSTNAME = "firstname";
    public static final String EXTRA_MIDDLENAME = "middlename";
    public static final String EXTRA_LASTNAME = "lastname";
    public static final String EXTRA_CLASSROOM = "classroon";

    Intent startIntent;

    private int resultCode;

    private EditText editTextFirstName;
    private EditText editTextMiddleName;
    private EditText editTextLastName;
    private EditText editTextEmail;
    private EditText editTextClassroom;
    private EditText editTextStudentNumber;
    private FloatingActionButton mailButton;
    private Student student;
    private int studentNumber;
    private Set<String> existingClassrooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);

        mailButton = findViewById(R.id.btn_mail);
        mailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("mailto:" + student.getEmailAddress());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        existingClassrooms = DataProvider.getClassroomsKeys(this);

        resultCode = RESULT_CANCELED;

        startIntent = getIntent();
        studentNumber = startIntent.getIntExtra(StudentListActivity.EXTRA_STUDENT, -1);
        student = DataProvider.findStudent(studentNumber);

        editTextFirstName = findViewById(R.id.editText_firstname);
        editTextMiddleName = findViewById(R.id.editText_middlename);
        editTextLastName = findViewById(R.id.editText_lastname);
        editTextEmail = findViewById(R.id.editText_email);
        editTextClassroom = findViewById(R.id.editText_classroom);
        editTextStudentNumber = findViewById(R.id.editText_studentnumber);

        editTextFirstName.setText(student.getFirstName());
        editTextMiddleName.setText(student.getMiddleName());
        editTextLastName.setText(student.getLastName());
        editTextEmail.setText(student.getEmailAddress());
        editTextClassroom.setText(student.getClassroom());
        editTextStudentNumber.setText(String.valueOf(student.getStudentNumber()));

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StudentDetailActivity.this, R.string.toast_unique_student_value, Toast.LENGTH_SHORT).show();
            }
        };

        editTextEmail.setOnClickListener(listener);
        editTextStudentNumber.setOnClickListener(listener);

        editTextClassroom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0){
                    if(!existingClassrooms.contains(s.toString())){
                        editTextClassroom.setTextColor(Color.RED);
                    }else{
                        editTextClassroom.setTextColor(Color.BLACK);
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        MenuItem deleteOption = menu.add(R.string.option_delete);
        deleteOption.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                resultCode = StudentListActivity.RESULT_DELETED;
                onBackPressed();
                return false;
            }
        });
        MenuItem confirmOption = menu.add(R.string.option_confirm);
        confirmOption.setIcon(R.drawable.ic_check);
        confirmOption.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        confirmOption.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                resultCode = RESULT_OK;
                onBackPressed();
                return false;
            }
        });
        MenuItem cancelOption = menu.add(R.string.option_cancel);
        cancelOption.setIcon(R.drawable.ic_clear);
        cancelOption.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        cancelOption.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                resultCode = RESULT_CANCELED;
                onBackPressed();
                return false;
            }
        });
        MenuItem showSubjects = menu.add(R.string.option_show_subjects_list);
        showSubjects.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent showSubjectsIntent = new Intent(StudentDetailActivity.this, SubjectsListActivity.class);
                showSubjectsIntent.putExtra(StudentListActivity.EXTRA_STUDENT, student.getStudentNumber());
                startActivity(showSubjectsIntent);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        if(resultCode == RESULT_OK){
            if(editTextFirstName.getText().length() == 0){
                Toast.makeText(this, R.string.toast_missing_name, Toast.LENGTH_SHORT).show();
            }else if(editTextClassroom.getText().length() == 0 || editTextClassroom.getCurrentTextColor() == Color.RED){
                Toast.makeText(this, R.string.toast_classroom_invalid, Toast.LENGTH_SHORT).show();
            }else{
                resultIntent.putExtra(EXTRA_FIRSTNAME, editTextFirstName.getText().toString().trim());
                resultIntent.putExtra(EXTRA_MIDDLENAME, editTextMiddleName.getText().toString().trim());
                resultIntent.putExtra(EXTRA_LASTNAME, editTextLastName.getText().toString().trim());
                resultIntent.putExtra(EXTRA_CLASSROOM, editTextClassroom.getText().toString().trim());
                resultIntent.putExtra(StudentListActivity.EXTRA_STUDENT, studentNumber);
                setResult(resultCode, resultIntent);
                super.onBackPressed();
            }
        }else{
            resultIntent.putExtra(StudentListActivity.EXTRA_STUDENT, studentNumber);
            setResult(resultCode, resultIntent);
            super.onBackPressed();
        }

    }
}