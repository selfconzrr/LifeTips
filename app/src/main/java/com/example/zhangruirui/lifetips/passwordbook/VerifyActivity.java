package com.example.zhangruirui.lifetips.passwordbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.zhangruirui.lifetips.R;
import com.example.zhangruirui.utils.ToastUtil;

public class VerifyActivity extends AppCompatActivity {

  private LocusPassWordView mLocusPassWordView;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_verify);
    mLocusPassWordView = this.findViewById(R.id.mLocusPassWordView);
    mLocusPassWordView.setOnCompleteListener(mPassword -> {
      // 如果密码正确,则进入主页面。
      if (mLocusPassWordView.verifyPassword(mPassword)) {
        ToastUtil.showToast(VerifyActivity.this, "登陆成功!");
        Intent intent = new Intent(VerifyActivity.this,
            HomeActivity.class);
        startActivity(intent);
        finish();
      } else {
        ToastUtil.showToast(VerifyActivity.this, "密码输入错误,请重新输入!");
        mLocusPassWordView.markError();
      }
    });

  }

  @Override
  protected void onStart() {
    super.onStart();
    // 如果密码为空,则进入设置密码的界面
    View noSetPassword = this.findViewById(R.id.tvNoSetPassword);
    TextView toastTv = findViewById(R.id.login_toast);
    if (mLocusPassWordView.isPasswordEmpty()) {
      mLocusPassWordView.setVisibility(View.GONE);
      noSetPassword.setVisibility(View.VISIBLE);
      toastTv.setText("请先绘制手势密码");
      noSetPassword.setOnClickListener(v -> {
        Intent intent = new Intent(VerifyActivity.this,
            SetHandPasswordActivity.class);
        startActivity(intent);
        finish();
      });
    } else {
      toastTv.setText("请输入手势密码");
      mLocusPassWordView.setVisibility(View.VISIBLE);
      noSetPassword.setVisibility(View.GONE);
    }
  }

  @Override
  protected void onStop() {
    super.onStop();
  }
}
