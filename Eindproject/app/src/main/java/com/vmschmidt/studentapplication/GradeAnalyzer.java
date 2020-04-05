package com.vmschmidt.studentapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.vmschmidt.studentapplication.student.Student;

import java.util.HashMap;

public class GradeAnalyzer extends ConstraintLayout {

    private Student currentStudent;

    private GradePercentage gradePercentage;
    private TextView visualizedSubject;
    private EditText subjectToLook;
    private Button btnConfirm;

    public GradeAnalyzer(Context context) {
        super(context);
        init();
    }

    public GradeAnalyzer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GradeAnalyzer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setCurrentStudent(Student currentStudent){
        this.currentStudent = currentStudent;
    }

    public void init(){
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.grade_analyzer, this);

        gradePercentage = findViewById(R.id.gradePercentage);
        gradePercentage.setCurrentGrade(0.0);
        visualizedSubject = findViewById(R.id.textView_current_subject);
        subjectToLook = findViewById(R.id.editText_subject_to_look);
        btnConfirm = findViewById(R.id.btn_confirm_look_subject);
        btnConfirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(subjectToLook.getText().length() > 0){
                    String subject = subjectToLook.getText().toString().trim();
                    if(currentStudent.hasSubject(subject)){
                        double grade = currentStudent.getGrade(subject);
                        visualizedSubject.setText(subject + ": " + (grade));
                        gradePercentage.setCurrentGrade(grade);
                        //visualizedSubject.setTextColor(gradePercentage.getCurrentBarPaint().getColor());
                    }else{
                        Toast.makeText(getContext(), R.string.toast_invalid_subject_name, Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(), R.string.toast_missing_subject_name, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
