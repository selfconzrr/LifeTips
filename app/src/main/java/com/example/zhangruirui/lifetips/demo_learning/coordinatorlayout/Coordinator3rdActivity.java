package com.example.zhangruirui.lifetips.demo_learning.coordinatorlayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.zhangruirui.lifetips.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Coordinator属性较全解析
 */
public class Coordinator3rdActivity extends AppCompatActivity {

  @BindView(R.id.fab)
  FloatingActionButton mFab;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_coordinator3rd);
    ButterKnife.bind(this);
  }

  @OnClick(R.id.fab)
  public void onClick() {
    Snackbar.make(mFab, "Hello ZRR", Snackbar.LENGTH_LONG).setAction("ActionIII", new View
        .OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(Coordinator3rdActivity.this, Coordinator4thActivity.class);
        startActivity(intent);
      }
    }).show();
  }
}
