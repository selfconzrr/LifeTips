package com.example.zhangruirui.lifetips.skeleton;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.zhangruirui.lifetips.R;

public class SkeletonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skeleton);
        findViewById(R.id.btn_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerViewActivity.start(SkeletonActivity.this, RecyclerViewActivity.TYPE_LINEAR);
            }
        });
        findViewById(R.id.btn_grid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerViewActivity.start(SkeletonActivity.this, RecyclerViewActivity.TYPE_GRID);
            }
        });
        findViewById(R.id.btn_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewActivity.start(SkeletonActivity.this, ViewActivity.TYPE_VIEW);
            }
        });
        findViewById(R.id.btn_Imgloading).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewActivity.start(SkeletonActivity.this, ViewActivity.TYPE_IMG_LOADING);
            }
        });

        findViewById(R.id.btn_status).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SkeletonActivity.this, StatusViewActivity.class));
            }
        });
    }
}