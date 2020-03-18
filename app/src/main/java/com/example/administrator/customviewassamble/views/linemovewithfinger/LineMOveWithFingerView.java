package com.example.administrator.customviewassamble.views.linemovewithfinger;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LineMOveWithFingerView extends View {
    private Paint mPointPaint;              //点画笔
    private Paint mLinePaint;               //线画笔

    private int mPointCount = 50;           //点总数
    private int mMaxLineWidth = 120;        //连线最大长度
    private List<LinePoint> mPoints;
    private Random mRandom = new Random();
    private int mWidth,mHeight;

    private boolean mFingerTouched = false;
    private VelocityTracker mVelocityTracker;
    private int mTouchSlop;
    private FingerPoint mFingerPoint;
    private int mLastTouchX;
    private int mLastTouchY;


    public LineMOveWithFingerView(Context context) {
        super(context);
    }

    public LineMOveWithFingerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LineMOveWithFingerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        mPointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPointPaint.setStrokeWidth(5);
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(Color.parseColor("#666666"));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),MeasureSpec.getSize(heightMeasureSpec));
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        initPoints();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }

    /**
     * 初始化点集合
     */
    private void initPoints() {
        mPoints = new ArrayList<>(mPointCount);
        for (int i = 0; i < mPointCount; i++) {
            LinePoint point = new LinePoint(mRandom.nextInt(mWidth),mRandom.nextInt(mHeight));
            point.color = Color.rgb(mRandom.nextInt(255),mRandom.nextInt(255),mRandom.nextInt(255));
            point.xAccelerated = 0;
            point.yAccelerated = 0;
            point.xSpeed = Math.max(5,mRandom.nextInt(10)) * (mRandom.nextInt(2)%2 == 1?-1:1);
            point.ySpeed = Math.max(5,mRandom.nextInt(10)) * (mRandom.nextInt(2)%2 == 1?-1:1);
            mPoints.add(point);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPoints(canvas);
        drawLines(canvas);
        if(mFingerTouched){
            drawFingerPoint(canvas);
        }
        updatePoint();
        postInvalidateDelayed(50);
    }




    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mVelocityTracker.addMovement(event);
                mFingerTouched = true;
                mFingerPoint = new FingerPoint((int)event.getX(),(int)event.getY());
                mLastTouchX = (int) event.getX();
                mLastTouchY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
//                mVelocityTracker.addMovement(event);
//                if()
//                if(mFingerPoint != null){
//                    mFingerPoint.xVelocity =
//                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mFingerTouched = false;
                mFingerPoint = null;
                mVelocityTracker.clear();
                break;
        }
        return true;

    }

    private void updatePoint() {
        //第一次遍历
        if(mFingerTouched){
            //1.查找出
            for (int i = 0; i < mPoints.size(); i++) {
                LinePoint point = mPoints.get(i);
                if(!point.isCatched){
                    if(point.getDistance(mFingerPoint) < 240){
                        point.isCatching = true;
                        point.x += point.xSpeed;
                        point.y += point.ySpeed;
                        fixXY(point);
                        int distance = (int) point.getDistance(mFingerPoint);
                        if(distance >= 240){
                            if(point.y ==  mFingerPoint.y){
                                point.x -= distance - 240;
                            }else if(point.x ==  mFingerPoint.x){
                                point.y -= distance - 240;
                            } else{
                                point.x = (int) ((240f/distance)*(point.x - mFingerPoint.x) + mFingerPoint.x);
                                point.y = (int) ((240f/distance)*(point.y - mFingerPoint.y) + mFingerPoint.y);
                            }
                            point.isCatched = true;
                        }
                    }
                }

                if(point.isCatched){

                }
            }

            for (int i = 0; i < mPoints.size(); i++) {
                LinePoint point = mPoints.get(i);
                if(!point.isCatched && !point.isCatching){
                    if(!point.isInderectCatched){
                        for (int j = 0; j < mPoints.size(); j++) {
                            if(mPoints.get(j).isCatched){
                                //间接捕获
                                if(point.getDistance(mPoints.get(j)) < mMaxLineWidth){
                                    LinePoint point1 = mPoints.get(j);
                                    //改变移动方向
                                    float vector = (float) Math.sqrt(point.xSpeed * point.xSpeed + point.ySpeed * point.ySpeed );
                                    if(point.x == mFingerPoint.x){
                                        point.xSpeed = 0;
                                        if(point.y > mFingerPoint.y){
                                            point.ySpeed = (int)-vector;
                                        }else{
                                            point.ySpeed = (int)vector;
                                        }
                                    }else if(point.y == mFingerPoint.y){
                                        point.ySpeed = 0;
                                        if(point.x > mFingerPoint.x){
                                            point.xSpeed = (int) - vector;
                                        }else{
                                            point.xSpeed = (int)vector;
                                        }
                                    }else{
                                        point.xSpeed = Math.abs((int) ((point.x - mFingerPoint.x)*vector/point.getDistance(mFingerPoint)));
                                        if(point.x > mFingerPoint.x){
                                            point.xSpeed = -point.xSpeed;
                                        }

                                        point.ySpeed = Math.abs((int) ((point.y - mFingerPoint.y)*vector/point.getDistance(mFingerPoint)));
                                        if(point.y > mFingerPoint.y){
                                            point.ySpeed = -point.ySpeed;
                                        }
                                    }
                                    point.isInderectCatched = true;
                                    break;
                                }
                            }
                        }
                    }
                    point.x += point.xSpeed;
                    point.y += point.ySpeed;
                    fixXY(point);
                }

            }
        }else{
            for (int i = 0; i < mPoints.size(); i++) {
                LinePoint point = mPoints.get(i);
                point.x += point.xSpeed;
                point.y += point.ySpeed;
                fixXY(point);
            }
        }
    }

    private void fixXY(LinePoint point) {
        //撞壁反弹
        if(point.x > mWidth){
            point.x -= point.x - mWidth;
            point.xSpeed = -point.xSpeed;
        }
        if(point.x < 0){
            point.x = -point.x;
            point.xSpeed = -point.xSpeed;
        }
        if(point.y > mHeight){
            point.y -= point.y - mHeight;
            point.ySpeed = -point.ySpeed;
        }
        if(point.y < 0){
            point.y = -point.y;
            point.ySpeed = -point.ySpeed;
        }
    }

    /**
     * 绘制点
     */
    private void drawPoints(Canvas canvas) {
        for (int i = 0; i < mPoints.size(); i++) {
            mPointPaint.setColor(mPoints.get(i).color);
            canvas.drawPoint(mPoints.get(i).x,mPoints.get(i).y,mPointPaint);

        }
    }

    /**
     * 绘制线
     * @param canvas
     */
    private void drawLines(Canvas canvas) {
        int count = 0;
        for (int i = 0; i < mPoints.size(); i++) {
            LinePoint point1 = mPoints.get(i);
            for (int j = i+1; j < mPoints.size(); j++) {
                LinePoint point2 = mPoints.get(j);
                int lineWidth = (int) point1.getDistance(point2);
                if(lineWidth < mMaxLineWidth){
                    if(lineWidth < 60){
                        mLinePaint.setAlpha(255);
                    }else{
                        mLinePaint.setAlpha(480 - 4 * lineWidth);
                    }
                    canvas.drawLine(point1.x,point1.y,point2.x,point2.y,mLinePaint);
                    count ++;
                }
            }
        }
    }

    private void drawFingerPoint(Canvas canvas) {
        if(mFingerPoint != null){
            for (int i = 0; i < mPoints.size(); i++) {
                LinePoint point = mPoints.get(i);
                if(point.isCatching || point.isCatched){
                    mLinePaint.setAlpha(255);
                    canvas.drawLine(point.x,point.y,mFingerPoint.x,mFingerPoint.y,mLinePaint);
                }
            }
        }
    }
}
