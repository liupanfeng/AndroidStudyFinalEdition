package com.example.androidstudyfinaledition.wn;

/**
 * * All rights reserved,Designed by www.meishesdk.com
 *
 * @Author : lpf
 * @CreateDate : 2022/4/13 下午10:26
 * @Description : 生产者和消费者模型
 * @Copyright :www.meishesdk.com Inc.All rights reserved.
 */
public class ConsumeAndProduct {

    private int number;


    public synchronized int getNumber() {
        if (number>0){
            number--;
        }
        return number;
    }

    public synchronized void setNumber(int number) {
        this.number++;
        while (number > 10) {
            try {
                wait();
                System.out.println("产品数量是：" + number + "上限了，已经等待了");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
