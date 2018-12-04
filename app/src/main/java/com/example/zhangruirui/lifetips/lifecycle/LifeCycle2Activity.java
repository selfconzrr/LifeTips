package com.example.zhangruirui.lifetips.lifecycle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.zhangruirui.lifetips.R;

public class LifeCycle2Activity extends AppCompatActivity {

  private static final String TAG = LifeCycle2Activity.class.getSimpleName();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.e(TAG, "onCreate " + TAG);
    setContentView(R.layout.activity_life_cycle2);
    final Intent intent = new Intent();
    intent.setClass(LifeCycle2Activity.this, LifeCycle3Activity.class);
    final Button btn_second = findViewById(R.id.btn_jump_to_3);
    btn_second.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        startActivity(intent);
      }
    });
  }

  @Override
  protected void onStart() {
    super.onStart();
    Log.e(TAG, "onStart " + TAG);
  }

  @Override
  protected void onResume() {
    super.onResume();
    Log.e(TAG, "onResume " + TAG);
  }

  @Override
  protected void onPause() {
    super.onPause();
    Log.e(TAG, "onPause " + TAG);
  }

  @Override
  protected void onStop() {
    super.onStop();
    Log.e(TAG, "onStop " + TAG);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    Log.e(TAG, "onDestroy " + TAG);
  }
}
