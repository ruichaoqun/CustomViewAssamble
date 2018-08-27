package com.example.administrator.customviewassamble.views.drawcardbehavior;

import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;

public class DrawCardBehavior extends  CoordinatorLayout.Behavior<DrawCardLayout>{


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
        return true;
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull DrawCardLayout child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        boolean isVertical = (axes & ViewCompat.SCROLL_AXIS_VERTICAL ) != 0;
        return isVertical && child == directTargetChild;
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull DrawCardLayout child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        int initialOffset = child.getTop();
        int offset = initialOffset - dyUnconsumed;
        Log.w("AAA","dyUnconsumed-->"+dyUnconsumed);
        child.offsetTopAndBottom(dyUnconsumed);
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
    }
}
