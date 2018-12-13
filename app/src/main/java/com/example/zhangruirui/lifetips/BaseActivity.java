package com.example.zhangruirui.lifetips;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.zhangruirui.utils.ActivityCollector;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.tencent.mmkv.MMKV;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

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
    // 友盟统计 sdk 初始化
    // https://developer.umeng.com/docs/66632/detail/66889
    UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, null);
  }

  @Override
  protected void onResume() {

    super.onResume();
    MobclickAgent.onResume(this);
    MMKV mmkv = MMKV.defaultMMKV();
    final int value = mmkv.getInt("light_value", 180);
    SetActivity set = new SetActivity();
    set.changeAppBrightness(this, value);
  }

  @Override
  protected void onPause() {
    super.onPause();
    MobclickAgent.onPause(this);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    ActivityCollector.removeActivity(this);
  }

}
