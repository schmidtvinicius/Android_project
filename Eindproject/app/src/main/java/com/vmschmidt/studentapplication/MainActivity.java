package com.vmschmidt.studentapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_REGEX = "regex";
    public static final String REGEX_SOFTWARE = "^EHI[1-4]V.S[a-z]$";
    public static final String REGEX_BUSINESS = "^EHI[1-4]V.B[a-z]$";
    public static final String REGEX_ITSM = "^EHI[1-4]V.I[a-z]$";

    public static final String COURSE_SOFTWARE_ENGINEERING = "Software Engineering";
    public static final String COURSE_BUSINESS = "Business";
    public static final String COURSE_ITSM = "ITSM";

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
                Intent classroomActivityIntent = new Intent(MainActivity.this, ClassroomsListActivity.class);
                String course = ((TextView) v).getText().toString();
                if(course.equals(COURSE_SOFTWARE_ENGINEERING)){
                    classroomActivityIntent.putExtra(EXTRA_REGEX, REGEX_SOFTWARE);
                }else if(course.equals(COURSE_BUSINESS)){
                    classroomActivityIntent.putExtra(EXTRA_REGEX, REGEX_BUSINESS);
                }else{
                    classroomActivityIntent.putExtra(EXTRA_REGEX, REGEX_ITSM);
                }
                startActivity(classroomActivityIntent);
            }
        };

        seTextView.setOnClickListener(listener);
        businessTextView.setOnClickListener(listener);
        itsmTextView.setOnClickListener(listener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        MenuItem analyzeCourses = menu.add(R.string.option_analyze_courses);
        analyzeCourses.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent analyzeCoursesIntent = new Intent(MainActivity.this, AnalyzeCoursesActivity.class);
                startActivity(analyzeCoursesIntent);
                return false;
            }
        });
        return true;
    }
}
