package com.vmschmidt.studentapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.vmschmidt.studentapplication.dataprovider.DataProvider;
import com.vmschmidt.studentapplication.student.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class SubjectsListActivity extends AppCompatActivity implements EditSubjectDialog.EditSubjectDialogListener {

    private Student currentStudent;
    private int studentNumber;
    private Set<String> subjects;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> subjectNames;

    public static final String KEY_SUBJECT_NAME = "subjectname";
    public static final String KEY_GRADE = "grade";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects_list);

        ListView listViewSubjects = findViewById(R.id.listview_subjects);

        studentNumber = getIntent().getIntExtra(StudentListActivity.EXTRA_STUDENT, -1);

        currentStudent = DataProvider.findStudent(studentNumber);
        updateSubjects();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, subjectNames);
        listViewSubjects.setAdapter(adapter);

        listViewSubjects.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle args = new Bundle();
                String subjectName = subjectNames.get(position);
                double grade = currentStudent.getGrade(subjectName);
                args.putString(KEY_SUBJECT_NAME, subjectName);
                args.putDouble(KEY_GRADE, grade);
                EditSubjectDialog editSubjectDialog = new EditSubjectDialog();
                editSubjectDialog.setArguments(args);
                editSubjectDialog.show(getSupportFragmentManager(), "EDITSUBJECT");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        MenuItem analyzeGrades = menu.add(R.string.option_analyze_grades);
        analyzeGrades.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent analyzeResultsIntent = new Intent(SubjectsListActivity.this, AnalyzeReusltsActivity.class);
                analyzeResultsIntent.putExtra(StudentListActivity.EXTRA_STUDENT, studentNumber);
                startActivity(analyzeResultsIntent);
                return false;
            }
        });
        return true;
    }

    public void updateSubjects(){
        if(subjectNames == null){
            subjectNames = new ArrayList<>();
        }
        subjects = currentStudent.getSubjects();
        subjectNames.clear();
        subjectNames.addAll(subjects);
    }

    @Override
    public void onEditSubjectDialogComplete(boolean isDeleted, String subjectName, double grade) {
        if(isDeleted){
            currentStudent.removeSubject(subjectName);
        }else{
            currentStudent.setGrade(subjectName, grade);
        }
        updateSubjects();
        adapter.notifyDataSetChanged();
    }
}
