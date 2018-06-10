package com.example.administrator.customviewassamble.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

/**
 * <p>Description.</p>
 *
 * <b>Maintenance History</b>:
 * <table>
 * 		<tr>
 * 			<th>Date</th>
 * 			<th>Developer</th>
 * 			<th>Target</th>
 * 			<th>Content</th>
 * 		</tr>
 * 		<tr>
 * 			<td>2018-06-09 19:41</td>
 * 			<td>Rui chaoqun</td>
 * 			<td>All</td>
 *			<td>Created.</td>
 * 		</tr>
 * </table>
 */
public class SortDisplayView extends View {
    private Paint mPaint;
    private List<Integer> mIntegerList;
    private int lineWidth;//line宽度
    private int lineSpace = 2;//间距
    private int heightDenisty;

    public SortDisplayView(Context context) {
        this(context,null);
    }

    public SortDisplayView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mIntegerList == null || mIntegerList.size() == 0)
            return;
        for (int i = 0; i < mIntegerList.size(); i++) {
            int startX = i * (lineWidth + lineSpace) + lineWidth/2;
            int startY = getHeight();
            int endX = startX;
            int endY = getHeight() - mIntegerList.get(i) * heightDenisty;
            canvas.drawLine(startX,startY,endX,endY,mPaint);
        }
    }

    private void caculateLine() {
        int width = getMeasuredWidth();
        lineWidth = width/mIntegerList.size() - lineSpace;
        heightDenisty = (getMeasuredHeight() - 10)/100;
        mPaint.setStrokeWidth(lineWidth);
    }

    public List<Integer> getIntegerList() {
        return mIntegerList;
    }

    public void setIntegerList(List<Integer> integerList) {
        this.mIntegerList = integerList;
        caculateLine();
        postInvalidate();
    }
}
