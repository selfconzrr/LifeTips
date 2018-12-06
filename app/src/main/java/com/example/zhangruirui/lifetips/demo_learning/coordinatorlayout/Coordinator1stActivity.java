package com.example.zhangruirui.lifetips.demo_learning.coordinatorlayout;

import android.content.ComponentCallbacks2;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.example.zhangruirui.lifetips.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zhangruirui
 * email：1138517609@qq.com
 * GitHub：https://github.com/selfconzrr
 * Blog：http://blog.csdn.net/u011489043
 * Date：12/06/18
 */
public class Coordinator1stActivity extends AppCompatActivity {

  @BindView(R.id.fab)
  FloatingActionButton mFloatingActionButton;
  @BindView(R.id.PopUp)
  Button mPopBtn;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_coordinator1st);
    ButterKnife.bind(this);
  }

  @OnClick(R.id.fab)
  public void onClick() {
    Snackbar.make(mFloatingActionButton, "Hello ZRR", Snackbar.LENGTH_LONG).setAction("ActionI",
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Intent intent = new Intent(Coordinator1stActivity.this, Coordinator2ndActivity.class);
            startActivity(intent);
          }
        }).show();
  }

  @OnClick(R.id.PopUp)
  public void onClick2() {
    PopupWindow popupWindow = new PopupWindow(this);
    popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
    popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
    popupWindow.setContentView(LayoutInflater.from(this).inflate(R.layout.popupwindow, null));
    popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
    popupWindow.setOutsideTouchable(false);
    popupWindow.setFocusable(true);
    popupWindow.showAsDropDown(mPopBtn);
  }

  @Override
  protected void onStart() {
    super.onStart();
  }

  @Override
  protected void onResume() {
    super.onResume();
  }

  /**
   * onWindowFocusChanged 是当 activity 可见时（获取到焦点）的调用的，在此之前的函数中调用 View.getWidth/getHeight 得到的是0，
   * onWindowFocusChanged 函数中获取 View.getWidth/getHeight 是合适的。
   * 因为此时 View 已经绘制完成，也能获取到 View 的准确宽高了。需要注意的是这个方法会调用多次，当 hasFocus 为 true 时，才可进行相应的操作。
   */
  @Override
  public void onWindowFocusChanged(boolean hasFocus) {
    super.onWindowFocusChanged(hasFocus);
  }

  /**
   * Release memory when the UI becomes hidden or when system resources become low.
   * <p>
   * The onTrimMemory() callback was added in Android 4.0 (API level 14).
   * For earlier versions, you can use the onLowMemory(), which
   * is roughly equivalent to the TRIM_MEMORY_COMPLETE event.
   * </p>
   *
   * @param level the memory-related event that was raised.
   */
  @Override
  public void onTrimMemory(int level) {
    Log.e("zhangrr", "onTrimMemory() called with: level = [" + level + "]");
    super.onTrimMemory(level);
    switch (level) {
      case ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN:
        /*
        Release any UI objects that currently hold memory.
        The user interface has moved to the background.
        */
        break;

      case ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE:
      case ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW:
      case ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL:
        /*
        Release any memory that your app doesn't need to run.
        The device is running low on memory while the app is running.
        The event raised indicates the severity of the memory-related event.
        If the event is TRIM_MEMORY_RUNNING_CRITICAL, then the system will
        begin killing background processes.
        */
        break;

      case ComponentCallbacks2.TRIM_MEMORY_BACKGROUND:
      case ComponentCallbacks2.TRIM_MEMORY_COMPLETE:
      case ComponentCallbacks2.TRIM_MEMORY_MODERATE:
        /*
        Release as much memory as the process can.
        The app is on the LRU list and the system is running low on memory.
        The event raised indicates where the app sits within the LRU list.
        If the event is TRIM_MEMORY_COMPLETE, the process will be one of
        the first to be terminated.
        */
        break;

      default:
        /*
        Release any non-critical data structures.
        The app received an unrecognized memory level value
        from the system. Treat this as a generic low-memory message.
        */
        break;
    }
  }

  @Override
  public void onLowMemory() {
    super.onLowMemory();
    Log.e("zhangrr", "onLowMemory() called");
  }

  @Override
  protected void onStop() {
    super.onStop();
    Log.e("zhangrr", "onStop() called");
  }
}
