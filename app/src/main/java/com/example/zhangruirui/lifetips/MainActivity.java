package com.example.zhangruirui.lifetips;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.bugfree.zhangruirui.banner.CycleViewPager;
import com.bugfree.zhangruirui.banner.model.PicInfo;
import com.example.zhangruirui.lifetips.bmi.BMIActivity;
import com.example.zhangruirui.lifetips.compass.CompassActivity;
import com.example.zhangruirui.lifetips.demo_learning.MenuDemoActivity;
import com.example.zhangruirui.lifetips.demo_learning.coordinatorlayout.Coordinator1stActivity;
import com.example.zhangruirui.lifetips.demo_learning.coordinatorlayout.ViewActivity;
import com.example.zhangruirui.lifetips.demo_learning.dialog.DialogShowHelper;
import com.example.zhangruirui.lifetips.demo_learning.jscommunication.JSActivity;
import com.example.zhangruirui.lifetips.demo_learning.refreshlayout.SmartRefreshActivity;
import com.example.zhangruirui.lifetips.demo_learning.rxjava.RxActivity;
import com.example.zhangruirui.lifetips.leetcode.activity.LeetcodeActivity;
import com.example.zhangruirui.lifetips.music.MusicActivity;
import com.example.zhangruirui.lifetips.notebook.activity.NotebookActivity;
import com.example.zhangruirui.lifetips.notes.TimeDiaryActivity;
import com.example.zhangruirui.lifetips.passwordbook.VerifyActivity;
import com.example.zhangruirui.lifetips.remind.RemindActivity;
import com.example.zhangruirui.lifetips.wechat.WelcomeWeChatActivity;
import com.example.zhangruirui.utils.Log;
import com.example.zhangruirui.utils.ToastUtil;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.mmkv.MMKV;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
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

  // FIXME: zhangruirui 2018/12/27
  // 注意通过 AS 下载程序和通过 apk 安装程序，生成的签名是不一样的，申请 app_id 需要注意
  private static final String APP_ID = "wxdef6a13ebf99866d"; //申请的 app_id
  public static IWXAPI api;

  private int mBackCount = 0; // 点击返回键的次数
  /**
   * 模拟请求后得到的数据
   */
  List<PicInfo> mList = new ArrayList<>();

  @BindView(R.id.view_pager)
  CycleViewPager mCycleViewPager;

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

    // 腾讯 Bugly 初始化 建议在测试阶段建议设置成 true，发布时设置为 false
    CrashReport.initCrashReport(getApplicationContext(), APP_ID, true);
    setContentView(R.layout.activity_main);
    registerToWx();
    ButterKnife.bind(this);
    initData();

    //设置选中和未选中时的图片
    mCycleViewPager.setIndicators(R.mipmap.ad_select, R.mipmap.ad_unselect);
    mCycleViewPager.setDelay(2000);
    mCycleViewPager.setData(mList, mAdCycleViewListener);
  }

  private void initData() {
    mList.add(new PicInfo("标题1",
        "http://img2.3lian.com/2014/c7/25/d/40.jpg"));
    mList.add(new PicInfo("标题2",
        "http://img2.3lian.com/2014/c7/25/d/41.jpg"));
    mList.add(new PicInfo("标题3",
        "http://imgsrc.baidu.com/forum/pic/item/b64543a98226cffc8872e00cb9014a90f603ea30.jpg"));
    mList.add(new PicInfo("标题4",
        "http://imgsrc.baidu.com/forum/pic/item/261bee0a19d8bc3e6db92913828ba61eaad345d4.jpg"));
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

  @OnClick({R.id.downRefresh})
  public void onClickDownRefresh() {
    final Intent intent = new Intent(MainActivity.this, SmartRefreshActivity.class);
    startActivity(intent);
  }

  @OnClick({R.id.passwordBook})
  public void onClickPasswordBook() {
    final Intent intent = new Intent(MainActivity.this, VerifyActivity.class);
    startActivity(intent);
  }

  @OnClick({R.id.drawShape})
  public void onClickDrawShape() {
    final Intent intent = new Intent(MainActivity.this, DrawShapeActivity.class);
    startActivity(intent);
  }

  @OnClick({R.id.myWechat})
  public void onClickWeChat() {
    final Intent intent = new Intent(MainActivity.this, WelcomeWeChatActivity.class);
    startActivity(intent);
  }

  private void doExit() {
//    new AlertDialog.Builder(MainActivity.this)
//        .setTitle("Quit")
//        .setMessage("小主，确认退出吗？")
//        .setIcon(R.drawable.quit)
//        .setPositiveButton("残忍退出",
//            (dialog, whichButton) -> {
//              MMKV mmkv = MMKV.defaultMMKV();
//              final int value = mmkv.getInt("light_value", 180);
//              mmkv.putInt("light_value", value);
//              ActivityCollector.finishAll();
//            })
//        .setNegativeButton("再玩一会",
//            (dialog, whichButton) -> {
//              // LEFT DO NOTHING
//              ToastUtil.showToast(MainActivity.this, "感谢您的挽留");
//            }).show();

    // 测试强制下线功能，提示用户，将退出登录状态，下次需要重新登录
    Intent intent = new Intent("com.example.zhangruirui.lifetips.FORCE_OFFLINE");
    sendBroadcast(intent);
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

  /**
   * 轮播图点击监听
   */
  private CycleViewPager.ImageCycleViewListener mAdCycleViewListener =
      new CycleViewPager.ImageCycleViewListener() {

        @Override
        public void onImageClick(PicInfo info, int position, View imageView) {

          if (mCycleViewPager.isCycle()) {
            position = position - 1;
          }
          // CrashReport.testJavaCrash();
          Toast.makeText(MainActivity.this, info.getTitle() +
              "选择了--" + position, Toast.LENGTH_LONG).show();
        }
      };

  /**
   * 读取手机联系人列表
   */
  private void readContacts() {
    Cursor cursor = null;
    try {
      cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
          null, null, null, null);
      while (cursor != null && cursor.moveToNext()) {
        String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract
            .CommonDataKinds.Phone.DISPLAY_NAME));
        String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds
            .Phone.NUMBER));
        Log.e("姓名+电话", displayName + "\n" + number);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (cursor != null) {
        cursor.close();
      }
    }
  }

  public boolean onKeyDown(int keyCode, KeyEvent event) {//back退出
    if (keyCode == KeyEvent.KEYCODE_BACK) {
      mBackCount++;
      switch (mBackCount) {
        case 1:
          ToastUtil.showToast(this, "再按一次退出程序");
          break;
        case 2:
          mBackCount = 0; // 初始化 back 值
          MainActivity.this.finish();
          android.os.Process.killProcess(android.os.Process.myPid()); // 关闭进程
          break;
      }
      return true; // 设置成 false 让 back 失效，true 表示 不失效
    } else {
      return super.onKeyDown(keyCode, event);
    }
  }
}
