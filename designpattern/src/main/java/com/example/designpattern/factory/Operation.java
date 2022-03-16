package com.example.designpattern.factory;

import com.example.designpattern.inter.IGetResult;

/**
 * All rights Reserved, Designed By www.meishesdk.com
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/12/23 16:18
 * @Description : 操作类
 * @Copyright : www.meishesdk.com Inc. All rights reserved.
 */
public abstract class Operation  {

    public double numberA;
    public double numberB;
    //定义一个抽象方法用于获取结果
    public abstract double getResult();

}
