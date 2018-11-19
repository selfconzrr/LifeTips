package com.example.zhangruirui.lifetips.notes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

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
public class TimeDiaryActivity extends AppCompatActivity {

  public NotepadAdapter adapter;
  public ArrayList<Map<String, Object>> itemList;
  public ListView listView;
  public int number;
  public Button numberButton;
  public Button topButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_time_diary);
    numberButton = findViewById(R.id.number);
    topButton = findViewById(R.id.add_event);
    listView = findViewById(R.id.listview);
    // this.listView.setDivider(getResources().getDrawable(android.R.color.white));
    listView.setDivider(null);
    listView.setOnItemClickListener(new ItemClick());
    topButton.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        Intent intent = new Intent(TimeDiaryActivity.this, WriteActivity.class);
        startActivity(intent);
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu;
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }


  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.change_bg:
        listView.setBackgroundDrawable(getResources().getDrawable(R.drawable.background));
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
    itemList = new ArrayList<>();
    SQLiteDatabase localSqLiteDatabase = new SqliteHelper(this, null, null,
        0).getReadableDatabase();
    Iterator<Notepad> localIterator = new SqliteOperation().query(
        localSqLiteDatabase).iterator();
    while (true) {
      if (!localIterator.hasNext()) {
        Collections.reverse(itemList);
        adapter = new NotepadAdapter(this, itemList);
        listView.setAdapter(adapter);
        if (itemList.size() == 0) {
          number = 0;
          numberButton.setText("");
        }
        return;
      }
      Notepad localNotepad = localIterator.next();
      HashMap<String, Object> localHashMap = new HashMap<>();
      localHashMap.put("titleItem", localNotepad.getTitle());
      localHashMap.put("dateItem", localNotepad.getdata());
      localHashMap.put("contentItem", localNotepad.getContent());
      localHashMap.put("idItem", localNotepad.getid());
      localHashMap.put("EXPANDED", Boolean.TRUE);
      itemList.add(localHashMap);
      number = this.itemList.size();
      System.out.println("number----------number=" + number);
      this.numberButton.setText("(" + this.number + ")");
    }
  }

  class ItemClick implements AdapterView.OnItemClickListener {

    @Override
    public void onItemClick(AdapterView<?> paramAdapterView,
                            View paramView, int paramInt, long paramLong) {
      System.out.println("item----------click");
      Map<String, Object> localMap = TimeDiaryActivity.this.itemList
          .get(paramInt);
      if ((Boolean) localMap.get("EXPANDED")) {
        localMap.put("EXPANDED", Boolean.FALSE);
      } else {
        localMap.put("EXPANDED", Boolean.TRUE);
      }
      TimeDiaryActivity.this.adapter.notifyDataSetChanged();
    }
  }
}
