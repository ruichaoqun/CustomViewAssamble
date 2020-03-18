package com.example.administrator.customviewassamble.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.customviewassamble.R;
import com.example.administrator.customviewassamble.SortUtils;
import com.example.administrator.customviewassamble.views.SortDisplayView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MergeSortActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merge_sort);
        final SortDisplayView sortDisplayView = findViewById(R.id.sort_view);
        final List<Integer> list = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            list.add(i+1);
        }
        Collections.shuffle(list);
        new Thread(new Runnable() {
            @Override
            public void run() {
                SortUtils.mergeSort(list,0,list.size() - 1, new SortUtils.OnExchangedListener() {
                    @Override
                    public void onExchanged(final List<Integer> list1) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                sortDisplayView.setIntegerList(list1);
                            }
                        });
                    }
                });
            }
        }).start();

    }
}
