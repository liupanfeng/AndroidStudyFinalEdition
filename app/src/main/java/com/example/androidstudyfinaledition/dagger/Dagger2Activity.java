package com.example.androidstudyfinaledition.dagger;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.androidstudyfinaledition.R;

import javax.inject.Inject;

public class Dagger2Activity extends AppCompatActivity {

    @Inject
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger2);
//        DaggerUserComponent.builder().build().injectDagger2Activity(this);
        user.setName("葫芦哇");
        user.setLevel("小神仙");
        Toast.makeText(this,user.getName()+"是一个"+user.getLevel(),Toast.LENGTH_LONG).show();

    }
}