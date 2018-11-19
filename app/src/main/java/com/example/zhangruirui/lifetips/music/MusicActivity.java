package com.example.zhangruirui.lifetips.music;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.zhangruirui.lifetips.R;
import com.example.zhangruirui.utils.PlayUtil;
import com.example.zhangruirui.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangruirui
 * email：1138517609@qq.com
 * GitHub：https://github.com/selfconzrr
 * Blog：http://blog.csdn.net/u011489043
 * Date：11/17/18
 */
public class MusicActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

  private ImageButton mPlay;
  private ImageButton mPrev;
  private ImageButton mNext;
  private TextView mMusicName;
  private TextView mDuration;
  private TextView mCurrPro;
  private SeekBar mPlayPro;
  private ListView mlistview;
  private PlayService.MyBind mBind;
  private int REFTIME = 500;
  private int mPosition = 0;
  private Handler mHandler;
  private List<Map<String, Object>> mediaFile;
  private Runnable callback = new Runnable() {
    public void run() {
      if (mBind == null) {
        mHandler.postDelayed(callback, REFTIME);
        return;
      }
      setView();
      if (mBind.getDuartion() - mBind.getCurr() < 500) {
        mPosition++;
        playnext(mPosition);
      }
      mHandler.postDelayed(callback, REFTIME);
    }
  };

  private ServiceConnection conn = new ServiceConnection() {

    public void onServiceDisconnected(ComponentName name) {
    }

    public void onServiceConnected(ComponentName name, IBinder service) {
      mBind = (PlayService.MyBind) service;
    }
  };

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_music);

    findView();
    setClickListener(new MyOnClickListener());
    mPlayPro.setOnSeekBarChangeListener(new MyOnSeekBarChangeListener());
    mHandler = new Handler();
    mediaFile = new ArrayList<Map<String, Object>>();
    if (getMediaFile() != null) {
      mediaFile = getMediaFile();
    }
    mlistview.setAdapter(new SimpleAdapter(this, mediaFile,
        android.R.layout.simple_list_item_1, new String[]{"name"},
        new int[]{android.R.id.text1,}));
    mlistview.setOnItemClickListener(this);
  }

  @Override
  protected void onStart() {
    super.onStart();
    Intent intent = new Intent();
    intent.setClass(this, PlayService.class);
    bindService(intent, conn, BIND_AUTO_CREATE);
  }

  @Override
  protected void onStop() {
    super.onStop();
    unbindService(conn);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }

  private void setClickListener(MyOnClickListener listener) {
    mPlay.setOnClickListener(listener);
    mPrev.setOnClickListener(listener);
    mNext.setOnClickListener(listener);
  }

  private void findView() {
    mPlay = (ImageButton) findViewById(R.id.play);
    mPrev = (ImageButton) findViewById(R.id.prev);
    mNext = (ImageButton) findViewById(R.id.next);
    mMusicName = (TextView) findViewById(R.id.musicname);
    mDuration = (TextView) findViewById(R.id.duration);
    mCurrPro = (TextView) findViewById(R.id.curr);
    mPlayPro = (SeekBar) findViewById(R.id.playprogress);
    mlistview = (ListView) findViewById(R.id.musiclist);

  }

  public void setView() {
    mPlayPro.setMax(mBind.getDuartion());
    mCurrPro.setText(PlayUtil.parseInt(mBind.getCurr() / 1000));
    mDuration.setText(PlayUtil.parseInt(mBind.getDuartion() / 1000));
    mPlayPro.setProgress(mBind.getCurr());
  }

  class MyOnClickListener implements View.OnClickListener {

    boolean f = false;

    public void onClick(View v) {
      switch (v.getId()) {
        case R.id.play:
          if (mBind.isPlaying()) {
            mPlay.setBackgroundResource(R.drawable.button_play);
            mBind.pause();
          } else {
            mPlay.setBackgroundResource(R.drawable.button_pause);
            if (f) {
              mBind.continuePlay();
            } else {
              if (mediaFile.size() > 0) {
                mBind.play((String) mediaFile.get(0).get("path"));
                mMusicName.setText((String) mediaFile.get(0).get(
                    "name"));
                f = true;
                mHandler.postDelayed(callback, REFTIME);
              } else {
                ToastUtil.showToast(MusicActivity.this, "当前没有可播放的音乐文件");
              }
            }
          }
          break;
        case R.id.next:
          if (mPosition < mediaFile.size()) {
            mPosition++;
            playnext(mPosition);
          }
          break;
        case R.id.prev:
          if (mPosition > 0) {
            mPosition--;
            playnext(mPosition);
          }
          break;
      }
    }
  }

  class MyOnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

    public void onProgressChanged(SeekBar seekBar, int progress,
                                  boolean fromUser) {

    }

    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    public void onStopTrackingTouch(SeekBar seekBar) {
      mBind.setSeek(mPlayPro.getProgress());
    }

  }

  private List<Map<String, Object>> getMediaFile() {
    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    ContentResolver cr = getContentResolver();
    Uri AUDIO_URI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    String[] columns = new String[]{MediaStore.Audio.Media.DISPLAY_NAME,
        MediaStore.Audio.Media.DATA};
    Cursor cursor = cr.query(AUDIO_URI, columns,
        MediaStore.Audio.Media.DURATION + ">?",
        new String[]{"10000"}, null);
    while (cursor.moveToNext()) {
      Map<String, Object> map = new HashMap<String, Object>();
      map.put("path", cursor.getString(1));
      String name = cursor.getString(0);
      if (name.endsWith(".mp3")) {
        name = name.substring(0, name.indexOf(".mp3"));
      } else if (name.endsWith(".MP3")) {
        name = name.substring(0, name.indexOf(".MP3"));
      }
      map.put("name", name);
      list.add(map);
    }

    return list;

  }

  public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
    mPosition = arg2;
    playnext(arg2);
    mHandler.postDelayed(callback, REFTIME);
  }

  public void playnext(int i) {
    if (i < mediaFile.size() && i >= 0) {
      Map<String, Object> map = mediaFile.get(i);
      mBind.play((String) map.get("path"));
      mMusicName.setText((String) map.get("name"));
    }
  }
}
