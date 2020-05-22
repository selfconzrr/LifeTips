package com.example.zhangruirui.lifetips.demo_learning.coordinatorlayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.zhangruirui.lifetips.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author zhangruirui
 * email：1138517609@qq.com
 * GitHub：https://github.com/selfconzrr
 * Blog：http://blog.csdn.net/u011489043
 * Date：12/06/18
 */
@SuppressWarnings({"ConstantConditions", "unchecked"})
public class FragmentModel extends Fragment {
  private static final String PARAM = "param";
  private static final int INITIAL_CAPACITY = 100;

  @BindView(R.id.recycler)
  RecyclerView mRecyclerView;
  @BindView(R.id.refresh)
  SwipeRefreshLayout mSwipeRefresh;

  private String mTabTitleText;

  private List<String> mList;

  private MyAdapter myAdapter;

  LinearLayoutManager mLinearLayoutManager;

  private boolean mNeedMove = false;
  private int mIndex;

  public FragmentModel() {
    Log.e("zhangrr", "FragmentModel() called");
  }

  /**
   * 如果在创建 Fragment 时要传入参数，必须要通过 setArguments(Bundle bundle) 方式添加，而不建议通过为 Fragment 添加带参数的构造函数，
   * 因为通过 setArguments() 方式添加，在由于内存紧张导致 Fragment 被系统杀掉并恢复 （re-instantiate） 时能保留这些数据。
   * 官方建议如下：
   * It is strongly recommended that subclasses do not have other constructors with parameters,
   * since these constructors will not be called when the fragment is re-instantiated.
   */
  public static FragmentModel newInstance(String title) {
    Log.e("zhangrr", "newInstance() called title = [" + title + "]");
    FragmentModel fragment = new FragmentModel();
    Bundle args = new Bundle();
    args.putString(PARAM, title);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mTabTitleText = getArguments().getString(PARAM);
    }
    Log.e("zhangrr", "onCreate() called with: savedInstanceState = [" + mTabTitleText);
  }

  // 生命周期函数中只有 onCreateView() 在重写时不用写 super 方法，其他都需要
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    Log.e("zhangrr", "onCreateView() called mTabTitleText = " + mTabTitleText);
    // 需要注意的是 inflate() 的第三个参数是 false，因为在 Fragment 内部实现中，会把该布局添加到 container 中，如果设为
    // true，那么就会重复做两次添加，会抛出
    // Caused by: java.lang.IllegalStateException: The specified child already has a parent. You
    // must call removeView() on the child's parent first.

    final View view = inflater.inflate(R.layout.fragment_layout, container, false);
    ButterKnife.bind(this, view);
    initData(mTabTitleText);
    // 设置 item 增加和删除时的动画
    mRecyclerView.setItemAnimator(new DefaultItemAnimator());

    // 如果可以确定每个 item 的高度是固定的，设置这个选项可以提高性能
    mRecyclerView.setHasFixedSize(true);

    myAdapter = new MyAdapter(mList);
    // myAdapter.setDatas(mList);
    mRecyclerView.setAdapter(myAdapter);

    mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        /*
          The RecyclerView is not currently scrolling.（静止没有滚动）
          public static final int SCROLL_STATE_IDLE = 0;
         */

        /*
          The RecyclerView is currently being dragged by outside input such as user touch input.
         （正在被外部拖拽,一般为用户正在用手指滚动）
           public static final int SCROLL_STATE_DRAGGING = 1;
         */

        /*
          The RecyclerView is currently animating to a final position while not under outside
          control.
         （自动滚动）
          public static final int SCROLL_STATE_SETTLING = 2;
         */

        Log.e("zhangrr", "onScrollStateChanged() called with: recyclerView");
        // 使用 smoothScrollToPosition 是触发滑动器 SmoothScroller 来重绘界面，滑动结束会触发滑动监听器，
        // 而 scrollToPosition 是界面的直接重新绘制，不涉及滑动，也就无法获取滑动结束的状态，这就是不使用 scrollToPosition 方法的原因
      }

      @Override
      public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        Log.e("zhangrr", "onScrolled called with: mNeedMove = " + mNeedMove);

        if (mNeedMove) {
          mNeedMove = false;
          // 获取要置顶的项顶部 离 RecyclerView 顶部的距离
          int top = mLinearLayoutManager.findViewByPosition(mIndex).getTop();
          mRecyclerView.scrollBy(0, top);
        }
      }
    });

    myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
      @Override
      public void onClick(int position) {
        Toast.makeText(getActivity(), "您选择了 " + mList.get(position),
            Toast.LENGTH_SHORT).show();
        Log.e("zhangrr", "onClick() called with: height = " + mLinearLayoutManager
            .findViewByPosition(position).getHeight() + " measure = "
            + mLinearLayoutManager.findViewByPosition(position).getMeasuredHeight());
        mList.set(position, "new " + position + " item");
        doCalculate();

//        moveToPosition(myAdapter.getItemCount() - 1);
//        mRecyclerView.scrollToPosition(5); // 将点击的 item 置顶：如果移动到当前屏幕可见列表的前面的项时，会将目标 item
// 的顶部和布局的顶部对齐；如果是移动到后面的项时，会将目标 item 的底部和布局底部对齐
//        mRecyclerView.scrollTo(0, 100); // 总是相对于 view 的初始位置进行偏移
//        mRecyclerView.scrollBy(0, 100); // 总是相对于 view 的当前位置进行偏移，比如，每点击一次 item ，该 item 向上位移 100 dp
      }

      @Override
      public void onLongClick(final int position) {
        Toast.makeText(getActivity(), "您长按了 " + mList.get(position),
            Toast.LENGTH_SHORT).show();

        new AlertDialog.Builder(getActivity())
            .setTitle("确认删除吗")
            .setNegativeButton("取消", null)
            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int i) {
                // myAdapter.removeItem(position); // 调用自定义的 removeItem 方法
                if (position < mList.size()) {

                  mList.remove(position);
                  doCalculate();

//                  myAdapter.setDatas(mList);
//                  myAdapter.notifyItemRemoved(position); // 使用该方法，一般还需要我们再调用下面的方法或者
                  // notifyDataSetChanged()
                  // 因为该方法不会使 position 及其之后位置的 itemView 重新 onBindViewHolder
                  // 在删除单项后，已经出现在画面里的项不会再有调用 onBind 机会，这样它保留的 position 一直是未进行删除操作前的 position
                  // 值。所以，我们一般需要在 notifyItemRemoved 之后再刷新一次。改用 DiffUtil 之后就不需要了。
                  // myAdapter.notifyItemRangeChanged(position, mList.size() - position);
                }
              }
            }).show();
      }
    });

    mSwipeRefresh.setColorSchemeColors(Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW);
    mSwipeRefresh.setSize(SwipeRefreshLayout.LARGE);
    // 设置手指在屏幕下拉多少距离会触发下拉刷新
    // mSwipeRefresh.setDistanceToTriggerSync(300);

    mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        // LEFT DO NOTHING
        // 此处一般是模拟网络刷新，获取新的数据 or 视频
        int tempSize = mList.size();
        for (int i = 0; i < tempSize; i++) {
          mList.set(i, "New " + i + " Item ");
        }
        try {
          Thread.sleep(4000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        doCalculate();
        mSwipeRefresh.setRefreshing(false);
      }
    });

    return view;
  }

  private void moveToPosition(int index) {
    // 获取当前 recycleView 屏幕可见的第一项和最后一项的 Position
    int firstItem = mLinearLayoutManager.findFirstVisibleItemPosition();
    int lastItem = mLinearLayoutManager.findLastVisibleItemPosition();

    if (index <= firstItem) {
      // 当要置顶的项在当前显示的第一个项的前面时
      Log.e("zhangrr", " if ");
      // mRecyclerView.scrollToPosition(index);
      mRecyclerView.smoothScrollToPosition(index); // smooth 方法有滑动的效果
    } else if (index <= lastItem) {
      // 当要置顶的项已经在屏幕上显示时，计算它离屏幕原点的距离
      int top = mLinearLayoutManager.findViewByPosition(index).getTop();
      Log.e("zhangrr", " else if ");
      mRecyclerView.scrollBy(0, top);
    } else {
      // 当要置顶的项在当前界面显示的最后一项的后面时
      Log.e("zhangrr", " else ");
      mRecyclerView.scrollToPosition(index);
      mIndex = index;
      mNeedMove = true; // 用于监听滑动状态，计算缺少的滑动距离，再通过 scrollBy 进行滑动
    }
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    Log.e("zhangrr", "onActivityCreated() called with: mTabTitleText = [" + mTabTitleText + "]");

    RecyclerView.LayoutManager layoutManager;

    switch (mTabTitleText) {
      case "作品":
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mLinearLayoutManager = (LinearLayoutManager) layoutManager;
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
            DividerItemDecoration.VERTICAL));
        break;
      case "喜欢":
        layoutManager = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        break;
      case "草稿":
        layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL);
        break;
      default:
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        break;
    }
    mRecyclerView.setLayoutManager(layoutManager);
  }

  private void initData(String titleText) {
    mList = new ArrayList<>(INITIAL_CAPACITY);
    // 不新开线程，数据量 1000 的时候，耗时 7ms
    // 不新开线程，数据量 10000 的时候，耗时 29ms
    // 不新开线程，数据量 100000 的时候，耗时 105ms
    // 所以我们应该将获取 DiffResult 的过程放到子线程中，并在主线程中更新 RecyclerView
    // 此处使用 RxJava，当数据量为 100000 的时候，耗时 13ms

    for (int i = 0; i < INITIAL_CAPACITY; i++) {
      mList.add(titleText + " 第 " + i + " 个item");
    }
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
  }

  private void doCalculate() {
    Observable.create(new ObservableOnSubscribe<DiffUtil.DiffResult>() {
      @Override
      public void subscribe(ObservableEmitter<DiffUtil.DiffResult> e) {
        // 三、利用DiffUtil
        // 利用 DiffUtil.calculateDiff() 方法，传入一个规则 DiffUtil.Callback 对象，和是否检测移动 item 的 boolean
        // 变量，得到 DiffUtil.DiffResult 的对象
        // 第二个参数代表是否检测Item的移动，改为false算法效率更高，按需设置，我们这里是true。
        Log.e("zhangrr", "doCalculate() called with: dialog = [" + myAdapter.getDatas().size() +
            "], i = [" + mList.size() + "]");
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallBack(myAdapter
            .getDatas(), mList), true);
        e.onNext(diffResult);
      }
    }).subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<DiffUtil.DiffResult>() {
          @Override
          public void accept(DiffUtil.DiffResult diffResult) {
            // 利用 DiffUtil.DiffResult 对象的 dispatchUpdatesTo() 方法，传入 RecyclerView 的 Adapter
            diffResult.dispatchUpdatesTo(myAdapter);
            // 别忘了将新数据给 Adapter
             myAdapter.setDatas(mList);
          }
        });
  }
}
