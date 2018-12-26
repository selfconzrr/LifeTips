package com.example.zhangruirui.lifetips.demo_learning.refreshlayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.zhangruirui.lifetips.R;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.header.TwoLevelHeader;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SmartRefreshActivity extends AppCompatActivity {

  @BindView(R.id.recyclerView)
  RecyclerView mRecyclerView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_smart_refresh);
    ButterKnife.bind(this);

    RefreshLayout refreshLayout = findViewById(R.id.refreshLayout);
    refreshLayout.setOnRefreshListener(refreshlayout -> {
      refreshlayout.finishRefresh(2000/*,false*/); // 传入 false 表示刷新失败
    });
    refreshLayout.setOnLoadMoreListener(refreshlayout -> {
      refreshlayout.finishLoadMore(2000/*,false*/); // 传入 false 表示加载失败
    });

    // 1、设置 Header 为 贝塞尔雷达 样式
    // refreshLayout.setRefreshHeader(new BezierRadarHeader(this));

    // 2、设置 Header 为 经典 样式，显示上次刷新的时间和当前刷新的时间
    // refreshLayout.setRefreshHeader(new ClassicsHeader(this).setTimeFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)));

    // 3、设置 Header 为 二级刷新 样式
    refreshLayout.setRefreshHeader(new TwoLevelHeader(this));

    // 1、设置 Footer 为 球脉冲 样式
    // refreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));

    // 2、设置 Footer 为 经典 样式
    refreshLayout.setRefreshFooter(new ClassicsFooter(this));
  }
}
