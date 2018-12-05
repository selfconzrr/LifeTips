package com.example.zhangruirui.lifetips;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.zhangruirui.utils.ActivityCollector;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * @author zhangruirui
 * email：1138517609@qq.com
 * GitHub：https://github.com/selfconzrr
 * Blog：http://blog.csdn.net/u011489043
 * Date：11/19/18
 */
public class BaseActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d("BaseActivity", getClass().getSimpleName());
    ActivityCollector.addActivity(this);
    Fresco.initialize(this);
  }

  @Override
  protected void onResume() {

    super.onResume();
    final SharedPreferences pref = getSharedPreferences("light", MODE_PRIVATE);
    final int value = pref.getInt("light_value", 180);
    SetActivity set = new SetActivity();
    set.changeAppBrightness(this, value);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    ActivityCollector.removeActivity(this);
  }

}
