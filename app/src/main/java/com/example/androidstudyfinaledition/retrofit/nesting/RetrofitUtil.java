package com.example.androidstudyfinaledition.retrofit.nesting;

import android.os.Build;

import com.example.androidstudyfinaledition.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * * All rights reserved,Designed by www.meishesdk.com
 *
 * @Author : lpf
 * @CreateDate : 2022/4/26 上午10:23
 * @Description : retrofit 工具
 * @Copyright :www.meishesdk.com Inc.All rights reserved.
 */
public class RetrofitUtil {

    public static Retrofit createRetrofit(){
        OkHttpClient.Builder builder=new OkHttpClient.Builder();
        builder.readTimeout(5000, TimeUnit.MILLISECONDS);
        builder.connectTimeout(5000,TimeUnit.MILLISECONDS);

        if (BuildConfig.DEBUG){
            HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }

        return new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl("")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
