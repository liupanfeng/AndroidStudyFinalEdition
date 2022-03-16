package com.example.designpattern.prototype;

import java.io.Serializable;

/**
 * All rights Reserved, Designed By www.meishesdk.com
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/12/27 14:18
 * @Description :
 * @Copyright : www.meishesdk.com Inc. All rights reserved.
 */
public class Person implements Serializable ,IPrototype,Cloneable {

    private int age=10;
    private String name="lilei";
    private Brain brain;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Brain getBrain() {
        return brain;
    }

    public void setBrain(Brain brain) {
        this.brain = brain;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", brain=" + brain.toString() +
                '}';
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public IPrototype getShadowCloneInstance() throws CloneNotSupportedException {
        return (IPrototype) clone();
    }

    @Override
    public IPrototype getDeepCloneInstance(IPrototype prototype) {
        return PrototypeUtil.getSerializeInstance(prototype);
    }

}
