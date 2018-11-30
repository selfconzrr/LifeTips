package com.example.zhangruirui.lifetips.leetcode.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
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

public class ProblemListAdapter extends RecyclerView.Adapter<ProblemListAdapter.ProblemItem> {

  private Context context;
  private List<Problem> problems;

  public ProblemListAdapter(Context context, List<Problem> problems) {
    this.context = context;
    this.problems = problems;
  }

  @NonNull
  @Override
  public ProblemItem onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View view = View.inflate(context, R.layout.item_problem, null);
    return new ProblemItem(view);
  }

  @SuppressLint("DefaultLocale")
  @Override
  public void onBindViewHolder(@NonNull ProblemItem problemItem, int i) {
    final Problem problem = problems.get(i);
    problemItem.tvTitle.setText(problem.getTitle());
    problemItem.tvAcceptRate.setText("Accept Rate: " + problem.getAcceptance());
    problemItem.tvDifficulty.setText("Difficulty: " + problem.getHot_degree());
    problemItem.view.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(context, ProblemDetailActivity.class);
        intent.putExtra("Pid", problem.getId());
        Log.i("loadProblem", "put:" + problem.getId());
        context.startActivity(intent);
      }
    });
    Log.i("onBindViewHolder", "" + i);
  }

  @Override
  public int getItemCount() {
    return problems.size();
  }

  class ProblemItem extends RecyclerView.ViewHolder {

    View view;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.tv_accept_rate)
    TextView tvAcceptRate;

    @BindView(R.id.tv_difficulty)
    TextView tvDifficulty;

    ProblemItem(View itemView) {
      super(itemView);
      view = itemView;
      ButterKnife.bind(this, itemView);
      tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
    }
  }

  public static class DividerLine extends RecyclerView.ItemDecoration {
    /**
     * 水平方向
     */
    public static final int HORIZONTAL = LinearLayoutManager.HORIZONTAL;

    /**
     * 垂直方向
     */
    public static final int VERTICAL = LinearLayoutManager.VERTICAL;

    // 画笔
    private Paint paint;

    // 布局方向
    private int orientation;
    // 分割线颜色
    private int color;
    // 分割线尺寸
    private int size;

    public DividerLine() {
      this(VERTICAL);
    }

    public DividerLine(int orientation) {
      this.orientation = orientation;

      paint = new Paint();
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
      super.onDrawOver(c, parent, state);

      if (orientation == VERTICAL) {
        drawHorizontal(c, parent);
      } else {
        drawVertical(c, parent);
      }
    }

    /**
     * 设置分割线颜色
     *
     * @param color 颜色
     */
    public void setColor(int color) {
      this.color = color;
      paint.setColor(color);
    }

    /**
     * 设置分割线尺寸
     *
     * @param size 尺寸
     */
    public void setSize(int size) {
      this.size = size;
    }

    // 绘制垂直分割线
    protected void drawVertical(Canvas c, RecyclerView parent) {
      final int top = parent.getPaddingTop();
      final int bottom = parent.getHeight() - parent.getPaddingBottom();

      final int childCount = parent.getChildCount();
      for (int i = 0; i < childCount; i++) {
        final View child = parent.getChildAt(i);
        final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
            .getLayoutParams();
        final int left = child.getRight() + params.rightMargin;
        final int right = left + size;

        c.drawRect(left, top, right, bottom, paint);
      }
    }

    // 绘制水平分割线
    protected void drawHorizontal(Canvas c, RecyclerView parent) {
      final int left = parent.getPaddingLeft();
      final int right = parent.getWidth() - parent.getPaddingRight();

      final int childCount = parent.getChildCount();
      for (int i = 0; i < childCount; i++) {
        final View child = parent.getChildAt(i);
        final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
            .getLayoutParams();
        final int top = child.getBottom() + params.bottomMargin;
        final int bottom = top + size;

        c.drawRect(left, top, right, bottom, paint);
      }
    }
  }
}
