package com.example.androidstudyfinaledition.retrofit.antishake.util;

import com.example.androidstudyfinaledition.retrofit.antishake.WanAndroidApi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * * All rights reserved,Designed by www.meishesdk.com
 *
 * @Author : lpf
 * @CreateDate : 2022/4/26 下午5:13
 * @Description :
 * @Copyright :www.meishesdk.com Inc.All rights reserved.
 */
public class RetrofitClient {

    public static String BASE_URL = "https://www.wanandroid.com/";

    public static Retrofit createRetrofit(){
        OkHttpClient.Builder builder=new OkHttpClient.Builder();
        builder.readTimeout(5000, TimeUnit.MILLISECONDS);
        builder.connectTimeout(5000,TimeUnit.MILLISECONDS);
        builder.writeTimeout(5000,TimeUnit.MILLISECONDS);
        OkHttpClient okHttpClient = builder.build();

        return new Retrofit.Builder().baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }

}
