package com.example.androidstudyfinaledition.rxjava;

/**
 * @author : lpf
 * @FileName: MSObserver
 * @Date: 2022/3/30 17:52
 * @Description: 观察者
 */
public abstract class MSObserver<T> {

    public abstract void onNext(T t);
    public abstract void onError(Throwable e);
    public abstract void onComplete();

}
