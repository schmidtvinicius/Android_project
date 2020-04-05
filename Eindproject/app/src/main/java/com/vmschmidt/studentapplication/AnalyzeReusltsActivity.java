package com.vmschmidt.studentapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.vmschmidt.studentapplication.dataprovider.DataProvider;
import com.vmschmidt.studentapplication.student.Student;

public class AnalyzeReusltsActivity extends AppCompatActivity {

    private GradeAnalyzer gradeAnalyzer;
    private Student currentStudent;
    private int studentNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze_reuslts);

        studentNumber = getIntent().getIntExtra(StudentListActivity.EXTRA_STUDENT,  -1);

        currentStudent = DataProvider.findStudent(studentNumber);

        gradeAnalyzer = findViewById(R.id.gradeAnalyzer);

        gradeAnalyzer.setCurrentStudent(currentStudent);

    }
}
