package com.example.administrator.customviewassamble.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * @author Rui Chaoqun
 * @date :2020/7/3 15:35
 * description:
 */
public class DiscView extends FrameLayout {

    private int mWhichChild;
    private Scroller mScroller;
    //本次手指滑动的距离
    private int mDistanceX = 0;
    //当前滑动的X距离
    private int mOffsetX = 0;

    private OnGestureListener mOnGestureListener = new SimpleOnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            getParent().requestDisallowInterceptTouchEvent(true);
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            mDistanceX = (int) (e2.getX() - e1.getX());
//            if(mDistanceX > 0){
//
//            }
            requestLayout();
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.w("AAAAA","onFling-->"+(e2.getX()-e1.getX())+"   "+velocityX);
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    };
    private GestureDetector mGesture = new GestureDetector(getContext(), this.mOnGestureListener);
    private Runnable requestRunnable = new Runnable() {
        @Override
        public void run() {
            requestLayout();
        }
    };


    public DiscView(Context context) {
        super(context);
        init();
    }

    public DiscView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext(), new DecelerateInterpolator());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        if(action == MotionEvent.ACTION_UP || action ==  MotionEvent.ACTION_CANCEL){
            int width = getMeasuredWidth();
            if(mDistanceX > width/2){
                //上一首
                mScroller.startScroll(0,0,width-mDistanceX,0,400);
            }else if(mDistanceX < -width/2){
                //下一首
                mScroller.startScroll(0,0,-(width+mDistanceX),0,400);
            }else{
                //返回当前
                mScroller.startScroll(0,0,-mDistanceX,0,400);
            }
            requestLayout();
        }
        mGesture.onTouchEvent(event);
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int disX;
        if(mScroller.computeScrollOffset()){
            disX = mDistanceX + mScroller.getCurrX();
        }else{
            disX = mDistanceX;
        }
        getChildAt(mWhichChild).layout(disX, 0, disX + getMeasuredWidth(), getMeasuredHeight());
        int nextLeft, nextRight;
        if (disX > 0) {
            nextLeft = disX - getMeasuredWidth();
        } else {
            nextLeft = getMeasuredWidth() + disX;
        }
        nextRight = nextLeft + getMeasuredWidth();
        getNextView().layout(nextLeft, 0, nextRight, getMeasuredHeight());
        //如果Scroller未结束。继续刷新layout
        if (!mScroller.isFinished()) {
            post(requestRunnable);
            return;
        }
    }

    private View getCurrentView() {
        return getChildAt(mWhichChild);
    }

    private View getNextView() {
        if (mWhichChild == 0) {
            return getChildAt(1);
        }
        return getChildAt(0);
    }

    public interface OnPlayerDiscListener{
        /**
         * 当手势滑动方向改变时调用
         * @param isScrollToNext true：向下一个滑动  false：向上一个滑动
         */
        void onScrollDirectionChanged(boolean isScrollToNext);

        /**
         * 开始滑动时回调，仅调用一次
         */
        void onScroll();

        /**
         * @param needSwitch 是否需要切换
         * @param isScrollToNext
         */
        void onScrollComplete(boolean needSwitch, boolean isScrollToNext);
    }
}

