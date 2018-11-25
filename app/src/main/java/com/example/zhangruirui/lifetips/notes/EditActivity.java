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

public class EditActivity extends AppCompatActivity {

  private Context context = this;
  private String date;
  private EditText editText;
  private String id;

  @Override
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(R.layout.activity_edit);
    TextView textView = findViewById(R.id.editdate);
    this.editText = ((DrawLine) findViewById(R.id.edittexttwo));
    Button cancelButton = findViewById(R.id.editbutton);
    Button sureButton = findViewById(R.id.editbutton2);
    this.date = getIntent().getStringExtra("dateItem");
    String content = getIntent().getStringExtra("contentItem");
    this.id = getIntent().getStringExtra("idItem");

    System.out.println("-----idItem-----id=" + id);
    this.editText.setText(content);
    this.editText.setSelection(editText.getText().length());
    textView.setText(this.date);
    MyDate dateNow = new MyDate();
    this.date = dateNow.getDate();
    textView.setText(this.date);
    sureButton.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        SQLiteDatabase localSqLiteDatabase = new SQLiteHelper(
            EditActivity.this.context)
            .getWritableDatabase();
        Notepad localNotepad = new Notepad();
        SqliteOperation localChangeSqlite = new SqliteOperation();
        String strContent = EditActivity.this.editText.getText()
            .toString();
        if (strContent.equals("")) {
          ToastUtil.showToast(EditActivity.this, "内容不能为空");
          return;
        }
        String strTitle = strContent.length() > 11 ? " "
            + strContent.substring(0, 11) : strContent;
        localNotepad.setContent(strContent);
        localNotepad.setTitle(strTitle);
        localNotepad.setData(date);
        localNotepad.setId(id);
        System.out.println("-----id-----id=" + id);
        localChangeSqlite.update(localSqLiteDatabase, localNotepad);
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
