package com.example.androidstudyfinaledition.rxjava;

/**
 * @author : lpf
 * @FileName: MSAction
 * @Date: 2022/3/30 17:44
 * @Description: 规定了动作行为
 */
public interface MSAction<T> {

    void subscribe(T t);

}
