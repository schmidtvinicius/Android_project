package com.vmschmidt.studentapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.vmschmidt.studentapplication.classroom.Classroom;
import com.vmschmidt.studentapplication.dataprovider.DataProvider;

public class CreateStudentActivity extends AppCompatActivity {

    public static final String EXTRA_FIRSTNAME = "firstname";
    public static final String EXTRA_MIDDLENAME = "middlename";
    public static final String EXTRA_LASTNAME = "lastname";
    public static final String EXTRA_STUDENT_NUMBER = "studentnumber";

    private EditText editTextFirstName;
    private EditText editTextMiddleName;
    private EditText editTextLastName;
    private EditText editTextEmail;
    private EditText editTextClassroom;
    private EditText editTextStudentNumber;

    Classroom classroom;
    private String classroomCode;
    private Intent startIntent;
    private Intent resultIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_student);

        //actionBar.setCustomView();

        startIntent = getIntent();

        classroomCode = startIntent.getStringExtra(ClassroomsListActivity.EXTRA_CLASSROOM);

        classroom = DataProvider.classrooms.get(classroomCode);

        editTextFirstName = findViewById(R.id.editText_new_firstname);
        editTextMiddleName = findViewById(R.id.editText_new_middlename);
        editTextLastName = findViewById(R.id.editText_new_lastname);
        editTextEmail = findViewById(R.id.editText_new_email);
        editTextClassroom = findViewById(R.id.editText_new_classcode);
        editTextStudentNumber = findViewById(R.id.editText_new_studentnumber);

        TextWatcher studentNumberWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0){
                    if(!s.toString().trim().matches("^[0-9]{6}$") || DataProvider.studentNumbers.containsKey(Integer.parseInt(s.toString().trim()))){
                        editTextStudentNumber.setTextColor(Color.RED);
                    }else{
                        editTextStudentNumber.setTextColor(Color.BLACK);
                        editTextEmail.setText(s.toString().concat("@student.saxion.nl"));
                    }
                }
            }
        };
        editTextStudentNumber.addTextChangedListener(studentNumberWatcher);

        editTextClassroom.setText(classroomCode);
        editTextClassroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CreateStudentActivity.this, R.string.toast_cannot_alter_class, Toast.LENGTH_SHORT).show();
            }
        });
        editTextEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CreateStudentActivity.this, R.string.toast_cannot_edit_email, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        MenuItem addOption = menu.add(R.string.option_confirm);
        addOption.setIcon(R.drawable.ic_check);
        addOption.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        addOption.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(editTextStudentNumber.getCurrentTextColor() == Color.RED || editTextStudentNumber.getText().length() == 0){
                    Toast.makeText(CreateStudentActivity.this, R.string.toast_invalid_studentnumber, Toast.LENGTH_SHORT).show();
                }else if(editTextFirstName.getText().length() == 0){
                    Toast.makeText(CreateStudentActivity.this, R.string.toast_missing_name, Toast.LENGTH_SHORT).show();
                } else{
                    resultIntent = new Intent();
                    resultIntent.putExtra(CreateStudentActivity.EXTRA_FIRSTNAME, editTextFirstName.getText().toString());
                    if(editTextMiddleName.getText().length() > 0){
                        resultIntent.putExtra(CreateStudentActivity.EXTRA_MIDDLENAME, editTextMiddleName.getText().toString());
                    }else{
                        resultIntent.putExtra(CreateStudentActivity.EXTRA_MIDDLENAME, "");
                    }
                    if(editTextLastName.getText().length() > 0){
                        resultIntent.putExtra(CreateStudentActivity.EXTRA_LASTNAME, editTextLastName.getText().toString());
                    }else{
                        resultIntent.putExtra(CreateStudentActivity.EXTRA_LASTNAME, "");
                    }
                    resultIntent.putExtra(CreateStudentActivity.EXTRA_STUDENT_NUMBER, Integer.parseInt(editTextStudentNumber.getText().toString()));
                    setResult(RESULT_OK, resultIntent);
                    onBackPressed();
                }
                return false;
            }
        });
        MenuItem cancelOption = menu.add(R.string.option_cancel);
        cancelOption.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        cancelOption.setIcon(R.drawable.ic_clear);
        cancelOption.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                resultIntent = new Intent();
                setResult(RESULT_CANCELED, resultIntent);
                onBackPressed();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


}