package com.example.zhangruirui.lifetips.bmi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.bugfree.zhangruirui.slideback.SlideBackHelper;
import com.bugfree.zhangruirui.slideback.SlideBackLayout;
import com.example.zhangruirui.lifetips.BaseActivity;
import com.example.zhangruirui.lifetips.R;
import com.example.zhangruirui.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zhangruirui
 * email：1138517609@qq.com
 * GitHub：https://github.com/selfconzrr
 * Blog：http://blog.csdn.net/u011489043
 * Date：11/14/18
 */
public class BMIActivity extends BaseActivity {

  @BindView(R.id.height)
  EditText mHeight;

  @BindView(R.id.weight)
  EditText mWeight;

  @BindView(R.id.radioGroup)
  RadioGroup mSex;

  private int mSexFlag = -1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // 继承 AppCompatActivity 的页面用此方法隐藏标题栏无效
    // this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_bmi);
    ButterKnife.bind(this);
    mSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
          case R.id.radio_man:
            mSexFlag = 0;
            break;
          case R.id.radio_woman:
            mSexFlag = 1;
            break;
        }
      }
    });

    SlideBackLayout mirrorSwipeBackLayout = SlideBackHelper.attach(this, R.layout
        .swipe_back);
    // mMirrorSwipeBackLayout.setRightSwipeEnable(true);
    // mMirrorSwipeBackLayout.setLeftSwipeEnable(true);
    mirrorSwipeBackLayout.setSwipeBackListener(this::finish);
  }

  @OnClick(R.id.compute)
  public void onClick() {
    final String height = mHeight.getText().toString();
    final String weight = mWeight.getText().toString();
    float he = 0;
    float we = 0;
    if (height.equals("")) {
      mHeight.setError("身高不能为空");
    } else {
      he = Float.parseFloat(height);
    }
    if (weight.equals("")) {
      mWeight.setError("体重不能为空");
    } else {
      we = Float.parseFloat(weight);
    }
    if (mSexFlag == -1) {
      ToastUtil.showToast(BMIActivity.this, "请选择性别！");
    }

    if (!(height.equals("") || weight.equals("")) && mSexFlag != -1) {
      float value = we / ((he / 100) * (he / 100));
      final Intent intent = new Intent();
      intent.setClass(BMIActivity.this, ResultActivity.class);
      intent.putExtra("bmi_index", value);
      intent.putExtra("height", he);
      intent.putExtra("choose_sex", mSexFlag);
      startActivity(intent);
    }
  }

  @OnClick(R.id.clear)
  public void onClickClear() {
    mHeight.setText("");
    mWeight.setText("");
    mSex.clearCheck();
  }
}
