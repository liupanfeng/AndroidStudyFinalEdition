package com.example.androidstudyfinaledition.utils;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * * All rights reserved,Designed by www.meishesdk.com
 *
 * @Author : lpf
 * @CreateDate : 2022/4/25 下午5:58
 * @Description :
 * @Copyright :www.meishesdk.com Inc.All rights reserved.
 */
public class RxJavaUtils {

    private static final String TAG = RxJavaUtils.class.getSimpleName();

    /**
     * 切换线程的工具方法
     * @param <UD>
     * @return
     */
    public final static <UD>ObservableTransformer<UD,UD> rxud(){
        return new ObservableTransformer<UD, UD>() {
            @NotNull
            @Override
            public ObservableSource<UD> apply(@NotNull Observable<UD> upstream) {
                return upstream.subscribeOn(Schedulers.io())//给上面的代码分配异步线程
                        .observeOn(AndroidSchedulers.mainThread())  //给下面的代码分配同步线程
                        .map(new Function<UD, UD>() {
                            @Override
                            public UD apply(@NotNull UD ud) throws Exception {
                                Log.d("lpf", "apply: 对线程执行了切换操作");
                                return ud;
                            }
                        });
            }
        };
    }
}
