package com.example.zhangruirui.ks_usefulcode;

import android.support.annotation.NonNull;
import android.view.View;

public class InputLocationUtil {
  private final View mParentLayout;

  public InputLocationUtil(@NonNull View parentLayout) {
    this.mParentLayout = parentLayout;
  }

  // targetView is mConfirmButton
  public void assist(@NonNull final View targetView) {
    mParentLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {

      @Override
      public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft,
                                 int oldTop, int oldRight, int oldBottom) {
        if (bottom == oldBottom) {
          return;
        }

        postAndCheckScrollResult(targetView);
      }
    });
  }

  void postAndCheckScrollResult(final View targetView) {
    mParentLayout.post(new Runnable() {
      @Override
      public void run() {

        final int targetScrollY = KSUtils.getBottomRelativeAncestor(targetView, mParentLayout)
            - mParentLayout.getMeasuredHeight();
        mParentLayout.scrollTo(0, targetScrollY);

        mParentLayout.postDelayed(new Runnable() {
          @Override
          public void run() {
            final int targetScrollY = KSUtils.getBottomRelativeAncestor(targetView, mParentLayout)
                - mParentLayout.getMeasuredHeight();
            if (targetScrollY != mParentLayout.getScrollY()) {
              postAndCheckScrollResult(targetView);
            }
          }
        }, 100);
      }
    });
  }
}
