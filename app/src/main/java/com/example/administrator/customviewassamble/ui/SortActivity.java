package com.example.administrator.customviewassamble.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.customviewassamble.R;
import com.example.administrator.customviewassamble.SortUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 排序算法展示
 */
public class SortActivity extends AppCompatActivity {
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);
        mTextView = findViewById(R.id.textView);
    }

    public void bubbleSort(View view) {
        Intent intent = new Intent(this,BubbleSortActivity.class);
        startActivity(intent);
    }

    public void selectionSort(View view) {
        Intent intent = new Intent(this,SelectionSortActivity.class);
        startActivity(intent);
    }

    public void insertSort(View view) {
        Intent intent = new Intent(this,InsertSortActivity.class);
        startActivity(intent);
    }
}
