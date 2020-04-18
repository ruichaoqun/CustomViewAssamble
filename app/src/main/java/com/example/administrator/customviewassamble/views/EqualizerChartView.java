package com.example.administrator.customviewassamble.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.administrator.customviewassamble.UIUtils;


public class EqualizerChartView extends View {
    private Paint mLinePaint;
    private Paint mTextPaint;
    private Paint mVisibleRectPaint;
    private Paint mBackLinePaint;
    private Paint mArrowPaint;

    private float mTextMarginBottom;

    private float[] mFloatsX;
    private float[] mFloatsY;
    private int[] mProgress = new int[10];

    private Path mPath;

    private float mRectRatio = 0.5f;
    private float mScrollRatio = 0.0f;
    private float mRatio;

    private Path mArrowPath;
    private float mArrowLength;

    private float mTouchedX;


    public EqualizerChartView(Context context) {
        super(context);
        init(context);
    }

    public EqualizerChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mTextMarginBottom = UIUtils.dip2px(context, 5.0f);
        mArrowLength = UIUtils.dip2px(context, 8.0f);
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setStrokeWidth(UIUtils.dip2px(context, 2.0f));
        mLinePaint.setColor(Color.WHITE);
        mLinePaint.setPathEffect(new CornerPathEffect(UIUtils.dip2px(context, 20.0f)));
        mLinePaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(UIUtils.sp2px(context, 8.0f));
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mVisibleRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mVisibleRectPaint.setColor(Color.parseColor("#44ffffff"));

        mBackLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackLinePaint.setStrokeWidth(UIUtils.dip2px(context, 0.5f));
        mBackLinePaint.setColor(Color.parseColor("#55ffffff"));

        mArrowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArrowPaint.setColor(Color.parseColor("#44ffffff"));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float width = getWidth();
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchedX = x;
                mRatio = mScrollRatio;
                if (!isInRect(mTouchedX)) {
                    return super.onTouchEvent(event);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_MOVE:
                float distanceX = x - mTouchedX;
                float ratio = distanceX / ((1 - mRectRatio) * width);
                mScrollRatio = mRatio + ratio;
                if (mScrollRatio < 0) {
                    mScrollRatio = 0;
                }
                if (mScrollRatio > 1) {
                    mScrollRatio = 1;
                }
                break;
            default:
        }
        invalidate();
        return true;
    }

    private boolean isInRect(float mTouchedX) {
        float x1 = mScrollRatio * (1 - mRectRatio) * getWidth();
        float x2 = x1 + mRectRatio * getWidth();
        return mTouchedX > x1 && mTouchedX < x2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        canvas.drawColor(Color.RED);
        if (mFloatsX == null) {
            mFloatsX = new float[10];
            mFloatsY = new float[7];
            float xStep = width / 11.0f;
            float yStep = height / 8;
            for (int i = 1; i <= mFloatsX.length; i++) {
                mFloatsX[i - 1] = i * xStep;
            }
            for (int i = 1; i <= mFloatsY.length; i++) {
                mFloatsY[i - 1] = i * yStep;
            }
        }
        if (mPath == null) {
            mPath = new Path();
            mArrowPath = new Path();
        }
        mPath.reset();
        mPath.moveTo(0, height / 2);
        for (int i = 0; i < mFloatsX.length; i++) {
            canvas.drawLine(mFloatsX[i] - 4.0f, 0, mFloatsX[i] - 4.0f, height, mBackLinePaint);
            canvas.drawLine(mFloatsX[i] + 4.0f, 0, mFloatsX[i] + 4.0f, height, mBackLinePaint);
            int value = (int) ((mProgress[i] / 100.0f) - 12);
            canvas.drawText(String.valueOf(value), mFloatsX[i], height - mTextMarginBottom, mTextPaint);
            mPath.lineTo(mFloatsX[i], (1 - mProgress[i] / 2400.0f) * height);
        }
        for (int i = 0; i < mFloatsY.length; i++) {
            canvas.drawLine(0, mFloatsY[i], width, mFloatsY[i], mBackLinePaint);
        }
        mPath.lineTo(width, height / 2);
        canvas.drawPath(mPath, mLinePaint);
        float left = mScrollRatio * (width * (1 - mRectRatio));
        float right = left + width * mRectRatio;
        canvas.drawRect(left, 0, right, height, mVisibleRectPaint);
        mArrowPath.reset();
        float x = right - 8.0f;
        float y = mArrowLength / 2 + 8.0f;
        float k = (float) (mArrowLength * Math.sin(60 * Math.PI / 180));
        mArrowPath.moveTo(x, y);
        mArrowPath.lineTo(x - k, 8.0f);
        mArrowPath.lineTo(x - k, mArrowLength + 8.0f);
        mArrowPath.close();
        canvas.drawPath(mArrowPath, mArrowPaint);
        mArrowPath.reset();
        mArrowPath.moveTo(x - k - 8.0f, 8.0f);
        mArrowPath.lineTo(x - k - 8.0f, mArrowLength + 8.0f);
        mArrowPath.lineTo(x - k - 8.0f - k, mArrowLength / 2 + 8.0f);
        mArrowPath.close();
        canvas.drawPath(mArrowPath, mArrowPaint);
    }
}
