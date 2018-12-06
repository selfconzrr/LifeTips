package com.example.zhangruirui.lifetips.demo_learning.coordinatorlayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.zhangruirui.lifetips.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Coordinator4thActivity extends AppCompatActivity {

  @BindView(R.id.tabs)
  TabLayout mTabLayout;
  @BindView(R.id.viewpager)
  ViewPager mViewPager;

  @BindView(R.id.fab)
  FloatingActionButton mFloatingActionButton;

  List<Fragment> mFragments;

  String[] mTitles = new String[]{
      "作品", "喜欢", "草稿"
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_coordinator4th);
    ButterKnife.bind(this);

    initTabLayout();

    initFragments();

    // 将 TabLayout 和 ViewPager 进行关联
    mTabLayout.setupWithViewPager(mViewPager);
  }

  private void initFragments() {
    mFragments = new ArrayList<>();

    // 给 fragments 添加三个 fragment
    for (String mTitle : mTitles) {
      mFragments.add(FragmentModel.newInstance(mTitle));
    }

    // 给 viewPager 设置适配器
    mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
      @Override
      public Fragment getItem(int position) {
        return mFragments.get(position);
      }

      @Override
      public int getCount() {
        return mFragments.size();
      }

      @Override
      public CharSequence getPageTitle(int position) {
        return mTitles[position];
      }
    });
    mViewPager.setOffscreenPageLimit(3);
  }

  private void initTabLayout() {
    // MODE_FIXED：固定 tabs，并同时显示所有的 tabs，尽可能填充，和 TabLayout.GRAVITY_FILL 一起使用才有效果
    // MODE_SCROLLABLE：可滚动 tabs，屏幕只显示一部分 tabs，在这个模式下能包含长标签和大量的 tabs
    mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    mTabLayout.setTabMode(TabLayout.MODE_FIXED);
    mTabLayout.setBackgroundColor(getResources().getColor(R.color.white));
    // 设置 tab 文字的颜色，第一个参数表示未选中状态下的文字颜色（黑色），第二个参数表示选中后的文字颜色（蓝色）
    mTabLayout.setTabTextColors(R.color.black, R.color.colorSelected);
    // 设置 tab 选中的底部的指示条的颜色（蓝色）
    mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorSelected));
  }

  @OnClick(R.id.fab)
  public void onClick() {
    Snackbar.make(mFloatingActionButton, "Hello ZRR", Snackbar.LENGTH_LONG).setAction
        ("ActionIV", new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Intent intent = new Intent(Coordinator4thActivity.this, Coordinator1stActivity.class);
            startActivity(intent);
          }
        }).show();
  }
}
