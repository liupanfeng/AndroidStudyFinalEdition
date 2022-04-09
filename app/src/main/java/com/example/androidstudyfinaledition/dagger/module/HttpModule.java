package com.example.androidstudyfinaledition.dagger.module;

import com.example.androidstudyfinaledition.dagger.obj.HttpObject;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author : lpf
 * @FileName: HttpModule
 * @Date: 2022/4/9 18:18
 * @Description:
 */

@Module
public class HttpModule {

    @Provides
    public HttpObject providerHttpObject(){
        return new HttpObject();
    }
}
