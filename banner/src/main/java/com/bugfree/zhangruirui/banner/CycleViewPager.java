package com.bugfree.zhangruirui.banner;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bugfree.zhangruirui.banner.model.PicInfo;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class CycleViewPager extends FrameLayout implements ViewPager.OnPageChangeListener {

  private Context mContext;

  private ViewPager mViewPager; // 实现轮播图的 ViewPager

  private TextView mTitle; // 图片标题

  private LinearLayout mIndicatorLayout; // 指示器

  private Handler mHandler; // 每几秒后执行下一张的切换

  private int WHEEL = 100; // 转动

  private int WHEEL_WAIT = 101; // 等待

  private List<View> mViews = new ArrayList<>(); // 需要轮播的View，数量为轮播图数量+2

  private ImageView[] mIndicators; // 指示器小圆点

  private boolean isScrolling = false; // 滚动框是否滚动着

  private boolean isCycle = true; // 是否循环，默认为 true

  private boolean isWheel = true; // 是否轮播，默认为 true

  private int delay = 4000; // 默认轮播时间

  private int mCurrentPosition = 0; // 轮播的当前位置

  private long releaseTime = 0; // 手指松开、页面不滚动时间，防止手指松开后短时间进行切换

  private ViewPagerAdapter mAdapter;

  private ImageCycleViewListener mImageCycleViewListener;

  private List<PicInfo> infos; // 要展示的数据集合

  private int mIndicatorSelected; // 指示器图片，被选择状态（滚动到当前图片）

  private int mIndicatorUnselected; // 指示器图片，未被选择状态

  final Runnable runnable = new Runnable() {
    @Override
    public void run() {
      if (mContext != null && isWheel) {
        long now = System.currentTimeMillis();
        // 检测上一次滑动时间与本次之间是否有触击(手滑动)操作，有的话等待下次轮播
        if (now - releaseTime > delay - 500) {
          mHandler.sendEmptyMessage(WHEEL);
        } else {
          mHandler.sendEmptyMessage(WHEEL_WAIT);
        }
      }
    }
  };

  public CycleViewPager(@NonNull Context context) {
    this(context, null);
  }

  public CycleViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public CycleViewPager(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    this.mContext = context;
    initView();
  }

  /**
   * 初始化 view
   */
  private void initView() {
    LayoutInflater.from(mContext).inflate(R.layout.banner_layout, this, true);
    mViewPager = findViewById(R.id.cycle_view_pager);
    mTitle = findViewById(R.id.cycle_title);
    mIndicatorLayout = findViewById(R.id.cycle_indicator);
    mHandler = new NewHandler(this);
  }

  private void handleMessage(Message msg) {
    if (msg.what == WHEEL && mViews.size() > 0) {
      if (!isScrolling) {
        // 当前为非滚动状态，切换到下一页
        int position = (mCurrentPosition + 1) % mViews.size();
        mViewPager.setCurrentItem(position, true);
      }
      releaseTime = System.currentTimeMillis();
      mHandler.removeCallbacks(runnable);
      mHandler.postDelayed(runnable, delay);
      return;
    }
    if (msg.what == WHEEL_WAIT && mViews.size() > 0) {
      mHandler.removeCallbacks(runnable);
      mHandler.postDelayed(runnable, delay);
    }
  }

  private static class NewHandler extends Handler {
    private WeakReference<CycleViewPager> mCycleViewPager;

    NewHandler(CycleViewPager viewPager) {
      this.mCycleViewPager = new WeakReference<>(viewPager);
    }

    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      if (mCycleViewPager.get() != null) {
        mCycleViewPager.get().handleMessage(msg);
      }
    }
  }

  /**
   * 设置指示器图片，在 setData 之前调用
   *
   * @param select   选中时的图片
   * @param unselect 未选中时的图片
   */
  public void setIndicators(int select, int unselect) {
    mIndicatorSelected = select;
    mIndicatorUnselected = unselect;
  }

  public void setData(List<PicInfo> list, ImageCycleViewListener listener) {
    setData(list, listener, 0);
  }

  /**
   * 初始化 viewpager
   *
   * @param list         要显示的数据
   * @param showPosition 默认显示位置
   */
  public void setData(List<PicInfo> list, ImageCycleViewListener listener,
                      int showPosition) {
    if (list == null || list.size() == 0) {
      // 没有数据时隐藏整个布局
      this.setVisibility(View.GONE);
      return;
    }
    mViews.clear();
    infos = list;
    if (isCycle) {
      // 添加轮播图 View，数量为集合数+2
      mViews.add(getImageView(mContext, infos.get(infos.size() - 1).getUrl())); // 将最后一个 View 添加进来

      for (int i = 0; i < infos.size(); i++) {
        mViews.add(getImageView(mContext, infos.get(i).getUrl()));
      }

      mViews.add(getImageView(mContext, infos.get(0).getUrl())); // 将第一个 View 添加进来
    } else {
      // 只添加对应数量的 View
      for (int i = 0; i < infos.size(); i++) {
        mViews.add(getImageView(mContext, infos.get(i).getUrl()));
      }
    }
    if (mViews == null || mViews.size() == 0) { // 没有 View 时隐藏整个布局
      this.setVisibility(View.GONE);
      return;
    }
    mImageCycleViewListener = listener;
    int ivSize = mViews.size();
    // 设置指示器
    mIndicators = new ImageView[ivSize];

    if (isCycle)
      mIndicators = new ImageView[ivSize - 2];

    mIndicatorLayout.removeAllViews();
    for (int i = 0; i < mIndicators.length; i++) {
      mIndicators[i] = new ImageView(mContext);
      LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
          LinearLayout.LayoutParams.WRAP_CONTENT,
          LinearLayout.LayoutParams.WRAP_CONTENT);
      lp.setMargins(10, 0, 10, 0);
      mIndicators[i].setLayoutParams(lp);
      mIndicatorLayout.addView(mIndicators[i]);
    }
    mAdapter = new ViewPagerAdapter();
    // 默认指向第一项，下方 viewPager.setCurrentItem 将触发重新计算指示器指向
    setIndicator(0);
    // 预加载 item 数量，可以在一定程度上解决滑动卡顿问题
    mViewPager.setOffscreenPageLimit(3);
    mViewPager.addOnPageChangeListener(this);
    mViewPager.setAdapter(mAdapter);
    if (showPosition < 0 || showPosition >= mViews.size())
      showPosition = 0;
    if (isCycle) {
      showPosition = showPosition + 1;
    }
    mViewPager.setCurrentItem(showPosition);
    setWheel(true); // 设置轮播
  }

  /**
   * 获取轮播图View
   */
  private View getImageView(Context context, String url) {
    RelativeLayout rl = new RelativeLayout(context);
    // 添加一个 ImageView，并加载图片
    ImageView imageView = new ImageView(context);
    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
        RelativeLayout.LayoutParams.MATCH_PARENT,
        RelativeLayout.LayoutParams.MATCH_PARENT);
    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    imageView.setLayoutParams(layoutParams);
    // 使用 Picasso 来加载图片
    Picasso.with(context).load(url).into(imageView);
    // 在 ImageView 前添加一个半透明的黑色背景，防止文字和图片混在一起
    ImageView backGround = new ImageView(context);
    backGround.setLayoutParams(layoutParams);
    backGround.setBackgroundResource(R.color.cycle_image_bg);
    rl.addView(imageView);
    rl.addView(backGround);
    return rl;
  }

  /**
   * 设置指示器
   *
   * @param selectedPosition 默认指示器位置
   */
  private void setIndicator(int selectedPosition) {
    setText(mTitle, infos.get(selectedPosition).getTitle());
    try {
      for (ImageView mIndicator : mIndicators) {
        mIndicator.setBackgroundResource(mIndicatorUnselected);
      }
      if (mIndicators.length > selectedPosition)
        mIndicators[selectedPosition].setBackgroundResource(mIndicatorSelected);
    } catch (Exception e) {
      Log.i("zhangrr", "指示器路径不正确");
    }
  }

  /**
   * 页面适配器 返回对应的 view
   */
  private class ViewPagerAdapter extends PagerAdapter {
    @Override
    public int getCount() {
      return mViews.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View arg0, @NonNull Object arg1) {
      return arg0 == arg1;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
      container.removeView((View) object);
    }

    @NonNull
    @Override
    public View instantiateItem(@NonNull ViewGroup container, final int position) {
      View v = mViews.get(position);
      if (mImageCycleViewListener != null) {
        v.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View v) {
            mImageCycleViewListener.onImageClick(infos.get(mCurrentPosition - 1),
                mCurrentPosition, v);
          }
        });
      }
      container.addView(v);
      return v;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
      return POSITION_NONE;
    }
  }

  @Override
  public void onPageScrolled(int i, float v, int i1) {

  }

  @Override
  public void onPageSelected(int i) {

    int max = mViews.size() - 1;
    int position = i;
    mCurrentPosition = i;
    if (isCycle) {
      if (i == 0) {
        // 滚动到 mView 的第一个（界面上的最后一个），将 mCurrentPosition 设置为 max - 1
        mCurrentPosition = max - 1;
      } else if (i == max) {
        //滚动到 mView 的最后一个（界面上的第一个），将 mCurrentPosition 设置为 1
        mCurrentPosition = 1;
      }
      position = mCurrentPosition - 1;
    }
    setIndicator(position);
  }

  @Override
  public void onPageScrollStateChanged(int state) {
    if (state == 1) { // viewPager 在滚动
      isScrolling = true;
      return;
    } else if (state == 0) { // viewPager 结束滚动
      releaseTime = System.currentTimeMillis();
      // 跳转到第 mCurrentPosition 个页面（没有动画效果，实际效果页面上没变化）
      mViewPager.setCurrentItem(mCurrentPosition, false);
    }
    isScrolling = false;
  }

  /**
   * 为 textView 设置文字
   */
  public static void setText(TextView textView, String text) {
    if (text != null && textView != null) {
      textView.setText(text);
    }
  }

  /**
   * 为 textView 设置文字
   */
  public static void setText(TextView textView, int text) {
    if (textView != null) {
      setText(textView, text + "");
    }
  }

  /**
   * 是否循环，默认开启。必须在 setData 前调用
   */
  public void setCycle(boolean isCycle) {
    this.isCycle = isCycle;
  }

  /**
   * 是否处于循环状态
   */
  public boolean isCycle() {
    return isCycle;
  }

  /**
   * 设置是否轮播，默认轮播，轮播一定是循环的
   */
  public void setWheel(boolean isWheel) {
    this.isWheel = isWheel;
    isCycle = true;
    if (isWheel) {
      mHandler.postDelayed(runnable, delay);
    }
  }

  /**
   * 刷新数据，当外部视图更新后，通知刷新数据
   */
  public void refreshData() {
    if (mAdapter != null)
      mAdapter.notifyDataSetChanged();
  }

  /**
   * 是否处于轮播状态
   */
  public boolean isWheel() {
    return isWheel;
  }

  /**
   * 设置轮播暂停时间,单位毫秒（默认 4000 毫秒）
   */
  public void setDelay(int delay) {
    this.delay = delay;
  }

  /**
   * 轮播控件的监听事件
   */
  public interface ImageCycleViewListener {

    /**
     * 单击图片事件
     */
    void onImageClick(PicInfo info, int position, View imageView);
  }

}
