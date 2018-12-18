package com.example.zhangruirui.lifetips.demo_learning;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.zhangruirui.lifetips.R;
import com.example.zhangruirui.lifetips.demo_learning.launchmode.BasicActivity;

import java.lang.ref.SoftReference;

import butterknife.ButterKnife;
/*
 * Handler操作，异步处理，主线程不做耗时操作，子线程不更新UI
 *
 * 一：Handler的消息机制
 *
 * Handler主要用于一个耗时的操作，异步任务，如网络访问，这些操作需要放到另外一个线程里去做。如果UI线程5秒钟得不到响应的话，就会出现"ANR"错误。
 *
 * 主要涉及四个类Handler、Message、MessageQueue、Looper：
 *
 * 新建Handler，通过sendMessage或者post发送消息，Handler调用sendMessageAtTime将Message交给MessageQueue；
 *
 * MessageQueue.enqueueMessage方法将Message以链表的形式放入队列中；
 *
 * Looper的loop方法循环调用MessageQueue.next()取出消息，并且调用Handler的dispatchMessage来处理消息；
 *
 * 在dispatchMessage中，分别判断msg
 * .callback、mCallback也就是post方法或者构造方法传入的不为空就执行他们的回调，如果都为空就执行我们最常用重写的handleMessage。
 *
 * 二：handler的内存泄露（重点）
 *
 * 当使用内部类（包括匿名类）来创建Handler的时候，Handler对象会隐式地持有Activity的引用。
 *
 * 而Handler通常会伴随着一个耗时的后台线程一起出现，这个后台线程在任务执行完毕后发送消息去更新UI。然而，如果用户在网络请求过程中关闭了Activity，
 * 正常情况下，Activity不再被使用，它就有可能在GC检查时被回收掉，但由于这时线程尚未执行完，而该线程持有Handler的引用（不然它怎么发消息给Handler？），
 * 这个Handler又持有Activity的引用，就导致该Activity无法被回收（即内存泄露），直到网络请求结束。
 *
 * 另外，如果执行了Handler的postDelayed()方法，那么在设定的delay到达之前，会有一条MessageQueue -> Message -> Handler ->
 * Activity的链，导致你的Activity被持有引用而无法被回收。
 */

/**
 * HandlerDemo：注意避免内存泄漏，在 Activity 或者 fragment 生命周期结束时，移除掉
 * MessageQueue 中尚未处理的 message 及 callback；
 * 借助软引用存储 Handler 对 Activity 的引用。
 */
public class HandlerDemoActivity extends BasicActivity {

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
