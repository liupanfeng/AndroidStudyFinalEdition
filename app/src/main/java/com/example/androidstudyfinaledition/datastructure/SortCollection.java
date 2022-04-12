package com.example.androidstudyfinaledition.datastructure;

/**
 * * All rights reserved,Designed by www.meishesdk.com
 *
 * @Author : lpf
 * @CreateDate : 2022/4/12 下午1:08
 * @Description :
 * @Copyright :www.meishesdk.com Inc.All rights reserved.
 */
public class SortCollection {

    private final static SortCollection instance=new SortCollection();

    static class SortCollectionHelper{
        public static SortCollection getInstance(){
            return instance;
        }
    }
    /**
     * 冒泡排序
     *
     * @param array
     */
    public void bubbleSort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) { //控制排序的轮次
            for (int j = 0; j < array.length - 1 - i; j++) { //控制两个临近元素的比较
                if (array[j] > array[j + 1]) {
                    //交换位置
                    int temp = array[j];  //将这个位置的值暂存
                    array[j] = array[j + 1]; //将j+1位置的值放置到j的位置
                    array[j + 1] = temp;     //将i位置的值放置到j+1的位置
                }
            }
        }
    }

    /**
     * 快速排序 本质上是一种分治的排序算法
     *
     * @param array 待排序数组
     * @param start 开始位置
     * @param end   结束位置
     */
    public void quickSort(int[] array, int start, int end) {
        //结束条件
        if (start >= end) {
            return;
        }
        int part = partition(array, start, end);
        //处理切点的前半部分 递归
        quickSort(array, start, part - 1);
        //处理切点的后半部分 递归
        quickSort(array, part + 1, end);
    }

    /**
     * 得到切点
     *
     * @param array
     * @param low
     * @param high
     * @return
     */
    private int partition(int[] array, int low, int high) {
        int part = array[low]; //得到切点的值
        while (low < high) {//循环结束条件
            while (low < high && array[high] > part) {
                high--;
            }
            array[low] = array[high];
            while (low < high && array[low] <= part) {
                low++;
            }
            array[high] = array[low];
        }
        array[low] = part;   //将切点的值，赋值给切点
        return low;   //要的是最终计算得到的位置
    }

}
