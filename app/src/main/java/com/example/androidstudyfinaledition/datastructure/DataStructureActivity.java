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
        SortCollection sortCollection = SortCollection.SortCollectionHelper.getInstance();
        int[] array = {5, 7, 1, 3, 9, 4, 2, 11};  //5 1 3 7 4 2 9 11  ->  1  3 5 4 2 7 9
//        bubbleSort(array);
        sortCollection.quickSort(array, 0, array.length - 1);
        print(array);
    }

    private void print(int[] array) {
        for (int i = 0; i < array.length; i++) {
            Log.d("lpf", "result=" + array[i]);
        }
    }
}