package com.example.zhangruirui.lifetips.wechat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhangruirui.lifetips.R;

/**
 * 开门的动画效果，可保存供以后使用
 */
public class WhatsNewDoorActivity extends AppCompatActivity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_whats_new_door);

    ImageView left = findViewById(R.id.imageLeft);
    ImageView right = findViewById(R.id.imageRight);
    TextView text = findViewById(R.id.anim_text);

    AnimationSet anim = new AnimationSet(true);
    TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f,
        Animation.RELATIVE_TO_SELF, -1f, Animation.RELATIVE_TO_SELF, 0f, Animation
        .RELATIVE_TO_SELF, 0f); // 位移
    translateAnimation.setDuration(2000);
    anim.setStartOffset(800);
    anim.addAnimation(translateAnimation);
    anim.setFillAfter(true);
    left.startAnimation(anim);

    AnimationSet anim1 = new AnimationSet(true);
    TranslateAnimation translateAnimation1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f,
        Animation.RELATIVE_TO_SELF, +1f, Animation.RELATIVE_TO_SELF, 0f, Animation
        .RELATIVE_TO_SELF, 0f); // 位移
    translateAnimation1.setDuration(1500);
    anim1.addAnimation(translateAnimation1);
    anim1.setStartOffset(800);
    anim1.setFillAfter(true);
    right.startAnimation(anim1);

    AnimationSet anim2 = new AnimationSet(true);
    ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 3f, 1f, 3f, Animation.RELATIVE_TO_SELF,
        0.5f, Animation.RELATIVE_TO_SELF, 0.5f); // 缩放
    scaleAnimation.setDuration(1000);

    AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0.0001f); // 透明
    alphaAnimation.setDuration(1500);
    anim2.addAnimation(scaleAnimation);
    anim2.addAnimation(alphaAnimation);
    anim2.setFillAfter(true);
    text.startAnimation(anim2);

    new Handler().postDelayed(() -> {
      Intent intent = new Intent(WhatsNewDoorActivity.this, MainWeChatActivity.class);
      startActivity(intent);
      WhatsNewDoorActivity.this.finish();
    }, 2300);
  }
}
