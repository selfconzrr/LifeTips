package com.example.zhangruirui.lifetips;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.zhangruirui.lifetips.bmi.BMIActivity;
import com.example.zhangruirui.lifetips.compass.CompassActivity;
import com.example.zhangruirui.lifetips.music.MusicActivity;
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
public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
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

  private void doExit() {
    new AlertDialog.Builder(MainActivity.this)
        .setTitle("Quit")
        .setMessage("小主，确认退出吗？")
        .setIcon(R.drawable.quit)
        .setPositiveButton("残忍退出",
            new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog,
                                  int whichButton) {
                // TODO: 2018/11/5 这里需要做一些操作：关闭数据库、保存用户设置的屏幕亮度等
                SetActivity set = new SetActivity();
                //Log.e("zry", bright+"ddd");
                final SharedPreferences pref = getSharedPreferences("light", MODE_PRIVATE);
                final int value = pref.getInt("light_value", 180);
                SharedPreferences.Editor editor = getSharedPreferences("light", MODE_PRIVATE)
                    .edit();
                editor.putInt("light_value", value);
                editor.apply();
                finish();
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
