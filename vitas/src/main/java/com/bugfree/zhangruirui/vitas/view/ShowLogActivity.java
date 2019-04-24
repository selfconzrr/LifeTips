package com.bugfree.zhangruirui.vitas.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.bugfree.zhangruirui.vitas.R;
import com.bugfree.zhangruirui.vitas.adapter.LogAdapter;
import com.bugfree.zhangruirui.vitas.presenter.ShowLogPresenter;
import com.bugfree.zhangruirui.vitas.repo.RequestInfo;

import java.util.List;

public class ShowLogActivity extends BaseActivity implements IShowLogView {

  private static final String TEXT_KEY = "text";

  private final int F_RC_SEARCH = 1;
  private final int TIME_DELAY = 500;

  private RecyclerView mRvLog;
  private SearchView mSvFilter;
  private ShowLogPresenter mShowLogPresenter;
  private LogAdapter mLogAdapter;

  @SuppressLint("HandlerLeak")
  private Handler mHandler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      if (msg.what == F_RC_SEARCH) {
        String text = msg.getData().getString(TEXT_KEY);
        mShowLogPresenter.showLogList(text);
      }
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.moudle_activity_show_logs);
    initToolbar();
    initView();
    mShowLogPresenter.showLogList();
  }

  private void initToolbar() {
    Toolbar toobar = findViewById(R.id.tb_show);
    toobar.setTitle(R.string.vitasTitle);
    toobar.inflateMenu(R.menu.menu_show_logs_toolbar);
    setSupportActionBar(toobar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    toobar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_show_logs_toolbar, menu);
    MenuItem searchItem = menu.findItem(R.id.sv_menu_item_filter);
    mSvFilter = (SearchView) searchItem.getActionView();
    mSvFilter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        mShowLogPresenter.showLogList(query);
        return true;
      }

      @Override
      public boolean onQueryTextChange(String newText) {
        if (mHandler.hasMessages(F_RC_SEARCH)) {
          mHandler.removeMessages(F_RC_SEARCH);
        }
        Message message = new Message();
        message.what = F_RC_SEARCH;
        Bundle data = new Bundle();
        data.putString(TEXT_KEY, newText);
        message.setData(data);
        mHandler.sendMessageDelayed(message, TIME_DELAY);
        return true;
      }
    });
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int i = item.getItemId();
    if (i == R.id.menu_item_clear_logs) {
      mShowLogPresenter.clearLogList();
      mShowLogPresenter.showLogList();
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void initView() {
    super.initView();
    mShowLogPresenter = new ShowLogPresenter(this);
    mRvLog = findViewById(R.id.rv_show_log);
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    mRvLog.setLayoutManager(layoutManager);
  }

  /**
   * new Adapter to refresh data.
   *
   * @param requestInfoList
   */
  @Override
  public void showLogList(List<RequestInfo> requestInfoList) {
    mLogAdapter = new LogAdapter(this, requestInfoList);
    mRvLog.setAdapter(mLogAdapter);
    mLogAdapter.notifyDataSetChanged();
  }
}