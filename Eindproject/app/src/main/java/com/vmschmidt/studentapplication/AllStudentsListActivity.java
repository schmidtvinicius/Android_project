package com.vmschmidt.studentapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.vmschmidt.studentapplication.classroom.Classroom;
import com.vmschmidt.studentapplication.dataprovider.DataProvider;
import com.vmschmidt.studentapplication.student.Student;
import com.vmschmidt.studentapplication.student.StudentAdapter;

import java.util.ArrayList;
import java.util.Set;

public class AllStudentsListActivity extends AppCompatActivity implements AddFriendDialog.AddFriendDialogListener {

    public static final int RESULT_FRIEND_ADDED = 5;

    private ListView studentListView;
    private StudentAdapter studentAdapter;
    private boolean friendAdded;
    private Set<String> allClassrooms;
    private ArrayList<Student> allStudents;
    private Student studentToAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_students_list);

        allClassrooms = DataProvider.getKeys(this);

        studentListView = findViewById(R.id.listview_all_students);

        allStudents = new ArrayList<>();

        for(String classroomCode : allClassrooms){
            Classroom classroom = DataProvider.getClassroom(classroomCode);
            allStudents.addAll(classroom.getStudentList());
        }

        studentAdapter = new StudentAdapter(this, allStudents);

        studentListView.setAdapter(studentAdapter);

        studentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                studentToAdd = allStudents.get(position);
                AddFriendDialog addFriendDialog = new AddFriendDialog();
                addFriendDialog.show(getSupportFragmentManager(), "ADDFRIEND");
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        if (friendAdded) {
            resultIntent.putExtra(StudentListActivity.EXTRA_STUDENT, studentToAdd.getStudentNumber());
            setResult(RESULT_FRIEND_ADDED, resultIntent);
        }else{
            setResult(RESULT_CANCELED, resultIntent);
        }
        super.onBackPressed();
    }

    @Override
    public void onAddFriendDialogComplete(boolean friendAdded) {
        this.friendAdded = friendAdded;
        onBackPressed();
    }
}
