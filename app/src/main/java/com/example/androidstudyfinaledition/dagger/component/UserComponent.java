package com.example.androidstudyfinaledition.dagger.component;

import com.example.androidstudyfinaledition.dagger.Dagger2Activity;
import com.example.androidstudyfinaledition.dagger.module.UserModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * * All rights reserved,Designed by www.meishesdk.com
 *
 * @Author : lpf
 * @CreateDate : 2022/3/16 下午10:31
 * @Description :
 * @Copyright :www.meishesdk.com Inc.All rights reserved.
 */
@Component(modules = {UserModule.class})
public interface UserComponent {

    void injectDagger2Activity(Dagger2Activity dagger2Activity);
}
