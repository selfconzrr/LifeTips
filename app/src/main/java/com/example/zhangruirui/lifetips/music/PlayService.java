package com.example.zhangruirui.lifetips.music;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import java.io.IOException;

public class PlayService extends Service {
  MyBind mBind;
  MediaPlayer mPlayer;

  @Override
  public IBinder onBind(Intent intent) {
    return mBind;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    mBind = new MyBind();
    mPlayer = new MediaPlayer();

  }

  class MyBind extends Binder {

    public void play(String string) {
      mPlayer.reset();
      try {
        mPlayer.setDataSource(string);
        mPlayer.prepare();
        mPlayer.start();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    public int getCurr() {
      return mPlayer.getCurrentPosition();
    }

    public int getDuartion() {
      return mPlayer.getDuration();
    }

    public void pause() {
      mPlayer.pause();

    }

    public boolean isPlaying() {
      return mPlayer.isPlaying();
    }

    public void continuePlay() {
      mPlayer.start();

    }

    public void stop() {
      mPlayer.reset();
    }

    public void setSeek(int i) {
      mPlayer.seekTo(i);
    }

  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    mPlayer.stop();
  }
}
