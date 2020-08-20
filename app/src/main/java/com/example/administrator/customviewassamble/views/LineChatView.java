package com.example.administrator.customviewassamble.views;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.administrator.customviewassamble.R;

import java.util.ArrayList;

public class LineChatView extends View {
    private Paint gridPaint;
    private Paint linePaint;
    private Paint pointPaint;
    private int gridColor = 0x999999;
    private int lineColor = 0xff2200;
    private int bacgroundColor = 0x00000000;
    private int gridWidth = 2,lineWidth = 4;
    private int width,height;
    private int verticalStep,horizonalStep;
    private int vertialColums = 8;
    private int horizonalColums = 12;
    private ArrayList<PointF> pointFs;

    private int offset = 1;



    public LineChatView(Context context) {
        this(context,null);
    }

    public LineChatView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LineChatView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        gridColor = ContextCompat.getColor(getContext(), R.color.gray);
        lineColor = ContextCompat.getColor(getContext(), R.color.red);
        bacgroundColor = Color.TRANSPARENT;

        gridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        gridPaint.setColor(gridColor);
        gridPaint.setStrokeWidth(gridWidth);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(lineColor);
        linePaint.setStrokeWidth(lineWidth);
        linePaint.setStyle(Paint.Style.STROKE);


        pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pointPaint.setColor(Color.BLUE);
        pointPaint.setStrokeWidth(10);
        pointPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int tmpWidth = widthMeasureSpec;
        int tmpHeight = heightMeasureSpec;

        if (widthMode == MeasureSpec.AT_MOST) tmpWidth = 200;

        if (heightMode == MeasureSpec.AT_MOST) tmpHeight = 100;

        setMeasuredDimension(tmpWidth, tmpHeight);
        width = getMeasuredWidth() - getPaddingLeft() - getPaddingRight()-10;
        height = getMeasuredHeight() - getPaddingTop() - getPaddingBottom()-10;
        horizonalStep = width / horizonalColums;
        verticalStep = height / vertialColums;

        calculatePiont();
    }

    private void calculatePiont() {
        if(pointFs == null){
            pointFs = new ArrayList<>();
        }

        for (int i = 0; i < 5; i++) {
            PointF pointF = new PointF();
            pointF.x = getPaddingLeft() + 10 + 10 + i*(width - 30) / 4;
            pointF.y = i%2 == 0?getPaddingTop()+20:height - 20;
            pointFs.add(pointF);
            Log.w("LinChatView",i+" -->"+pointF.x);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(bacgroundColor);
        drawGrid(canvas);
        Path linePath = createLine();
        canvas.drawPath(linePath, linePaint);

        for (int i = 0; i < 5; i++) {
            canvas.drawPoint(pointFs.get(i).x,pointFs.get(i).y,pointPaint);
        }
    }

    private void drawGrid(Canvas canvas) {
        int startX = getPaddingLeft()+5;
        int startY = getPaddingTop()+5;
        int endX = canvas.getWidth() - getPaddingRight()-5;
        int endY = canvas.getHeight() - getPaddingBottom()-5;
        //垂直切割
        for (int i = 0; i <= horizonalColums; i++) {
            canvas.drawLine(startX + horizonalStep*i,startY,startX + horizonalStep*i,endY,gridPaint);
        }

        for (int i = 0; i <= vertialColums; i++) {
            canvas.drawLine(startX,startY + verticalStep*i,endX,startY + verticalStep*i,gridPaint);
        }

    }

    private Path createLine() {
        float thisPointX;
        float thisPointY;
        float nextPointX;
        float nextPointY;
        float startDiffX;
        float startDiffY;
        float endDiffX;
        float endDiffY;
        float firstControlX;
        float firstControlY;
        float secondControlX;
        float secondControlY;

        Path res = new Path();
        res.moveTo(pointFs.get(0).x, pointFs.get(0).y);


        for (int i = 0; i < 4; i++) {

            thisPointX = pointFs.get(i).x;
            thisPointY = pointFs.get(i).y;

            nextPointX = pointFs.get(i+1).x;
            nextPointY = pointFs.get(i+1).y;

            startDiffX = (nextPointX - pointFs.get(si(5, i - 1)).x);
            startDiffY = (nextPointY - pointFs.get(si(5, i - 1)).y);

            endDiffX = (pointFs.get(si(5, i + 2)).x - thisPointX);
            endDiffY = (pointFs.get(si(5, i + 2)).y - thisPointY);

            firstControlX = thisPointX + (0.15f * startDiffX);
            firstControlY = thisPointY + (0.15f * startDiffY);

            secondControlX = nextPointX - (0.15f * endDiffX);
            secondControlY = nextPointY - (0.15f * endDiffY);

            res.cubicTo(firstControlX, firstControlY, secondControlX, secondControlY, nextPointX,
                    nextPointY);
        }

        return res;

    }

    private static int si(int setSize, int i) {
        if (i > setSize - 1) return setSize - 1;
        else if (i < 0) return 0;
        return i;
    }

    public void setProgress(int position,float progress){
        pointFs.get(position).y = (height - 20)*progress;
        postInvalidate();
    }

}
