package com.example.designpattern.prototype;

/**
 * All rights Reserved, Designed By www.meishesdk.com
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/12/27 14:15
 * @Description : 原型设计模式接口
 * @Copyright : www.meishesdk.com Inc. All rights reserved.
 */
public interface IPrototype {

    /**
     * 获取浅拷贝对象
     * @return
     * @throws CloneNotSupportedException
     */
    IPrototype getShadowCloneInstance() throws CloneNotSupportedException;

    /**
     * 获取深拷贝对象
     * @return
     */
    IPrototype getDeepCloneInstance(IPrototype prototype);


}
