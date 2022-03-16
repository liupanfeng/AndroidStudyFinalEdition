package com.example.designpattern.factory;

/**
 * All rights Reserved, Designed By www.meishesdk.com
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/12/23 16:21
 * @Description : 加法操作
 * @Copyright : www.meishesdk.com Inc. All rights reserved.
 */
public class OperationAdd extends Operation {

    @Override
    public double getResult() {
        return numberA + numberB;
    }
}
