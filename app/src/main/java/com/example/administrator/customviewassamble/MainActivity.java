package com.example.administrator.customviewassamble;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.customviewassamble.ui.AnalogControllerActivity;
import com.example.administrator.customviewassamble.ui.CustomViewActivity;
import com.example.administrator.customviewassamble.ui.DrawCardActivity;
import com.example.administrator.customviewassamble.ui.HobbyViewActivity;
import com.example.administrator.customviewassamble.ui.LineChatViewActivity;
import com.example.administrator.customviewassamble.ui.LineMoveWithFingerActivity;
import com.example.administrator.customviewassamble.ui.SortActivity;
import com.example.administrator.customviewassamble.ui.WaveActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Adapter.OnItemClickListener, Adapter.OnItemLongClickListener {
    private static List<Description> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        DataUtils.setData(list);
        Adapter adapter = new Adapter(list);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemLongClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent();
        switch (position){
            case 0:
                intent.setClass(this, HobbyViewActivity.class);
                break;
            case 1:
                intent.setClass(this, WaveActivity.class);
                break;
            case 2:
                intent.setClass(this, AnalogControllerActivity.class);
                break;
            case 3:
                intent.setClass(this, LineChatViewActivity.class);
                break;
            case 4:
                intent.setClass(this, SortActivity.class);
                break;
            case 5:
                intent.setClass(this, DrawCardActivity.class);
                break;
            case 6:
                intent.setClass(this, LineMoveWithFingerActivity.class);
                break;
            case 7:
                intent.setClass(this, CustomViewActivity.class);
                break;
                default:
        }
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(int position) {
        Toast.makeText(this,list.get(position).getDescription(),Toast.LENGTH_SHORT).show();
    }
}
