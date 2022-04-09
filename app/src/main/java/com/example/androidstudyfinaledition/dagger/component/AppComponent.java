package com.example.androidstudyfinaledition.dagger.component;

import com.example.androidstudyfinaledition.MainActivity;
import com.example.androidstudyfinaledition.dagger.Dagger2Activity;
import com.example.androidstudyfinaledition.dagger.module.DatabaseModule;
import com.example.androidstudyfinaledition.dagger.module.HttpModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author : lpf
 * @FileName: AppComponent
 * @Date: 2022/4/9 18:16
 * @Description:
 */

@Component(modules = {HttpModule.class, DatabaseModule.class})
public interface AppComponent {

    void injectMainActivity(MainActivity mainActivity);

}
