package com.example.administrator.customviewassamble.views;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.customviewassamble.R;

/**
 * Created by Administrator on 2018/5/5.
 */

public class HobbyItemView extends FrameLayout{
    private ImageView imageView;
    private TextView hexagonView;
    private Context context;
    //0:未选中 1：选中
    private int status = 0;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public HobbyItemView(Context context) {
        this(context,null);
    }

    public HobbyItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        imageView = new ImageView(context);
        hexagonView = new TextView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        hexagonView.setBackgroundResource(R.mipmap.bac_hexagon);
        hexagonView.setGravity(Gravity.CENTER);
        hexagonView.setTextSize(12);
        hexagonView.setPadding(5,5,5,5);
        hexagonView.setTextColor(ContextCompat.getColor(context,android.R.color.white));
        addView(imageView);
        addView(hexagonView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /*int wd = MeasureSpec.getSize(widthMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int totalHeight;
        for (int i = 0;i < getChildCount();i++){
            View view = getChildAt(i);
            int widthSpec = MeasureSpec.makeMeasureSpec(100, mode);
            int heightSpec = MeasureSpec.makeMeasureSpec(86,mode);
            measureChild(view, widthMeasureSpec, widthMeasureSpec);
            //measureChild(view,widthMeasureSpec,heightMeasureSpec);
        }*/
        /*//行数
        int col = getChildCount()/5;
        if(getChildCount()%5!=0){
            col++;
        }
        totalHeight = 96*(col+1);*/
        //setMeasuredDimension(wd, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        FrameLayout.LayoutParams params = new LayoutParams(width,height);
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            view.setLayoutParams(params);
        }
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public TextView getHexagonView() {
        return hexagonView;
    }

    public void setHexagonView(TextView hexagonView) {
        this.hexagonView = hexagonView;
    }
}
