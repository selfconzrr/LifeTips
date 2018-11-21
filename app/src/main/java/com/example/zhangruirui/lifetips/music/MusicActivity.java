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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.zhangruirui.lifetips.BaseActivity;
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
public class MusicActivity extends BaseActivity implements AdapterView.OnItemClickListener {

  private ImageButton mPlay;
  private ImageButton mPrev;
  private ImageButton mNext;
  private TextView mMusicName;
  private TextView mDuration;
  private TextView mCurrPro;
  private SeekBar mPlayPro;
  private ListView mListView;
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
      if (mBind.getDuration() - mBind.getCurr() < 500) {
        mPosition++;
        playNext(mPosition);
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
    mediaFile = new ArrayList<>();
    if (getMediaFile() != null) {
      mediaFile = getMediaFile();
    }
    mListView.setAdapter(new SimpleAdapter(this, mediaFile,
        android.R.layout.simple_list_item_1, new String[]{"name"},
        new int[]{android.R.id.text1,}));
    mListView.setOnItemClickListener(this);
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
    mPlay = findViewById(R.id.play);
    mPrev = findViewById(R.id.prev);
    mNext = findViewById(R.id.next);
    mMusicName = findViewById(R.id.musicname);
    mDuration = findViewById(R.id.duration);
    mCurrPro = findViewById(R.id.curr);
    mPlayPro = findViewById(R.id.playprogress);
    mListView = findViewById(R.id.musiclist);

  }

  public void setView() {
    mPlayPro.setMax(mBind.getDuration());
    mCurrPro.setText(PlayUtil.parseInt(mBind.getCurr() / 1000));
    mDuration.setText(PlayUtil.parseInt(mBind.getDuration() / 1000));
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
            playNext(mPosition);
          }
          break;
        case R.id.prev:
          if (mPosition > 0) {
            mPosition--;
            playNext(mPosition);
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
    List<Map<String, Object>> list = new ArrayList<>();
    ContentResolver cr = getContentResolver();
    Uri AUDIO_URI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    String[] columns = new String[]{MediaStore.Audio.Media.DISPLAY_NAME,
        MediaStore.Audio.Media.DATA};
    Cursor cursor = cr.query(AUDIO_URI, columns,
        MediaStore.Audio.Media.DURATION + ">?",
        new String[]{"10000"}, null);
    while (cursor != null && cursor.moveToNext()) {
      Map<String, Object> map = new HashMap<>();
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
    if (cursor != null) {
      cursor.close();
    }
    return list;

  }

  public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
    mPosition = arg2;
    playNext(arg2);
    mHandler.postDelayed(callback, REFTIME);
  }

  public void playNext(int i) {
    if (i < mediaFile.size() && i >= 0) {
      Map<String, Object> map = mediaFile.get(i);
      mBind.play((String) map.get("path"));
      mMusicName.setText((String) map.get("name"));
    }
  }
}
