package com.example.zhangruirui.ks_usefulcode;

import android.support.annotation.MainThread;

import org.greenrobot.eventbus.EventBus;

public class EventBusDemo {

  /**
   * 1、EventBus 的注册与解注册逻辑封装
   * <p>
   * 一般：onCreate 时 toggleEvent(true), onDestroy 时 toggleEvent(false)
   */

  @MainThread
  protected void toggleEvent(boolean enabled) {
    final boolean isRegistered = EventBus.getDefault().isRegistered(this);
    if (enabled && !isRegistered) {
      EventBus.getDefault().register(this);
    }
    if (!enabled && isRegistered) {
      EventBus.getDefault().unregister(this);
    }
  }
}
