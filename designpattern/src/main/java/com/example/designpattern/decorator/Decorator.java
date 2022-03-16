package com.example.designpattern.decorator;

/**
 * All rights Reserved, Designed By www.meishesdk.com
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/12/28 11:00
 * @Description : 装饰者接口   编程是一门艺术，大批量的改动，显然是非常丑陋的做法。
 * @Copyright : www.meishesdk.com Inc. All rights reserved.
 */
public abstract class Decorator implements IComponent{

    //持有一个组件对象
    protected IComponent component;

    //通过构造方法 进行实例化
    public Decorator(IComponent component) {
        this.component = component;
    }

    @Override
    public String display(String content) {
        return component.display(content);
    }

    /**
     * 对组件进行装饰的抽象方法
     * @return
     */
    protected abstract String transform(String content);

}
