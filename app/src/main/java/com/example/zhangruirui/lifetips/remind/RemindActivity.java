package com.example.zhangruirui.lifetips.remind;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.bugfree.zhangruirui.slideback.SlideBackHelper;
import com.bugfree.zhangruirui.slideback.SlideBackLayout;
import com.example.zhangruirui.lifetips.BaseActivity;
import com.example.zhangruirui.lifetips.R;
import com.example.zhangruirui.utils.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author zhangruirui
 * email：1138517609@qq.com
 * GitHub：https://github.com/selfconzrr
 * Blog：http://blog.csdn.net/u011489043
 * Date：11/18/18
 */
public class RemindActivity extends BaseActivity implements View.OnClickListener {

  private EditText mDate, mTime, mEdit, mRemark;
  public DatePickerDialog mDatePicker = null;
  private Calendar mCalendar = Calendar.getInstance();

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_remind);
    Button choose_date = findViewById(R.id.choose_date);
    Button choose_time = findViewById(R.id.choose_time);
    Button add_remark = findViewById(R.id.add_remark);
    Button sure = findViewById(R.id.sure);
    Button cancel = findViewById(R.id.cancel);
    mDate = findViewById(R.id.show_date);
    mTime = findViewById(R.id.show_time);
    mRemark = findViewById(R.id.show_things);
    choose_date.setOnClickListener(this);
    choose_time.setOnClickListener(this);
    add_remark.setOnClickListener(this);
    sure.setOnClickListener(this);
    cancel.setOnClickListener(this);

    SlideBackLayout mirrorSwipeBackLayout = SlideBackHelper.attach(this, R.layout
        .swipe_back);
    // mMirrorSwipeBackLayout.setRightSwipeEnable(true);
    // mMirrorSwipeBackLayout.setLeftSwipeEnable(true);
    mirrorSwipeBackLayout.setSwipeBackListener(this::finish);
  }

  @Override
  public void onClick(View v) {

    switch (v.getId()) {
      case R.id.choose_date:
        mDatePicker = new DatePickerDialog(this, mDateSetListener,
            mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
            mCalendar.get(Calendar.DAY_OF_MONTH));
        mDatePicker.show();
        break;
      case R.id.choose_time:
        TimePickerDialog timePicker = new TimePickerDialog(this, mTimeSetListener,
            mCalendar.get(Calendar.HOUR_OF_DAY),
            mCalendar.get(Calendar.MINUTE), true);
        timePicker.show();
        break;
      case R.id.add_remark:
        mEdit = new EditText(this);
        mEdit.setLines(5);
        mEdit.setText(mRemark.getText());
        new AlertDialog.Builder(this)
            .setTitle(getString(R.string.dialog_memo_title))
            .setView(mEdit)
            .setPositiveButton(getString(R.string.dialog_memo_ok),
                new DialogInterface.OnClickListener() {

                  @Override
                  public void onClick(DialogInterface dialog,
                                      int which) {

                    mRemark.setText(mEdit.getText());
                  }
                })
            .setNegativeButton(getString(R.string.dialog_memo_cancle),
                new DialogInterface.OnClickListener() {

                  @Override
                  public void onClick(DialogInterface dialog,
                                      int which) {

                    mEdit.setText("");
                  }
                }).show();
        break;
      case R.id.sure:
        String txt_date = mDate.getText().toString();
        String txt_time = mTime.getText().toString();
        String txt_remark = mRemark.getText().toString();
        if (txt_date.equals("")) {
          mDate.setError("日期不能为空");
        }
        if (txt_time.equals("")) {
          mTime.setError("时间不能为空");
        }
        if (txt_remark.equals("")) {
          mRemark.setError("待办事项不能为空");
        }

        Date today = null;
        String all = txt_date + " " + txt_time;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

        try {
          today = sdf.parse(all);
        } catch (java.text.ParseException e) {

          e.printStackTrace();
        }
        long value = today.getTime();
        long value2 = System.currentTimeMillis();
        if (value <= value2) {
          Log.e("value= ", value + " value2 = " + value2);
          ToastUtil.showToast(this, "您当前选择的时间早于现在的时间，请重新选择");
          cancel();
          return;
        }
        if (!(txt_date.equals("") || txt_time.equals("") || txt_remark
            .equals("")))
          ToastUtil.showToast(this, " 提醒您： " + mDate.getText() + " " + mTime.getText()
              + "  " + mRemark.getText() + " !");
        cancel();
        int delayTime = (int) (value - value2);
        Intent intent = new Intent();
        intent.setClass(RemindActivity.this, AlarmService.class);
        intent.putExtra("delayTime", delayTime);
        intent.putExtra("tickerText", "");
        intent.putExtra("contentTitle", "待办事项");
        intent.putExtra("contentText", txt_remark);
        startService(intent);
        break;
      case R.id.cancel:
        cancel();
        break;
      default:
        break;
    }
  }

  private void cancel() {
    mDate.setText("");
    mTime.setText("");
    mRemark.setText("");
  }

  private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog
      .OnDateSetListener() {

    public void onDateSet(DatePicker view, int year, int monthOfYear,
                          int dayOfMonth) {
      mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
      mCalendar.set(Calendar.MONTH, monthOfYear);
      mCalendar.set(Calendar.YEAR, year);
      mDate.setText(format(mCalendar.getTime()));

    }
  };
  private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog
      .OnTimeSetListener() {

    @Override
    public void onTimeSet(TimePicker view, int hour, int minute) {

      mCalendar.set(Calendar.HOUR_OF_DAY, hour);
      mCalendar.set(Calendar.MINUTE, minute);
      SimpleDateFormat tt = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);// hh:mm:ss 12小时制
      mTime.setText(tt.format(mCalendar.getTime()));
    }
  };

  private String format(Date date) {
    // Locale.US
    SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd",
        Locale.getDefault());
    return ymd.format(date);
  }
}
