package com.example.zhangruirui.lifetips.passwordbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.zhangruirui.lifetips.R;
import com.example.zhangruirui.lifetips.passwordbook.util.StringUtil;
import com.example.zhangruirui.utils.ToastUtil;

public class SetHandPasswordActivity extends AppCompatActivity {

  private LocusPassWordView mLocusPassWordView;
  private String password;
  private boolean mNeedVerify = true;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_set_hand_password);
    mLocusPassWordView = this.findViewById(R.id.mLocusPassWordView);
    mLocusPassWordView.setOnCompleteListener(mPassword -> {
      password = mPassword;
      if (mNeedVerify) {
        if (mLocusPassWordView.verifyPassword(mPassword)) {
          ToastUtil.showToast(SetHandPasswordActivity.this, "密码输入正确,请输入新密码!");
          mLocusPassWordView.clearPassword();
          mNeedVerify = false;
        } else {
          ToastUtil.showToast(SetHandPasswordActivity.this, "错误的密码,请重新输入!");
          mLocusPassWordView.clearPassword();
          password = "";
        }
      }
    });

    View.OnClickListener mOnClickListener = v -> {
      switch (v.getId()) {
        case R.id.tvSave:
          if (StringUtil.isNotEmpty(password)) {
            mLocusPassWordView.resetPassWord(password);
            mLocusPassWordView.clearPassword();

            ToastUtil.showToast(SetHandPasswordActivity.this, "密码修改成功,请记住密码!");

            startActivity(new Intent(SetHandPasswordActivity.this,
                VerifyActivity.class));
            finish();
          } else {
            mLocusPassWordView.clearPassword();
            ToastUtil.showToast(SetHandPasswordActivity.this, "密码不能为空,请输入密码!");
          }
          break;
        case R.id.tvReset:
          mLocusPassWordView.clearPassword();
          break;
      }
    };
    Button buttonSave = this.findViewById(R.id.tvSave);
    buttonSave.setOnClickListener(mOnClickListener);

    Button tvReset = this.findViewById(R.id.tvReset);
    tvReset.setOnClickListener(mOnClickListener);
    // 如果密码为空,直接输入密码
    if (mLocusPassWordView.isPasswordEmpty()) {
      this.mNeedVerify = false;
      ToastUtil.showToast(SetHandPasswordActivity.this, "请输入密码!");
    }
  }

  @Override
  protected void onStart() {
    super.onStart();
  }

  @Override
  protected void onStop() {
    super.onStop();
  }
}
