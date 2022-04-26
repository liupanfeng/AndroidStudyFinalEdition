package com.example.androidstudyfinaledition.retrofit.antishake;

import com.example.androidstudyfinaledition.retrofit.antishake.bean.ProjectBean;
import com.example.androidstudyfinaledition.retrofit.antishake.bean.ProjectItem;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * * All rights reserved,Designed by www.meishesdk.com
 *
 * @Author : lpf
 * @CreateDate : 2022/4/26 上午11:22
 * @Description :
 * @Copyright :www.meishesdk.com Inc.All rights reserved.
 */
public interface WanAndroidApi {


    /**
     * 总数据 异步线程 耗时操作
     * @return
     */
    @GET("project/tree/json")
    Observable<ProjectBean> getProject();

    /**
     * ITem数据 ?cid=294  异步线程 耗时操作
     * @param pageIndex
     * @param cid
     * @return
     */
    @GET("project/list/{pageIndex}/json")
    Observable<ProjectItem> getProjectItem(@Path("pageIndex") int pageIndex, @Query("cid") int cid);

}
