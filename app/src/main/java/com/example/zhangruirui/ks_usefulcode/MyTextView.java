package com.example.zhangruirui.ks_usefulcode;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.method.Touch;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

public class MyTextView extends android.support.v7.widget.AppCompatTextView {
  public MyTextView(Context context) {
    super(context);
  }

  public MyTextView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  /**
   * 一、处理 URLSpan 的点击事件和 TextView 点击事件冲突
   * 参考链接：https://angeldevil.me/2014/08/27/Long-click-about-Clickable(URLSpan)/
   * 参考链接：https://blog.csdn.net/gdutxiaoxu/article/details/85240017
   *
   * 使用的时候
   * tv.setMovementMethod(MyLinkMovementMethod.getInstance());
   */
  static class MyLinkMovementMethod extends LinkMovementMethod {

    private static MyLinkMovementMethod sInstance;

    private long mTime;

    private MyLinkMovementMethod() {
    }

    public static MyLinkMovementMethod getInstance() {
      if (sInstance == null) {
        synchronized (MyLinkMovementMethod.class) {
          if (sInstance == null) {
            sInstance = new MyLinkMovementMethod();
          }
        }
      }
      return sInstance;
    }

    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
      int action = event.getAction();

      if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_DOWN) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        x -= widget.getTotalPaddingLeft();
        y -= widget.getTotalPaddingTop();

        x += widget.getScrollX();
        y += widget.getScrollY();

        Layout layout = widget.getLayout();
        int line = layout.getLineForVertical(y);
        int off = layout.getOffsetForHorizontal(line, x);

        ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);

        // 其实很简单，既然，link 的 onClick 是在 ACTION_UP 事件处理的，那么我们只需要监听到长按事件，
        // 并且当前 MotionEvent 是 ACTION_UP 的时候，我们直接返回 true，不让他继续往下处理就 ok 了。
        if (link.length != 0) {
          if (action == MotionEvent.ACTION_UP) {
            if (System.currentTimeMillis() - mTime >= 500) {
              return true;
            }
            link[0].onClick(widget);

          } else if (action == MotionEvent.ACTION_DOWN) {
            mTime = System.currentTimeMillis();

            Selection.setSelection(buffer,
                buffer.getSpanStart(link[0]),
                buffer.getSpanEnd(link[0]));
          }

          return true;
        } else {
          Selection.removeSelection(buffer);
          super.onTouchEvent(widget, buffer, event);
          return false;
        }
      }
      return Touch.onTouchEvent(widget, buffer, event);
    }
  }

}
