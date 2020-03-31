package com.vmschmidt.studentapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toolbar;

import com.vmschmidt.studentapplication.dataprovider.DataProvider;

import java.util.ArrayList;
import java.util.Set;

public class ClassroomsListActivity extends AppCompatActivity {

    public static final String EXTRA_CLASSROOM = "classroomCode";
    private Set<String> keys = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classroms_list_activity);

        keys = DataProvider.getKeys(this);

        String course = getIntent().getStringExtra(MainActivity.EXTRA_COURSE);
        final ArrayList<String> classroomCodes = new ArrayList<String>();

        if(course.equalsIgnoreCase("Software Engineering")){
            for(String classroomCode : keys){
                if(classroomCode.matches("^EHI1V.S[a-d]$")){
                    classroomCodes.add(classroomCode);
                    Log.d("ADDED CODE", "Added " + classroomCode);
                }
            }
        }else if(course.equalsIgnoreCase("Business")){
            for(String classroomCode : keys){
                if(classroomCode.matches("^EHI1V.B[ab]$")){
                    classroomCodes.add(classroomCode);
                    Log.d("ADDED CODE", "Added " + classroomCode);
                }
            }
        }else{
            for(String classroomCode : keys){
                if(classroomCode.matches("^EHI1V.I[ab]$")){
                    classroomCodes.add(classroomCode);
                    Log.d("ADDED CODE", "Added " + classroomCode);
                }
            }
        }

        ListView classroomListView = findViewById(R.id.classroms_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, classroomCodes);
        classroomListView.setAdapter(adapter);

        classroomListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String classroomCode = classroomCodes.get(position);
                Log.d("TEST", "Clicked on " + classroomCode);

                Intent studentListIntent = new Intent(ClassroomsListActivity.this, StudentListActivity.class);
                studentListIntent.putExtra(ClassroomsListActivity.EXTRA_CLASSROOM, classroomCode);
                startActivity(studentListIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }
}
