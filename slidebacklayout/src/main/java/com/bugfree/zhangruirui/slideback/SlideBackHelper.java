package com.bugfree.zhangruirui.slideback;


import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SlideBackHelper {

  public static SlideBackLayout attach(Activity activity, @LayoutRes int layoutId) {
    return attach(activity, null, layoutId);
  }

  public static SlideBackLayout attach(Activity activity, SlideBackLayout
      .OnSwipeBackListener fun, @LayoutRes int layoutId) {
    SlideBackLayout mSwipeBackLayout = (SlideBackLayout) LayoutInflater.from
        (activity).inflate(layoutId, null);
    if (fun == null) {
      mSwipeBackLayout.setSwipeBackListener(activity::finish);
    } else {
      mSwipeBackLayout.setSwipeBackListener(fun);
    }

    if (activity == null || activity.getWindow() == null || !(activity.getWindow().getDecorView()
        instanceof ViewGroup)) {
      return mSwipeBackLayout;
    }

    ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
    if (decorView != null) {
      if (decorView.getChildCount() > 0) {
        View child = decorView.getChildAt(0);
        decorView.removeView(child);
        mSwipeBackLayout.addView(child);
      }

      decorView.addView(mSwipeBackLayout);
    }
    return mSwipeBackLayout;
  }
}
