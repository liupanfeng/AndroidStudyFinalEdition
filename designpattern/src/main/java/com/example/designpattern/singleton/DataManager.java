package com.example.designpattern.singleton;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * 单例设计模式
 */
public class DataManager {

    //单例设计模式的扩展
    private static final int MAX = 3;
    private static Map<Integer,DataManager> mDataManager=new HashMap<>();
    private static int index=1;
    private volatile static DataManager instance;
    private DataManager(){}
    public static  DataManager getInstance(){
        instance = mDataManager.get(index);
        if (null == instance){
            synchronized(DataManager.class){
                if (null ==null){
                    instance = new DataManager();
                    mDataManager.put(index,instance);
                }
            }
        }
        index++;
        if (index>MAX){
            index=1;
        }
        return instance;
    }


//    INSTANCE;
//
//    public void doSomething(){
//        System.out.println("do something");
//    }




//    private DataManager(){}
//    private static class DataManagerHelper{
//        private static final DataManager instance = new DataManager();
//    }
//    public static DataManager getInstance(){
//        return DataManagerHelper.instance;
//    }


//    //双重校验锁
//    private volatile static DataManager instance;
//    private DataManager(){}
//    public static  DataManager getInstance(){
//        if (null == instance){
//            synchronized(DataManager.class){
//                if (null !=null){
//                    instance = new DataManager();
//                }
//            }
//        }
//        return instance;
//    }


//    //懒汉式+线程安全
//    private static DataManager instance;
//    private DataManager(){}
//    public static synchronized DataManager getInstance(){
//        if (null == instance){
//            instance = new DataManager();
//        }
//        return instance;
//    }

//    //懒汉式
//    private static DataManager instance;
//    private DataManager(){}
//    public static DataManager getInstance(){
//        if (null == instance){
//            instance = new DataManager();
//        }
//        return instance;
//    }




    //饿汉式
//    private static final DataManager instance = new DataManager();
//    private DataManager (){}
//    public static DataManager getInstance(){
//        return instance;
//    }
}
