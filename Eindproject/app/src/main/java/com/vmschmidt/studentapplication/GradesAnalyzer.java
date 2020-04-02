package com.vmschmidt.studentapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class GradesAnalyzer extends View {

    private Paint greenPaint;
    private Paint redPaint;
    private double currentGrade;

    public GradesAnalyzer(Context context) {
        super(context);
        init();
    }

    public GradesAnalyzer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GradesAnalyzer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public GradesAnalyzer(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void setCurrentGrade(double newGrade){
        this.currentGrade = newGrade;
    }

    private void init(){
        greenPaint = new Paint();
        redPaint = new Paint();

        greenPaint.setColor(Color.rgb(90, 255, 120));
        redPaint.setColor(Color.rgb(255, 90, 70));

        greenPaint.setStyle(Paint.Style.FILL);
        redPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int height = getHeight();
        int width = getWidth();

        int barLength = Math.max(height, width);
        int barWidth = height / 10;



    }
}
