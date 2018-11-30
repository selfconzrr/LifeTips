package com.example.zhangruirui.lifetips.leetcode.activity;

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

public class LeetcodeActivity extends AppCompatActivity {
  @BindView(R.id.rev_problems)
  RecyclerView revProblems;

  ProblemListAdapter problemListAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_leetcode);
    ButterKnife.bind(this);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    revProblems.setLayoutManager(linearLayoutManager);
  }

  @Override
  protected void onResume() {
    super.onResume();
    new GetProblemAsyncTask().execute("https://www.nowcoder.com/ta/coding-interviews");
  }

  private class GetProblemAsyncTask extends AsyncTask<String, Void, List<Problem>> {

    @Override
    public List<Problem> doInBackground(String... uriString) {
      List<Problem> problems;
      try {
        Document document = Jsoup.connect(uriString[0]).get();
        problems = ProblemParser.parseProblems(document);

      } catch (IOException e) {
        Log.e("zhangrr", "doInBackground() called with: error = [" + e.getMessage() + "]");
        return null;
      }

      return problems;
    }

    public void onPostExecute(List<Problem> problems) {
      if (problems == null || problems.size() == 0) {
        Log.d("zhangrr", "problems is empty");
      }
      problemListAdapter = new ProblemListAdapter(getBaseContext(), problems);
      revProblems.setAdapter(problemListAdapter);
      problemListAdapter.notifyDataSetChanged();
    }

  }

}
