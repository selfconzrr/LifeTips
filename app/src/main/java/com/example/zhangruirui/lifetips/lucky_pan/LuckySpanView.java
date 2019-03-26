package com.example.zhangruirui.lifetips.lucky_pan;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.zhangruirui.lifetips.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LuckySpanView extends View {

  private Paint mPaint;
  private float mStrokeWidth = 5;
  private int mRepeatCount = 5; // 转的圈数
  private int mRectSize; // 矩形的宽和高（矩形为正方形）
  private boolean mShouldStartFlag;
  private boolean mShouldStartNextTurn = true; // 标记是否应该开启下一轮抽奖
  private int mStartLuckPosition = 0; // 开始抽奖的位置
  private int mCurrentPosition = -1; // 当前转圈所在的位置

  private OnLuckAnimationEndListener mLuckAnimationEndListener;

  /**
   * 可以通过对 mLuckNum 设置计算策略，来控制用户 中哪些奖 以及 中大奖 的概率
   */
  private int mLuckNum = 3; // 默认最终中奖位置

  private List<RectF> mRectFs; // 存储矩形的集合
  private int[] mItemColor = {Color.GREEN, Color.YELLOW}; // 矩形的颜色
  private String[] mPrizeDescription = {"豆腐", "鸡腿", "米饭", "卷心菜", "南瓜", "糖葫芦", "大虾", "香肠", "Go"};
  private int[] mLuckyPrizes = {R.drawable.ic_df, R.drawable.ic_jt, R.drawable.ic_mf, R.drawable
      .ic_scjx, R.drawable.ic_scng, R.drawable.ic_thl, R.drawable.ic_x, R.drawable.ic_xc, R
      .drawable.ic_j};

  public LuckySpanView(Context context) {
    this(context, null);
  }

  public LuckySpanView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public LuckySpanView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    mPaint = new Paint(Paint.ANTI_ALIAS_FLAG); // 抗锯齿
    mPaint.setStyle(Paint.Style.FILL);
    // mPaint.setStyle(Paint.Style.STROKE); // 设置样式为描边
    mPaint.setStrokeWidth(mStrokeWidth); // 设置描边的宽度

    mRectFs = new ArrayList<>();
  }

  public void setLuckAnimationEndListener(OnLuckAnimationEndListener luckAnimationEndListener) {
    mLuckAnimationEndListener = luckAnimationEndListener;
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    mRectSize = Math.min(w, h) / 3; // 矩形的宽高

    mRectFs.clear(); // 当控件大小改变的时候清空数据
    initNineRect();
  }

  /**
   * 初始化 9 个矩形（正方形）的位置信息
   */
  private void initNineRect() {
    final float width = getWidth();

    // 加载前三个矩形
    for (int i = 0; i < 3; i++) {
      float left = i * mRectSize;
      float right = (i + 1) * mRectSize;
      float top = 0;
      float bottom = mRectSize;
      RectF rectF = new RectF(left, top, right, bottom);
      mRectFs.add(rectF);
    }

    // 加载第 4 个矩形
    mRectFs.add(new RectF(width - mRectSize, mRectSize, width, 2 * mRectSize));

    // 加载第 5~7 个矩形
    for (int j = 3; j > 0; j--) {
      float left = width - (4 - j) * mRectSize;
      float right = width - (3 - j) * mRectSize;
      float top = 2 * mRectSize;
      float bottom = 3 * mRectSize;
      RectF rectF = new RectF(left, top, right, bottom);
      mRectFs.add(rectF);
    }

    // 加载第 8 个矩形
    mRectFs.add(new RectF(0, mRectSize, mRectSize, 2 * mRectSize));

    // 加载中心第 9 个矩形
    mRectFs.add(new RectF(mRectSize, mRectSize, 2 * mRectSize, 2 * mRectSize));
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    drawNineRect(canvas);
    // drawNineText(canvas); // 填充奖品文字
    drawNineBitmaps(canvas); // 填充奖品图片
  }

  /**
   * 在每个矩形中填充奖品图片
   * left：The position of the left side of the bitmap being drawn
   * top：The position of the top side of the bitmap being drawn
   */
  private void drawNineBitmaps(Canvas canvas) {
    for (int i = 0; i < mRectFs.size(); i++) {
      RectF rectF = mRectFs.get(i);
      float left = rectF.left + mRectSize / 4; // 将图片设置在每个矩形中央
      float top = rectF.top + mRectSize / 4;
      canvas.drawBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
          mLuckyPrizes[i]), mRectSize / 2, mRectSize / 2, false), left, top, null);
    }
  }

  /**
   * 在每个矩形中央填充文字，代替抽奖图片
   * x：he x-coordinate of the origin of the text being drawn
   * y：The y-coordinate of the baseline of the text being drawn
   */
  private void drawNineText(Canvas canvas) {
    for (int i = 0; i < mRectFs.size(); i++) {
      RectF rectF = mRectFs.get(i);
      float x = rectF.left + mRectSize / 4; // 将文字设置在每个矩形中央
      float y = rectF.top + mRectSize / 2;
      mPaint.setColor(Color.RED);
      mPaint.setStyle(Paint.Style.FILL);
      mPaint.setTextSize(40); // unit px
      canvas.drawText(mPrizeDescription[i], x, y, mPaint);
    }
  }

  /**
   * 执行真正的绘制矩形操作
   */
  private void drawNineRect(Canvas canvas) {
    for (int x = 0; x < mRectFs.size(); x++) {
      RectF rectF = mRectFs.get(x);
      if (x == 8) {
        mPaint.setColor(Color.WHITE);
      } else {
        if (mCurrentPosition == x) {
          mPaint.setColor(Color.BLUE);
        } else {
          mPaint.setColor(mItemColor[x % 2]); // 标记当前转盘经过的位置
        }
      }
      canvas.drawRect(rectF, mPaint);
    }
  }

  @SuppressLint("ClickableViewAccessibility")
  @Override
  public boolean onTouchEvent(MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_DOWN) {
      mShouldStartFlag = mRectFs.get(8).contains(event.getX(), event.getY());
      return true;
    }
    if (event.getAction() == MotionEvent.ACTION_UP) {
      if (mShouldStartFlag) {
        if (mRectFs.get(8).contains(event.getX(), event.getY())) {
          startAnim(); // 判断只有手指落下和抬起都在中间的矩形内时才开始执行动画抽奖
        }
        mShouldStartFlag = false;
      }
    }
    return super.onTouchEvent(event);
  }

  private void startAnim() {
    if (!mShouldStartNextTurn) {
      return;
    }

    Random random = new Random();
    mLuckNum = random.nextInt(8); // 生成 [0,8) 的随机整数

    ValueAnimator animator = ValueAnimator.ofInt(mStartLuckPosition, mRepeatCount * 8 + mLuckNum)
        .setDuration(5000);

    animator.addUpdateListener(animation -> {
      final int position = (int) animation.getAnimatedValue();
      setCurrentPosition(position % 8);
      mShouldStartNextTurn = false;
    });

    animator.addListener(new AnimatorListenerAdapter() {
      @Override
      public void onAnimationEnd(Animator animation) {
        mStartLuckPosition = mLuckNum;
        Log.e("zhangrr", "onAnimationEnd() called with: mLuckNum = " + mLuckNum + " mPosition = "
            + mCurrentPosition);
        if (mLuckAnimationEndListener != null) {
          mLuckAnimationEndListener.onLuckAnimationEnd(mCurrentPosition,
              mPrizeDescription[mCurrentPosition]);
        }
        mShouldStartNextTurn = true;
      }
    });

    animator.start();
  }

  private void setCurrentPosition(int position) {
    mCurrentPosition = position;
    invalidate(); // 强制刷新，在 UI 线程回调 onDraw()
  }

  /**
   * 用于抽奖结果回调
   */
  public interface OnLuckAnimationEndListener {
    void onLuckAnimationEnd(int pos, String msg);
  }
}
