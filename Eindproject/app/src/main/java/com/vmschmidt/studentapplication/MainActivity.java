package com.vmschmidt.studentapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_COURSE = "course";

//    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        TextView seTextView = findViewById(R.id.textView_se);
        TextView businessTextView = findViewById(R.id.textView_business);
        TextView itsmTextView = findViewById(R.id.textView_itsm);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String course = ((TextView) v).getText().toString();
                Intent classroomActivityIntent = new Intent(MainActivity.this, ClassroomsListActivity.class);
                classroomActivityIntent.putExtra(MainActivity.EXTRA_COURSE, course);
                startActivity(classroomActivityIntent);
            }
        };

        seTextView.setOnClickListener(listener);
        businessTextView.setOnClickListener(listener);
        itsmTextView.setOnClickListener(listener);
    }

}
