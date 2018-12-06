package com.example.zhangruirui.lifetips.demo_learning;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.zhangruirui.lifetips.R;

import java.lang.ref.SoftReference;

import butterknife.ButterKnife;
/**
 * HandlerDemo：注意避免内存泄漏，在 Activity 或者 fragment 生命周期结束时，移除掉
 * MessageQueue 中尚未处理的 message 及 callback；
 * 借助软引用存储 Handler 对 Activity 的引用。
 */
public class HandlerDemoActivity extends AppCompatActivity {

  private NewHandler mHandler = new NewHandler(this);

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_handler_demo);
    ButterKnife.bind(this);
    // 传统的匿名内部类写法
//    Handler handler = new Handler(){
//      @Override
//      public void handleMessage(Message msg) {
//        super.handleMessage(msg);
//        // DO SOMETHING
//      }
//    };
  }

  private static class NewHandler extends Handler {
    private SoftReference<HandlerDemoActivity> activityRef;

    private NewHandler(HandlerDemoActivity handlerDemo) {
      activityRef = new SoftReference<>(handlerDemo);
    }

    @Override
    public void handleMessage(Message msg) {
      if (activityRef != null && activityRef.get() != null) {
        Log.e("zhangrr", "handleMessage() called with: msg = [" + activityRef.get() + "]");
      }
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (mHandler != null) {
      mHandler.removeCallbacksAndMessages(null);
    }
  }
}
