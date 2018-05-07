package com.example.administrator.customviewassamble.views;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.TextView;

import com.example.administrator.customviewassamble.R;
import com.example.administrator.customviewassamble.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 自定义兴趣ViewGroup
 */
public class InterestViewGroup extends ViewGroup{
    private Context mContext;
    private int mSpanCount = 5;//5列
    private InterestAdapter mAdapter;
    private OnItemClickListener listener;
    private ArrayList<View> childs;
    private int desiteny;
    //多边形长宽根据控件大小适配
    private double itemWidth,itemHeight;
    //间距
    private int interval = 10;

    private View view;
    private TextView textView;
    private int targetViewLeft;
    private int targetViewTop;
    private boolean isShowTips = false;
    int margintop = 130;

    public InterestViewGroup(Context context) {
        super(context);
        mContext = context;
        init();
    }



    public InterestViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        view = LayoutInflater.from(mContext).inflate(R.layout.layout_tips,null);
        textView = (TextView) view.findViewById(R.id.textview);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wd = MeasureSpec.getSize(widthMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        //多边形长宽根据控件大小适配
        itemWidth = wd/5;
        //宽度根据长度适配成五边形
        itemHeight = itemWidth*Math.sin(Math.toRadians(60));
        //Log.i("AAA","itemWidth"+itemWidth+"   "+"itemHeight"+itemHeight);

        view.setLayoutParams(new ViewGroup.LayoutParams(180,61));
        int totalHeight;
        if(getChildCount()>0){
            for (int i = 0;i < getAdapter().getCount();i++){
                View view = getChildAt(i);
                int widthSpec = MeasureSpec.makeMeasureSpec((int) itemWidth, mode);
                int heightSpec = MeasureSpec.makeMeasureSpec((int) itemHeight,mode);
                measureChild(view, widthSpec, heightSpec);
                //measureChild(view,widthMeasureSpec,heightMeasureSpec);
            }
            if(getAdapter().getCount()<getChildCount()){
                int widthSpec = MeasureSpec.makeMeasureSpec(106, mode);
                int heightSpec = MeasureSpec.makeMeasureSpec(61,mode);
                View view = getChildAt(getChildCount()-1);
                measureChild(view,widthSpec,heightSpec);
            }
            //行数
            int col = getChildCount()/5;
            if(getChildCount()%5!=0){
                col++;
            }
            totalHeight = (int) (itemHeight*(col+3)+margintop);
            if(totalHeight< UIUtils.dip2px(mContext,300)){
                setMeasuredDimension(wd, UIUtils.dip2px(mContext,300));
            }else{
                setMeasuredDimension(wd, totalHeight);
            }
        }else{
            setMeasuredDimension(wd, UIUtils.dip2px(mContext,300));
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //Log.i("AAA",l+"   "+t+"   "+r+"   "+b);
        double firstLeft;
        firstLeft = (r-l-((20*Math.sin(Math.toRadians(60))+itemWidth/2)*2+itemWidth*3))/2;
        int left = 0,right = 0,top = 0,bottom = 0;
        int firstSpanTop = (int) (itemHeight/2+5);

        if(getChildCount()>0){
            for (int i = 0 ;i < getAdapter().getCount(); i ++){
                View view = getChildAt(i);
                int row = i/5;
                switch (i%5){
                    case 0:
                        left = (int) firstLeft;
                        top = (int) (row*(itemHeight+10)+margintop+firstSpanTop);
                        right = (int) (left+itemWidth);
                        bottom = (int) (top+itemHeight);
                        break;
                    case 1:
                        left = (int) (firstLeft+10*Math.sin(Math.toRadians(60))+(itemWidth/4)*3);
                        top = (int) (row*(itemHeight+10)+margintop);
                        right = (int) (left+itemWidth);
                        bottom = (int) (top+itemHeight);
                        break;
                    case 2:
                        left = (int) (firstLeft+(itemWidth/2)*3+20*Math.sin(Math.toRadians(60)));
                        top = (int) (row*(itemHeight+10)+margintop+firstSpanTop);
                        right = (int) (left+itemWidth);
                        bottom = (int) (top+itemHeight);
                        break;
                    case 3:
                        left = (int) (firstLeft+(itemWidth/2)*3+20*Math.sin(Math.toRadians(60))+10*Math.sin(Math.toRadians(60))+(itemWidth/4)*3);
                        top = (int) (row*(itemHeight+10)+margintop);
                        right = (int) (left+itemWidth);
                        bottom = (int) (top+itemHeight);
                        break;
                    case 4:
                        left = (int) (firstLeft+(itemWidth/2)*6+40*Math.sin(Math.toRadians(60)));
                        top = (int) (firstSpanTop+row*(itemHeight+10)+margintop);
                        right = (int) (left+itemWidth);
                        bottom = (int) (top+itemHeight);
                        break;
                }
                view.layout(left,top,right,bottom);
            }
            if(getAdapter().getCount()<getChildCount()){
                View view = getChildAt(getChildCount()-1);
                //Log.i("AAA",targetViewLeft-3+"   "+(targetViewTop-62)+"   "+(targetViewLeft+103)+"   "+targetViewTop);
                view.layout((int) (targetViewLeft+itemWidth/2-90),targetViewTop-61,(int)(targetViewLeft+itemWidth/2+90),targetViewTop);
            }
        }
    }

    public void setAdapter(final InterestAdapter adapter){
        mAdapter = adapter;
        setView();

    }

    private void setView() {
        if(mAdapter == null){
            return;
        }
        if(mAdapter.getCount() == 0){

            return;
        }
        removeAllViews();
        childs = new ArrayList<>();
        for (int i = 0;i < mAdapter.getCount();i++){
            final int index = i;
            View view = mAdapter.getView(index,null,this);
            childs.add(view);
            addView(view);
        }
        requestLayout();
        invalidate();
    }

    public InterestAdapter getAdapter() {
        if(mAdapter == null){
            mAdapter = new InterestAdapter(mContext);
        }
        return mAdapter;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public void setData() {
        setView();
        mAdapter.setTipsClickListener(new InterestAdapter.TipsClickListener() {
            @Override
            public void tipsClick(String tag,int top,int left) {
                if(!isShowTips){
                    isShowTips = true;
                    addView(view);
                }
                textView.setText(tag);
                textView.setGravity(Gravity.CENTER);
                targetViewLeft = left;
                targetViewTop = top;
                requestLayout();
                invalidate();
            }
        });
    }

    public List<TagInfo> getSelectedData(){
        if(getAdapter()!= null){
            List<TagInfo> list = getAdapter().getList();
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setIsSelect(((HobbyItemView)getChildAt(i)).getStatus());
            }
            return  list;
        }
        return null;
    }



    public interface  OnItemClickListener{
        void onItemClick(View view, int position);
    }
}
