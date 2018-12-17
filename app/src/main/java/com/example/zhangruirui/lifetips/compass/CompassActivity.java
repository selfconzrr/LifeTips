package com.example.zhangruirui.lifetips.compass;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;

import com.bugfree.zhangruirui.slideback.SlideBackHelper;
import com.bugfree.zhangruirui.slideback.SlideBackLayout;
import com.example.zhangruirui.lifetips.BaseActivity;
import com.example.zhangruirui.lifetips.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zhangruirui
 * email：1138517609@qq.com
 * GitHub：https://github.com/selfconzrr
 * Blog：http://blog.csdn.net/u011489043
 * Date：11/15/18
 */
public class CompassActivity extends BaseActivity {

  @BindView(R.id.direction)
  Button mDirection;

  @BindView(R.id.sampleView)
  SampleView mSampleView;

  private SensorManager mSensorManager;
  private Sensor mSensor;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_compass);
    ButterKnife.bind(this);
    mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    if (mSensorManager != null) {
      mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
    }

    SlideBackLayout mirrorSwipeBackLayout = SlideBackHelper.attach(this, R.layout
        .swipe_back);
    // mMirrorSwipeBackLayout.setRightSwipeEnable(true);
    // mMirrorSwipeBackLayout.setLeftSwipeEnable(true);
    mirrorSwipeBackLayout.setSwipeBackListener(this::finish);
  }

  @Override
  protected void onResume() {
    super.onResume();
    mSensorManager.registerListener(mListener, mSensor, SensorManager.SENSOR_DELAY_GAME);
  }

  @Override
  protected void onStop() {
    super.onStop();
    mSensorManager.unregisterListener(mListener);
  }

  private final SensorEventListener mListener = new SensorEventListener() {
    public void onSensorChanged(SensorEvent event) {
      final float[] values = event.values;
      if (mSampleView != null) {
        mSampleView.setValues(values);
        mSampleView.invalidate();// call onDraw
      }

      if (mDirection != null) {
        final float direction = values[0];
        if (direction > 22.5f && direction < 157.5f) {
          // east
          mDirection.setText("东");
        } else if (direction > 202.5f && direction < 337.5f) {
          // west
          mDirection.setText("西");
        } else if (direction > 112.5f && direction < 247.5f) {
          // south
          mDirection.setText("南");
        } else if (direction < 67.5 || direction > 292.5f) {
          // north
          mDirection.setText("北");
        }
      }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
  };
}
