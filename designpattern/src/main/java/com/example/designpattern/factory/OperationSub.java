package com.example.designpattern.factory;

/**
 * All rights Reserved, Designed By www.meishesdk.com
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/12/23 16:25
 * @Description :
 * @Copyright : www.meishesdk.com Inc. All rights reserved.
 */
public class OperationSub extends Operation {
    @Override
    public double getResult() {
        return numberA - numberB;
    }
}
