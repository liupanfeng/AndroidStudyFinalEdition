package com.example.designpattern.factory_method;


import com.example.designpattern.factory.Operation;

/**
 * All rights Reserved, Designed By www.meishesdk.com
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/12/24 11:08
 * @Description :创建产品  工厂的方法肯定是创建产品
 * @Copyright : www.meishesdk.com Inc. All rights reserved.
 */
public interface IFactory {
    //抽象工厂，定义操作算法的接口
    Operation concreteOperation();
}
