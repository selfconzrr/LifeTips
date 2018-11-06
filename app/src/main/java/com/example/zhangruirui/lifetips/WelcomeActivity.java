package com.example.zhangruirui.lifetips;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WelcomeActivity extends AppCompatActivity {

  @BindView(R.id.welcome_pic)
  ImageView mWelcomePic;

  final int mAlpha = 255;

  private boolean mClicked = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    setContentView(R.layout.activity_welcome);
    ButterKnife.bind(this);
    mWelcomePic.setAlpha(mAlpha);
    new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          Thread.sleep(4000);
          if (!mClicked) {
            onClick();
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }).start();

  }

  @OnClick(R.id.welcome_pic)
  public void onClick() {
    // TODO: 2018/11/6  等将来添加完 LoginActivity 时改为 Login
    final Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
    mClicked = true;
    finish();
  }
}
