package com.example.zhangruirui.lifetips.wechat;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;


import com.example.zhangruirui.lifetips.R;

import butterknife.BindView;
import butterknife.ButterKnife;

// TODO: 2019/2/1 微信登录模块
public class LoginWeChatActivity extends AppCompatActivity {

  @BindView(R.id.login_user_edit)
  EditText mUser;

  @BindView(R.id.login_passwd_edit)
  EditText mPassWord;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login_we_chat);
    ButterKnife.bind(this);
  }

  public void login_back(View view) {
    this.finish();
  }

  public void forget_password(View view) {
    Uri uri = Uri.parse("http://3g.qq.com");
    final Intent intent = new Intent(Intent.ACTION_VIEW, uri);
    startActivity(intent);
    // Intent intent = new Intent();
    // intent.setClass(Login.this, WhatsNew.class);
    // startActivity(intent);
  }

  public void login_main_wechat(View view) {
    if ("admin".equals(mUser.getText().toString()) && "123456".equals(mPassWord.getText().toString())) {
      final Intent intent = new Intent();
      intent.setClass(LoginWeChatActivity.this, LoadingActivity.class);
      startActivity(intent);
    } else if ("".equals(mUser.getText().toString()) || "".equals(mPassWord.getText().toString())) {
      new AlertDialog.Builder(LoginWeChatActivity.this)
          .setTitle("登录错误")
          .setMessage("微信帐号或者密码不能为空，\n请输入后再登录！")
          .create().show();
    } else {
      new AlertDialog.Builder(LoginWeChatActivity.this)
          .setTitle("登录失败")
          .setMessage("微信帐号或者密码不正确，\n请检查后重新输入！")
          .create().show();
    }
  }
}
