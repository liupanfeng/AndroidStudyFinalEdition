package com.example.designpattern.factory_method;

import com.example.designpattern.factory.Operation;
import com.example.designpattern.factory.OperationAdd;

/**
 * All rights Reserved, Designed By www.meishesdk.com
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/12/24 11:10
 * @Description :
 * @Copyright : www.meishesdk.com Inc. All rights reserved.
 */
public class FactoryAdd implements IFactory {

    @Override
    public Operation concreteOperation() {
        return new OperationAdd();
    }

}
