package com.example.androidstudyfinaledition.rxjava;

/**
 * @author : lpf
 * @FileName: MSAction
 * @Date: 2022/3/30 17:44
 * @Description:
 */
public interface MSAction<T> {

    void subscribe(T t);

}
