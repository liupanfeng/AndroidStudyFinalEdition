package com.example.androidstudyfinaledition.wn;

import com.example.androidstudyfinaledition.concurrent.ThreadLocalUnsafe;

import org.junit.Test;

/**
 * * All rights reserved,Designed by www.meishesdk.com
 *
 * @Author : lpf
 * @CreateDate : 2022/4/13 下午10:08
 * @Description :
 * @Copyright :www.meishesdk.com Inc.All rights reserved.
 */
public class TestWn {

     Express express=new Express(20);

    private   class WaitKm extends Thread{
        @Override
        public void run() {
            super.run();
            express.waitKm();
        }
    }

    private  class ChangeKm extends Thread{
        @Override
        public void run() {
            super.run();
            express.changeKm(220);
        }
    }
    @Test
    public void testWn() throws InterruptedException {

       for (int i=0;i<3;i++){
           new WaitKm().start();
       }

        Thread.sleep(1000);
        ChangeKm changeKm=new ChangeKm();
        changeKm.start();

    }
}
