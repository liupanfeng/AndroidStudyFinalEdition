package com.example.androidstudyfinaledition;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * All rights Reserved, Designed By www.meishesdk.com
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2022/1/10 20:42
 * @Description :
 * @Copyright : www.meishesdk.com Inc. All rights reserved.
 */
public class ImageRepertory {

    private Retrofit mRetrofit;

    public ImageRepertory() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://cn.bing.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private interface Service {

        @GET("HPImageArchive.aspx")
        Observable getImage(
                @Query("format") String format,
                @Query("idx") int idx,
                @Query("n") int n
        );

    }

    public Observable getImage(String format, int idx, int n) {
        return mRetrofit.create(Service.class).getImage(format, idx, n);
    }

}
