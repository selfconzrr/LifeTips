package com.example.zhangruirui.lifetips;

import android.os.SystemClock;
import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;

import com.example.zhangruirui.utils.Log;
import com.tencent.mmkv.MMKV;

import org.junit.Test;
import org.junit.runner.RunWith;

@MediumTest
@RunWith(AndroidJUnit4.class)
public class MMKVTest {

  @Test
  public void testMMKV() {

    MMKV mmkv = MMKV.defaultMMKV();
    Log.e("zhangrr", "mmkv 开始时间 = " + SystemClock.uptimeMillis());
    for (int i = 0; i < 1000; i++) {
      mmkv.putInt("键" + i, i);
    }
    Log.e("zhangrr", "mmkv 结束时间 = " + SystemClock.uptimeMillis());

//    Log.e("zhangrr","sp 开始时间 = " + SystemClock.uptimeMillis());

//    SharedPreferences mySharedPreferences = getSharedPreferences("test",
//        Activity.MODE_PRIVATE);
//实例化SharedPreferences.Editor对象（第二步）
//    SharedPreferences.Editor editor = mySharedPreferences.edit();
//  for (int i = 0; i < 1000; i ++){
//      mmkv.putInt("键" + i, i);
//    }
//    Log.e("zhangrr","sp 结束时间 = " + SystemClock.uptimeMillis());

  }

}
