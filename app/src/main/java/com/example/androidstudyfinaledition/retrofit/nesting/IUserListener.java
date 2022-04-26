package com.example.androidstudyfinaledition.retrofit.nesting;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * * All rights reserved,Designed by www.meishesdk.com
 *
 * @Author : lpf
 * @CreateDate : 2022/4/26 上午10:50
 * @Description :
 * @Copyright :www.meishesdk.com Inc.All rights reserved.
 */
public interface IUserListener {

    /**
     * 执行注册操作 耗时操作
     * @param registerRequest
     * @return
     */
    @POST
    Observable<RegisterResponse> registerAction(@Body RegisterRequest registerRequest);


    /**
     * 执行登录操作 耗时操作
     * @param loginReqeust
     * @return
     */
    @POST
    Observable<LoginResponse> loginAction(@Body LoginReqeust loginReqeust);


}
