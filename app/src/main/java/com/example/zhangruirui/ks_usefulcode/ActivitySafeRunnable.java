package com.example.zhangruirui.ks_usefulcode;

import android.app.Activity;
import android.support.annotation.MainThread;

import java.lang.ref.WeakReference;

public abstract class ActivitySafeRunnable<T extends Activity> implements Runnable {
  private final WeakReference<T> mActivityReference;

  ActivitySafeRunnable(T activity) {
    mActivityReference = new WeakReference<>(activity);
  }

  @MainThread
  @Override
  public void run() {
    Activity activity = mActivityReference.get();
    if (activity == null || activity.isFinishing()) {
      return;
    }

    try {
      safeRun();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @MainThread
  protected abstract void safeRun();
}
