package com.example.androidstudyfinaledition.concurrent;


import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * * All rights reserved,Designed by www.meishesdk.com
 *
 * @Author : lpf
 * @CreateDate : 2022/4/13 下午3:22
 * @Description : 测试ThreadLocal
 * @Copyright :www.meishesdk.com Inc.All rights reserved.
 */
public class ThreadLocalTest {

    @Test
    public void testUseThreadLocal(){
        UseThreadLocal useThreadLocal=new UseThreadLocal();
        useThreadLocal.startThreadArray();
    }

    @Test
    public void testThreadLocalUnsafe(){
        ThreadLocalUnsafe useThreadLocal=new ThreadLocalUnsafe();
        useThreadLocal.printThreadArray();
    }

}
