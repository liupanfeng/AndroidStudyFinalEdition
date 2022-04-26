package com.example.androidstudyfinaledition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.androidstudyfinaledition.databinding.ActivityMainBinding;
import com.example.androidstudyfinaledition.rxjava.RxjavaActivity;


/**
 * 此工程用于Android学习，单独的模块会使用单独的module，零散的东西会直接放在主工程
 */
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, Dagger2Activity.class));
                startActivity(new Intent(MainActivity.this, RxjavaActivity.class));
            }
        });

    }


    /**
     * 有内存等原因Activity被回收的时候，这个方法会回调，我们可以将数据存在outState 中，这样这个Activity再次启动的时候，当执行onCreate这个方法
     * 的时候会传进来一个bundle ，一般这个bundle是null的，但是当异常退出了持久化了数据，从可以通过这个bundle来恢复数据了，
     * 这个技术点一般用于需要记录当前状态的，，比如读书软件需要保存当前进度的这个场景
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}