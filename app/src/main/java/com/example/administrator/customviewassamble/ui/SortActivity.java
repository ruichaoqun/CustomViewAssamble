package com.example.administrator.customviewassamble.ui;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.customviewassamble.R;

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

    public void shellSort(View view) {
        Intent intent = new Intent(this,ShellSortActivity.class);
        startActivity(intent);
    }

    public void quickSort(View view) {
        Intent intent = new Intent(this,QuickSortActivity.class);
        startActivity(intent);
    }

    public void mergeSort(View view) {
        Intent intent = new Intent(this,MergeSortActivity.class);
        startActivity(intent);
    }

}
