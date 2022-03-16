package com.example.androidstudyfinaledition.mvp.net;

/**
 * All rights Reserved, Designed By www.meishesdk.com
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2022/1/11 20:37
 * @Description :
 * @Copyright : www.meishesdk.com Inc. All rights reserved.
 */
public interface LoadTasksCallBack<T> {

    void onSuccess(T t);

    void onFail();

    void onStart();

    void onFinish();


}
