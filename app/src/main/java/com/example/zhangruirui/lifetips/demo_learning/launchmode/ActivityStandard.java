package com.example.zhangruirui.lifetips.demo_learning.launchmode;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.zhangruirui.lifetips.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Standard 是默认的启动模式，即标准模式，在不指定启动模式的前提下，系统默认使用该模式启动 Activity，
 * 每次启动一个 Activity 都会重新创建一个新的实例，不管这个实例存不存在。
 * 这种模式下，谁启动了该模式的 Activity，该 Activity 就属于启动它的 Activity 任务栈中。
 */
public class ActivityStandard extends BasicActivity {

  @BindView(R.id.standard_btn)
  Button mButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_standard);
    ButterKnife.bind(this);
  }

  /**
   * 每一个 Activity 所属的任务栈的 id 都是 5067，这也验证了谁启动了该模式的 Activity，该 Activity 就属于启动它的 Activity 的任务栈中这句话
   * 每一个 Activity 的 hashcode 都是不一样的，说明他们是不同的实例，即“每次启动一个 Activity 都会重新创建一个新的实例”
   */
  @OnClick(R.id.standard_btn)
  public void onClick() {
    Intent intent = new Intent(ActivityStandard.this, ActivityStandard.class);
    startActivity(intent);
  }
}
