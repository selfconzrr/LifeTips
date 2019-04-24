package com.example.zhangruirui.lifetips.vitas_log.model;

import android.util.Log;

// 用于解析服务器返回的数据
// {"mStatus":1,"mContent":{"mFrom":"en-EU","mTo":"zh-CN","mOut":"\u793a\u4f8b","mVendor":"ciba",
// "err_no":0}}

public class GetTranslation {
  private static final String TAG = "GetTranslation:zrr";

  private int mStatus;
  private Content mContent;

  private static class Content {
    private String mFrom;
    private String mTo;
    private String mVendor;
    private String mOut;
    private String mErrNo;
  }

  public void show() {
    Log.d(TAG, "show:mStatus " + mStatus);
    Log.d(TAG, "show:mFrom" + mContent.mFrom);
    Log.d(TAG, "show:mTo " + mContent.mTo);
    Log.d(TAG, "show:mVendor " + mContent.mVendor);
    Log.d(TAG, "show:mOut " + mContent.mOut);
    Log.d(TAG, "show:mErrNo " + mContent.mErrNo);
  }
}