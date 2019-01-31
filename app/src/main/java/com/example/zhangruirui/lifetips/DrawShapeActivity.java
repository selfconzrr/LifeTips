package com.example.zhangruirui.lifetips;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;

import com.bugfree.zhangruirui.shape_draw.Draw_Circle;
import com.bugfree.zhangruirui.shape_draw.Draw_Column;
import com.bugfree.zhangruirui.shape_draw.Draw_Cube;
import com.bugfree.zhangruirui.shape_draw.Draw_Line;
import com.bugfree.zhangruirui.shape_draw.Draw_Path;
import com.bugfree.zhangruirui.shape_draw.Draw_Rectangle;
import com.bugfree.zhangruirui.shape_draw.Draw_triangle;
import com.bugfree.zhangruirui.slideback.SlideBackHelper;
import com.bugfree.zhangruirui.slideback.SlideBackLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DrawShapeActivity extends AppCompatActivity {

  @BindView(R.id.select_shape)
  Button mSelectShape; // 选择图形的按钮

  // 各种画图工作类：直线、矩形、圆等等
  private Draw_Line mLine;
  private Draw_Rectangle mRectangle;
  private Draw_Circle mCircle;
  private Draw_triangle mTriangle;
  private Draw_Cube mCube;
  private Draw_Column mColumn;
  private Draw_Path mPath;

  /* 设置每个 view 的布局大小
   * This set of layout parameters defaults the width and the height
   *
   * LayoutParams.MATCH_PARENT 和 xml 中定义控件大小的 match_parent 属性一样
   * height：设置为 700，如果设置为 match_parent、wrap_content 则 view 会占满全屏挡住 mSelectShape 按钮控件
   */

  LayoutParams mLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, 700);

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_draw_shape);
    ButterKnife.bind(this);

    SlideBackLayout mirrorSwipeBackLayout = SlideBackHelper.attach(this, R.layout
        .swipe_back);
    // mMirrorSwipeBackLayout.setRightSwipeEnable(true);
    // mMirrorSwipeBackLayout.setLeftSwipeEnable(true);
    mirrorSwipeBackLayout.setSwipeBackListener(this::finish);
  }

  @OnClick(R.id.select_shape)
  public void onClick() {
    /*
     * 单击按钮后会弹出选择图形对话框
     */
    final String[] mItems = {"直线", "矩形", "圆形", "三角形", "立方体", "圆柱体", "涂鸦"};
    AlertDialog.Builder builder = new AlertDialog.Builder(DrawShapeActivity.this);

    builder.setTitle("选择形状");
    builder.setItems(mItems, (dialog, which) -> {
      // 选择后，根据选择的内容实例化相应的类
      switch (which) {
        case 0: // 直线
          mLine = new Draw_Line(getApplicationContext());
          // 将直线 view 类加入到当前 activity
          addContentView(mLine, mLayoutParams);
          break;

        case 1:
          mRectangle = new Draw_Rectangle(getApplicationContext());
          addContentView(mRectangle, mLayoutParams);
          break;

        case 2:
          mCircle = new Draw_Circle(getApplicationContext());
          addContentView(mCircle, mLayoutParams);
          break;

        case 3:
          mTriangle = new Draw_triangle(getApplicationContext());
          addContentView(mTriangle, mLayoutParams);
          break;

        case 4:
          mCube = new Draw_Cube(getApplicationContext());
          addContentView(mCube, mLayoutParams);
          break;

        case 5:
          mColumn = new Draw_Column(getApplicationContext());
          addContentView(mColumn, mLayoutParams);
          break;

        default:
          mPath = new Draw_Path(getApplicationContext());
          addContentView(mPath, mLayoutParams);
          break;
      }
    });
    builder.create().show();
  }

}
