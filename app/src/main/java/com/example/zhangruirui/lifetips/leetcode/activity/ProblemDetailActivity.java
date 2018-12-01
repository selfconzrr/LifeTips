package com.example.zhangruirui.lifetips.leetcode.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Button;

import com.example.zhangruirui.lifetips.R;
import com.example.zhangruirui.lifetips.leetcode.model.ProblemParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProblemDetailActivity extends AppCompatActivity {

  @BindView(R.id.problem_detail_view)
  WebView mProblemDetailWebView;

  private String mProblemUrl;

  @BindView(R.id.go_answer)
  Button mSeeAnswer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_problem_detail);
    ButterKnife.bind(this);
    Intent intent = getIntent();
    mProblemUrl = intent.getStringExtra("PUrl");
  }

  @Override
  protected void onResume() {
    super.onResume();
    new GetProblemDetailAsyncTask(this).execute(mProblemUrl);
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
