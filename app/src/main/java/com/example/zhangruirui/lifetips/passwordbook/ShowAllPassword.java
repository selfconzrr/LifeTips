package com.example.zhangruirui.lifetips.passwordbook;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.bugfree.zhangruirui.slideback.SlideBackHelper;
import com.bugfree.zhangruirui.slideback.SlideBackLayout;
import com.example.zhangruirui.lifetips.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ShowAllPassword extends AppCompatActivity {

  Intent intent;
  ListView list;
  String id, keyword, account, password, remind; // 查询到的字段
  Cursor cursor;
  String indexID;

  HashMap<String, Object> map;
  DBHelper helper;
  ArrayList<String> idList = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search_result);
    list = findViewById(R.id.preview_listview);
    list.setOnItemClickListener(new ListOnItem());
    list.setOnItemLongClickListener(new ListOnItemLong());
    list.setOnCreateContextMenuListener(new ListOnCreate());

    helper = new DBHelper(ShowAllPassword.this, "information.db");

    SQLiteDatabase db = helper.getReadableDatabase();
    cursor = db.query("information", new String[]{"ID", "keyword", "account", "password",
        "remind"}, null, null, null, null, "ID");
    ArrayList<HashMap<String, Object>> listItem = new ArrayList<>();

    while (cursor.moveToNext()) {  // 判断下一个下标是否有内容
      id = cursor.getString(cursor.getColumnIndex("ID"));
      keyword = cursor.getString(cursor.getColumnIndex("keyword"));
      account = cursor.getString(cursor.getColumnIndex("account"));
      password = cursor.getString(cursor.getColumnIndex("password"));
      remind = cursor.getString(cursor.getColumnIndex("remind"));
      idList.add(id);

      map = new HashMap<>();
      map.put("ItemKeyword", "检索关键字：" + keyword);
      map.put("ItemAccount", "账号：" + account);
      map.put("ItemPassword", "密码：" + password);
      map.put("ItemRemind", "备注：" + remind);
      listItem.add(map);
    }
    SimpleAdapter listAdapter = new SimpleAdapter(ShowAllPassword.this, listItem, R.layout
        .preview_item,
        new String[]{"ItemKeyword", "ItemAccount", "ItemPassword", "ItemRemind"}, new int[]{R.id
        .check_textview01, R.id.check_textview02, R.id.check_textview03, R.id.check_textview04});
    list.setAdapter(listAdapter);

    SlideBackLayout mirrorSwipeBackLayout = SlideBackHelper.attach(this, R.layout
        .swipe_back);
    // mMirrorSwipeBackLayout.setRightSwipeEnable(true);
    // mMirrorSwipeBackLayout.setLeftSwipeEnable(true);
    mirrorSwipeBackLayout.setSwipeBackListener(this::finish);

  }

  class ListOnItem implements AdapterView.OnItemClickListener {

    public void onItemClick(AdapterView<?> adapterView, View v, int position, long arg3) {
      indexID = idList.get(position);
    }
  }

  class ListOnItemLong implements AdapterView.OnItemLongClickListener {

    public boolean onItemLongClick(AdapterView<?> parent, View view,
                                   int position, long id) {
      indexID = idList.get(position);
      return false;
    }
  }

  // ListView 长按监听器弹出菜单
  class ListOnCreate implements View.OnCreateContextMenuListener {

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo
        menuInfo) {
      menu.setHeaderTitle("操作");
      // menu.add(0,0,0,"打开");
      menu.add(0, 1, 0, "删除");
      // menu.add(0,2,0,"编辑");
    }
  }

  // 长按弹出菜单响应函数
  public boolean onContextItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case 1: // 删除
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from information where ID=" + "'" + indexID + "'" + ";");
        intent = new Intent(ShowAllPassword.this, ShowAllPassword.class);
        startActivity(intent);
        ShowAllPassword.this.finish();
    }
    return super.onContextItemSelected(item);
  }

}
