package com.example.zhangruirui.lifetips;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.zhangruirui.utils.ActivityCollector;
import com.example.zhangruirui.utils.MMKVManager;
import com.example.zhangruirui.utils.ShakeUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
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

  private ShakeUtils mShakeUtil;

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
    final int value = MMKVManager.getInstance().getLightValue();
    SetActivity set = new SetActivity();
    set.changeAppBrightness(this, value);
  }

  @Override
  protected void onStart() {
    super.onStart();
    if (mShakeUtil == null) {
      createShakeDetectorIfNeeded();
    }

    if (mShakeUtil != null) {
      mShakeUtil.register();
    }
  }

  @Override
  protected void onPause() {
    super.onPause();
    MobclickAgent.onPause(this);
    /**
     * 弹出 debug 调试界面，即注销监听，防止多次吊起
     * 如果吊起的是 Activity，解注册需要放在 onStop() 中，如果是 dialog，放在 onPause()
     */
    if (mShakeUtil != null) {
      mShakeUtil.unregister();
    }
  }

  @Override
  protected void onStop() {
    super.onStop();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    ActivityCollector.removeActivity(this);
  }

  private void createShakeDetectorIfNeeded() {
    if (BuildConfig.DEBUG) {
      mShakeUtil = new ShakeUtils(this, new ShakeUtils.ShakeListener() {
        @Override
        public void onShake() {
          // here add what you want to do
          Log.e("zhangrr", "onShake() called");
        }
      });
    }
  }
}
