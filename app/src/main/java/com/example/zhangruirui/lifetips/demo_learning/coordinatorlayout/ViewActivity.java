package com.example.zhangruirui.lifetips.demo_learning.coordinatorlayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;

import com.example.zhangruirui.lifetips.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewActivity extends AppCompatActivity {

  @BindView(R.id.btn)
  Button mBtn;

  @BindView(R.id.myview)
  View mView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view);
    ButterKnife.bind(this);
    final int height = mBtn.getHeight();
    final int measureHeight = mBtn.getMeasuredHeight();
    Log.e("zhangrr", "onCreate() 一 called height = " + height + " measureHeight = " +
        measureHeight);

    // 获取高度方式一：不指定父布局宽高，手动调用 measure
    // 这种方法获取到的宽高并不准确，因为这种方式计算宽高是完全不考虑父布局的大小，
    // 并且手动调用了 measure 导致 measure 多执行了一次。
    // 这种方式的优点就是，都不需要将 View 添加到 ViewGroup 就可以提前知道 View 的宽高。
//    final int width1 = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//    final int height1 = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//    mBtn.measure(width1, height1);
//    final int height2 = mBtn.getHeight();
//    final int measureHeight2 = mBtn.getMeasuredHeight();
//    Log.e("zhangrr", "onCreate() 二 called height2 = " +height2 + " measureHeight2 = "+
// measureHeight2);

    // 获取高度方式二：增加组件绘制之前的监听
    // PreDraw 的调用时间是，当一个视图树将要绘制时
    // 调用这个回调函数，这就说明已经执行过 measure 和 layout，在 draw 之前调用，
    // 但注意该方法会多次调用，在每一次绘制之前都会调用。
    ViewTreeObserver vto = mBtn.getViewTreeObserver();
    vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
      @Override
      public boolean onPreDraw() {
        final int height = mBtn.getMeasuredHeight();
        final int measureHeight = mBtn.getMeasuredHeight();
        Log.e("zhangrr", "onCreate() 三 called height = " + height + " measureHeight = " +
            measureHeight);
        return true;
      }
    });

    // 获取高度方式三：增加整体布局监听
    // onGlobalLayout 的调用时间是，当在一个视图树中全局布局发生改变 或者 视图树中的某个视图的可视状态发生改变时
    // 调用这个回调函数。避免频繁调用，推荐这种方式获取宽高。
    vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
      @Override
      public void onGlobalLayout() {
        mBtn.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        final int height = mBtn.getHeight();
        final int measureHeight = mBtn.getMeasuredHeight();
        Log.e("zhangrr", "onCreate() 四 called height = " + height + " measureHeight = " +
            measureHeight);
      }
    });
  }

  @Override
  protected void onStart() {
    super.onStart();
    final int height = mBtn.getHeight();
    final int measureHeight = mBtn.getMeasuredHeight();
    Log.e("zhangrr", "onStart() 一 called height = " + height + " measureHeight = " + measureHeight);
    // 通过 View.post 将获取高度的逻辑，在 onLayout 执行完之后执行
    mBtn.post(new Runnable() {
      @Override
      public void run() {
        final int height = mBtn.getHeight();
        final int measureHeight = mBtn.getMeasuredHeight();
        Log.e("zhangrr", "onStart() 二 called height = " + height + " measureHeight = " +
            measureHeight);
      }
    });
  }

  @Override
  protected void onResume() {
    super.onResume();
    final int height = mBtn.getHeight();
    final int measureHeight = mBtn.getMeasuredHeight();
    Log.e("zhangrr", "onResume() 一 called height = " + height + " measureHeight = " +
        measureHeight);
  }

  /**
   * onWindowFocusChanged 是当 activity 可见时（获取到焦点）调用的，在此之前的函数中调用 View.getWidth/getHeight 得到的是0，
   * onWindowFocusChanged 函数中获取 View.getWidth/getHeight 是合适的。
   * 因为此时 View 已经绘制完成，也能获取到 View 的准确宽高了。需要注意的是这个方法会调用多次，当 hasFocus 为 true 时，才可进行相应的操作。
   */
  @Override
  public void onWindowFocusChanged(boolean hasFocus) {
    super.onWindowFocusChanged(hasFocus);
    if (hasFocus) {
      final int height = mBtn.getHeight();
      final int measuredHeight = mBtn.getMeasuredHeight();
      Log.e("zhangrr", "btn onWindowFocusChanged() called height = " + height
          + " measuredHeight = " + measuredHeight);
      Log.e("zhangrr", "myView onWindowFocusChanged() called height = " + mView.getHeight()
          + " measuredHeight = " + mView.getMeasuredHeight());
//      int defaultMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,100,
// getResources().getDisplayMetrics());
//      Log.e("zhangrr", "onWindowFocusChanged() called with: defaultMargin = [" + defaultMargin
// + "]");
    }
  }
}
