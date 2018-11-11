package com.example.zhangruirui.lifetips;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.zhangruirui.utils.CacheCleanUtil;
import com.example.zhangruirui.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zhangruirui
 * email：1138517609@qq.com
 * GitHub：https://github.com/selfcon
 * Blog：http://blog.csdn.net/u011489043
 * Date：11/11/18
 */
public class SetActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

  @BindView(R.id.light)
  SeekBar mLight;

  @BindView(R.id.show_value)
  TextView mShowLightValue;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_set);
    ButterKnife.bind(this);
    final SharedPreferences pref = getSharedPreferences("light", MODE_PRIVATE);
    final int value = pref.getInt("light_value", 180);
    mLight.setOnSeekBarChangeListener(this); // 不要忘了设置 Listener，否则不会触发 onProgressChanged
    mLight.setProgress(value);

  }

  @SuppressLint("SetTextI18n")
  @Override
  public void onProgressChanged(SeekBar seekBar, int progress,
                                boolean fromUser) {
    final int bright = seekBar.getProgress();
    final SharedPreferences.Editor editor = getSharedPreferences("light",
        MODE_PRIVATE).edit();
    editor.putInt("light_value", bright);
    editor.apply();
    mShowLightValue.setText(" : " + String.valueOf(bright));
    changeAppBrightness(this, bright);
  }

  private void changeAppBrightness(Context context, int brightness) {
    final Window window = ((Activity) context).getWindow();
    final WindowManager.LayoutParams lp = window.getAttributes();
    if (brightness == -1) {
      lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
    } else {
      lp.screenBrightness = (brightness <= 0 ? 1 : brightness) / 255f;
    }
    window.setAttributes(lp);
  }

  @Override
  public void onStartTrackingTouch(SeekBar seekBar) {

  }

  @Override
  public void onStopTrackingTouch(SeekBar seekBar) {

  }

  @OnClick(R.id.clear_cache)
  public void onClickClear() {
    clear_cache();
  }

  private void clear_cache() {
    final Context context = getApplicationContext();
    CacheCleanUtil cache_clean = new CacheCleanUtil();
    cache_clean.cleanFiles(context);
    cache_clean.cleanInternalCache(context);
    cache_clean.cleanExternalCache(context);
    cache_clean.cleanSharedPreference(context);
    ToastUtil.showToast(SetActivity.this, "清除完毕");
  }

  @OnClick(R.id.help)
  public void onClickHelp() {
    final Intent intent = new Intent(SetActivity.this, HelpActivity.class);
    startActivity(intent);
  }

  @OnClick(R.id.about)
  public void onClickAbout() {
    showAboutUs();
  }

  @OnClick(R.id.advice)
  public void onClickAdvice() {
    showAdviceEdit();
  }

  private void showAdviceEdit() {
    final EditText editText = new EditText(this);
    editText.setLines(5);
    editText.setHint("在此处输入您的宝贵意见或建议");
    new AlertDialog.Builder(this)
        .setTitle(R.string.advice)
        .setView(editText)
        .setPositiveButton(getString(R.string.dialog_memo_ok), new DialogInterface
            .OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            // TODO: 2018/11/11 如何真正提交到后台？待做
            ToastUtil.showToast(SetActivity.this, "您的意见或建议已提交，感谢您的反馈");
          }
        })
        .setNegativeButton(getString(R.string.dialog_memo_cancle), new DialogInterface
            .OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            editText.setText("");
          }
        }).show();
  }

  private void showAboutUs() {
    new AlertDialog.Builder(this)
        .setTitle("Life Tips")
        .setMessage(
            "Author:ZhangRuirui" + "\n" + "QQ:1138517609" + "\n"
                + "Email:1138517609@qq.com" + "\n"
                + "GitHub:https://github.com/selfconzrr" + "\n"
                + "Blog:http://blog.csdn.net/u011489043")
        .show();
  }

}
