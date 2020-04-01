package com.vmschmidt.studentapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.vmschmidt.studentapplication.classroom.Classroom;
import com.vmschmidt.studentapplication.dataprovider.DataProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

public class ClassroomsListActivity extends AppCompatActivity implements CreateClassroomDialog.CreateClassroomListener {

    private String currentRegex;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> classroomCodes;

    public static final int VIEW_CLASSROOM_REQUEST = 10;
    public static final String EXTRA_CLASSROOM = "classroomCode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classroms_list_activity);

        currentRegex = getIntent().getStringExtra(MainActivity.EXTRA_REGEX);

        updateCurrentClassrooms();

        ListView classroomListView = findViewById(R.id.classroms_list);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, classroomCodes);
        classroomListView.setAdapter(adapter);

        classroomListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String classroomCode = classroomCodes.get(position);
                Log.d("TEST", "Clicked on " + classroomCode);

                Intent studentListIntent = new Intent(ClassroomsListActivity.this, StudentListActivity.class);
                studentListIntent.putExtra(ClassroomsListActivity.EXTRA_CLASSROOM, classroomCode);
                startActivityForResult(studentListIntent, VIEW_CLASSROOM_REQUEST);
            }
        });
    }

    private void updateCurrentClassrooms(){
        if(classroomCodes == null){
            classroomCodes = new ArrayList<>();
        }
        Set<String> keys = DataProvider.getKeys(this);
        Log.d("TOTALKEYS", String.valueOf(keys.size()));
        classroomCodes.clear();
        for(String classroomCode : keys){
            if(classroomCode.matches(currentRegex)){
                classroomCodes.add(classroomCode);
                Log.d("ADDED CODE", "Added " + classroomCode);
            }
        }
        Collections.sort(classroomCodes);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        MenuItem addOption = menu.add(R.string.option_add);
        addOption.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                CreateClassroomDialog dialog = CreateClassroomDialog.newInstance(classroomCodes,currentRegex);
                dialog.show(getSupportFragmentManager(), "BLA");
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == VIEW_CLASSROOM_REQUEST) {
            if (resultCode == StudentListActivity.RESULT_DELETED) {
                String classroomToDelete = data.getStringExtra(EXTRA_CLASSROOM);
                DataProvider.removeClassroom(classroomToDelete);
                updateCurrentClassrooms();
                adapter.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCreateClassroomComplete(String newClassroomCode) {
        Classroom newClassroom = new Classroom(newClassroomCode);
        DataProvider.classrooms.put(newClassroomCode, newClassroom);
        updateCurrentClassrooms();
        adapter.notifyDataSetChanged();
    }
}