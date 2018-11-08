package com.example.zhangruirui.lifetips;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.example.zhangruirui.events.GetTimeEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zhangruirui
 * email：1138517609@qq.com
 * GitHub：https://github.com/selfcon
 * Blog：http://blog.csdn.net/u011489043
 * Date：11/08/18
 */
public class NetworkTimeActivity extends AppCompatActivity {

  @BindView(R.id.showTime)
  EditText mShowTime;

  @BindView(R.id.getNetworkTime)
  Button mGetNetworkTime;

  private Long mTime = null;

  private SimpleDateFormat mSimpleDateFormat;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_network_time);
    ButterKnife.bind(this);
    EventBus.getDefault().register(this);

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

