package com.example.zhangruirui.lifetips;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.WindowManager;

import com.example.zhangruirui.utils.ActivityCollector;

/**
 * 强制下线逻辑，通过广播监听
 */
public class ForceOfflineReceiver extends BroadcastReceiver {

  @Override
  public void onReceive(Context context, Intent intent) {

    // TODO: This method is called when the BroadcastReceiver is receiving an Intent broadcast.

    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
    dialogBuilder.setTitle("Warning");
    dialogBuilder.setMessage("you are forced to be offline. please try to login again.");
    dialogBuilder.setCancelable(false); // 将对话框设置为不可取消
    dialogBuilder.setPositiveButton("OK", (dialog, which) -> {
      ActivityCollector.finishAll(); // 销毁所有活动

      Intent intent1 = new Intent(context, LoginActivity.class);
      intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      // 由于是在广播接收器里启动活动的，因此要给 intent 加入上面的标志
      // 区别于默认优先启动在 activity 栈中已经存在的 activity（如果之前启动过，并还没有被 destroy 的话）
      // 而是无论是否存在，都重新启动新的 activity
      context.startActivity(intent1); // 重新启动 LoginActivity
    });

    AlertDialog alertDialog = dialogBuilder.create();
    // 需要设置 AlertDialog 的类型，保证在广播接收器中可以正常弹出
    alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
    // 需要把对话框的类型设为 TYPE_SYSTEM_ALERT，否则无法在广播接收器里弹出
    alertDialog.show();
  }
}
