package com.example.androidstudyfinaledition.concurrent;

/**
 * * All rights reserved,Designed by www.meishesdk.com
 *
 * @Author : lpf
 * @CreateDate : 2022/4/13 下午3:22
 * @Description : 对ThreadLocal的使用
 * @Copyright :www.meishesdk.com Inc.All rights reserved.
 */
public class UseThreadLocal {

    //ThreadLocal 通过给线程建立一个变量副本的方式来达到线程隔离的目的
    private static ThreadLocal<Integer> mIntLocal=new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return 1;
        }
    };

    public class LocalThread implements Runnable{
        private int id;
        public LocalThread(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            Integer integer = mIntLocal.get();
            integer=integer+id;
            mIntLocal.set(integer);
            System.out.println("current thread is "+Thread.currentThread().getName()+" :"+mIntLocal.get());
            mIntLocal.remove();//使用完了，记得释放，否则可能会导致内存泄露
        }
    }

    public void startThreadArray(){
        Thread[] arr=new Thread[3];
        for (int i = 0; i < arr.length; i++) {
            arr[i]=new Thread(new LocalThread(i));
            arr[i].start();
        }

    }

}
