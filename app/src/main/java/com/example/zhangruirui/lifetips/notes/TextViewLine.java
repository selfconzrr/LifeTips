package com.example.zhangruirui.lifetips.notes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

/**
 * @author zhangruirui
 * email：1138517609@qq.com
 * GitHub：https://github.com/selfconzrr
 * Blog：http://blog.csdn.net/u011489043
 * Date：11/18/18
 */
public class TextViewLine extends android.support.v7.widget.AppCompatTextView {
  private Paint mPaint = new Paint();
  // 禁止在自定义的 View 的 onMeasure、onLayout、onDraw 方法中做 new 对象的操作
  // 因为这些方法会被多次调用，进而导致大量对象被创建，引起频繁的 gc 操作，影响性能
  private float[] mArrayOfFloat = new float[4];

  public TextViewLine(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.mPaint.setColor(-16777216);
    this.mPaint.setStyle(Paint.Style.STROKE);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    int i = getLineCount();
    for (int j = 0; ; ++j) {
      if (j >= i) {
        super.onDraw(canvas);
        return;
      }
      mArrayOfFloat[0] = 15.0F;
      mArrayOfFloat[1] = ((j + 1) * getLineHeight());
      mArrayOfFloat[2] = (-20 + getWidth());
      mArrayOfFloat[3] = ((j + 1) * getLineHeight());
      canvas.drawLines(mArrayOfFloat, this.mPaint);
    }
  }
}
