package com.example.zhangruirui.lifetips;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zhangruirui
 * email：1138517609@qq.com
 * GitHub：https://github.com/selfconzrr
 * Blog：http://blog.csdn.net/u011489043
 * Date：11/06/18
 */
public class WelcomeActivity extends BaseActivity {

  @BindView(R.id.welcome_pic)
  ImageView mWelcomePic;

  final int mAlpha = 255;

  private boolean mClicked = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager
        .LayoutParams.FLAG_KEEP_SCREEN_ON);
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
    final Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
    mClicked = true;
    finish();
  }
}
