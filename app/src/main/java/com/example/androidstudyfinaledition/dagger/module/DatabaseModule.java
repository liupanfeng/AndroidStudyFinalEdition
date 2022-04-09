package com.example.androidstudyfinaledition.dagger.module;

import com.example.androidstudyfinaledition.dagger.obj.DatabaseObject;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author : lpf
 * @FileName: DatabaseModel
 * @Date: 2022/4/9 18:17
 * @Description:
 */

@Module
public class DatabaseModule {

    @Provides
    public DatabaseObject providerDatabaseObject(){
        return new DatabaseObject();
    }

}
