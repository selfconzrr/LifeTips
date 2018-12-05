package com.example.zhangruirui.lifetips.demo_learning.launchmode;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * @author zhangruirui
 * email：1138517609@qq.com
 * GitHub：https://github.com/selfconzrr
 * Blog：http://blog.csdn.net/u011489043
 * Date：12/05/18
 * <p>
 * 测试 Activity 的启动模式
 * <p>
 * 为了打印方便，定义一个基础 Activity，在其 onCreate 方法和 onNewIntent 方法中打印出当前 Activity 的日志信息，
 * 主要包括所属的 task，当前类的 hashcode，以及 taskAffinity 的值。
 * 之后我们进行测试的 Activity 都直接继承该 Activity
 */
public class BasicActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.e("zhangrr", "onCreate：" + getClass().getSimpleName() + " TaskId: " + getTaskId() + " " +
        "hasCode:" + this.hashCode());
    dumpTaskAffinity();
  }

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    Log.e("zhangrr", "onNewIntent：" + getClass().getSimpleName() + " TaskId: " + getTaskId() + " " +
        "hasCode:" + this.hashCode());
  }

  protected void dumpTaskAffinity() {
    try {
      ActivityInfo info = this.getPackageManager()
          .getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
      Log.e("zhangrr", "taskAffinity:" + info.taskAffinity);
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
  }
}
