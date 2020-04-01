package com.vmschmidt.studentapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.vmschmidt.studentapplication.student.Student;
import com.vmschmidt.studentapplication.student.StudentAdapter;

import java.util.ArrayList;

public class FriendsList extends AppCompatActivity {

    private String classroomCode;
    private ListView studentListView;
    private StudentAdapter studentAdapter;
    private int resultCode;
    private ArrayList<Student> friendsList;

    public static final String EXTRA_STUDENT = "studentPosition";
    public static final int RESULT_DELETED = -2;
    public static final int VIEW_STUDENT_REQUEST = 20;
    public static final int ADD_STUDENT_REQUEST = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        studentListView = findViewById(R.id.listview_friends);

        //noinspection unchecked
        friendsList = (ArrayList<Student>) getIntent().getSerializableExtra(StudentDetailActivity.EXTRA_FRIENDS_LIST);

        studentAdapter = new StudentAdapter(this, friendsList);

        studentListView.setAdapter(studentAdapter);

        studentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent studentDetailIntent = new Intent(FriendsList.this, StudentDetailActivity.class);
                studentDetailIntent.putExtra(ClassroomsListActivity.EXTRA_CLASSROOM, classroomCode);
                studentDetailIntent.putExtra(EXTRA_STUDENT, position);
                startActivityForResult(studentDetailIntent, VIEW_STUDENT_REQUEST);
            }
        });
    }
}
