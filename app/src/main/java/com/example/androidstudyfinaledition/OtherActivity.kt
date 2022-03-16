package com.example.androidstudyfinaledition

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class OtherActivity : AppCompatActivity() {
//    companion object{
//        fun actionStart(context:Context,age:Int,name:String){
//            val intent=Intent(context,OtherActivity::class.java)
//            intent.putExtra("age",age)
//            intent.putExtra("name",name)
//            context.startActivity(intent)
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other)
    }
}