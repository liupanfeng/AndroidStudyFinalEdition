package com.example.androidstudyfinaledition.datastructure;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.androidstudyfinaledition.R;

/**
 * 算法
 */
public class DataStructureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_structure);
        int[] array={5,7,1,3,9,4,2,11};  //5 1 3 7 4 2 9 11  ->  1  3 5 4 2 7 9
        bubbleSort(array);
        print(array);
    }

    /**
     * 冒泡排序
     * @param array
     */
    private void bubbleSort(int[] array){
        for (int i = 0; i < array.length-1; i++) { //控制排序的轮次
            for (int j = 0; j < array.length-1-i; j++) { //控制两个临近元素的比较
               if (array[j]>array[j+1]){
                   //交换位置
                   int temp=array[j];  //将这个位置的值暂存
                   array[j]=array[j+1]; //将j+1位置的值放置到j的位置
                   array[j+1]=temp;     //将i位置的值放置到j+1的位置
               }
            }
        }
    }


    

    private void print(int[] array){
        for (int i = 0; i < array.length; i++) {
            Log.d("lpf","result="+array[i]);
        }
    }
}