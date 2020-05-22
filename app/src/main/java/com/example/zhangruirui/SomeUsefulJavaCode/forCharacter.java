package com.example.zhangruirui.SomeUsefulJavaCode;

/**
 * Date：2020/2/28
 * Author：zhangruirui
 * Wechat：18811227256
 * Description：
 */
public class forCharacter {
    /*
     * Description：一个多线程的问题，用三个线程，顺序打印字母A-Z，输出结果是1A 2B 3C 1D 2E...打印完毕最后输出一个Ok
     * Author：ZhangRuirui
     * Date：2018/05/25
     * Email：zhangrrbugfree@163.com
     */
    private static char c = 'A';// 必要的时候声明为volatile类型的
    private static int i = 0;

    public static void main(String[] args) throws InterruptedException {
        Runnable r = new Runnable() {
            public void run() {
                synchronized (this) {
                    System.out.println("this = " + this + " ");
                    try {
                        int threadId = Integer.parseInt(Thread.currentThread().getName());
                        System.out.println("当前线程id：" + threadId + " ");
                        while (i < 26) {
                            if (i % 3 == threadId - 1) {
                                System.out.println("线程id：" + threadId + " " + (char) c++);
                                i++;
                                if (i == 26)
                                    System.out.println("哈哈，祝拿到offer！");
                                notifyAll();// 唤醒其他线程
                            } else {
                                wait();// 阻塞其他线程
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread t1 = new Thread(r, "1");
        Thread t2 = new Thread(r, "2");
        Thread t3 = new Thread(r, "3");
        t1.start();
        t2.start();
        t3.start();
    }
}

