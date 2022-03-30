package com.example.androidstudyfinaledition.rxjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.androidstudyfinaledition.R;

/**
 * 模拟rxjava各种操作符号
 */
public class RxjavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        MSObserable.create(new MSObservableOnSubscribe<String>() {

            @Override
            public void subscribe(MSObserver<? super String> msObserver) {
                msObserver.onNext("hi 观察者");
            }
        }).subscrible(new MSObserver<String>() {
            @Override
            public void onNext(String s) {
                Log.d("lpf","观察者收到消息---"+s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}