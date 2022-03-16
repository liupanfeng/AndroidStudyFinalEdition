package com.example.designpattern.prototype;

import java.io.Serializable;

/**
 * All rights Reserved, Designed By www.meishesdk.com
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/12/27 14:41
 * @Description :
 * @Copyright : www.meishesdk.com Inc. All rights reserved.
 */
public class Water implements Serializable {

    private int mineral;

    public int getMineral() {
        return mineral;
    }

    public Water setMineral(int mineral) {
        this.mineral = mineral;
        return this;
    }

    @Override
    public String toString() {
        return "Water{" +
                "mineral=" + mineral +
                '}';
    }
}
