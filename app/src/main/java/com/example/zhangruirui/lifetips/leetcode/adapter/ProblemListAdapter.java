package com.example.zhangruirui.lifetips.leetcode.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhangruirui.lifetips.R;
import com.example.zhangruirui.lifetips.leetcode.activity.ProblemDetailActivity;
import com.example.zhangruirui.lifetips.leetcode.model.Problem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zhangruirui
 * email：1138517609@qq.com
 * GitHub：https://github.com/selfconzrr
 * Blog：http://blog.csdn.net/u011489043
 * Date：11/30/18
 * <p>
 * 用于展示题目列表的 Adapter
 */
public class ProblemListAdapter extends RecyclerView.Adapter<ProblemListAdapter.ProblemItem> {

  private Context mContext;
  private List<Problem> mProblems;

  public ProblemListAdapter(Context context, List<Problem> problems) {
    this.mContext = context;
    this.mProblems = problems;
  }

  @NonNull
  @Override
  public ProblemItem onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View view = View.inflate(mContext, R.layout.item_problem, null);
    return new ProblemItem(view);
  }

  @SuppressLint({"DefaultLocale", "SetTextI18n"})
  @Override
  public void onBindViewHolder(@NonNull ProblemItem problemItem, int i) {
    final Problem problem = mProblems.get(i);
    problemItem.tvTitle.setText(problem.getTitle());
    problemItem.tvKnowledge.setText(problem.getKnowledge_point());
    problemItem.tvAcceptRate.setText("Accept Rate: " + problem.getAcceptance());
    problemItem.tvHotIndex.setText("Hot Index: " + problem.getHot_index());

    problemItem.view.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        final Intent intent = new Intent(mContext, ProblemDetailActivity.class);
        intent.putExtra("PUrl", problem.getUrl()); // 将题目网页传给试题详情页进行展示
        intent.putExtra("PTitle", problem.getTitle());
        Log.e("zhangrr", "put:" + problem.getUrl() + " title = " + problem.getTitle());
        mContext.startActivity(intent);
      }
    });
  }

  @Override
  public int getItemCount() {
    return mProblems.size();
  }

  class ProblemItem extends RecyclerView.ViewHolder {

    View view;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.tv_knowledge)
    TextView tvKnowledge;

    @BindView(R.id.tv_accept_rate)
    TextView tvAcceptRate;

    @BindView(R.id.tv_hot_index)
    TextView tvHotIndex;

    ProblemItem(View itemView) {
      super(itemView);
      view = itemView;
      ButterKnife.bind(this, itemView);
      tvTitle = itemView.findViewById(R.id.tv_title);
    }
  }
}
