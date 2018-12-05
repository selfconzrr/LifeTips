package com.example.zhangruirui.lifetips.demo_learning.rxjava;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.BufferedReader;
import java.io.FileReader;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

@SuppressWarnings("ThrowablePrintedToSystemOut")
public class TestFlowable {
  private static Subscription subscription;

  public static void main(String[] args) {

    testRxJava10();
    try {
      Thread.sleep(10000000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private static void testRxJava10() {

    Flowable.create(new FlowableOnSubscribe<String>() {
      @Override
      public void subscribe(FlowableEmitter<String> e) {
        try {
          FileReader fileReader = new FileReader("test.txt");
          BufferedReader br = new BufferedReader(fileReader);
          String str;
          while ((str = br.readLine()) != null && !e.isCancelled()) {
            while (e.requested() == 0) {
              if (e.isCancelled()) {
                break;
              }
            }
            e.onNext(str);
          }
          br.close();
          fileReader.close();
          e.onComplete();
        } catch (Exception ex) {
          e.onError(ex);
        }
      }
    }, BackpressureStrategy.ERROR)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.newThread())
        .subscribe(new Subscriber<String>() {
          @Override
          public void onSubscribe(Subscription s) {
            subscription = s;
            s.request(1);
          }

          @Override
          public void onNext(String s) {
            System.out.println(s);
            try {
              Thread.sleep(2000);
              subscription.request(1);
            } catch (InterruptedException ie) {
              ie.printStackTrace();
            }
          }

          @Override
          public void onError(Throwable t) {
            System.out.println(t);
          }

          @Override
          public void onComplete() {

          }
        });
  }
}
