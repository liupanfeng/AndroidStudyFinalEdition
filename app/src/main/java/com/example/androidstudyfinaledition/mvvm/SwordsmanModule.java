package com.example.androidstudyfinaledition.mvvm;

import dagger.Module;
import dagger.Provides;

/**
 * * All rights reserved,Designed by www.meishesdk.com
 *
 * @Author : lpf
 * @CreateDate : 2022/3/16 下午10:38
 * @Description :
 * @Copyright :www.meishesdk.com Inc.All rights reserved.
 */
@Module
public class SwordsmanModule {

    @Provides
    public Swordsman provideSwordsman(){
        return new Swordsman();
    }
}
