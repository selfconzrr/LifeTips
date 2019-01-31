package com.bugfree.zhangruirui.shape_draw;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;

/**
 * 画圆
 * <p>
 * 无论拖动也好，拉伸也好，其实都是重新画圆，只是不改变某些属性的情况下，看起来就行是移动的
 */
public class Draw_Circle extends DrawUtil {

  private Point mRDotsPoint; // 圆心
  private int mRadius = 0; // 半径

  public Draw_Circle(Context context) {
    super(context);
    mRDotsPoint = new Point();
  }

  @SuppressLint("ClickableViewAccessibility")
  @Override
  public boolean onTouchEvent(MotionEvent event) {
    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
        // 获取起点坐标
        downPoint.set((int) event.getX(), (int) event.getY());

        if (mRadius != 0) {
          // 计算当前所点击的点到圆心的距离
          Double distance = Math.sqrt((downPoint.x - mRDotsPoint.x)
              * (downPoint.x - mRDotsPoint.x)
              + (downPoint.y - mRDotsPoint.y)
              * (downPoint.y - mRDotsPoint.y));
          // 如果距离  在半径减20和加20个范围内，则认为用户点击在圆上
          if (distance >= mRadius - 20 && distance <= mRadius + 20) {
            Log.e("zhangrr", "onTouchEvent() called with: ACTION_DOWN = 在圆上");
            downState = 1;
            // 如果距离小于半径，则认为用户点击在圆内
          } else if (distance < mRadius) {
            Log.e("zhangrr", "onTouchEvent() called with: ACTION_DOWN = 在圆内");
            downState = -1;
            // 在圆外
          } else if (distance > mRadius) {
            Log.e("zhangrr", "onTouchEvent() called with: ACTION_DOWN = 在圆外");
            downState = 0;
          }
        } else {
          downState = 0;
        }
        break;

      case MotionEvent.ACTION_MOVE:
        // 获取当前拖动点坐标
        movePoint.set((int) event.getX(), (int) event.getY());

        switch (downState) {
          case 1:
            // 在圆上，圆心不变，重新计算半径
            mRadius = (int) Math.sqrt((movePoint.x - mRDotsPoint.x)
                * (movePoint.x - mRDotsPoint.x)
                + (movePoint.y - mRDotsPoint.y)
                * (movePoint.y - mRDotsPoint.y));
            break;

          case -1:
            // 在圆内，半径不变，改变其圆心坐标
            mRDotsPoint.x = movePoint.x;
            mRDotsPoint.y = movePoint.y;
            break;

          default:
            // 在圆外，重新设置圆心坐标、半径。画圆
            mRDotsPoint.set(downPoint.x, downPoint.y);
            mRadius = (int) Math.sqrt((movePoint.x - mRDotsPoint.x)
                * (movePoint.x - mRDotsPoint.x)
                + (movePoint.y - mRDotsPoint.y)
                * (movePoint.y - mRDotsPoint.y));
            break;
        }
        /*
         * 拖动过程中不停的将 bitmap 的颜色设置为透明
         * 否则，整个拖动过程的轨迹都会画出来
         */
        bitmap.eraseColor(Color.TRANSPARENT);
        // 根据圆心和半径重新画圆
        canvas.drawCircle(mRDotsPoint.x, mRDotsPoint.y, mRadius, paint);
        invalidate();
    }
    return true;
  }
}
