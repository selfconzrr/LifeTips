package com.example.zhangruirui.lifetips.wechat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.zhangruirui.lifetips.R;
import com.example.zhangruirui.utils.ToastUtil;

// TODO: 2019/2/19 增加登录成功之后，向用户展示 图片轮播
public class LoadingActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_loading);

    new Handler().postDelayed(() -> {
      final Intent intent = new Intent(LoadingActivity.this, WhatsNewDoorActivity.class);
      startActivity(intent);
      LoadingActivity.this.finish();
      ToastUtil.showToast(LoadingActivity.this, "登录成功");
    }, 200);
  }
}
