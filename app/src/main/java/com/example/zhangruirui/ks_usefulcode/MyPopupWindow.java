package com.example.zhangruirui.ks_usefulcode;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

public class MyPopupWindow extends PopupWindow {
  /**
   * 默认的 tips 消失时间 5s
   */
  private static final long DEFAULT_TIMEOUT = 5000L;

  private View mView;
  private Activity mActivity;
  private String mShareTips;

  private boolean mShowTitleFollow;

  public MyPopupWindow(Activity activity, View view, String tips, boolean showTitleFollow) {
    this.mActivity = activity;
    this.mView = view;
    this.mShareTips = tips;
    this.mShowTitleFollow = showTitleFollow;
    initPopupWindow();

    Runnable delayDismissRunnable = new ActivitySafeRunnable<Activity>(mActivity) {
      @Override
      protected void safeRun() {
        dismiss();
      }
    };
    KSUtils.runOnUiThreadDelay(delayDismissRunnable, DEFAULT_TIMEOUT);
  }

  private void initPopupWindow() {
    setWidth(352); // in px
    setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

    FrameLayout contentView = new FrameLayout(mActivity);

    setContentView(contentView);
    setTouchable(true); // 是否可点击
    setOutsideTouchable(true); // 点击 tips 外部是否允许 dismiss
    setFocusable(false); // 是否获取焦点

    // popWindow 必须依附于某一个 view，而在 onCreate/onBind 中 view 还没有加载完毕，
    // 必须要等 activity 的生命周期函数全部执行完毕，你需要依附的 view 加载好后才可以执行
    // Exception：Unable to add window --token null is not valid; is your activity running

    mView.post(() -> {
      if (KSUtils.isAttachedToWindowManager(mActivity)) {
        if (mShowTitleFollow) {
          showAsDropDown(mView, mView.getWidth() / 2, 0);
        } else {
          showAsDropDown(mView, mView.getWidth() / 2 - getWidth() / 2, 0);
        }
      }
    });
  }
}
