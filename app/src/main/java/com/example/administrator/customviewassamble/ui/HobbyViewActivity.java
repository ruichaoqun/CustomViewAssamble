package com.example.administrator.customviewassamble.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.administrator.customviewassamble.R;
import com.example.administrator.customviewassamble.views.InterestViewGroup;
import com.example.administrator.customviewassamble.views.TagInfo;

import java.util.ArrayList;
import java.util.List;

public class HobbyViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hobby_view);
        InterestViewGroup interestViewGroup = findViewById(R.id.interestview);
        interestViewGroup.getAdapter().setData(initData());
        interestViewGroup.setData();
    }

    public List<TagInfo> initData(){
        List<TagInfo> list = new ArrayList<>();
        list.add(new TagInfo("棒球",R.mipmap.in_1));
        list.add(new TagInfo("单车",R.mipmap.in_2));
        list.add(new TagInfo("登山",R.mipmap.in_3));
        list.add(new TagInfo("橄榄球",R.mipmap.in_4));
        list.add(new TagInfo("滑冰",R.mipmap.in_5));
        list.add(new TagInfo("滑雪",R.mipmap.in_6));
        list.add(new TagInfo("击剑",R.mipmap.in_7));
        list.add(new TagInfo("健身",R.mipmap.in_8));
        list.add(new TagInfo("篮球",R.mipmap.in_9));
        list.add(new TagInfo("排球",R.mipmap.in_10));
        list.add(new TagInfo("跑步",R.mipmap.in_11));
        list.add(new TagInfo("乒乓球",R.mipmap.in_12));
        list.add(new TagInfo("射击",R.mipmap.in_13));
        list.add(new TagInfo("射箭",R.mipmap.in_14));
        list.add(new TagInfo("台球",R.mipmap.in_15));
        list.add(new TagInfo("体操",R.mipmap.in_16));
        list.add(new TagInfo("网球",R.mipmap.in_17));
        list.add(new TagInfo("武术",R.mipmap.in_18));
        list.add(new TagInfo("游泳",R.mipmap.in_19));
        list.add(new TagInfo("瑜伽",R.mipmap.in_20));
        list.add(new TagInfo("羽毛球",R.mipmap.in_21));
        list.add(new TagInfo("足球",R.mipmap.in_22));
        return list;
    }
}
