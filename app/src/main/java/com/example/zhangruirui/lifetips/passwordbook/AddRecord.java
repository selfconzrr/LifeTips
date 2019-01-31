package com.example.zhangruirui.lifetips.passwordbook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.example.zhangruirui.lifetips.R;
import com.example.zhangruirui.utils.ToastUtil;

public class AddRecord extends AppCompatActivity {

  private EditText add_record_edittext01, add_record_edittext02, add_record_edittext03,
      add_record_edittext04;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_record);

    Button add_record = findViewById(R.id.add_record_add);

    add_record_edittext01 = findViewById(R.id.add_record_edittext01);
    add_record_edittext02 = findViewById(R.id.add_record_edittext02);
    add_record_edittext03 = findViewById(R.id.add_record_edittext03);
    add_record_edittext04 = findViewById(R.id.add_record_edittext04);

    add_record.setOnClickListener(arg0 -> AddData());

  }

  public void AddData() {
    DBHelper helper = new DBHelper(this.getBaseContext(), "information.db");
    String user_keyword = add_record_edittext01.getText().toString(); // 搜索时用的关键词
    String user_account = add_record_edittext02.getText().toString(); // 账号
    String user_password = add_record_edittext03.getText().toString(); // 账号的密码
    String user_remind = add_record_edittext04.getText().toString(); // 备注
    if (user_keyword.equals("") || user_account.equals("") || user_password.equals("")) {
      ToastUtil.showToast(this, "请填写完前三项!");
      return;
    }
    helper.add(user_keyword, user_account, user_password, user_remind);
    ToastUtil.showToast(this, "添加成功!");
  }
}
