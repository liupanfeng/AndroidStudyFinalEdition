package com.example.androidstudyfinaledition.mvp.net;

/**
 * All rights Reserved, Designed By www.meishesdk.com
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2022/1/11 20:40
 * @Description : 具体的任务请求类
 * @Copyright : www.meishesdk.com Inc. All rights reserved.
 */
public class IpInfoTask implements NetTask<String>{

    private static final IpInfoTask instance=new IpInfoTask();

    @Override
    public void execute(String s, LoadTasksCallBack loadTasksCallBack) {

    }

    private IpInfoTask(){}

    private IpInfoTask getInstance(){
        return instance;
    }


}
