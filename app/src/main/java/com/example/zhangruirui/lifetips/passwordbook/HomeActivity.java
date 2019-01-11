package com.example.zhangruirui.lifetips.passwordbook;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.example.zhangruirui.lifetips.R;

public class HomeActivity extends AppCompatActivity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    ImageButton button_add = findViewById(R.id.button_add);
    button_add.setImageDrawable(getResources().getDrawable(R.drawable.add_record));
    button_add.setOnClickListener(new myButtonListener());

    ImageButton button_search = findViewById(R.id.button_search);
    button_search.setImageDrawable(getResources().getDrawable(R.drawable.seek_record));
    button_search.setOnClickListener(new myButtonListener());

    ImageButton button_preview = findViewById(R.id.button_preview);
    button_preview.setImageDrawable(getResources().getDrawable(R.drawable.record_preview));
    button_preview.setOnClickListener(new myButtonListener());

    ImageButton button_about = findViewById(R.id.button_about);
    button_about.setImageDrawable(getResources().getDrawable(R.drawable.about_us));
    button_about.setOnClickListener(new myButtonListener());

    ImageButton button_chongzhimima = findViewById(R.id.button_chongzhimima);
    button_chongzhimima.setImageDrawable(getResources().getDrawable(R.drawable
        .button_chongzhimima));
    button_chongzhimima.setOnClickListener(new myButtonListener());

  }

  class myButtonListener implements View.OnClickListener {
    public void onClick(View v) {
      switch (v.getId()) {
        case R.id.button_add:
          Intent intent = new Intent();
          intent.setClass(HomeActivity.this, AddRecord.class);
          startActivity(intent);
          //加入酷果广告
          //Ckm pm0 = Ckm.getInstance(HomeActivity.this);
          //pm0.setCooId(HomeActivity.this, "95775bdda2db453494b56680a665d50c");
          //pm0.setChannelId(HomeActivity.this, "k-app360");
          //pm0.receiveMessage(HomeActivity.this, true);
          break;
        case R.id.button_search:
          Intent intent1 = new Intent();
          intent1.setClass(HomeActivity.this, SearchRecord.class);
          startActivity(intent1);
          //加入酷果广告
          //Ckm pm1 = Ckm.getInstance(HomeActivity.this);
          //pm1.setCooId(HomeActivity.this, "95775bdda2db453494b56680a665d50c");
          //pm1.setChannelId(HomeActivity.this, "k-app360");
          //pm1.receiveMessage(HomeActivity.this, true);
          break;
        case R.id.button_preview:
          Intent intent2 = new Intent();
          intent2.setClass(HomeActivity.this, ShowAllPassword.class);
          startActivity(intent2);
          //加入酷果广告
				/*Ckm pm2 = Ckm.getInstance(HomeActivity.this);
				pm2.setCooId(HomeActivity.this, "95775bdda2db453494b56680a665d50c");
				pm2.setChannelId(HomeActivity.this, "k-app360");
				pm2.receiveMessage(HomeActivity.this, true);*/
          break;
        case R.id.button_chongzhimima:
          Intent intent3 = new Intent();
          intent3.setClass(HomeActivity.this, SetHandPasswordActivity.class);
          startActivity(intent3);
          finish();
          //加入酷果广告
				/*Ckm pm3 = Ckm.getInstance(HomeActivity.this);
				pm3.setCooId(HomeActivity.this, "95775bdda2db453494b56680a665d50c");
				pm3.setChannelId(HomeActivity.this, "k-app360");
				pm3.receiveMessage(HomeActivity.this, true);*/
          break;
        case R.id.button_about:
          new AlertDialog.Builder(HomeActivity.this)
              .setTitle("Life Tips")
              .setMessage(
                  "Author:ZhangRuirui" + "\n" + "QQ:1138517609" + "\n"
                      + "Email:1138517609@qq.com" + "\n"
                      + "GitHub:https://github.com/selfconzrr" + "\n"
                      + "Blog:http://blog.csdn.net/u011489043")
              .show();
          //加入酷果广告
				/*Ckm pm4 = Ckm.getInstance(HomeActivity.this);
				pm4.setCooId(HomeActivity.this, "95775bdda2db453494b56680a665d50c");
				pm4.setChannelId(HomeActivity.this, "k-app360");
				pm4.receiveMessage(HomeActivity.this, true);*/

      }

    }
  }

}