package com.example.zhangruirui.lifetips.fallingball;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.example.zhangruirui.lifetips.R;

/**
 * Date：2019/7/22
 * Author：zhangruirui
 * Wechat：18811227256
 * Description：可以拖动的 Ball
 * <p>
 * 1、记录 velocityX 和 velocityY 作为初始速度，之后不断让速度衰减，直至为零。
 * 2、根据速度和当前小球的位置计算一段时间后的位置，并在该位置重新绘制小球。
 * 3、判断小球边缘是否碰触控件边界，如果碰触了边界则让速度反向。
 */
public class FallingBallView extends View {
    private int mWidth;
    private int mHeight;

    private float mStartX = 0; // 小方块开始位置X
    private float mStartY = 0; // 小方块开始位置Y

    private float mEdgeLength = 200; // 边长
    private RectF mRect;
    private Rect mRect2;

    private float mFixedX = 0; // 修正距离X
    private float mFixedY = 0; // 修正距离Y

    private Paint mPaint;

    private GestureDetector mGestureDetector;
    private boolean mCanFail = false; // 是否可以拖动

    private float mSpeedX = 0; // 像素/s
    private float mSpeedY = 0;

    private Boolean mXFixed = false;
    private Boolean mYFixed = false;

    private Bitmap mBitmap;

    public FallingBallView(Context context) {
        this(context, null);
    }

    // 在布局文件中声明的，回调该构造函数
    public FallingBallView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FallingBallView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mGestureDetector = new GestureDetector(context, mSimpleOnGestureListener);

        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);

        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ball);
        mRect = new RectF(mStartX, mStartY, mStartX + mEdgeLength, mStartY + mEdgeLength);
        mRect2 = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mStartX = (w - mEdgeLength) / 2;
        mStartY = (h - mEdgeLength) / 2;
        refreshRectByCurrentPoint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawOval(mRect, mPaint);
        canvas.drawBitmap(mBitmap, mRect2, mRect, mPaint);
    }

    // 每 100 ms 更新一次
    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mStartX = mStartX + mSpeedX / 30;
            mStartY = mStartY + mSpeedY / 30;
            mSpeedX *= 0.97; // 慢慢降低速度
            mSpeedY *= 0.97;
            if (Math.abs(mSpeedX) < 10) {
                mSpeedX = 0;
            }
            if (Math.abs(mSpeedY) < 10) {
                mSpeedY = 0;
            }
            if (refreshRectByCurrentPoint()) {
                // 转向
                if (mXFixed) {
                    mSpeedX = -mSpeedX;
                }
                if (mYFixed) {
                    mSpeedY = -mSpeedY;
                }
            }
            invalidate();
            if (mSpeedX == 0 && mSpeedY == 0) {
                mHandler.removeCallbacks(this);
                return;
            }
            mHandler.postDelayed(this, 33);
        }
    };

    private GestureDetector.SimpleOnGestureListener mSimpleOnGestureListener = new
            GestureDetector.SimpleOnGestureListener() {
                /**
                 * @param e1 手指按下时的 Event
                 * @param e2 手指抬起时的 Event。
                 * @param velocityX 在 X 轴上的运动速度(像素／秒)
                 * @param velocityY 在 Y 轴上的运动速度(像素／秒)
                 * 我们可以通过 e1 和 e2 获取到手指按下和抬起时的坐标、时间等相关信息，
                 * 通过 velocityX 和 velocityY 获取到在这段时间内的运动速度，单位是像素／秒(即 1 秒内滑动的像素距离)。
                 */
                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float
                        velocityY) {
                    Log.e("Failing", velocityX + " : " + velocityY);
                    if (!mCanFail) {
                        return false;
                    }
                    mSpeedX = velocityX;
                    mSpeedY = velocityY;
                    mHandler.removeCallbacks(mRunnable);
                    mHandler.postDelayed(mRunnable, 0);
                    return super.onFling(e1, e2, velocityX, velocityY);
                }
            };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                if (contains(event.getX(), event.getY())) {
                    mCanFail = true;
                    mFixedX = event.getX() - mStartX;
                    mFixedY = event.getY() - mStartY;
                    mSpeedX = 0;
                    mSpeedY = 0;
                } else {
                    mCanFail = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (!mCanFail) {
                    break;
                }
                mStartX = event.getX() - mFixedX;
                mStartY = event.getY() - mFixedY;
                if (refreshRectByCurrentPoint()) {
                    mFixedX = event.getX() - mStartX;
                    mFixedY = event.getY() - mStartY;
                }
                invalidate();
                break;
        }
        return true;
    }

    private Boolean contains(float x, float y) {
        float radius = mEdgeLength / 2;
        float centerX = mRect.left + radius;
        float centerY = mRect.top + radius;
        return Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2)) <= radius;
    }

    /**
     * 刷新方块位置
     *
     * @return true 表示修正过位置, false 表示没有修正过位置
     */
    private Boolean refreshRectByCurrentPoint() {
        Boolean fixed = false;
        mXFixed = false;
        mYFixed = false;
        // 修正坐标
        if (mStartX < 0) {
            mStartX = 0;
            fixed = true;
            mXFixed = true;
        }
        if (mStartY < 0) {
            mStartY = 0;
            fixed = true;
            mYFixed = true;
        }
        if (mStartX + mEdgeLength > mWidth) {
            mStartX = mWidth - mEdgeLength;
            fixed = true;
            mXFixed = true;
        }
        if (mStartY + mEdgeLength > mHeight) {
            mStartY = mHeight - mEdgeLength;
            fixed = true;
            mYFixed = true;
        }
        mRect.left = mStartX;
        mRect.top = mStartY;
        mRect.right = mStartX + mEdgeLength;
        mRect.bottom = mStartY + mEdgeLength;
        return fixed;
    }
}
