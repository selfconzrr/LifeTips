package com.example.zhangruirui.lifetips.bmi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhangruirui.lifetips.R;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zhangruirui
 * email：1138517609@qq.com
 * GitHub：https://github.com/selfconzrr
 * Blog：http://blog.csdn.net/u011489043
 * Date：11/14/18
 */
public class ResultActivity extends AppCompatActivity {

  @BindView(R.id.result_image)
  ImageView mResultIV;

  @BindView(R.id.index_value)
  TextView mIndex;

  @BindView(R.id.condition_value)
  TextView mCondition;

  @BindView(R.id.weight_value)
  TextView mFitWeight;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_result);
    ButterKnife.bind(this);
    initViews();
  }

  @SuppressLint("SetTextI18n")
  private void initViews() {
    final Intent intent = getIntent();
    float index_bmi = intent.getFloatExtra("bmi_index", (float) 2.2);
    float height = intent.getFloatExtra("height", (float) 1.0);
    DecimalFormat format = new DecimalFormat("####0.00");
    mIndex.setText(format.format(index_bmi) + "");
    if (intent.getIntExtra("choose_sex", 0) == 1) {
      index_bmi++;
    }
    // BMI 健康指数：18.5~23.9
    float fit_low = (height / 100) * (height / 100) * (float) 18.5;
    float fit_high = (height / 100) * (height / 100) * (float) 23.9;
    mFitWeight.setText(format.format(fit_low) + "kg" + "~"
        + format.format(fit_high) + "kg");
    if (index_bmi <= 18.4) {
      mResultIV.setImageResource(R.drawable.little_thin);
      mCondition.setText("偏瘦");
    } else if (index_bmi < 24) {
      mResultIV.setImageResource(R.drawable.normal);
      mCondition.setText("正常");
    } else if (index_bmi < 27.9) {
      mResultIV.setImageResource(R.drawable.little_fat);
      mCondition.setText("微胖");
    } else if (index_bmi < 35) {
      mResultIV.setImageResource(R.drawable.too_fat);
      mCondition.setText("肥胖");
    } else {
      mCondition.setText("您该好好考虑考虑减肥了");
    }
  }

}
