package com.example.administrator.customviewassamble.views.drawcardbehavior;

import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;

public class DrawCardBehavior extends  CoordinatorLayout.Behavior<DrawCardLayout>{
    private int mInitialOffset;
    private int mMaxOffset;

    @Override
    public boolean onMeasureChild(CoordinatorLayout parent, DrawCardLayout child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        int offset = getChildMeasureOffset(parent,child);
        int height = View.MeasureSpec.getSize(parentHeightMeasureSpec) - offset;
        child.measure(parentWidthMeasureSpec, View.MeasureSpec.makeMeasureSpec(height,View.MeasureSpec.EXACTLY));
        return true;
    }

    private int getChildMeasureOffset(CoordinatorLayout parent,DrawCardLayout child) {
        int count = parent.getChildCount();
        int offset = 0;
        for (int i = 0; i < count; i++) {
            View view = parent.getChildAt(i);
            if(view != child && view instanceof DrawCardLayout){
                offset += ((DrawCardLayout)view).getHeadHeight();
            }
        }


        return offset;
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, DrawCardLayout child, int layoutDirection) {
        parent.onLayoutChild(child,layoutDirection);
        int offset = 0;
        for (int i = 0; i < parent.indexOfChild(child); i++) {
            View view = parent.getChildAt(i);
            if(view instanceof DrawCardLayout){
                offset += ((DrawCardLayout)view).getHeadHeight();
            }
        }
        child.offsetTopAndBottom(offset);
        mInitialOffset = offset;

        mMaxOffset = parent.getMeasuredHeight() - child.getHeadHeight();
        for (int i = parent.getChildCount(); i > parent.indexOfChild(child) ; i--) {
            View view = parent.getChildAt(i);
            if(view instanceof DrawCardLayout){
                mMaxOffset -= child.getHeadHeight();
            }
        }
        return true;
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull DrawCardLayout child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        boolean isVertical = (axes & ViewCompat.SCROLL_AXIS_VERTICAL ) != 0;
        return isVertical && child == directTargetChild;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull DrawCardLayout child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        int top = child.getTop();
        int offset = top - dy;
        consumed[0] = dx;
        if(offset < mInitialOffset){
            offset = mInitialOffset;
            consumed[1] = mInitialOffset - top;
        }else if(offset > mMaxOffset){
            offset = mMaxOffset;
            consumed[1] = -mMaxOffset + top;
        }else{
            consumed[1] = dy;
        }
        child.offsetTopAndBottom(offset - top);
        if(dy < 0){
            int lowChildIndex = 0;
            for (int i = coordinatorLayout.indexOfChild(child) + 1; i < coordinatorLayout.getChildCount(); i++) {
                View view = coordinatorLayout.getChildAt(i);
                if(view instanceof DrawCardLayout){
                    lowChildIndex ++;
                    int viewTop = view.getTop();
                    if(viewTop < top + lowChildIndex* child.getHeadHeight()){
                        view.offsetTopAndBottom(top + lowChildIndex* child.getHeadHeight() - viewTop);
                    }
                }
            }
        }else{
            int aboveChildIndex = 0;
            for (int i = coordinatorLayout.indexOfChild(child) - 1; i >= 0 ; i--) {
                View view = coordinatorLayout.getChildAt(i);
                if(view instanceof DrawCardLayout){
                    aboveChildIndex ++;
                    int viewTop = view.getTop();
                    if(viewTop > top - aboveChildIndex* child.getHeadHeight()){
                        view.offsetTopAndBottom(top - aboveChildIndex* child.getHeadHeight() - viewTop);
                    }
                }
            }
        }
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull DrawCardLayout child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {

        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
    }
}
