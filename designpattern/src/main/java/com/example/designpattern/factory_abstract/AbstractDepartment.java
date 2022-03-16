package com.example.designpattern.factory_abstract;

/**
 * All rights Reserved, Designed By www.meishesdk.com
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/12/24 13:36
 * @Description : 接口规范的是行为和规范   接口应该填写的是具体的动作 这个是具体的商品 也就是具体的表格
 * @Copyright : www.meishesdk.com Inc. All rights reserved.
 */
public abstract class AbstractDepartment {

    public abstract void insert(Department department);  //插入部门

    public abstract Department getDeparment(int id); //获取部门


}
