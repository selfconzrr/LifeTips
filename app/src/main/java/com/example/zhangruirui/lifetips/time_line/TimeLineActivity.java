package com.example.zhangruirui.lifetips.time_line;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.zhangruirui.lifetips.R;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimeLineActivity extends AppCompatActivity {

  @BindView(R.id.time_line)
  RecyclerView mRecyclerView;

  private ArrayList<HashMap<String, Object>> mListItems;
  private TimeLineAdapter mTimeLineAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_time_line);
    ButterKnife.bind(this);
    // 初始化要显示的数据
    initData();

    // 绑定数据到 RecyclerView
    initView();
  }

  private void initView() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    mRecyclerView.setLayoutManager(layoutManager);
    mRecyclerView.setHasFixedSize(true);

    mRecyclerView.addItemDecoration(new DividerItemDecoration(this)); // 设置自定义的分割线

    mTimeLineAdapter = new TimeLineAdapter(this, mListItems);
    mRecyclerView.setAdapter(mTimeLineAdapter);

  }

  private void initData() {
    mListItems = new ArrayList<>();

    HashMap<String, Object> map1 = new HashMap<>();
    HashMap<String, Object> map2 = new HashMap<>();
    HashMap<String, Object> map3 = new HashMap<>();
    HashMap<String, Object> map4 = new HashMap<>();
    HashMap<String, Object> map5 = new HashMap<>();
    HashMap<String, Object> map6 = new HashMap<>();

    map1.put("ItemTitle", "美国谷歌公司已发出");
    map1.put("ItemText", "发件人:谷歌 CEO Sundar Pichai");
    mListItems.add(map1);

    map2.put("ItemTitle", "国际顺丰已收入");
    map2.put("ItemText", "等待中转");
    mListItems.add(map2);

    map3.put("ItemTitle", "国际顺丰转件中");
    map3.put("ItemText", "下一站中国");
    mListItems.add(map3);

    map4.put("ItemTitle", "中国顺丰已收入");
    map4.put("ItemText", "下一站广州华南理工大学");
    mListItems.add(map4);

    map5.put("ItemTitle", "中国顺丰派件中");
    map5.put("ItemText", "等待派件");
    mListItems.add(map5);

    map6.put("ItemTitle", "华南理工大学已签收");
    map6.put("ItemText", "收件人:Carson");
    mListItems.add(map6);
  }
}
