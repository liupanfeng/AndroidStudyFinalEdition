package com.example.designpattern.factory_method;

import com.example.designpattern.factory.Operation;
import com.example.designpattern.factory.OperationMul;

/**
 * All rights Reserved, Designed By www.meishesdk.com
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/12/24 11:12
 * @Description :
 * @Copyright : www.meishesdk.com Inc. All rights reserved.
 */
public class FactoryMul implements IFactory{
    @Override
    public Operation concreteOperation() {
        return new OperationMul();
    }
}
