package com.bugfree.zhangruirui.shape_draw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.View;

/**
 * 主 view
 * 其他画图工具 view 类都继承了该自定义 view
 * <p>
 * 在主 view 类中定义了统一的 paint、bitmap、canvas
 * 以及子类中需要用到的3个点 downPoint,movePoint,upPoint
 */

public class DrawUtil extends View {

  // public 类型，子类需要用到
  public Point downPoint, movePoint, upPoint; // 起点、拖动点
  public Paint paint; // 画笔
  public Canvas canvas; // 画布
  public Bitmap bitmap; // 位图
  public int downState;
  public int moveState;

  public DrawUtil(Context context) {
    super(context);

    paint = new Paint(Paint.DITHER_FLAG); // 创建一个画笔
    bitmap = Bitmap.createBitmap(480, 700, Bitmap.Config.ARGB_8888); // 设置位图的宽高
    canvas = new Canvas(bitmap);

    // 设置画笔
    paint.setStyle(Paint.Style.STROKE); // 设置非填充
    paint.setStrokeWidth(5); // 笔宽5像素
    paint.setColor(Color.RED); // 设置为红笔
    paint.setAntiAlias(true); // 锯齿不显示

    downPoint = new Point();
    movePoint = new Point();
    upPoint = new Point();

  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    canvas.drawBitmap(bitmap, 0, 0, null);
  }

}
