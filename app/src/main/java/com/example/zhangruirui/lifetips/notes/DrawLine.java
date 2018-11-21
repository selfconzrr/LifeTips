package com.example.zhangruirui.lifetips.notes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

/**
 * @author zhangruirui
 * email：1138517609@qq.com
 * GitHub：https://github.com/selfconzrr
 * Blog：http://blog.csdn.net/u011489043
 * Date：11/18/18
 */
public class DrawLine extends android.support.v7.widget.AppCompatEditText {
  private Paint mPaint;

  public DrawLine(Context context, AttributeSet attrs) {
    super(context, attrs);
    mPaint = new Paint();
    mPaint.setColor(Color.BLACK);
    mPaint.setStyle(Paint.Style.STROKE);
  }

  public void onDraw(Canvas canvas) {
    int count = getLineCount();
    for (int i = 0; i < count + 11; i++) {
      float[] pts = {15.0F, (i + 1) * getLineHeight(),
          this.getWidth() - 20.0F, (i + 1) * getLineHeight()};
      // canvas.drawLine(15, i*42, this.getWidth()-20,i*42, mPaint);
      canvas.drawLines(pts, mPaint);
    }
    super.onDraw(canvas);
  }
}
