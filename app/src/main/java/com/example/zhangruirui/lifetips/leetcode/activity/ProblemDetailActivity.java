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
import us.feras.mdv.MarkdownView;

public class ProblemDetailActivity extends AppCompatActivity {

  @BindView(R.id.problem_detail_view)
  WebView mProblemDetailWebView;

  private String mProblemUrl;

  private String mProblemTitle;

  @BindView(R.id.go_answer)
  Button mSeeAnswer;

  @BindView(R.id.problem_discuss_view)
  MarkdownView mProblemDiscussWebView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_problem_detail);
    ButterKnife.bind(this);
    Intent intent = getIntent();
    mProblemUrl = intent.getStringExtra("PUrl");
    mProblemTitle = intent.getStringExtra("PTitle");
//    mProblemDiscussWebView.setWebViewClient(new WebViewClient(){
//      @Override
//      public boolean shouldOverrideUrlLoading(WebView view, String url) {
//        view.loadUrl(url);
//        return true;
//      }
//    });
  }

  @Override
  protected void onResume() {
    super.onResume();
    new GetProblemDetailAsyncTask(this).execute(mProblemUrl);
  }

  // TODO: 2018/12/4 讨论区或者试题答案的展示
  @OnClick(R.id.go_answer)
  public void onClick() {
//    mProblemDiscussWebView.loadUrl(Mapping.getMap().get(mProblemTitle));
//    mProblemDiscussWebView.loadUrl(mProblemUrl);
    Log.e("zhangrr", "onClick() called = " + Mapping.getMap().get(mProblemTitle));
    mProblemDiscussWebView.loadMarkdownFile(Mapping.getMap().get(mProblemTitle));

//    String html= null;
//    try {
//      html = new Markdown4jProcessor().process(Mapping.getMap().get(mProblemTitle));
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//    mProblemDiscussWebView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
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
