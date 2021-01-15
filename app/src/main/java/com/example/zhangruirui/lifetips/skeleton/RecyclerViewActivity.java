package com.example.zhangruirui.lifetips.skeleton;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.skeleton.Skeleton;
import com.example.skeleton.SkeletonScreen;
import com.example.zhangruirui.lifetips.R;
import com.example.zhangruirui.lifetips.skeleton.adapter.NewsAdapter;
import com.example.zhangruirui.lifetips.skeleton.adapter.PersonAdapter;

public class RecyclerViewActivity extends AppCompatActivity {

    private static final String PARAMS_TYPE = "params_type";
    public static final String TYPE_LINEAR = "type_linear";
    public static final String TYPE_GRID = "type_grid";
    private String mType;

    public static void start(Context context, String type) {
        Intent intent = new Intent(context, RecyclerViewActivity.class);
        intent.putExtra(PARAMS_TYPE, type);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_skeleton);
        mType = getIntent().getStringExtra(PARAMS_TYPE);
        init();
    }

    private void init() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        if (TYPE_LINEAR.equals(mType)) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            NewsAdapter adapter = new NewsAdapter();
            final SkeletonScreen skeletonScreen = new Skeleton().bind(recyclerView)
                    .adapter(adapter)
                    .shimmer(true)
                    .angle(20)
                    .frozen(false)
                    .duration(1200)
                    .count(10)
                    .load(R.layout.item_skeleton_news)
                    .show(); //default count is 10
            recyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    skeletonScreen.hide();
                }
            }, 3000);
            return;
        }
        if (TYPE_GRID.equals(mType)) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            PersonAdapter adapter = new PersonAdapter();
            final SkeletonScreen skeletonScreen = new Skeleton().bind(recyclerView)
                    .adapter(adapter)
                    .load(R.layout.item_skeleton_person)
                    .shimmer(false)
                    .show();
            recyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    skeletonScreen.hide();
                }
            }, 3000);
        }
    }
}