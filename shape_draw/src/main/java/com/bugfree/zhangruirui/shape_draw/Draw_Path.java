package com.bugfree.zhangruirui.shape_draw;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.Log;
import android.view.MotionEvent;

/**
 * 涂鸦
 */

public class Draw_Path extends DrawUtil {

  private Path mPath;
  private float mX, mY;

  public Draw_Path(Context context) {
    super(context);

    mPath = new Path();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    canvas.drawPath(mPath, paint);
  }

  @SuppressLint("ClickableViewAccessibility")
  @Override
  public boolean onTouchEvent(MotionEvent event) {
    float x = event.getX();
    float y = event.getY();

    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
        onTouchDown(x, y);
        invalidate();
        break;

      case MotionEvent.ACTION_MOVE:
        onTouchMove(x, y);
        invalidate();
        break;

      case MotionEvent.ACTION_UP:
        onTouchUp(x, y);
        invalidate();
        break;

      default:
        break;
    }
    return true;
  }

  private void onTouchDown(float x, float y) {
    Log.e("paint----", "onTouchDown");
    mPath.reset();
    mPath.moveTo(x, y);
    mX = x;
    mY = y;
  }

  private void onTouchMove(float x, float y) {
    Log.e("paint---", "onTouchMove");

    float dx = Math.abs(x - mX);
    float dy = Math.abs(y - mY);

    if (dx > 0 || dy > 0) {
      mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
      mX = x;
      mY = y;
    } else if (dx == 0 || dy == 0) {
      mPath.quadTo(mX, mY, (x + 1 + mX) / 2, (y + 1 + mY) / 2);
      mX = x + 1;
      mY = y + 1;
    }
  }

  private void onTouchUp(float x, float y) {
    Log.e("paint----.", "onTouchUp");
    // mPath.lineTo(mX, mY);
    canvas.drawPath(mPath, paint);
    mPath.reset();
  }
}
