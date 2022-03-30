package com.example.androidstudyfinaledition.dagger;

import dagger.Module;
import dagger.Provides;

/**
 * * All rights reserved,Designed by www.meishesdk.com
 *
 * @Author : lpf
 * @CreateDate : 2022/3/16 下午10:29
 * @Description :
 * @Copyright :www.meishesdk.com Inc.All rights reserved.
 */
@Module
public class UserModule {

    @Provides
    public User provideUser(){
        return new User();
    }
}
