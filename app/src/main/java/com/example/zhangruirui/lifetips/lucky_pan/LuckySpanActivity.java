package com.example.zhangruirui.lifetips.lucky_pan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.zhangruirui.lifetips.R;
import com.example.zhangruirui.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LuckySpanActivity extends AppCompatActivity {

  @BindView(R.id.lucky_span)
  LuckySpanView mLuckySpanView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_lucky_span);
    ButterKnife.bind(this);

    mLuckySpanView.setLuckAnimationEndListener(new LuckySpanView.OnLuckAnimationEndListener() {
      @Override
      public void onLuckAnimationEnd(int pos, String msg) {
        ToastUtil.showToast(LuckySpanActivity.this, "恭喜您抽中了： " + msg);
      }
    });
  }
}
