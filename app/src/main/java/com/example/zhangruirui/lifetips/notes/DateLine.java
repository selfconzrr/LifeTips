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
public class DateLine extends android.support.v7.widget.AppCompatTextView {
  private Paint mPaint = new Paint();

  public DateLine(Context context) {
    super(context);
  }

  public DateLine(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.mPaint.setColor(-16777216);
    this.mPaint.setStyle(Paint.Style.STROKE);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    canvas.drawLine(0.0F, 40.0F, getWidth(), 40.0F, this.mPaint);
    super.onDraw(canvas);
  }
}
