package com.example.androidstudyfinaledition.dagger.obj;

import javax.inject.Inject;

/**
 * @author : lpf
 * @FileName: Database
 * @Date: 2022/4/9 19:00
 * @Description:
 */
public class DatabaseObject {


    public DatabaseObject() {
    }

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
