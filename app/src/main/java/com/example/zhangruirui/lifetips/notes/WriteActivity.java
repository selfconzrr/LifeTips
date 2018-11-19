package com.example.zhangruirui.lifetips.notes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zhangruirui.lifetips.R;
import com.example.zhangruirui.utils.ToastUtil;

public class WriteActivity extends AppCompatActivity {

  private Context context = this;
  private String date;
  private EditText editText;

  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(R.layout.activity_write);
    TextView textView = findViewById(R.id.writedate);
    this.editText = ((DrawLine) findViewById(R.id.edittext));
    Button cancelButton = findViewById(R.id.button);
    Button sureButton = findViewById(R.id.button2);
    MyDate date = new MyDate();
    this.date = date.getDate();
    textView.setText(this.date);
    this.editText.setSelection(editText.getText().length());
    sureButton.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        SQLiteDatabase localSqLiteDatabase = new SqliteHelper(
            WriteActivity.this.context, null, null, 0)
            .getWritableDatabase();
        Notepad localNotepad = new Notepad();
        SqliteOperation localChangeSqlite = new SqliteOperation();
        String strContent = WriteActivity.this.editText.getText()
            .toString();
        if (strContent.equals("")) {
          ToastUtil.showToast(WriteActivity.this, "笔记内容不能为空");
          return;
        }
        String strTitle = strContent.length() > 11 ? " "
            + strContent.substring(0, 11) : strContent;
        localNotepad.setContent(strContent);
        localNotepad.setTitle(strTitle);
        localNotepad.setdata(WriteActivity.this.date);
        localChangeSqlite.add(localSqLiteDatabase, localNotepad);
        finish();
      }
    });
    cancelButton.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        finish();
      }
    });
  }
}
