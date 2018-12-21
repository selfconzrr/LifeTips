package com.example.zhangruirui.lifetips.notes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.bugfree.zhangruirui.slideback.SlideBackHelper;
import com.bugfree.zhangruirui.slideback.SlideBackLayout;
import com.example.zhangruirui.lifetips.BaseActivity;
import com.example.zhangruirui.lifetips.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author zhangruirui
 * email：1138517609@qq.com
 * GitHub：https://github.com/selfconzrr
 * Blog：http://blog.csdn.net/u011489043
 * Date：11/18/18
 */
public class TimeDiaryActivity extends BaseActivity {

  public NotepadAdapter mAdapter;
  public ArrayList<Map<String, Object>> mItemList;
  public ListView mListView;
  public int mNumber;
  public Button mNumberButton;
  public Button mTopButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_time_diary);
    mNumberButton = findViewById(R.id.number);
    mTopButton = findViewById(R.id.add_event);
    mListView = findViewById(R.id.listview);
    mListView.setDivider(null);
    mListView.setOnItemClickListener(new ItemClick());
    mTopButton.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        final Intent intent = new Intent(TimeDiaryActivity.this, WriteActivity.class);
        startActivity(intent);
      }
    });

    SlideBackLayout mirrorSwipeBackLayout = SlideBackHelper.attach(this, R.layout
        .swipe_back);
    // mMirrorSwipeBackLayout.setRightSwipeEnable(true);
    // mMirrorSwipeBackLayout.setLeftSwipeEnable(true);
    mirrorSwipeBackLayout.setSwipeBackListener(this::finish);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }


  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.change_bg:
        mListView.setBackgroundDrawable(getResources().getDrawable(R.drawable.background));
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onResume() {
    super.onResume();
    showUpdate();
  }

  @SuppressLint("SetTextI18n")
  public void showUpdate() {
    mItemList = new ArrayList<>();
    SQLiteDatabase localSqLiteDatabase = new SQLiteHelper(this
    ).getReadableDatabase();
    Iterator<Notepad> localIterator = new SqliteOperation().query(
        localSqLiteDatabase).iterator();
    while (true) {
      if (!localIterator.hasNext()) {
        Collections.reverse(mItemList);
        mAdapter = new NotepadAdapter(this, mItemList);
        mListView.setAdapter(mAdapter);
        if (mItemList.size() == 0) {
          mNumber = 0;
          mNumberButton.setText("");
        }
        return;
      }
      Notepad localNotepad = localIterator.next();
      HashMap<String, Object> localHashMap = new HashMap<>();
      localHashMap.put("titleItem", localNotepad.getTitle());
      localHashMap.put("dateItem", localNotepad.getData());
      localHashMap.put("contentItem", localNotepad.getContent());
      localHashMap.put("idItem", localNotepad.getId());
      localHashMap.put("EXPANDED", Boolean.TRUE);
      mItemList.add(localHashMap);
      mNumber = this.mItemList.size();
      this.mNumberButton.setText("(" + this.mNumber + ")");
    }
  }

  class ItemClick implements AdapterView.OnItemClickListener {

    @Override
    public void onItemClick(AdapterView<?> paramAdapterView,
                            View paramView, int paramInt, long paramLong) {
      Map<String, Object> localMap = TimeDiaryActivity.this.mItemList
          .get(paramInt);
      if ((Boolean) localMap.get("EXPANDED")) {
        localMap.put("EXPANDED", Boolean.FALSE);
      } else {
        localMap.put("EXPANDED", Boolean.TRUE);
      }
      TimeDiaryActivity.this.mAdapter.notifyDataSetChanged();
    }
  }
}
