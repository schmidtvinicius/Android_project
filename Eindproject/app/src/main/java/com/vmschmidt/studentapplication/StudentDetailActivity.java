package com.vmschmidt.studentapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vmschmidt.studentapplication.dataprovider.DataProvider;
import com.vmschmidt.studentapplication.student.Student;

public class StudentDetailActivity extends AppCompatActivity {

    public static final String EXTRA_FIRSTNAME = "firstname";
    public static final String EXTRA_MIDDLENAME = "middlename";
    public static final String EXTRA_LASTNAME = "lastname";
    public static final String EXTRA_CLASSROOM = "classroon";

    Intent startIntent;
    Intent resultIntent;

    private int resultCode;

    EditText editTextFirstName;
    EditText editTextMiddleName;
    EditText editTextLastName;
    EditText editTextEmail;
    EditText editTextClassroom;
    EditText editTextStudentNumber;
    FloatingActionButton mailButton;
    Student student;
    int studentIndex;
    int originalClassroomSize;
    String classroomCode;

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

        resultIntent = new Intent();

        startIntent = getIntent();
        classroomCode = startIntent.getStringExtra(ClassroomsListActivity.EXTRA_CLASSROOM);
        studentIndex = startIntent.getIntExtra(StudentListActivity.EXTRA_STUDENT, -1);
        student = DataProvider.classrooms.get(classroomCode).getStudentList().get(studentIndex);
        originalClassroomSize = DataProvider.classrooms.get(classroomCode).totalStudents();

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
                if(!s.toString().trim().matches("^EHI1V.([BI][ab]|S[a-d])$")){
                    editTextClassroom.setTextColor(Color.RED);

                }else{
                    editTextClassroom.setTextColor(Color.BLACK);
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        int orientation = this.getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            MenuItem mailOption = menu.add(R.string.option_mail);
            mailOption.setIcon(R.drawable.ic_email);
            mailOption.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            mailOption.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    mailButton.callOnClick();
                    return false;
                }
            });
        }
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
                onBackPressed();
                return false;
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {
        if(resultCode == RESULT_OK){
            resultIntent.putExtra(EXTRA_FIRSTNAME, editTextFirstName.getText().toString().trim());
            resultIntent.putExtra(EXTRA_MIDDLENAME, editTextMiddleName.getText().toString().trim());
            resultIntent.putExtra(EXTRA_LASTNAME, editTextLastName.getText().toString().trim());
            resultIntent.putExtra(EXTRA_CLASSROOM, editTextClassroom.getText().toString().trim());
        }
        resultIntent.putExtra(StudentListActivity.EXTRA_STUDENT, studentIndex);
        setResult(resultCode, resultIntent);
        super.onBackPressed();
    }
}
