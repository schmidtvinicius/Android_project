package com.vmschmidt.studentapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
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
    public static final int RESULT_DELETED = -2;

    Intent startIntent;

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

        final int orientation = this.getResources().getConfiguration().orientation;

        mailButton = findViewById(R.id.btn_mail);
        mailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TEST", "Clicked on send mail. Orientation: " + orientation);
                Uri uri = Uri.parse("mailto:" + student.getEmailAddress());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        startIntent = getIntent();
        classroomCode = startIntent.getStringExtra(ClassroomsListActivity.EXTRA_CLASSROOM);
        studentIndex = startIntent.getIntExtra(StudentListActivity.EXTRA_STUDENT, -1);
        student = DataProvider.classrooms.get(classroomCode).getStudentList().get(studentIndex);
        originalClassroomSize = DataProvider.classrooms.get(classroomCode).totalStudents();

        //noinspection ConstantConditions
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_check);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        MenuItem deleteOption = menu.add(Menu.NONE, Menu.NONE, 101, R.string.option_delete);
        deleteOption.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                DataProvider.classrooms.get(classroomCode).removeStudent(student);
                onBackPressed();
                return false;
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {

        if(DataProvider.classrooms.get(classroomCode).totalStudents() == originalClassroomSize){
            Intent returnIntent = new Intent();
            returnIntent.putExtra(EXTRA_FIRSTNAME, editTextFirstName.getText().toString().trim());
            returnIntent.putExtra(EXTRA_MIDDLENAME, editTextMiddleName.getText().toString().trim());
            returnIntent.putExtra(EXTRA_LASTNAME, editTextLastName.getText().toString().trim());
            returnIntent.putExtra(EXTRA_CLASSROOM, editTextClassroom.getText().toString().trim());
            returnIntent.putExtra(StudentListActivity.EXTRA_STUDENT, studentIndex);
            setResult(RESULT_OK, returnIntent);
        }else {
            Intent returnIntent = new Intent();
            setResult(RESULT_DELETED, returnIntent);
        }
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            if(editTextClassroom.getCurrentTextColor() == Color.RED){
                Toast.makeText(StudentDetailActivity.this, R.string.toast_classroom_invalid, Toast.LENGTH_LONG).show();
            }
            onBackPressed();
            return true;
        }else if(item.getItemId() == R.id.option_send_mail){
            mailButton.callOnClick();
        }
        return super.onOptionsItemSelected(item);
    }
}
