package com.example.androidstudyfinaledition.annotation;

import org.junit.Test;

import java.lang.reflect.Method;

/**
 * * All rights reserved,Designed by www.meishesdk.com
 *
 * @Author : lpf
 * @CreateDate : 2022/4/27 下午7:58
 * @Description :
 * @Copyright :www.meishesdk.com Inc.All rights reserved.
 */
public class AnnotationTest {

    /**
     * 获取运行时注解
     */
    @Test
    public void testLogin(){
        Method[] declaredMethods = LoginManager.class.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            if (declaredMethod.getName().equals("login")){
                POST annotation = declaredMethod.getAnnotation(POST.class);
                System.out.println("注解的value="+annotation.value());
            }
        }
    }
}
