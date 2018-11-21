package com.example.zhangruirui.lifetips;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.zhangruirui.utils.ToastUtil;

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
public class LoginActivity extends BaseActivity {

  @BindView(R.id.account)
  EditText mAccount;

  @BindView(R.id.password)
  EditText mPassword;

  @BindView(R.id.remember_password)
  CheckBox mIsRemember;

  @BindView(R.id.login)
  Button mLogin;

  private SharedPreferences mSharedPreferences;

  private SharedPreferences.Editor mEditor;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    ButterKnife.bind(this);
    mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    boolean isRemember = mSharedPreferences.getBoolean("remember_password", false);
    if (isRemember) {
      String account = mSharedPreferences.getString("account", "");
      String password = mSharedPreferences.getString("password", "");
      mAccount.setText(account);
      mPassword.setText(password);
      mIsRemember.setChecked(true);
    }
  }

  @OnClick(R.id.login)
  public void onClick() {
    String account = mAccount.getText().toString();
    String password = mPassword.getText().toString();
    // TODO: 2018/11/21 如何实现真正的用户名与密码配对
    if (account.equals("admin") && password.equals("123456")) {
      if (mIsRemember.isChecked()) {
        mEditor = mSharedPreferences.edit();
        mEditor.putString("account", account);
        mEditor.putString("password", password);
        mEditor.putBoolean("remember_password", true);
        mEditor.apply();
      } else {
        mEditor.clear();
      }
      final Intent intent = new Intent(LoginActivity.this, MainActivity.class);
      startActivity(intent);
      finish();
    } else {
      ToastUtil.showToast(this, getResources().getString(R.string.login_toast));
    }
  }

}
