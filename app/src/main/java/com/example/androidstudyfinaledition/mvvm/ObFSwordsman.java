package com.example.androidstudyfinaledition.mvvm;

import androidx.databinding.ObservableField;

/**
 * All rights Reserved, Designed By www.meishesdk.com
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2022/1/11 10:53
 * @Description :
 * @Copyright : www.meishesdk.com Inc. All rights reserved.
 */
public class ObFSwordsman {
    public ObservableField<String> name=new ObservableField();
    public ObservableField<String> level=new ObservableField();

    public ObFSwordsman(String name, String level) {
        this.name.set(name);
        this.level.set(level);
    }

    public ObservableField<String> getName() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public ObservableField<String> getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level.set(level);
    }
}
