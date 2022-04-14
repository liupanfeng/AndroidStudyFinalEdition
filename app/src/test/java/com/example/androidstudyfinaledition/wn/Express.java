package com.example.androidstudyfinaledition.wn;

/**
 * * All rights reserved,Designed by www.meishesdk.com
 *
 * @Author : lpf
 * @CreateDate : 2022/4/13 下午10:03
 * @Description :
 * @Copyright :www.meishesdk.com Inc.All rights reserved.
 */
public class Express {

    private int mKM; //运送的里程数目

    public Express(int mKM) {
        this.mKM = mKM;
    }

    public  synchronized void changeKm(int km){
        mKM=km;
        notifyAll();
    }

    public synchronized void waitKm()  {
        while (mKM<200){
            try {
                wait();
                //等待之后 这个是可以执行的，后边的打印是不会执行的
                System.out.println(Thread.currentThread().getName()+" wait 当前的里程数目是："+mKM);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName()+"已经到达："+mKM);
    }
}
