package com.example.administrator.customviewassamble.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.administrator.customviewassamble.R;
import com.example.administrator.customviewassamble.views.EqureView;

public class CustomViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        LinearLayout layout = findViewById(R.id.parent);
        EqureView equreView = new EqureView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        equreView.setLayoutParams(params);
        layout.addView(equreView);
    }
}
