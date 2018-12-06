package com.example.zhangruirui.lifetips;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.zhangruirui.lifetips.bmi.BMIActivity;
import com.example.zhangruirui.lifetips.compass.CompassActivity;
import com.example.zhangruirui.lifetips.demo_learning.dialog.DialogShowHelper;
import com.example.zhangruirui.lifetips.demo_learning.rxjava.RxActivity;
import com.example.zhangruirui.lifetips.leetcode.activity.LeetcodeActivity;
import com.example.zhangruirui.lifetips.music.MusicActivity;
import com.example.zhangruirui.lifetips.notebook.activity.NotebookActivity;
import com.example.zhangruirui.lifetips.notes.TimeDiaryActivity;
import com.example.zhangruirui.lifetips.remind.RemindActivity;
import com.example.zhangruirui.utils.ActivityCollector;
import com.example.zhangruirui.utils.ToastUtil;

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

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // The easiest way to transition back to your normal theme in your launcher (main) activity
    // is to call setTheme ()
    // Make sure this is before calling super.onCreate
    setTheme(R.style.AppTheme);

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
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

  private void doExit() {
    new AlertDialog.Builder(MainActivity.this)
        .setTitle("Quit")
        .setMessage("小主，确认退出吗？")
        .setIcon(R.drawable.quit)
        .setPositiveButton("残忍退出",
            new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog,
                                  int whichButton) {
                final SharedPreferences pref = getSharedPreferences("light", MODE_PRIVATE);
                final int value = pref.getInt("light_value", 180);
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("light_value", value);
                editor.apply();
                ActivityCollector.finishAll();
              }
            })
        .setNegativeButton("再玩一会",
            new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog,
                                  int whichButton) {
                // LEFT DO NOTHING
                ToastUtil.showToast(MainActivity.this, "感谢您的挽留");
              }
            }).show();
  }

}
