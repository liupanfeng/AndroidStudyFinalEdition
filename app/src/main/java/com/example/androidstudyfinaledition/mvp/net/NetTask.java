package com.example.androidstudyfinaledition.mvp.net;

/**
 * All rights Reserved, Designed By www.meishesdk.com
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2022/1/11 20:36
 * @Description :
 * @Copyright : www.meishesdk.com Inc. All rights reserved.
 */
public interface NetTask<T> {

    /**
     *
     * @param t 这个是请求参数  声明为泛型，可以承载任何类型的请求参数
     * @param loadTasksCallBack  请求的网络回调
     */
    void execute(T t,LoadTasksCallBack loadTasksCallBack);

}
