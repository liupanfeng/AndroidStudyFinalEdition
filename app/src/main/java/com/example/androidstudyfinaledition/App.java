package com.example.androidstudyfinaledition;

import android.app.Application;

import com.example.androidstudyfinaledition.dagger.component.AppComponent;
import com.example.androidstudyfinaledition.dagger.component.DaggerAppComponent;

/**
 * All rights Reserved, Designed By www.meishesdk.com
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2022/1/11 20:26
 * @Description :
 * @Copyright : www.meishesdk.com Inc. All rights reserved.
 */
public class App extends Application {

    private AppComponent mAppComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent=DaggerAppComponent.builder().build();
    }


    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
