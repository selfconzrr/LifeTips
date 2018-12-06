package com.example.zhangruirui.lifetips.demo_learning.coordinatorlayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.zhangruirui.lifetips.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * EditText，有一个叫hint的属性，它可以提示用户此处应该输入什么内容，然而当用户输入真实内容之后，hint的提示内容就消失了，
 * 有时候用户的体验效果是十分不好的，TextInputLayout的出现解决了这个问题。它会把hint内容填充到文本框上方
 * 使用：我们只需在EditText外面再嵌套一个TextInputLayout就行了
 * <p>
 * 如果不使用CoordinatorLayout， FAB 会被 Snackbar 遮挡。
 */
public class Coordinator2ndActivity extends AppCompatActivity {

  @BindView(R.id.username)
  EditText mUserName;
  @BindView(R.id.password)
  EditText mPassWord;
  @BindView(R.id.rawEditText)
  EditText mRawEditText;

  @BindView(R.id.fab)
  FloatingActionButton mFloatingActionButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_coordinator2nd);
    ButterKnife.bind(this);
  }

  @OnClick(R.id.fab)
  public void onClick() {
    Snackbar.make(mFloatingActionButton, "Hello ZRR", Snackbar.LENGTH_LONG).setAction
        ("ActionII", new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(Coordinator2ndActivity.this, Coordinator3rdActivity.class);
        startActivity(intent);
      }
    }).show();
  }
}
