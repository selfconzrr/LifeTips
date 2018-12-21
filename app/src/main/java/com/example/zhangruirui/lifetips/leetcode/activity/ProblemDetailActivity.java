package com.example.zhangruirui.lifetips.leetcode.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Button;

import com.bugfree.zhangruirui.slideback.SlideBackHelper;
import com.bugfree.zhangruirui.slideback.SlideBackLayout;
import com.example.zhangruirui.lifetips.R;
import com.example.zhangruirui.lifetips.demo_learning.launchmode.BasicActivity;
import com.example.zhangruirui.lifetips.leetcode.model.Mapping;
import com.example.zhangruirui.lifetips.leetcode.model.ProblemParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProblemDetailActivity extends BasicActivity {

  @BindView(R.id.problem_detail_view)
  WebView mProblemDetailWebView;

  private String mProblemUrl;

  private String mProblemTitle;

  @BindView(R.id.go_answer)
  Button mSeeAnswer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_problem_detail);
    ButterKnife.bind(this);
    Intent intent = getIntent();
    mProblemUrl = intent.getStringExtra("PUrl");
    mProblemTitle = intent.getStringExtra("PTitle");

    SlideBackLayout mirrorSwipeBackLayout = SlideBackHelper.attach(this, R.layout
        .swipe_back);
    // mMirrorSwipeBackLayout.setRightSwipeEnable(true);
    // mMirrorSwipeBackLayout.setLeftSwipeEnable(true);
    mirrorSwipeBackLayout.setSwipeBackListener(this::finish);
  }

  @Override
  protected void onResume() {
    super.onResume();
    new GetProblemDetailAsyncTask(this).execute(mProblemUrl);
  }

  // TODO: 2018/12/4 讨论区或者试题答案的展示
  @OnClick(R.id.go_answer)
  public void onClick() {
    String url = Mapping.getMap().get(mProblemTitle);
    Log.e("zhangrr", "onClick() called = " + Mapping.getMap().get(mProblemTitle));

    // actionIntent
//    Intent actionIntent = new Intent(Intent.ACTION_SEND);
//    actionIntent.setType("*/*");
//    actionIntent.putExtra(Intent.EXTRA_EMAIL, "example@example.com");
//    actionIntent.putExtra(Intent.EXTRA_SUBJECT, "example");
//    PendingIntent pi = PendingIntent.getActivity(this, 0, actionIntent, 0);
//    Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.button_play);
    //注意在正式项目中不要在UI线程读取图片

    // menuIntent
//    Intent menuIntent = new Intent();
//    menuIntent.setClass(getApplicationContext(), CustomTabActivity.class);
//    PendingIntent pi1 = PendingIntent.getActivity(getApplicationContext(), 0, menuIntent, 0);

    CustomTabsIntent tabsIntent = new CustomTabsIntent.Builder()
        .setToolbarColor(getResources().getColor(R.color.divider_gray))
        .build();

    tabsIntent.launchUrl(this, Uri.parse(url));
  }

  @SuppressLint("StaticFieldLeak")
  private class GetProblemDetailAsyncTask extends AsyncTask<String, Void, String> {

    private Context mContext;

    GetProblemDetailAsyncTask(Context context) {
      mContext = context;
    }

    @Override
    public String doInBackground(String... uriString) {

      try {
        Document document = Jsoup.connect(uriString[0]).get();
        return ProblemParser.parseDetailProblem(document);

      } catch (IOException e) {
        Log.e("zhangrr", "doInBackground() called with: error = " + e.getMessage());
        return null;
      }
    }

    @Override
    public void onPostExecute(String html) {
      File cachedDir = mContext.getCacheDir();
      File saveFile = new File(cachedDir, "1.html");

      try {
        FileOutputStream outStream = new FileOutputStream(saveFile);
        outStream.write(html.getBytes());
        outStream.close();
      } catch (Exception e) {
        Log.e("zhangrr", "onPostExecute() called with: error = " + e.getMessage());
      }
      mProblemDetailWebView.loadUrl("file://" + cachedDir.getAbsolutePath() + "/1.html");
    }
  }
}
