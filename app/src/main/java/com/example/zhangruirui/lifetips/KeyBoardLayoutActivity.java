package com.example.zhangruirui.lifetips;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import com.example.zhangruirui.ks_usefulcode.InputLocationUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KeyBoardLayoutActivity extends AppCompatActivity {

  @BindView(R.id.adjustLayout)
  ScrollView mAdjustLayout;

  @BindView(R.id.confirm_bt)
  Button mConfirm;

  @BindView(R.id.input_test)
  EditText mInput;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_key_board_layout);
    ButterKnife.bind(this);

    new InputLocationUtil(mAdjustLayout).assist(mConfirm);
  }
}
