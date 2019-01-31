package com.bugfree.zhangruirui.shape_draw;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

/**
 * 矩形
 * <p>
 * 思路：
 * 1、画矩形
 * 2、将矩形左上角的顶点始终设置为 point1 点，矩形右上角的顶点始终设置为 point2 点
 * 3、移动、拉伸后，执行步骤2
 */

public class Draw_Rectangle extends DrawUtil {

  // 矩形的4个顶点和中心点（用来移动）
  private Point point1, point2, point3, point4, cenPoint;
  private Rect point1Rect, point2Rect, point3Rect, point4Rect;
  Rect rect;

  public Draw_Rectangle(Context context) {
    super(context);

    point1 = new Point();
    point2 = new Point();
    point3 = new Point();
    point4 = new Point();
    cenPoint = new Point();

    rect = new Rect();
    point1Rect = null;
    point2Rect = null;
    point3Rect = null;
    point4Rect = null;
  }

  @SuppressLint("ClickableViewAccessibility")
  @Override
  public boolean onTouchEvent(MotionEvent event) {

    switch (event.getAction()) {

      case MotionEvent.ACTION_DOWN:
        // 获取起点坐标
        downPoint.set((int) event.getX(), (int) event.getY());

        /*
         * 判断以矩形顶点 point2 为中心的小矩形是否为空，
         * 为什么要判断 point2Rect 而不是 point1Rect？
         * 如果用户在当前页面只点击一下，也会产生 point1Rect 而不会产生 point2Rect。
         * 所以如果只有 point1Rect 而没有 point2Rect 判断是没有意义的
         * 而如果 point2Rect != null，则当前页面已经有画好的矩形了
         */
        if (point2Rect != null) {
          // 判断用户所点击的点是否在以矩形顶点 point1 为中心的矩形 point1Rect 内，
          // 如果在这个矩形内，则我们认为用户点击了该点
          if (point1Rect.contains(downPoint.x, downPoint.y)) {
            downState = 1;
            Log.e("zhangrr", "downState = 1");
          } else if (point2Rect.contains(downPoint.x, downPoint.y)) {
            downState = 2;
            Log.e("zhangrr", "downState = 2");
          } else if (point3Rect.contains(downPoint.x, downPoint.y)) {
            downState = 3;
            Log.e("zhangrr", "downState = 3");
          } else if (point4Rect.contains(downPoint.x, downPoint.y)) {
            downState = 4;
            Log.e("zhangrr", "downState = 4");
          } else if (rect.contains(downPoint.x, downPoint.y)) {
            downState = 5;
            Log.e("zhangrr", "downState = 5");
          } else {
            downState = 0;
            Log.e("zhangrr", "downState = 0");
          }
        }
        break;

      // 拖动
      case MotionEvent.ACTION_MOVE:
        // 获取当前拖动点坐标
        movePoint.set((int) event.getX(), (int) event.getY());

        // 根据用户所点击的坐标点画相应的矩形
        switch (downState) {
          case 1:
            // 点击点 point1，则 point2 不变；根据 point1、point2 重新设置点 point3,point4
            point1.set(movePoint.x, movePoint.y);
            point3.set(point1.x, point2.y);
            point4.set(point2.x, point1.y);
            break;

          case 2:
            // 点击点 point2，则 point1 不变；根据 point1、point2 重新设置点 point3,point4
            point2.set(movePoint.x, movePoint.y);
            point3.set(point1.x, point2.y);
            point4.set(point2.x, point1.y);
            break;

          case 3:
            // 点击点 point3，则重新设置矩形点 point3、1、2
            point3.set(movePoint.x, movePoint.y);
            point1.set(point3.x, point4.y);
            point2.set(point4.x, point3.y);
            break;

          case 4:
            // 点击点 point4，则重新设置矩形点 point4、1、2
            point4.set(movePoint.x, movePoint.y);
            point1.set(point3.x, point4.y);
            point2.set(point4.x, point3.y);
            break;

          case 5:
            // 计算矩形中间点
            cenPoint.x = (point1.x + point2.x) / 2;
            cenPoint.y = (point1.y + point2.y) / 2;

            // 移动距离
            int movedX = movePoint.x - cenPoint.x;
            int movedY = movePoint.y - cenPoint.y;

            // 重新设置矩形4个顶点的坐标
            point1.x = point1.x + movedX;
            point1.y = point1.y + movedY;
            point2.x = point2.x + movedX;
            point2.y = point2.y + movedY;
            point3.set(point1.x, point2.y);
            point4.set(point2.x, point1.y);
            break;
          default:
            getStartPoint();
            break;
        }
        // 每次拖动完之后需要重新设定4个顶点小矩形
        setRect();
        bitmap.eraseColor(Color.TRANSPARENT);
        // 画矩形
        canvas.drawRect(rect, paint);
        invalidate();
        break;
    }

    return true;
  }

  // 判断用户开始画矩形点
  public void getStartPoint() {

    if (downPoint.x < movePoint.x && downPoint.y < movePoint.y) {
      point1.set(downPoint.x, downPoint.y);
      point2.set(movePoint.x, movePoint.y);
      point3.set(point1.x, point2.y);
      point4.set(point2.x, point1.y);
    } else if (downPoint.x < movePoint.x && downPoint.y > movePoint.y) {
      point3.set(downPoint.x, downPoint.y);
      point4.set(movePoint.x, movePoint.y);
      point1.set(point3.x, point4.y);
      point2.set(point4.x, point3.y);
    } else if (downPoint.x > movePoint.x && downPoint.y > movePoint.y) {
      point2.set(downPoint.x, downPoint.y);
      point1.set(movePoint.x, movePoint.y);
      point3.set(point1.x, point2.y);
      point4.set(point2.x, point1.y);
    } else if (downPoint.x > movePoint.x && downPoint.y < movePoint.y) {
      point4.set(downPoint.x, downPoint.y);
      point3.set(movePoint.x, movePoint.y);
      point1.set(point3.x, point4.y);
      point2.set(point4.x, point3.y);
    }

    setRect();

  }

  public void setRect() {
    // 设置以矩形的4个顶点为中心的小矩形
    point1Rect = new Rect(point1.x - 30, point1.y - 30, point1.x + 30,
        point1.y + 30);
    point2Rect = new Rect(point2.x - 30, point2.y - 30, point2.x + 30,
        point2.y + 30);
    point3Rect = new Rect(point3.x - 30, point3.y - 30, point3.x + 30,
        point3.y + 30);
    point4Rect = new Rect(point4.x - 30, point4.y - 30, point4.x + 30,
        point4.y + 30);

    rect.set(point1.x, point1.y, point2.x, point2.y);

  }
}
