package com.example.zhangruirui.lifetips;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.zhangruirui.lifetips.bmi.BMIActivity;
import com.example.zhangruirui.lifetips.compass.CompassActivity;
import com.example.zhangruirui.lifetips.demo_learning.MenuDemoActivity;
import com.example.zhangruirui.lifetips.demo_learning.coordinatorlayout.Coordinator1stActivity;
import com.example.zhangruirui.lifetips.demo_learning.coordinatorlayout.ViewActivity;
import com.example.zhangruirui.lifetips.demo_learning.dialog.DialogShowHelper;
import com.example.zhangruirui.lifetips.demo_learning.jscommunication.JSActivity;
import com.example.zhangruirui.lifetips.demo_learning.rxjava.RxActivity;
import com.example.zhangruirui.lifetips.leetcode.activity.LeetcodeActivity;
import com.example.zhangruirui.lifetips.music.MusicActivity;
import com.example.zhangruirui.lifetips.notebook.activity.NotebookActivity;
import com.example.zhangruirui.lifetips.notes.TimeDiaryActivity;
import com.example.zhangruirui.lifetips.remind.RemindActivity;
import com.example.zhangruirui.utils.ActivityCollector;
import com.example.zhangruirui.utils.ToastUtil;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.mmkv.MMKV;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zhangruirui
 * email：1138517609@qq.com
 * GitHub：https://github.com/selfconzrr
 * Blog：http://blog.csdn.net/u011489043
 * Date：11/05/18
 */
public class MainActivity extends BaseActivity {

  // 注意通过 AS 下载程序和通过 apk 安装程序，生成的签名是不一样的，申请 app_id 需要注意
  private static final String APP_ID = "wx59b5f3646e7f0fde"; //申请的 app_id
  public static IWXAPI api;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // The easiest way to transition back to your normal theme in your launcher (main) activity
    // is to call setTheme ()
    // Make sure this is before calling super.onCreate
    setTheme(R.style.AppTheme);

    super.onCreate(savedInstanceState);
    // TODO: 2018/12/6 将 SharedPreference 替换为 MMKV
    // 参考链接：https://github.com/selfconzrr/MMKV/blob/master/readme_cn.md
    MMKV.initialize(this);

    setContentView(R.layout.activity_main);
    registerToWx();
    ButterKnife.bind(this);
  }

  // 避免多次启动 Splash Screen
  // TODO: 2018/12/6
  @Override
  public void onBackPressed() {
//     super.onBackPressed();
    Intent intent = new Intent(Intent.ACTION_MAIN);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.addCategory(Intent.CATEGORY_HOME);
    startActivity(intent);
  }

  @OnClick(R.id.quit)
  public void onClickQuit() {
    doExit();
  }

  @OnClick(R.id.light_intensity)
  public void onClickLight() {
    final Intent intent = new Intent(MainActivity.this, LightIntensityActivity.class);
    startActivity(intent);
  }

  @OnClick(R.id.getTime)
  public void onClickTime() {
    final Intent intent = new Intent(MainActivity.this, NetworkTimeActivity.class);
    startActivity(intent);
  }

  @OnClick(R.id.sms)
  public void onClickSms() {
    final Intent intent = new Intent(MainActivity.this, SMSActivity.class);
    startActivity(intent);
  }

  @OnClick(R.id.set)
  public void onClickSet() {
    final Intent intent = new Intent(MainActivity.this, SetActivity.class);
    startActivity(intent);
  }

  @OnClick(R.id.calculate)
  public void onClickCalculate() {
    final Intent intent = new Intent(MainActivity.this, CalculatorActivity.class);
    startActivity(intent);
  }

  @OnClick(R.id.BMI)
  public void onClickBMI() {
    final Intent intent = new Intent(MainActivity.this, BMIActivity.class);
    startActivity(intent);
  }

  @OnClick(R.id.compass)
  public void onClickCompass() {
    final Intent intent = new Intent(MainActivity.this, CompassActivity.class);
    startActivity(intent);
  }

  @OnClick(R.id.music_player)
  public void onClickMusic() {
    final Intent intent = new Intent(MainActivity.this, MusicActivity.class);
    startActivity(intent);
  }

  @OnClick(R.id.setRemind)
  public void onClickRemind() {
    final Intent intent = new Intent(MainActivity.this, RemindActivity.class);
    startActivity(intent);
  }

  @OnClick(R.id.diary)
  public void onClickDiary() {
    final Intent intent = new Intent(MainActivity.this, TimeDiaryActivity.class);
    startActivity(intent);
  }

  @OnClick({R.id.noteBook})
  public void onClickNoteBook() {
    final Intent intent = new Intent(MainActivity.this, NotebookActivity.class);
    startActivity(intent);
  }

  @OnClick({R.id.harvest_offer})
  public void onClickOffer() {
    final Intent intent = new Intent(MainActivity.this, LeetcodeActivity.class);
    startActivity(intent);
  }

  @OnClick({R.id.rx})
  public void onClickRx() {
    final Intent intent = new Intent(MainActivity.this, RxActivity.class);
    startActivity(intent);
  }

  @OnClick({R.id.dialog})
  public void onClickDialog() {
    final DialogShowHelper myDialog = new DialogShowHelper(this);
    myDialog.show();
  }

  @OnClick({R.id.coordinator})
  public void onClickCoordinator() {
    final Intent intent = new Intent(MainActivity.this, Coordinator1stActivity.class);
    startActivity(intent);
  }

  @OnClick({R.id.dianzan})
  public void onClickDianzan() {
    final Intent intent = new Intent(MainActivity.this, ViewActivity.class);
    startActivity(intent);
  }

  @OnClick({R.id.menu})
  public void onClickMenu() {
    final Intent intent = new Intent(MainActivity.this, MenuDemoActivity.class);
    startActivity(intent);
  }

  @OnClick({R.id.JSDemo})
  public void onClickJS() {
    final Intent intent = new Intent(MainActivity.this, JSActivity.class);
    startActivity(intent);
  }

  @OnClick({R.id.shareApp})
  public void onClickShare() {
    wechatShare(1);
  }

  private void doExit() {
    new AlertDialog.Builder(MainActivity.this)
        .setTitle("Quit")
        .setMessage("小主，确认退出吗？")
        .setIcon(R.drawable.quit)
        .setPositiveButton("残忍退出",
            (dialog, whichButton) -> {
              MMKV mmkv = MMKV.defaultMMKV();
              final int value = mmkv.getInt("light_value", 180);
              mmkv.putInt("light_value", value);
              ActivityCollector.finishAll();
            })
        .setNegativeButton("再玩一会",
            (dialog, whichButton) -> {
              // LEFT DO NOTHING
              ToastUtil.showToast(MainActivity.this, "感谢您的挽留");
            }).show();
  }

  private void registerToWx() {
    api = WXAPIFactory.createWXAPI(this, APP_ID, false);
    api.registerApp(APP_ID);
  }

  private void wechatShare(int i) {
    if (!MainActivity.api.isWXAppInstalled()) {
      ToastUtil.showToast(MainActivity.this, "您还未安装微信客户端");
      return;
    }
    final String text1 = "微信测试";
    final String content = "能不能成啊!";

    WXTextObject text = new WXTextObject();
    text.text = content;
    WXMediaMessage msg = new WXMediaMessage();
    msg.mediaObject = text;
    msg.title = text1;
    msg.description = text1;
    SendMessageToWX.Req req = new SendMessageToWX.Req();
    req.transaction = String.valueOf(System.currentTimeMillis());
    req.message = msg;
    req.scene = i;
    MainActivity.api.sendReq(req);
  }
}
