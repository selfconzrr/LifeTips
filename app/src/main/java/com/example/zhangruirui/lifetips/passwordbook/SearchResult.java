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

import com.example.zhangruirui.lifetips.R;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchResult extends AppCompatActivity {

  Intent intent;
  ListView list;
  String Id, Keyword, Account, Password, Remind; // 查询到的字段
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

    helper = new DBHelper(SearchResult.this, "information.db");
    SQLiteDatabase db = helper.getReadableDatabase();

    String bundle = (String) getIntent().getExtras().get("searchKey");

    cursor = db.query("information", new String[]{"ID", "keyword", "account", "password",
            "remind"}, DBHelper.TABLE_KEYWORD + " like ?", new String[]{"%" + bundle + "%"}, null,
        null, "ID");//查询数据
    ArrayList<HashMap<String, Object>> listItem = new ArrayList<>();
    // 创建 ListView
    while (cursor.moveToNext()) {  // 判断下一个下标是否有内容
      Id = cursor.getString(cursor.getColumnIndex("ID"));
      Keyword = cursor.getString(cursor.getColumnIndex("keyword"));
      Account = cursor.getString(cursor.getColumnIndex("account"));
      Password = cursor.getString(cursor.getColumnIndex("password"));
      Remind = cursor.getString(cursor.getColumnIndex("remind"));
      idList.add(Id);

      map = new HashMap<>();
      map.put("ItemKeyword", "检索关键字：" + Keyword);
      map.put("ItemAccount", "账号：" + Account);
      map.put("ItemPassword", "密码：" + Password);
      map.put("ItemRemind", "备注：" + Remind);
      listItem.add(map);
    }
    SimpleAdapter listAdapter = new SimpleAdapter(SearchResult.this, listItem, R.layout
        .preview_item, new String[]{"ItemKeyword", "ItemAccount", "ItemPassword", "ItemRemind"},
        new int[]{R.id.check_textview01, R.id.check_textview02, R.id.check_textview03, R.id
            .check_textview04});
    list.setAdapter(listAdapter);//添加到适配器并且显示
  }

  class ListOnItem implements AdapterView.OnItemClickListener {

    public void onItemClick(AdapterView<?> adapterView, View v, int position, long arg3) {
      // index 是 list 中被选中元素的下标，从0开始
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
        intent = new Intent();
        intent.setClass(SearchResult.this, HomeActivity.class);
        startActivity(intent);
    }
    return super.onContextItemSelected(item);
  }
}
