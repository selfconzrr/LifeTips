package com.example.zhangruirui.lifetips.leetcode.activity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.bugfree.zhangruirui.slideback.SlideBackHelper;
import com.bugfree.zhangruirui.slideback.SlideBackLayout;
import com.example.zhangruirui.lifetips.R;
import com.example.zhangruirui.lifetips.demo_learning.launchmode.BasicActivity;
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
@SuppressWarnings("ConstantConditions")
public class LeetcodeActivity extends BasicActivity {

  @BindView(R.id.rev_problems)
  RecyclerView mRecyclerView;

  ProblemListAdapter mProblemListAdapter;
  List<Problem> mAllProblems;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_leetcode);
    ButterKnife.bind(this);

    SlideBackLayout mirrorSwipeBackLayout = SlideBackHelper.attach(this, R.layout
        .swipe_back);
    // mMirrorSwipeBackLayout.setRightSwipeEnable(true);
    // mMirrorSwipeBackLayout.setLeftSwipeEnable(true);
    mirrorSwipeBackLayout.setSwipeBackListener(this::finish);

    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    mRecyclerView.setLayoutManager(linearLayoutManager);

    DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration
        .VERTICAL);
    itemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_decor));

    mRecyclerView.addItemDecoration(itemDecoration);
    mRecyclerView.post(() -> Log.e("zhangrr", "onCreate() called with: savedInstanceState = [" + mRecyclerView.getWidth() + "]"));
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
