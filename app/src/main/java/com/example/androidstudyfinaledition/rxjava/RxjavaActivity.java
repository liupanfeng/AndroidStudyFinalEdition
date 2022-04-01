package com.example.androidstudyfinaledition.rxjava;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import com.example.androidstudyfinaledition.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * 模拟rxjava各种操作符号
 * rxjava 比较难理解的就是泛型
 * ？通配符 用于赋值
 * T 用户声明类型
 * ？ extends ：可读不可写  类型的上界限
 * ？ super  ：可写不可读  类型的下界限
 *
 */
public class RxjavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);

        //测试手写rxjava create操作符号，并实现链式调度
//        MSObserable.create(new MSObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(MSObserver<? super String> msObserver) {
//                msObserver.onNext("hi 观察者");
//            }
//        }).subscrible(new MSObserver<String>() {
//            @Override
//            public void onNext(String s) {
//                Log.d("lpf","观察者收到消息---"+s);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });

        //interval
        //创建一个按固定时间间隔发射整数序列的 Observable，相当于定时器
        Observable.interval(50, 200, TimeUnit.MILLISECONDS).subscribe(new Consumer<Long>() {
            @SuppressLint("CheckResult")
            @Override
            public void accept(Long aLong) throws Exception {
                System.out.println("观察者收到来自被观察者的消息---"+aLong);
            }
        });
    }
}