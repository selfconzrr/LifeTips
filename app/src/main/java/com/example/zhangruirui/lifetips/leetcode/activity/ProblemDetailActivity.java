package com.example.zhangruirui.lifetips.leetcode.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.util.Log;
import android.webkit.WebView;

import com.example.zhangruirui.lifetips.R;
import com.example.zhangruirui.lifetips.leetcode.model.ProblemParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xml.sax.XMLReader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ProblemDetailActivity extends AppCompatActivity {

  private static String TAG = "ProblemDetailActivity";

  private WebView mProblemDetailWebView;

  private String html;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_problem_detail);

    mProblemDetailWebView = findViewById(R.id.problem_detail_view);
  }

  @Override
  protected void onResume() {
    super.onResume();
    new GetProblemDetailAsyncTask(this).execute("https://leetcode.com/problems/zigzag-conversion/");
  }

  private class GetProblemDetailAsyncTask extends AsyncTask<String, Void, String> {

    private Context mContext;

    GetProblemDetailAsyncTask(Context context) {
      mContext = context;
    }

    @Override
    public String doInBackground(String... uriString) {

      try {
        Document document = Jsoup.connect(uriString[0]).get();
        html = ProblemParser.parseDetailProblem(document);
        return html;

      } catch (IOException e) {
        Log.d(TAG, e.getMessage());
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
        Log.d(TAG, e.getMessage());
      }


      mProblemDetailWebView.loadUrl("file://" + cachedDir.getAbsolutePath() + "/1.html");
    }
  }

  private class MyTagHandler implements Html.TagHandler {

    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
      int start = 0;
      int end = 0;

      if (tag.equalsIgnoreCase("pre")) {
        if (opening) {
          start = output.length();
        } else {
          end = output.length();
          Log.d(TAG, "pre conent: " + html.substring(start, end));
        }

      }
    }
  }
}
