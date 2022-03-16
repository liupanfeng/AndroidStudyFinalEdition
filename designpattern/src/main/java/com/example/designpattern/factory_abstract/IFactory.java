package com.example.designpattern.factory_abstract;

/**
 * All rights Reserved, Designed By www.meishesdk.com
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/12/24 13:39
 * @Description : 工厂的抽象类里边定义的是生产商品的方法
 * @Copyright : www.meishesdk.com Inc. All rights reserved.
 */
public interface IFactory {

    AbstractUser createUser();

    AbstractDepartment createDepartment();

}
