package com.example.zhangruirui.lifetips.demo_learning.jscommunication;

import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.bugfree.zhangruirui.slideback.SlideBackHelper;
import com.bugfree.zhangruirui.slideback.SlideBackLayout;
import com.example.zhangruirui.lifetips.R;
import com.example.zhangruirui.lifetips.demo_learning.launchmode.BasicActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Android 和 js 交互的简单 demo
 */
public class JSActivity extends BasicActivity {

  @BindView(R.id.js)
  WebView mWebView;

  private Handler mHandler = new Handler();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_js);
    ButterKnife.bind(this);
    WebSettings webSettings = mWebView.getSettings();
    //webSettings.setSavePassword(false);// 是否记住密码
    //webSettings.setSaveFormData(false);// 是否保存表单数据
    //webSettings.setSupportZoom(false);// 是否支持缩放
    webSettings.setJavaScriptEnabled(true);// 支持js
    webSettings.setPluginState(WebSettings.PluginState.ON);//支持插件
    webSettings.setAllowFileAccess(true);
    webSettings.setDefaultTextEncodingName("GBK");
    mWebView.setBackgroundColor(0);

    /**
     * webView 设置一个和 js 交互的接口（注意这里并不是 java 中接口的含义），
     * 这个接口其实是一个一般的类，同时为这个接口取一个别名 demo
     * 上面的代码执行之后在 html 的 js 中就能通过别名（这里是“demo”）来调用 DemoJavaScriptInterface 类中的任何方法
     */
    mWebView.addJavascriptInterface(new DemoJavaScriptInterface(), "demo");
    mWebView.loadUrl("file:///android_asset/demo1.html"); // 注意是 3 个 /// 转义

    SlideBackLayout mirrorSwipeBackLayout = SlideBackHelper.attach(this, R.layout
        .swipe_back);
    mirrorSwipeBackLayout.setSwipeBackListener(this::finish);
  }

  /**
   * 避免 WebView 导致的内存泄漏
   */
  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (mWebView != null) {
      mWebView.setWebChromeClient(null);
      mWebView.setWebViewClient(null);
      mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
      mWebView.clearHistory();
      ((ViewGroup) mWebView.getParent()).removeView(mWebView);
      mWebView.destroy();
      mWebView = null;
    }
  }

  final class DemoJavaScriptInterface {
    DemoJavaScriptInterface() {
    }

    /**
     * 如果你的开发版本比较高，即 android:targetSdkVersion 数值
     * 大于17，出于安全考虑，需要在被调用的函数前加上 @JavascriptInterface 注解。
     */
    @JavascriptInterface
    public void clickOnAndroid() {
      // 不要在UI线程之外访问 Android UI toolkit

      mHandler.post(() -> {
        // js 调用 Android
        Toast.makeText(JSActivity.this, "哈哈", Toast.LENGTH_SHORT)
            .show();
        // Android 调用 js 里的方法 wave()
        mWebView.loadUrl("javascript:wave()");
      });
    }
  }
}
