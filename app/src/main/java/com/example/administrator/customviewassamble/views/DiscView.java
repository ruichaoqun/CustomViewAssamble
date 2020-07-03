package com.example.administrator.customviewassamble.views;

import android.content.Context;
import android.util.AttributeSet;
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
    };
    private GestureDetector mGesture = new GestureDetector(getContext(), this.mOnGestureListener);


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
            mScroller.startScroll();
        }
        mGesture.onTouchEvent(event);
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        getChildAt(mWhichChild).layout(mDistanceX, 0, mDistanceX + getMeasuredWidth(), getMeasuredHeight());
        int nextLeft, nextRight;
        if (mDistanceX > 0) {
            nextLeft = mDistanceX - getMeasuredWidth();
        } else {
            nextLeft = getMeasuredWidth() + mDistanceX;
        }
        nextRight = nextLeft + getMeasuredWidth();
        getNextView().layout(nextLeft, 0, nextRight, getMeasuredHeight());
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

