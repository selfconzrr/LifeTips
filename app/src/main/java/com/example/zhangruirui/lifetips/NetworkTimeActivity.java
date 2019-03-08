package com.example.zhangruirui.lifetips;

import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.bugfree.zhangruirui.slideback.SlideBackHelper;
import com.bugfree.zhangruirui.slideback.SlideBackLayout;
import com.example.zhangruirui.events.GetTimeEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zhangruirui
 * email：1138517609@qq.com
 * GitHub：https://github.com/selfconzrr
 * Blog：http://blog.csdn.net/u011489043
 * Date：11/08/18
 */
public class NetworkTimeActivity extends BaseActivity {

  @BindView(R.id.showTime)
  EditText mShowTime;

  @BindView(R.id.getNetworkTime)
  Button mGetNetworkTime;

  private Long mTime = null;

  private SimpleDateFormat mSimpleDateFormat;

  private Calendar mChooseBirthday = Calendar.getInstance();
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_network_time);
    ButterKnife.bind(this);
    EventBus.getDefault().register(this);

    DatePicker datePicker = findViewById(R.id.date_Picker);

    datePicker.setMaxDate(new Date().getTime());
    datePicker.init(mChooseBirthday.get(Calendar.YEAR), mChooseBirthday.get(Calendar.MONTH),
        mChooseBirthday.get(Calendar.DAY_OF_MONTH), (view, year, monthOfYear, dayOfMonth) -> {
          mChooseBirthday.set(Calendar.YEAR, year);
          mChooseBirthday.set(Calendar.MONTH, monthOfYear);
          mChooseBirthday.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        });

    SlideBackLayout mirrorSwipeBackLayout = SlideBackHelper.attach(this, R.layout
        .swipe_back);
    // mMirrorSwipeBackLayout.setRightSwipeEnable(true);
    // mMirrorSwipeBackLayout.setLeftSwipeEnable(true);
    mirrorSwipeBackLayout.setSwipeBackListener(this::finish);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    EventBus.getDefault().unregister(this);
  }

  @OnClick(R.id.getNetworkTime)
  public void onClick() {
    EventBus.getDefault().post(new GetTimeEvent());
  }

  @Subscribe(threadMode = ThreadMode.BACKGROUND)
  public void onEvent(GetTimeEvent event) {
    try {
      final URL url = new URL("https://www.baidu.com");
      final URLConnection urlConnection = url.openConnection();
      mSimpleDateFormat = new SimpleDateFormat(getResources().getString(R.string
          .showNetworkTime), Locale.CHINA); // 24小时制
      urlConnection.connect();
      mTime = urlConnection.getDate();

      runOnUiThread(new Runnable() {
        @Override
        public void run() {
          if (mTime != null) {
            mShowTime.setText(mSimpleDateFormat.format(new Date(mTime)));
          }
        }
      });
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

