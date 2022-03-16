package com.example.designpattern.factory_abstract;

public abstract class AbstractUser {

    public abstract void insert(User user);  //插入用户

    public abstract User getUser(int id); //获取用户

}
