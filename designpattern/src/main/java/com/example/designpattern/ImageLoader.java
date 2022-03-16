package com.example.designpattern;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.collection.LruCache;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * All rights Reserved, Designed By www.meishesdk.com
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/12/30 21:00
 * @Description : 手写图片加载器 学习设计模式
 * @Copyright : www.meishesdk.com Inc. All rights reserved.
 */
public class ImageLoader {

    //图片缓存池
    LruCache<String, Bitmap> mImageCache;
    //线程池，线程数量为CPU的数量
    ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    //UI Handler
    Handler mHandler = new Handler(Looper.getMainLooper());

    public ImageLoader() {
        initImageCache();
    }

    /**
     * 初始化图片缓存池
     */
    private void initImageCache() {
        //计算可使用的最大的内存
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        //取四分之一作为内存缓存
        final int cacheSize = maxMemory / 4;

        mImageCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            }
        };
    }
}
