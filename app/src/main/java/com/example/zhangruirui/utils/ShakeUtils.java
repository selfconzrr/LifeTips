package com.example.zhangruirui.utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * 摇一摇监听器：
 * 用于开发、测试同学，方便进行某种操作
 * 在任何界面都可以进行摇一摇操作，默认来回晃动三次即触发。
 */
public class ShakeUtils implements SensorEventListener {

  private static final int SHAKE_THRESHOLD = 3; // 晃动次数门限
  private static final int DETECT_INTERVAL_MS = 50; // 前后两次检测的时间间隔
  private static final int MAGNITUDE_THRESHOLD = 600; // 幅度阈值
  private static final int MIN_SHAKE_INTERVAL_MS = 1000;

  private Context mContext;
  private SensorManager mSensorManager;
  private ShakeListener mListener; // 监听摇一摇

  private long mLastSampleTimeMs;
  private int mHugeMovementCount; // 大幅度摇手机的次数
  private long mLastShakeTriggerTime; // 上次触发吊起 debug 界面的时机
  private long mLastHugeMovementTime; // 上次大幅度摇晃的时机

  private float[] mGravity = new float[3];
  private float[] mLinearAcceleration = new float[3];

  public ShakeUtils(Context context, ShakeListener listener) {
    mContext = context;
    mListener = listener;
  }

  public void register() {
    mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
    if (mSensorManager == null) {
      return;
    }
    Sensor sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    if (sensor != null) {
      mSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
    }
  }

  public void unregister() {
    if (mSensorManager == null) {
      return;
    }
    mSensorManager.unregisterListener(this);
  }

  @Override
  public void onSensorChanged(SensorEvent event) {
    final float alpha = 0.8f;

    mGravity[0] = alpha * mGravity[0] + (1 - alpha) * event.values[0];
    mGravity[1] = alpha * mGravity[1] + (1 - alpha) * event.values[1];
    mGravity[2] = alpha * mGravity[2] + (1 - alpha) * event.values[2];

    mLinearAcceleration[0] = event.values[0] - mGravity[0];
    mLinearAcceleration[1] = event.values[1] - mGravity[1];
    mLinearAcceleration[2] = event.values[2] - mGravity[2];

    long currentTime = System.currentTimeMillis();
    if ((currentTime - mLastSampleTimeMs) < DETECT_INTERVAL_MS) {
      return;
    }

    if (System.currentTimeMillis() - mLastShakeTriggerTime < MIN_SHAKE_INTERVAL_MS) {
      return;
    }

    mLastSampleTimeMs = currentTime;
    // 获取 x y z 坐标的变化值
    float x = mLinearAcceleration[0];
    float y = mLinearAcceleration[1];
    float z = mLinearAcceleration[2];

    double magnitude = x * x + y * y + z * z;

    if (magnitude > MAGNITUDE_THRESHOLD) {
      if (System.currentTimeMillis() - mLastHugeMovementTime > 150) {
        mLastHugeMovementTime = System.currentTimeMillis();
        mHugeMovementCount++;

        if (mHugeMovementCount >= SHAKE_THRESHOLD) {
          mHugeMovementCount = 0;
          mListener.onShake();
          mLastShakeTriggerTime = System.currentTimeMillis();
        }
      }
    } else {
      if (mHugeMovementCount > 0 && System.currentTimeMillis() - mLastHugeMovementTime > 1500) {
        mHugeMovementCount = 0;
      }
    }
  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int accuracy) {
  }

  public interface ShakeListener {
    void onShake();
  }

}
