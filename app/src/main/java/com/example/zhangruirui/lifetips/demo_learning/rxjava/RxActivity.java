package com.example.zhangruirui.lifetips.demo_learning.rxjava;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.zhangruirui.lifetips.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author zhangruirui
 * email：1138517609@qq.com
 * GitHub：https://github.com/selfconzrr
 * Blog：http://blog.csdn.net/u011489043
 * Date：12/05/18
 */
public class RxActivity extends AppCompatActivity {

  private static final String TAG = "RxActivity";
  RecyclerView.Adapter mAdapter;
  private ArrayList<String> mDataSet;
  private Disposable mDisposable;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_rx);
    RecyclerView recyclerView = findViewById(R.id.recycler_view);
    mDataSet = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      mDataSet.add("Welcome to #" + i);
    }

//    testRxJava1();
//    testRxJava2();
//    testRxJava3();
//    testRxJava4();
//    testRxJava5();
//    testRxJava6();
//    testRxJava7();
//    testRxJava8();
    testRxJava9();

    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    recyclerView.setHasFixedSize(true);
    mAdapter = new RxAdapter(mDataSet);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(mAdapter);
  }

  /**
   * map
   */
  private void testRxJava1() {
    Observable.create(new ObservableOnSubscribe<Integer>() {
      @Override
      public void subscribe(ObservableEmitter<Integer> e) throws Exception {
        e.onNext(1);
        e.onNext(2);
        e.onNext(3);
        e.onComplete();
      }
    }).map(new Function<Integer, String>() {
      @Override
      public String apply(Integer integer) throws Exception {
        return "map 转换后的结果 " + integer;
      }
    }).subscribe(new Consumer<String>() {
      @Override
      public void accept(String s) throws Exception {
        mDataSet.add("accept " + s);
      }
    });
  }

  /**
   * Disposable
   */
  private void testRxJava2() {
    Observable.create(new ObservableOnSubscribe<Integer>() {
      @Override
      public void subscribe(ObservableEmitter<Integer> e) throws Exception {
        mDataSet.add("Observable emit 1");
        e.onNext(1);
        mDataSet.add("Observable emit 2");
        e.onNext(2);
        mDataSet.add("Observable emit 3");
        e.onNext(3);
        e.onComplete();
        mDataSet.add("Observable emit 4");
        e.onNext(4);
      }
    }).subscribe(new Observer<Integer>() {
      private int i;
      private Disposable disposable;

      @Override
      public void onSubscribe(@NonNull Disposable d) {
        mDataSet.add("onSubscribe " + d.isDisposed());
        disposable = d;
      }

      @Override
      public void onNext(Integer value) {
        mDataSet.add("onNext " + value);
        i++;
        if (i == 2) {
          // 在RxJava 2.x 中，新增的Disposable可以做到切断的操作，让Observer观察者不再接收上游事件
          disposable.dispose();
          mDataSet.add("onNext " + disposable.isDisposed());
        }
      }

      @Override
      public void onError(Throwable e) {
        mDataSet.add("onError " + e.getMessage());
      }

      @Override
      public void onComplete() {
        mDataSet.add("onComplete");
      }
    });
  }

  /**
   * zip
   */
  private void testRxJava3() {
    Observable.zip(getStringObservable(), getIntegerObservable(), new BiFunction<String, Integer,
        String>() {
      @Override
      public String apply(String s, Integer integer) throws Exception {
        return s + integer;
      }
    }).subscribe(new Consumer<String>() {
      @Override
      public void accept(String s) throws Exception {
        mDataSet.add("zip accept：" + s);
      }
    });
  }

  private Observable<String> getStringObservable() {
    return Observable.create(new ObservableOnSubscribe<String>() {
      @Override
      public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
        if (!e.isDisposed()) {
          e.onNext("A");
          mDataSet.add("String emit : A");
          e.onNext("B");
          mDataSet.add("String emit : B");
          e.onNext("C");
          mDataSet.add("String emit : C");
        }
      }
    });
  }

  private Observable<Integer> getIntegerObservable() {
    return Observable.create(new ObservableOnSubscribe<Integer>() {
      @Override
      public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
        if (!e.isDisposed()) {
          e.onNext(1);
          mDataSet.add("Integer emit : 1");
          e.onNext(2);
          mDataSet.add("Integer emit : 2");
          e.onNext(3);
          mDataSet.add("Integer emit : 3");
          e.onNext(4);
          mDataSet.add("Integer emit : 4");
          e.onNext(5);
          mDataSet.add("Integer emit : 5");
        }
      }
    });
  }

  /**
   * concat、just
   */
  private void testRxJava4() {
    Observable.concat(Observable.just(1, 2, 3), Observable.just(4, 5, 6))
        .subscribe(new Consumer<Integer>() {
          @Override
          public void accept(Integer integer) throws Exception {
            mDataSet.add("concat " + integer);
          }
        });
  }

  /**
   * 线程调度
   * flatMap
   */
  private void testRxJava5() {
    Observable.create(new ObservableOnSubscribe<Integer>() {
      @Override
      public void subscribe(ObservableEmitter<Integer> e) throws Exception {
        e.onNext(1);
        e.onNext(2);
        e.onNext(3);
        e.onComplete();
      }
    }).flatMap(new Function<Integer, ObservableSource<String>>() {
      @Override
      public ObservableSource<String> apply(Integer integer) throws Exception {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
          list.add("I am value " + integer);
        }
        int delayTime = (int) (1 + Math.random() * 10);
        return Observable.fromIterable(list).delay(delayTime, TimeUnit.MILLISECONDS);
      }
    }).subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<String>() {
          @Override
          public void accept(String s) throws Exception {
            mDataSet.add("flatMap accept" + s);
          }
        });
  }

  /**
   * concatMap
   */
  private void testRxJava6() {
    Observable.create(new ObservableOnSubscribe<Integer>() {
      @Override
      public void subscribe(ObservableEmitter<Integer> e) throws Exception {
        e.onNext(1);
        e.onNext(2);
        e.onNext(3);
        e.onComplete();
      }
    }).concatMap(new Function<Integer, ObservableSource<String>>() {
      @Override
      public ObservableSource<String> apply(Integer integer) throws Exception {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
          list.add("I am value " + integer);
        }
        int delayTime = (int) (1 + Math.random() * 10);
        return Observable.fromIterable(list).delay(delayTime, TimeUnit.MILLISECONDS);
      }
    }).subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<String>() {
          @Override
          public void accept(String s) throws Exception {
            mDataSet.add("concatMap accept" + s);
          }
        });
  }

  /**
   * interval
   */
  private void testRxJava7() {
    mDataSet.add("interval start " + SystemClock.uptimeMillis());
    mDisposable = Observable.interval(3, 2, TimeUnit.SECONDS)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())// 由于interval默认在新线程，所以我们应该切回主线程
        .subscribe(new Consumer<Long>() {
          @Override
          public void accept(Long aLong) throws Exception {
            mDataSet.add("interval " + SystemClock.uptimeMillis());
            mAdapter.notifyDataSetChanged();
            Log.e(TAG, "interval :" + aLong + " at " + SystemClock.uptimeMillis() + "\n");
          }
        });
  }

  /**
   * debounce
   */
  private void testRxJava8() {
    Observable.create(new ObservableOnSubscribe<Integer>() {
      @Override
      public void subscribe(ObservableEmitter<Integer> e) throws Exception {
        e.onNext(1);
        Thread.sleep(400);
        e.onNext(2);
        Thread.sleep(505);
        e.onNext(3);
        Thread.sleep(100);
        e.onNext(4);
        Thread.sleep(605);
        e.onNext(5);
        Thread.sleep(510);
        e.onComplete();
      }
    }).debounce(500, TimeUnit.MILLISECONDS)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<Integer>() {
          @Override
          public void accept(Integer integer) throws Exception {
            mDataSet.add("Debounce " + integer);
          }
        });
  }

  /**
   * 线程调度
   */
  private void testRxJava9() {
    Observable.create(new ObservableOnSubscribe<String>() {
      @Override
      public void subscribe(ObservableEmitter<String> e) throws Exception {
        e.onNext("耗时1");
        Thread.sleep(1000);
        e.onNext("耗时2");
        Thread.sleep(2000);
        e.onNext("耗时3");
        Thread.sleep(3000);
      }
    }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<String>() {
          @Override
          public void accept(String s) throws Exception {
            mDataSet.add(s);
            mAdapter.notifyDataSetChanged();
          }
        });
  }

  /**
   * 由于我们这个是间隔执行，所以当我们的Activity销毁的时候，实际上这个操作还依然在进行
   * 所以需要在合适的时候主动销毁它
   */
  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (mDisposable != null && !mDisposable.isDisposed()) {
      mDisposable.dispose();
    }
  }
}
