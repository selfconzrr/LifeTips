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
import java.util.Random;

@SuppressWarnings("FieldCanBeLocal")
public class LuckySpanView extends View {
  private Paint mPaint;
  private ArrayList<RectF> mRectFs; // 存储矩形的集合
  private float mStrokeWidth = 5; // 矩形的描边宽度
  private int[] mItemColor = {Color.GREEN, Color.YELLOW}; // 矩形的颜色
  private int mRectSize; // 矩形的宽和高（矩形为正方形）
  private boolean mClickStartFlag = false; // 是否点击中间矩形的标记

  private int mRepeatCount = 5; // 转的圈数

  /**
   * 可以通过对 mLuckNum 设置计算策略，来控制用户中哪些奖以及中大奖的概率
   */
  private int mLuckNum = 3; // 默认最终中奖位置
  private int mPosition = -1; // 抽奖块的位置
  private int mStartLuckPosition = 0; // 开始抽奖的位置

  private int[] mAvailablePrizes = {R.drawable.ic_df, R.drawable.ic_jt, R.drawable.ic_mf, R.drawable
      .ic_scjx, R.drawable.ic_scng, R.drawable.ic_thl, R.drawable.ic_x, R.drawable.ic_xc, R
      .drawable.ic_j};

  private String[] mLuckStr = {"豆腐", "鸡腿", "米饭", "卷心菜", "南瓜", "糖葫芦", "大虾", "香肠"};
  private OnLuckPanAnimEndListener onLuckPanAnimEndListener;

  public void setOnLuckPanAnimEndListener(OnLuckPanAnimEndListener onLuckPanAnimEndListener) {
    this.onLuckPanAnimEndListener = onLuckPanAnimEndListener;
  }

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

  /**
   * 初始化数据
   */
  private void init() {
    mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mPaint.setStyle(Paint.Style.FILL);
    mPaint.setStrokeWidth(mStrokeWidth);

    mRectFs = new ArrayList<>();
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    mRectSize = Math.min(w, h) / 3; // 获取矩形的宽和高
    mRectFs.clear(); // 当控件大小改变的时候清空数据
    initRect(); // 重新加载矩形数据
  }

  /**
   * 加载矩形数据
   */
  private void initRect() {
    // 加载前三个矩形
    for (int x = 0; x < 3; x++) {
      float left = x * mRectSize;
      float top = 0;
      float right = (x + 1) * mRectSize;
      float bottom = mRectSize;
      RectF rectF = new RectF(left, top, right, bottom);
      mRectFs.add(rectF);
    }
    // 加载第四个
    mRectFs.add(new RectF(getWidth() - mRectSize, mRectSize, getWidth(), mRectSize * 2));
    // 加载第 五~七 个
    for (int y = 3; y > 0; y--) {
      float left = getWidth() - (4 - y) * mRectSize;
      float top = mRectSize * 2;
      float right = (y - 3) * mRectSize + getWidth();
      float bottom = mRectSize * 3;
      RectF rectF = new RectF(left, top, right, bottom);
      mRectFs.add(rectF);
    }
    // 加载第八个
    mRectFs.add(new RectF(0, mRectSize, mRectSize, mRectSize * 2));
    // 加载第九个
    mRectFs.add(new RectF(mRectSize, mRectSize, mRectSize * 2, mRectSize * 2));
  }

  @SuppressLint("ClickableViewAccessibility")
  @Override
  public boolean onTouchEvent(MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_DOWN) {
      mClickStartFlag = mRectFs.get(8).contains(event.getX(), event.getY());
      return true;
    }
    if (event.getAction() == MotionEvent.ACTION_UP) {
      if (mClickStartFlag) {
        if (mRectFs.get(8).contains(event.getX(), event.getY())) {
          startAnim(); // 判断只有手指落下和抬起都在中间的矩形内才开始抽奖
        }
        mClickStartFlag = false;
      }
    }
    return super.onTouchEvent(event);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    drawRects(canvas);
    drawImages(canvas);
  }

  /**
   * 画图片
   */
  private void drawImages(Canvas canvas) {
    for (int x = 0; x < mRectFs.size(); x++) {
      RectF rectF = mRectFs.get(x);
      float left = rectF.centerX() - mRectSize / 4;
      float top = rectF.centerY() - mRectSize / 4;
      canvas.drawBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
          mAvailablePrizes[x]), mRectSize / 2, mRectSize / 2, false), left, top, null);
    }
  }

  /**
   * 画矩形
   */
  private void drawRects(Canvas canvas) {
    for (int x = 0; x < mRectFs.size(); x++) {
      RectF rectF = mRectFs.get(x);
      if (x == 8) {
        mPaint.setColor(Color.WHITE);
        canvas.drawRect(rectF, mPaint);
      } else {
        mPaint.setColor(mItemColor[x % 2]);
        if (mPosition == x) {
          mPaint.setColor(Color.BLUE); // 标记转盘路过的 矩形
        }
        canvas.drawRect(rectF, mPaint);
      }
    }
  }

  public void setPosition(int position) {
    mPosition = position;
    invalidate();
  }

  /**
   * 开始动画
   */
  private void startAnim() {
    Random random = new Random();
    mLuckNum = random.nextInt(8); // 生成 [0,8) 的随机整数

    ValueAnimator valueAnimator = ValueAnimator.ofInt(mStartLuckPosition, mRepeatCount * 8 +
        mLuckNum).setDuration(5000);

    valueAnimator.addUpdateListener(animation -> {
      int position = (int) animation.getAnimatedValue();
      setPosition(position % 8);
    });

    valueAnimator.addListener(new AnimatorListenerAdapter() {
      @Override
      public void onAnimationEnd(Animator animation) {
        mStartLuckPosition = mLuckNum;
        Log.e("zhangrr", "onAnimationEnd() called with: mLuckNum = " + mLuckNum + " mPosition = " + mPosition);
        if (onLuckPanAnimEndListener != null) {
          onLuckPanAnimEndListener.onAnimEnd(mPosition, mLuckStr[mPosition]);
        }
      }
    });
    valueAnimator.start();
  }

  public interface OnLuckPanAnimEndListener {
    void onAnimEnd(int position, String msg);
  }
}
