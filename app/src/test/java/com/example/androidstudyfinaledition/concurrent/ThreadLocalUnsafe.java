package com.example.androidstudyfinaledition.concurrent;

/**
 * * All rights reserved,Designed by www.meishesdk.com
 *
 * @Author : lpf
 * @CreateDate : 2022/4/13 下午4:29
 * @Description : ThreadLocal 使用不恰当的时候，会造成线程不安全
 * @Copyright :www.meishesdk.com Inc.All rights reserved.
 */
public class ThreadLocalUnsafe implements Runnable {
    //如果这里添加static 那么当threadLocal保存的就是对象的栈引用
    // 如果不带静态，保存的才是对象本身呢，典型的静态变量导致的ThreadLocal线程非安全
    private  Number number = new Number(0);

    private ThreadLocal<Number> threadLocal = new ThreadLocal() {
    };

    @Override
    public void run() {
        number.setNum(number.num + 1);
        threadLocal.set(number);
        System.out.println("current thread " + Thread.currentThread().getName() +
                " " + threadLocal.get().num);
        threadLocal.remove();
    }

    public void printThreadArray() {
        Thread[] array = new Thread[3];
        for (int i = 0; i < array.length; i++) {
            array[i] = new Thread(new ThreadLocalUnsafe());
            array[i].start();
        }
    }

    private static class Number {
        public Number(int num) {
            this.num = num;
        }
        private int num;

        public void setNum(int num) {
            this.num = num;
        }

    }

}
