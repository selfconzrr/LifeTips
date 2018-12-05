package com.example.zhangruirui.lifetips;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    final int DELAY_TIME = 1000; // 1s之后执行相应的逻辑
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        // 启动登录界面
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        finish();
      }
    }, DELAY_TIME);
  }

  // 避免多次启动 启动界面
  @Override
  public void onBackPressed() {
    // super.onBackPressed();
    Intent intent = new Intent(Intent.ACTION_MAIN);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.addCategory(Intent.CATEGORY_HOME);
    startActivity(intent);
  }
}
