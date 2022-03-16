package com.example.designpattern.factory_method;

import com.example.designpattern.factory.Operation;
import com.example.designpattern.factory.OperationSub;

/**
 * All rights Reserved, Designed By www.meishesdk.com
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/12/24 11:11
 * @Description : 减法工厂
 * @Copyright : www.meishesdk.com Inc. All rights reserved.
 */
public class FactorySub implements IFactory {

    @Override
    public Operation concreteOperation() {
        return new OperationSub();
    }
}
