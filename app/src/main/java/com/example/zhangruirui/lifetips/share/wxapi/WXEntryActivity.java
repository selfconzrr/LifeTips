package com.example.zhangruirui.lifetips.share.wxapi;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.example.zhangruirui.lifetips.MainActivity;
import com.example.zhangruirui.lifetips.demo_learning.launchmode.BasicActivity;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

/**
 * 微信客户端回调 activity 示例
 */
public class WXEntryActivity extends BasicActivity implements IWXAPIEventHandler {

  // 两个常量代表了微信返回的消息类型，是对登录的处理还是对分享的处理，登录会在后面介绍到
  private static final int RETURN_MSG_TYPE_LOGIN = 1;
  private static final int RETURN_MSG_TYPE_SHARE = 2;

  private static final String TAG = "WXEntryActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    MainActivity.api.handleIntent(getIntent(), this);
  }

  //微信发送消息给app，app接受并处理的回调函数
  @Override
  public void onReq(BaseReq baseReq) {

  }

  //app发送消息给微信，微信返回的消息回调函数,根据不同的返回码来判断操作是否成功
  @Override
  public void onResp(BaseResp resp) {
    switch (resp.errCode) {
      case BaseResp.ErrCode.ERR_AUTH_DENIED:
        Log.d("weixin", "拒绝");
        finish();
        break;
      case BaseResp.ErrCode.ERR_USER_CANCEL:
        Log.d("weixin", "失败");
        finish();
        break;
      case BaseResp.ErrCode.ERR_COMM:
        Log.d("weixin", "拒绝2");
        finish();
        break;
      case BaseResp.ErrCode.ERR_SENT_FAILED:
        Log.d("weixin", "拒绝3");
        finish();
        break;
      case BaseResp.ErrCode.ERR_UNSUPPORT:
        Log.d("weixin", "拒绝4");
        finish();
        break;
      case BaseResp.ErrCode.ERR_OK:
        switch (resp.getType()) {
          case RETURN_MSG_TYPE_SHARE:
            Log.d("weixin", "成功");
            finish();
            break;
          case RETURN_MSG_TYPE_LOGIN:
            finish();
            break;
        }
        break;
    }
  }
}
