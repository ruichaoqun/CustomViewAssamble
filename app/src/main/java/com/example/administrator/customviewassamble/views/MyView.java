package com.example.administrator.customviewassamble.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2018/5/6.
 */

public class MyView extends View {
    private int width = 200;
    private int height = 200;
    private Bitmap dstBmp;
    private Bitmap srcBmp;
    private Paint mPaint;
    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        dstBmp = getDSTBitmap(width,height);
        srcBmp = getSRCBitmap(width,height);
        mPaint = new Paint();
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);//禁止硬件加速
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(dstBmp, 0, 0, mPaint);//绘制目标图形,目标图形是一个圆
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        canvas.drawBitmap(srcBmp, width/2, height/2, mPaint);//绘制源目标
        mPaint.setColor(Color.RED);
        canvas.drawRect(0,0,100,100,mPaint);
        mPaint.setColor(Color.GREEN);
        canvas.drawRect(100,0,200,100,mPaint);
        mPaint.setXfermode(null);//将画笔去除Xfermode
    }
    public Bitmap getDSTBitmap(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);//创建一个新的画布,以后在画布上绘制的图形都是绘制在bm上
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(0xFFFFCC44);
        c.drawOval(new RectF(0, 0, w, h), p);
        return bm;
    }
    public  Bitmap getSRCBitmap(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(0xFF66AAFF);
        c.drawRect(0, 0,w,h, p);
        return bm;
    }
}
