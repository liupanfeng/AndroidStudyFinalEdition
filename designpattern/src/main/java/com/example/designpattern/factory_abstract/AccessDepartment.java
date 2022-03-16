package com.example.designpattern.factory_abstract;

import android.util.Log;

public class AccessDepartment extends AbstractDepartment {

    @Override
    public void insert(Department department) {
        Log.d("lpf","AccessDepartment 在department表中增加一条数据");
    }

    @Override
    public Department getDeparment(int id) {
        Log.d("lpf","AccessDepartment 在department表中获取一条数据");
        return null;
    }
}
