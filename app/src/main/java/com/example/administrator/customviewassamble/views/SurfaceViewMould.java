package com.example.administrator.customviewassamble.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.administrator.customviewassamble.R;

/**
 * Created by Administrator on 2018/5/5.
 * SurfaceView模板
 */

public abstract class SurfaceViewMould extends SurfaceView implements SurfaceHolder.Callback{
    private static final String TAG = "SurfaceViewMould";
    private static final int SLEEP_TIME = 50;
    private final Object mSurfaceLock = new Object();
    private DrawThread mThread;

    protected float width;
    protected float height;

    public SurfaceViewMould(Context context) {
        this(context,null);

    }

    public SurfaceViewMould(Context context, AttributeSet attrs) {
        this(context, null,0);
    }

    public SurfaceViewMould(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getHolder().addCallback(this);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mThread = new DrawThread(holder);
        mThread.setRun(true);
        mThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        //这里可以获取SurfaceView的宽高等信息
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        synchronized (mSurfaceLock) {  //这里需要加锁，否则doDraw中有可能会crash
            mThread.setRun(false);
        }
    }

    private class DrawThread extends Thread {
        private SurfaceHolder mHolder;
        private boolean mIsRun = false;

        public DrawThread(SurfaceHolder holder) {
            super(TAG);
            mHolder = holder;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (mSurfaceLock) {
                    if (!mIsRun) {
                        return;
                    }
                    Canvas canvas = mHolder.lockCanvas();
                    if (canvas != null) {
                        if(width == 0){
                            width = canvas.getWidth();
                            height = canvas.getHeight();
                        }
                        doDraw(canvas);  //这里做真正绘制的事情
                        mHolder.unlockCanvasAndPost(canvas);
                    }
                }
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void setRun(boolean isRun) {
            this.mIsRun = isRun;
        }
    }

    protected abstract void doDraw(Canvas canvas);

}
