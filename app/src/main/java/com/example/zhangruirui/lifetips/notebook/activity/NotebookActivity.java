package com.example.zhangruirui.lifetips.notebook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.zhangruirui.lifetips.R;
import com.example.zhangruirui.lifetips.notebook.adapter.NaviListAdapter;
import com.example.zhangruirui.lifetips.notebook.fragment.AllNotesFragment;
import com.example.zhangruirui.lifetips.notebook.fragment.SearchNoteFragment;
import com.example.zhangruirui.lifetips.notebook.fragment.SettingFragment;

import java.util.Arrays;

/**
 * @author zhangruirui
 * email：1138517609@qq.com
 * GitHub：https://github.com/selfconzrr
 * Blog：http://blog.csdn.net/u011489043
 * Date：11/24/18
 * <p>
 * 笔记本首页
 * <p>
 * Android NoteBook
 * 一个入门级的 Android 项目，欢迎新手学习
 * 你将学会
 * 1.CursorLoader 和 CursorAdapter 实现异步加载数据
 * 2.Toolbar 的使用
 * 3.DrawerLayout 和 Fragment 实现侧滑菜单
 * 4.SQLite 存储数据
 */
public class NotebookActivity extends AppCompatActivity implements Toolbar
    .OnMenuItemClickListener, AdapterView.OnItemClickListener {

  private DrawerLayout mDlLayout;
  private String[] mLabels = new String[]{
      "AllNodes", "SearchByTitle", "Setting"
  };
  private Fragment mFragments[] = new Fragment[mLabels.length];

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_notebook);
    mFragments[0] = new AllNotesFragment();
    mFragments[1] = new SearchNoteFragment();
    mFragments[2] = new SettingFragment();
    initView();
    showFragment();
  }

  private void initView() {
    Toolbar toolbar = findViewById(R.id.id_toolbar);
    setSupportActionBar(toolbar);
    toolbar.setOnMenuItemClickListener(this);
    mDlLayout = findViewById(R.id.id_dl_main_layout);
    ListView lvNavi = findViewById(R.id.id_lv_navi);
    NaviListAdapter adapter = new NaviListAdapter(this, Arrays.asList(mLabels));
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDlLayout, toolbar, R.string
        .app_name, R.string
        .app_name);
    toggle.syncState();
    // mDlLayout.setDrawerListener(toggle); // 过时的 API 尽量不用，容易 NPE
    mDlLayout.addDrawerListener(toggle);
    lvNavi.setAdapter(adapter);
    // 为每个 ListItem 添加点击事件,即启动相应的 Fragment
    lvNavi.setOnItemClickListener(this);
  }

  private void showFragment() {
    AllNotesFragment notesFragment = new AllNotesFragment();
    getSupportFragmentManager().beginTransaction().replace(R.id.id_fl_main_content, notesFragment)
        .commit();
  }

  /**
   * 添加菜单
   */
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_notes, menu);
    return super.onCreateOptionsMenu(menu);
  }

  /**
   * 设置添加事件
   */
  @Override
  public boolean onMenuItemClick(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.id_menu_add_note:
        Intent intent = new Intent(this, NoteDetailActivity.class);
        startActivity(intent);
        break;
    }
    return false;
  }

  /**
   * 切换到相应的Fragment
   */
  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.id_fl_main_content, mFragments[position]).commit();
    mDlLayout.closeDrawers();
  }
}
