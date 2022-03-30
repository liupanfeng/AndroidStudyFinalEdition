package com.example.androidstudyfinaledition.mvvm;

import com.example.androidstudyfinaledition.MainActivity;

import dagger.Component;

/**
 * * All rights reserved,Designed by www.meishesdk.com
 *
 * @Author : lpf
 * @CreateDate : 2022/3/16 下午10:39
 * @Description :
 * @Copyright :www.meishesdk.com Inc.All rights reserved.
 */
@Component(modules = {SwordsmanModule.class})
public interface SwordsmanComponent {

    void injectMainActivity(MainActivity mainActivity);
}
