package com.example.zhangruirui.lifetips;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zhangruirui
 * email：1138517609@qq.com
 * GitHub：https://github.com/selfconzrr
 * Blog：http://blog.csdn.net/u011489043
 * Date：11/07/18
 */
public class LightIntensityActivity extends AppCompatActivity {

  @BindView(R.id.light_level)
  TextView mLightLevel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_light_intensity);
    ButterKnife.bind(this);
    SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    if (sensorManager != null) {
      Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
      sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
  }

  private SensorEventListener listener = new SensorEventListener() {

    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent event) {
      float value = event.values[0];
      mLightLevel.setText("Current light level is " + value + "lx");
      mLightLevel.setTextColor(android.graphics.Color.RED);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
  };
}
