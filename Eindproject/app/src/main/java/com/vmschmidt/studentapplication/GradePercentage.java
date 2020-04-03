package com.vmschmidt.studentapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class GradePercentage extends View {

    private Paint currentBarPaint;
    private Paint blackPaint;
    private double currentGrade;

    public GradePercentage(Context context) {
        super(context);
        init();
    }

    public GradePercentage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GradePercentage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public GradePercentage(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        currentBarPaint = new Paint();
        blackPaint = new Paint();
        currentGrade = 0;
        setCurrentBarColor();
        currentBarPaint.setStyle(Paint.Style.FILL);
        blackPaint.setColor(Color.BLACK);
        blackPaint.setStyle(Paint.Style.STROKE);
        blackPaint.setStrokeWidth(5);
    }

    public void setCurrentGrade(double newGrade){
        this.currentGrade = newGrade;
        setCurrentBarColor();
    }

    private void setCurrentBarColor(){
        if(currentGrade < 5){
            int greenGradient = (int) (currentGrade * 51);
            currentBarPaint.setColor(Color.rgb(255, greenGradient, 0));
        }else{
            int redGradient = (int) ((-51 * currentGrade) + 510);
            currentBarPaint.setColor(Color.rgb(redGradient, 255,0));
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int height = getHeight();
        int width = getWidth();
        int borderOffset = (width * 5) / 100;

        //setCurrentGrade(6);

        int barLength = (width - borderOffset);
        barLength = (int) ((barLength * currentGrade) / 10);
        int barWidth = height / 6;

        if(currentGrade > 0){
            canvas.drawRect(borderOffset, (height / 2) - (barWidth / 2), barLength,  (height / 2) + (barWidth / 2) , currentBarPaint);
        }
        canvas.drawRect(borderOffset,  (height / 2) - (barWidth / 2), (width - borderOffset), (height / 2) + (barWidth / 2), blackPaint);
    }
}
