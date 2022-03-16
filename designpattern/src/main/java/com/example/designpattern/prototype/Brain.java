package com.example.designpattern.prototype;

import java.io.Serializable;

/**
 * All rights Reserved, Designed By www.meishesdk.com
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/12/27 14:40
 * @Description :
 * @Copyright : www.meishesdk.com Inc. All rights reserved.
 */
public class Brain implements Serializable {

    private Water water;

    public Brain setWater(Water water) {
        this.water = water;
        return this;
    }

    public Water getWater() {
        return water;
    }

    @Override
    public String toString() {
        return "Brain{" +
                "water=" + water.toString() +
                '}';
    }
}
