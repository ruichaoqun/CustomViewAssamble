package com.example.administrator.customviewassamble.views.drawcardbehavior;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.administrator.customviewassamble.R;
import com.example.administrator.customviewassamble.UIUtils;

public class DrawCardLayout extends FrameLayout implements CoordinatorLayout.AttachedBehavior {
    private TextView mTvHead;
    private RecyclerView mRecyclerView;
    private int headHeight;

    public DrawCardLayout(@NonNull Context context) {
        this(context,null);
    }

    public DrawCardLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DrawCardLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_draw_card,this);
        mTvHead = findViewById(R.id.tv_head);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        TestAdapter adapter = createTestData();
        mRecyclerView.setAdapter(adapter);


        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.DrawCardLayout,defStyleAttr,0);
        int color = array.getColor(R.styleable.DrawCardLayout_android_colorBackground, Color.GRAY);
        mTvHead.setBackgroundColor(color);
        mTvHead.setText(array.getText(R.styleable.DrawCardLayout_android_text));
        array.recycle();
        headHeight = UIUtils.dip2px(context,45);

    }

    private TestAdapter createTestData() {
        String[] strings = new String[20];
        for (int i = 0; i < 20; i++) {
            strings[i] = "sdkfh"+i;
        }
        return new TestAdapter(strings);
    }


    public TextView getTvHead() {
        return mTvHead;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public int getHeadHeight() {
        return headHeight;
    }

    @NonNull
    @Override
    public CoordinatorLayout.Behavior getBehavior() {
        return new DrawCardBehavior();
    }
}
