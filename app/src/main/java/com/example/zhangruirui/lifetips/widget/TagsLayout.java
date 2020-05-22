package com.example.zhangruirui.lifetips.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class TagsLayout extends ViewGroup {

    private int maxLines = -1;

    public TagsLayout(Context context) {
        this(context, null);
    }

    public TagsLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setMaxLines(int lines) {
        maxLines = lines;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int rowNum = 1;
        int count = getChildCount();
        int horizontalPadding = getPaddingLeft() + getPaddingRight();
        int verticalPadding = getPaddingTop() + getPaddingBottom();
        int specWith = MeasureSpec.getSize(widthMeasureSpec);

        int height = verticalPadding;
        int curLeft = 0;

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            LayoutParams params = (LayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            curLeft += params.leftMargin;
            if (i == 0) { // 忽略第一行的topMargin
                height += childHeight + params.bottomMargin;
            }

            if (curLeft + childWidth + horizontalPadding > getMeasuredWidth()) {
                if (maxLines != -1 && rowNum >= maxLines) {
                    break;
                }
                rowNum++;
                height += params.topMargin + childHeight + params.bottomMargin;
                //换行之后的起点是params.leftMargin，但是curLeft在下一次循环中使用，下一个子View的起点应该要加上此次换行的这个子View的宽度
                //否则大几率出现高度少了一行导致显示不全的bug
                curLeft = params.leftMargin + childWidth;
            } else {
                curLeft += childWidth;
            }
        }

        setMeasuredDimension(specWith, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        int horizontalPadding = getPaddingLeft() + getPaddingRight();
        int curLeft = getPaddingLeft();
        int curTop = 0; // 忽略第一行的topMargin
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            LayoutParams params = (LayoutParams) child.getLayoutParams();

            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            curLeft += params.leftMargin;

            if (curLeft + childWidth + horizontalPadding > getMeasuredWidth()) {
                curTop += params.topMargin + childHeight + params.bottomMargin;
                curLeft = params.leftMargin + getPaddingLeft();
            }
            child.layout(curLeft, curTop, curLeft + childWidth, curTop + childHeight);

            curLeft += childWidth + params.rightMargin;

        }
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    public static class LayoutParams extends MarginLayoutParams {
        public boolean visible = true;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }
}
