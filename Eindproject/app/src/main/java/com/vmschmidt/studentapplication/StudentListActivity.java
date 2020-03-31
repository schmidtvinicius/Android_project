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

public class StudentListActivity extends AppCompatActivity {

    String classroomCode;
    ListView studentListView;
    StudentAdapter studentAdapter;
    public static final String EXTRA_STUDENT = "studentPosition";
    public static final int VIEW_STUDENT_REQUEST = 20;
    public static final int ADD_STUDENT_REQUEST = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        studentListView = findViewById(R.id.students_list);

        classroomCode = getIntent().getStringExtra(ClassroomsListActivity.EXTRA_CLASSROOM);

        studentAdapter = new StudentAdapter(this, DataProvider.classrooms.get(classroomCode).getStudentList());

        studentListView.setAdapter(studentAdapter);

        studentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent studentDetailIntent = new Intent(StudentListActivity.this, StudentDetailActivity.class);
                studentDetailIntent.putExtra(ClassroomsListActivity.EXTRA_CLASSROOM, classroomCode);
                studentDetailIntent.putExtra(EXTRA_STUDENT, position);
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
            int studentIndex = data.getIntExtra(EXTRA_STUDENT, -1);
            Student student = DataProvider.classrooms.get(classroomCode).getStudentList().get(studentIndex);
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
                        DataProvider.classrooms.get(classroomCode).getStudentList().remove(student);
                        newClassroom.addStudent(student);
                    }
                    student.setClassroom(newClassroomCode);
                }
            }else if(resultCode == StudentDetailActivity.RESULT_DELETED){
                DataProvider.removeStudent(student);
            }

        }else if(requestCode == StudentListActivity.ADD_STUDENT_REQUEST && resultCode == RESULT_OK){
            String firstName = data.getStringExtra(CreateStudentActivity.EXTRA_FIRSTNAME);
            String middleName = data.getStringExtra(CreateStudentActivity.EXTRA_MIDDLENAME);
            String lastName = data.getStringExtra(CreateStudentActivity.EXTRA_LASTNAME);
            int studentNumber = data.getIntExtra(CreateStudentActivity.EXTRA_STUDENT_NUMBER, -1);
            String email = String.valueOf(studentNumber).concat("@student.saxion.nl");
            Classroom classroom = DataProvider.classrooms.get(classroomCode);
            classroom.addStudent(new Student(firstName, middleName, lastName, email, studentNumber, classroomCode));
        }
        studentAdapter.notifyDataSetChanged();
        super.onActivityResult(requestCode, resultCode, data);
    }
}
