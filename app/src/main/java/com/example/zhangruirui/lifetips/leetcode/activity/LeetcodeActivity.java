package com.example.zhangruirui.lifetips.leetcode.activity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.zhangruirui.lifetips.R;
import com.example.zhangruirui.lifetips.leetcode.adapter.ProblemListAdapter;
import com.example.zhangruirui.lifetips.leetcode.model.Problem;
import com.example.zhangruirui.lifetips.leetcode.model.ProblemParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zhangruirui
 * email：1138517609@qq.com
 * GitHub：https://github.com/selfconzrr
 * Blog：http://blog.csdn.net/u011489043
 * Date：12/01/18
 * <p>
 * 剑指 Offer 试题的展示界面
 */
public class LeetcodeActivity extends AppCompatActivity {

  @BindView(R.id.rev_problems)
  RecyclerView mRecyclerView;

  ProblemListAdapter mProblemListAdapter;
  List<Problem> mAllProblems;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_leetcode);
    ButterKnife.bind(this);

    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    mRecyclerView.setLayoutManager(linearLayoutManager);

    loadData(); // 初次加载该界面时 实时加载最新的试题数据
  }

  private void loadData() {
    new GetProblemAsyncTask().execute("https://www.nowcoder" +
        ".com/ta/coding-interviews?query=&asc=true&order=&page=1");
    new GetProblemAsyncTask().execute("https://www.nowcoder" +
        ".com/ta/coding-interviews?query=&asc=true&order=&page=2");
    new GetProblemAsyncTask().execute("https://www.nowcoder" +
        ".com/ta/coding-interviews?query=&asc=true&order=&page=3");
    new GetProblemAsyncTask().execute("https://www.nowcoder" +
        ".com/ta/coding-interviews?query=&asc=true&order=&page=4");
  }

  @SuppressLint("StaticFieldLeak")
  private class GetProblemAsyncTask extends AsyncTask<String, Void, List<Problem>> {

    @Override
    public List<Problem> doInBackground(String... uriString) {
      List<Problem> problems;
      try {
        Document document = Jsoup.connect(uriString[0]).get();
        problems = ProblemParser.parseProblems(document); // 对 HTML 文档进行解析

      } catch (IOException e) {
        Log.e("zhangrr", "doInBackground() called with: error = [" + e.getMessage() + "]");
        return null;
      }

      return problems;
    }

    public void onPostExecute(List<Problem> problems) {
      if (problems == null || problems.size() == 0) {
        Log.e("zhangrr", "problems is empty");
        return;
      }
      if (mAllProblems == null || mAllProblems.size() == 0) {
        mAllProblems = problems;
      } else {
        mAllProblems.addAll(problems);
        Log.e("zhangrr", "onPostExecute() called with: mAllProblems = " + mAllProblems.size());
      }
      mProblemListAdapter = new ProblemListAdapter(getBaseContext(), mAllProblems);
      mRecyclerView.setAdapter(mProblemListAdapter);
      mProblemListAdapter.notifyDataSetChanged();
    }

  }

}
