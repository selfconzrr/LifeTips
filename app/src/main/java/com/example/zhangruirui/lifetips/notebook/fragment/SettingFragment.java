package com.example.zhangruirui.lifetips.notebook.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author zhangruirui
 * email：1138517609@qq.com
 * GitHub：https://github.com/selfconzrr
 * Blog：http://blog.csdn.net/u011489043
 * Date：11/24/18
 * <p>
 * 暂时为空 Fragment，留作他用
 */
public class SettingFragment extends Fragment {
  @SuppressLint("SetTextI18n")
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle
      savedInstanceState) {
    TextView tv = new TextView(getActivity());
    tv.setText("Setting");
    return tv;
  }
}
