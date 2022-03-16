package com.example.androidstudyfinaledition.mvvm;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.androidstudyfinaledition.BR;

/**
 * All rights Reserved, Designed By www.meishesdk.com
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2022/1/10 20:57
 * @Description : 剑客
 * @Copyright : www.meishesdk.com Inc. All rights reserved.
 */
public class Swordsman extends BaseObservable {

    private String name;
    private String level;

    public Swordsman(String name, String level) {
        this.name = name;
        this.level = level;
    }
    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
        notifyPropertyChanged(BR.level);
    }
}
