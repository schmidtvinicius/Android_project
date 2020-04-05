package com.vmschmidt.studentapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.vmschmidt.studentapplication.dataprovider.DataProvider;
import com.vmschmidt.studentapplication.student.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class CoursesGraphic extends View {

    private Paint paintSE;
    private Paint paintBusiness;
    private Paint paintITSM;
    private Paint blackPaint;
    private RectF rectF;
    private int totalStudents;
    private ArrayList<Student> studentsSE;
    private ArrayList<Student> studentsBusiness;
    private ArrayList<Student> studentsITSM;
    private GraphicType graphicType;
    private float percentageITSM;
    private float percentageSE;
    private float percentageBusiness;

    enum GraphicType{
        SECTOR,
        HORIZONTAL_BAR
    }

    public CoursesGraphic(Context context) {
        super(context);
        init();
    }

    public CoursesGraphic(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CoursesGraphic(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CoursesGraphic(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        paintSE = new Paint();
        paintBusiness = new Paint();
        paintITSM = new Paint();
        blackPaint = new Paint();
        rectF = new RectF();

        graphicType = GraphicType.SECTOR;

        paintSE.setColor(Color.rgb(180, 0, 0));
        paintBusiness.setColor(Color.rgb(0, 0, 180));
        paintITSM.setColor(Color.rgb(255, 210, 0));
        blackPaint.setColor(Color.BLACK);

        paintSE.setStyle(Paint.Style.FILL);
        paintBusiness.setStyle(Paint.Style.FILL);
        paintITSM.setStyle(Paint.Style.FILL);
        blackPaint.setStyle(Paint.Style.STROKE);
        blackPaint.setStrokeWidth(5);
    }

    public void setTotalStudents(Set<Integer> allStudents){
        studentsSE = new ArrayList<>();
        studentsBusiness = new ArrayList<>();
        studentsITSM = new ArrayList<>();
        for(Integer studentNumber : allStudents){
            Student student = DataProvider.findStudent(studentNumber);
            if(student.getClassroom().matches(MainActivity.REGEX_SOFTWARE)){
                studentsSE.add(student);
            }else if(student.getClassroom().matches(MainActivity.REGEX_BUSINESS)){
                studentsBusiness.add(student);
            }else{
                studentsITSM.add(student);
            }
        }
        totalStudents = studentsSE.size() + studentsBusiness.size() + studentsITSM.size();
        percentageSE = ((float) studentsSE.size() / totalStudents);
        percentageBusiness = ((float) studentsBusiness.size() / totalStudents);
        percentageITSM = ((float) studentsITSM.size() /totalStudents);
    }

    public void setGraphicType(GraphicType graphicType){
        this.graphicType = graphicType;
        invalidate();
    }


    public float getPercentageITSM() {
        return percentageITSM;
    }

    public float getPercentageSE() {
        return percentageSE;
    }

    public float getPercentageBusiness() {
        return percentageBusiness;
    }

    public Paint getPaintSE() {
        return paintSE;
    }

    public Paint getPaintBusiness() {
        return paintBusiness;
    }

    public Paint getPaintITSM() {
        return paintITSM;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int height = getHeight();
        int width = getWidth();
        int rectSide = Math.min(width, height);

        switch (graphicType){
            case SECTOR:
                rectF.set(((width / 2) - (rectSide / 2)) + 100, ((height / 2) - (rectSide / 2)) + 100, ((width / 2) + (rectSide / 2)) - 100, ((height / 2) + (rectSide / 2)) - 100);
                canvas.drawArc(rectF, 0, percentageSE * 360, true, paintSE);
                canvas.drawArc(rectF, percentageSE * 360, percentageBusiness * 360, true, paintBusiness);
                canvas.drawArc(rectF, (percentageBusiness * 360) + (percentageSE * 360), percentageITSM * 360, true, paintITSM);
                break;
            case HORIZONTAL_BAR:
                int borderOffset = (width * 5) / 100;
                int barWidth = Math.min(width, height) / 6;
                int barLength = (width - (borderOffset * 2));
                canvas.drawRect(borderOffset, (height / 2) - (barWidth / 2), (borderOffset +
                        (percentageSE * barLength)), (height / 2) + (barWidth / 2), paintSE);
                canvas.drawRect((borderOffset + (percentageSE * barLength)), (height / 2) -
                        (barWidth / 2), ((borderOffset + (percentageSE * barLength)) +
                        (percentageBusiness * barLength)), (height / 2) + (barWidth / 2), paintBusiness);
                canvas.drawRect(borderOffset + (percentageSE * barLength) + (percentageBusiness * barLength),
                        (height / 2) - (barWidth / 2), borderOffset + (percentageSE * barLength)
                                + (percentageBusiness * barLength) + (percentageITSM * barLength), (height / 2)
                                + (barWidth / 2), paintITSM);
                canvas.drawRect(borderOffset,  (height / 2) - (barWidth / 2), (width - borderOffset), (height / 2) + (barWidth / 2), blackPaint);
                break;
            default:break;
        }



    }
}
