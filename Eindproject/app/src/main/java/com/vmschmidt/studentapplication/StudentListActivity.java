package com.vmschmidt.studentapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.vmschmidt.studentapplication.classroom.Classroom;
import com.vmschmidt.studentapplication.dataprovider.DataProvider;
import com.vmschmidt.studentapplication.student.Student;
import com.vmschmidt.studentapplication.student.StudentAdapter;

import java.util.Iterator;

public class StudentListActivity extends AppCompatActivity implements AlertDialogFragment.AlertDialogListener {

    private String classroomCode;
    private ListView studentListView;
    private StudentAdapter studentAdapter;
    private int resultCode;
    private Student selectedStudent;

    public static final String EXTRA_STUDENT = "studentPosition";
    public static final int RESULT_DELETED = -2;
    public static final int VIEW_STUDENT_REQUEST = 20;
    public static final int ADD_STUDENT_REQUEST = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        studentListView = findViewById(R.id.students_list);

        classroomCode = getIntent().getStringExtra(ClassroomsListActivity.EXTRA_CLASSROOM);

        studentAdapter = new StudentAdapter(this, DataProvider.getClassroom(classroomCode).getStudentList());

        studentListView.setAdapter(studentAdapter);

        studentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedStudent = DataProvider.getClassroom(classroomCode).getStudentList().get(position);
                Intent studentDetailIntent = new Intent(StudentListActivity.this, StudentDetailActivity.class);
                studentDetailIntent.putExtra(EXTRA_STUDENT, selectedStudent.getStudentNumber());
                startActivityForResult(studentDetailIntent, VIEW_STUDENT_REQUEST);
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
                AlertDialogFragment dialogFragment = new AlertDialogFragment();
                dialogFragment.show(getSupportFragmentManager(), "ALERT");
                return false;
            }
        });
        MenuItem addOption = menu.add(Menu.NONE, Menu.NONE, 100, R.string.option_add);
        addOption.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent addStudentIntent = new Intent(StudentListActivity.this, CreateStudentActivity.class);
                addStudentIntent.putExtra(ClassroomsListActivity.EXTRA_CLASSROOM, classroomCode);
                startActivityForResult(addStudentIntent, ADD_STUDENT_REQUEST);
                return false;
            }
        });
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == StudentListActivity.VIEW_STUDENT_REQUEST){
            int studentNumber = data.getIntExtra(EXTRA_STUDENT, -1);
            Student student = DataProvider.findStudent(studentNumber);
            if(resultCode == RESULT_OK){
                String newFirstName = data.getStringExtra(StudentDetailActivity.EXTRA_FIRSTNAME);
                String newMiddleName = data.getStringExtra(StudentDetailActivity.EXTRA_MIDDLENAME);
                String newLastName = data.getStringExtra(StudentDetailActivity.EXTRA_LASTNAME);
                String newClassroomCode = data.getStringExtra(StudentDetailActivity.EXTRA_CLASSROOM);
                if(!newFirstName.equals(student.getFirstName())){
                    if(newFirstName.length() > 0){
                        newFirstName = newFirstName.trim();
                    }
                    student.setFirstName(newFirstName);
                }
                if(!newMiddleName.equals(student.getMiddleName())){
                    if(newMiddleName.length() > 0){
                        newMiddleName = newMiddleName.trim();
                    }
                    student.setMiddleName(newMiddleName);
                }
                if(!newLastName.equals(student.getLastName())){
                    if(newLastName.length() > 0){
                        newLastName = newLastName.trim();
                    }
                    student.setLastName(newLastName);
                }
                if(newClassroomCode.matches("^EHI1V.([BI][ab]|S[a-d])$") && !newClassroomCode.equals(student.getClassroom())){
                    Classroom newClassroom = DataProvider.checkExistingClassroom(newClassroomCode);
                    if(newClassroom != null){
                        DataProvider.getClassroom(classroomCode).getStudentList().remove(student);
                        newClassroom.addStudent(student);
                    }
                    student.setClassroom(newClassroomCode);
                }
            }else if(resultCode == RESULT_DELETED){
                DataProvider.removeStudent(student);
            }

        }else if(requestCode == StudentListActivity.ADD_STUDENT_REQUEST && resultCode == RESULT_OK){
            String firstName = data.getStringExtra(CreateStudentActivity.EXTRA_FIRSTNAME);
            String middleName = data.getStringExtra(CreateStudentActivity.EXTRA_MIDDLENAME);
            String lastName = data.getStringExtra(CreateStudentActivity.EXTRA_LASTNAME);
            int studentNumber = data.getIntExtra(CreateStudentActivity.EXTRA_STUDENT_NUMBER, -1);
            String email = String.valueOf(studentNumber).concat("@student.saxion.nl");
            Student newStudent = new Student(firstName, middleName, lastName, email, studentNumber, classroomCode);
            DataProvider.addStudent(newStudent);
        }
        studentAdapter.notifyDataSetChanged();
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        setResult(resultCode, resultIntent);
        resultIntent.putExtra(ClassroomsListActivity.EXTRA_CLASSROOM, classroomCode);
        super.onBackPressed();
    }

    @Override
    public void onAlertDialogComplete(boolean isDeleted) {
        if(isDeleted){
            resultCode = RESULT_DELETED;
        }else{
            resultCode = RESULT_CANCELED;
        }
        onBackPressed();
    }
}