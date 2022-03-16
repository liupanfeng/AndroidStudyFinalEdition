package com.example.designpattern.factory;

import android.util.Log;

/**
 * All rights Reserved, Designed By www.meishesdk.com
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/12/23 16:33
 * @Description : 操作工厂  使用这个工厂来创建对应的对象，如果没有基类，这里返回类型就没办法处理了
 * 工程模式就是为了创建对象的
 * @Copyright : www.meishesdk.com Inc. All rights reserved.
 */
public class OperationFactory {

    //这里存在一个问题是  工厂类跟分支耦合 需要对这里下手
    //根据依赖倒置 我们把工厂类抽象出来一个接口
    public static Operation createOperation(String operation) {
        Operation op = null;
        switch (operation) {
            case "+":
                op = new OperationAdd();
                break;
            case "-":
                op = new OperationSub();
                break;
            case "*":
                op = new OperationMul();
                break;
            case "/":
                op = new OperationDiv();
                break;
            default:
                Log.e("lpf", "input operation is error");
                break;
        }
        return op;
    }


    public static <T extends Operation> T crateOpe(Class<T> clz){
        Operation operation = null;
        try {
            operation = (Operation) Class.forName(clz.getName()).newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return (T)operation;
    }

}
