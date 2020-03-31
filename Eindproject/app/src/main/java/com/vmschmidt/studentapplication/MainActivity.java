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

    public static final String COURSE_SOFTWARE_ENGINEERING = "Software Engineering";
    public static final String COURSE_BUSINESS = "Business";
    public static final String COURSE_ITSM = "ITSM";
    public static final String EXTRA_COURSE = "course";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView seTextView = findViewById(R.id.textView_se);
        TextView businessTextView = findViewById(R.id.textView_business);
        TextView itsmTextView = findViewById(R.id.textView_itsm);

        seTextView.setText(COURSE_SOFTWARE_ENGINEERING);
        businessTextView.setText(COURSE_BUSINESS);
        itsmTextView.setText(COURSE_ITSM);

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
