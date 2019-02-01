package com.example.zhangruirui.lifetips.wechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.zhangruirui.lifetips.R;

public class WelcomeWeChatActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // The easiest way to transition back to your normal theme in your launcher (main) activity
    // is to call setTheme ()
    // Make sure this is before calling super.onCreate
    setTheme(R.style.AppTheme);

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_welcome_we_chat);
  }

  public void welcome_login(View v) {

    final Intent login_intent = new Intent(WelcomeWeChatActivity.this, LoginWeChatActivity.class);
    startActivity(login_intent);
  }

  public void welcome_register(View view) {
    final Intent register_intent = new Intent(WelcomeWeChatActivity.this, MainWeChatActivity.class);
    startActivity(register_intent);
  }
}
