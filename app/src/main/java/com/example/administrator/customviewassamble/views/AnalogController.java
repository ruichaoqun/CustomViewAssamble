package com.example.administrator.customviewassamble.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.administrator.customviewassamble.UIUtils;

/**
 * <p>Description.</p>
 * <p>
 * <b>Maintenance History</b>:
 * <table>
 * <tr>
 * <th>Date</th>
 * <th>Developer</th>
 * <th>Target</th>
 * <th>Content</th>
 * </tr>
 * <tr>
 * <td>2018-05-24 11:24</td>
 * <td>rcq</td>
 * <td>All</td>
 * <td>Created.</td>
 * </tr>
 * </table>
 */
public class AnalogController extends View {
    String TAG = AnalogController.class.getSimpleName();
    private Paint selectCirclePaint,unSelectCirclePaint;
    private Paint circlePaint;
    private Paint linePaint;
    private Paint textPaint;
    private String label;
    private int degree = 3;
    private float midx,midy;



    public AnalogController(Context context) {
        super(context);
        init();
    }


    public AnalogController(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AnalogController(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        selectCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        selectCirclePaint.setColor(Color.parseColor("#FF0000"));
        selectCirclePaint.setStyle(Paint.Style.FILL);

        unSelectCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        unSelectCirclePaint.setColor(Color.parseColor("#222222"));
        unSelectCirclePaint.setStyle(Paint.Style.FILL);

        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setStyle(Paint.Style.FILL);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStrokeWidth(UIUtils.dip2px(getContext(),7));
        linePaint.setColor(Color.parseColor("#FF0000"));

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStrokeWidth(5);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(UIUtils.dip2px(getContext(),30));
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setFakeBoldText(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        midx = canvas.getWidth()/2;
        midy = canvas.getHeight()/2;
        Log.w(TAG,"midx-->"+midx+"   midy-->"+midy);

        float radius = Math.min(midx,midy)*((float) 14.5/16);
        int degree2 = Math.max(3,degree);
        int degree3 = Math.min(degree,21);
        float x,y;
        for (int i = degree3; i <= 21; i++) {
            float temp = (float) i/24;
            x = (float) (midx + radius*Math.sin(2*Math.PI*(1-temp)));
            y = (float) (midy + radius*Math.cos(2*Math.PI*temp));
            canvas.drawCircle(x,y,radius/15,unSelectCirclePaint);
        }

        for (int i = 3; i <= degree2; i++) {
            float temp = (float)i/24;
            x = (float) (midx + radius*Math.sin(2*Math.PI*(1-temp)));
            y = (float) (midy + radius*Math.cos(2*Math.PI*temp));
            canvas.drawCircle(x,y,radius/15,selectCirclePaint);
        }

        circlePaint.setColor(Color.parseColor("#444444"));
        canvas.drawCircle(midx,midy, (float) (radius*13.5/15),circlePaint);
        circlePaint.setColor(Color.parseColor("#000000"));
        canvas.drawCircle(midx,midy, (float) (radius*12.5/15),circlePaint);

        float temp = (float)degree/24;
        float x1 = (float) (midx + (radius*11/15)*Math.sin(2*Math.PI*(1-temp)));
        float y1 = (float) (midy + (radius*11/15)*Math.cos(2*Math.PI*temp));
        float x2 = (float) (midx + (radius*12/15)*Math.sin(2*Math.PI*(1-temp)));
        float y2 = (float) (midy + (radius*12/15)*Math.cos(2*Math.PI*temp));
        canvas.drawLine(x1,y1,x2,y2,linePaint);

        canvas.drawText("LABEL",midx,midy+radius+30,textPaint);
    }

    private float downDegree,currentDegree;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                float x = midx - event.getX();
                float y = event.getY() - midy;
                if(x == 0&& y > 0)
                    downDegree = 0;
                if(x == 0&& y <= 0)
                    downDegree = 180;
                if(y == 0&& x > 0)
                    downDegree = 90;
                if(y == 0&& x <= 0)
                    downDegree = 270;
                if(x != 0 && y != 0){
                    downDegree = (float) (Math.atan2(x,y)*180/Math.PI);
                    if(x < 0){
                        downDegree += 360;
                    }
                }
                downDegree = (float) Math.floor(downDegree/15);
                return  true;
            case MotionEvent.ACTION_MOVE:
                float x1 = midx - event.getX();
                float y1 = event.getY() - midy;
                if(x1 == 0&& y1 > 0)
                    currentDegree = 0;
                if(x1 == 0&& y1 <= 0)
                    currentDegree = 180;
                if(y1 == 0&& x1 > 0)
                    currentDegree = 90;
                if(y1 == 0&& x1 <= 0)
                    currentDegree = 270;
                if(x1 != 0 && y1 != 0){
                    currentDegree = (float) (Math.atan2(x1,y1)*180/Math.PI);
                    if(x1 < 0){
                        currentDegree += 360;
                    }
                }
                currentDegree = (float) Math.floor(currentDegree/15);

                if (currentDegree == 0 && downDegree == 23) {
                    degree++;
                    if (degree > 21) {
                        degree = 21;
                    }
                    downDegree = currentDegree;
                } else if (currentDegree == 23 && downDegree == 0) {
                    degree--;
                    if (degree < 3) {
                        degree = 3;
                    }
                    downDegree = currentDegree;
                } else {
                    degree += (currentDegree - downDegree);
                    if (degree > 21) {
                        degree = 21;
                    }
                    if (degree < 3) {
                        degree = 3;
                    }
                    downDegree = currentDegree;
                }
                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
                return true;
        }
        return super.onTouchEvent(event);
    }
}