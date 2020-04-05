package com.vmschmidt.studentapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.vmschmidt.studentapplication.dataprovider.DataProvider;
import com.vmschmidt.studentapplication.student.Student;

import java.util.HashMap;
import java.util.Set;

public class CoursesAnalyzer extends ConstraintLayout {

    private Set<Integer> allStudents;

    public CoursesAnalyzer(Context context) {
        super(context);
        init();
    }

    public CoursesAnalyzer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CoursesAnalyzer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.courses_analyzer, this);

        final CoursesGraphic coursesGraphic = findViewById(R.id.coursesGraphic);
        final RadioButton sectorOption = findViewById(R.id.radioButton_sector_graphic);
        final RadioButton barGraphic = findViewById(R.id.radioButton_bar_graphic);
        TextView textViewSE = findViewById(R.id.textView_software_percentage);
        TextView textViewBusiness = findViewById(R.id.textView_business_percentage);
        TextView textViewITSM = findViewById(R.id.textView_itsm_percentage);

        allStudents = DataProvider.getStudentKeys(this.getContext());

        coursesGraphic.setTotalStudents(allStudents);

        textViewSE.setTextColor(coursesGraphic.getPaintSE().getColor());
        textViewBusiness.setTextColor(coursesGraphic.getPaintBusiness().getColor());
        textViewITSM.setTextColor(coursesGraphic.getPaintITSM().getColor());

        textViewSE.setText(MainActivity.COURSE_SOFTWARE_ENGINEERING + ": " + (coursesGraphic.getPercentageSE() * 100) + "%.");
        textViewBusiness.setText(MainActivity.COURSE_BUSINESS + ": " + (coursesGraphic.getPercentageBusiness() * 100) + "%.");
        textViewITSM.setText(MainActivity.COURSE_ITSM + ": " + (coursesGraphic.getPercentageITSM() * 100) + "%.");

        sectorOption.setChecked(true);

        sectorOption.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    coursesGraphic.setGraphicType(CoursesGraphic.GraphicType.SECTOR);
                    barGraphic.setChecked(false);
                }
            }
        });
        barGraphic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    coursesGraphic.setGraphicType(CoursesGraphic.GraphicType.HORIZONTAL_BAR);
                    sectorOption.setChecked(false);
                }
            }
        });
    }
}
