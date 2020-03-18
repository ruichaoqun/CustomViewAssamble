package com.example.administrator.customviewassamble;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Test {
    public static void main(String[] args){
        int[] arr = new int[10000];
        Random random = new Random();
        List<Integer> list = new ArrayList<>(10000);
        for (int i = 0; i < arr.length; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
//        arr = new int[]{5,9,8,7,6,5,4,3,2,5};
//        StringBuilder builder = new StringBuilder();
//        for (int i = 0; i < 15; i++) {
//            if(i < 10){
//                builder.append(" "+i);
//            }else{
//                builder.append(i);
//            }
//            builder.append(" , ");
//        }
//        System.out.println("初  0化：  ["+builder.toString()+"]");
//        printLn(arr,0);
//
//        selectSort(arr);
//        insertSort(arr);
//        shellSort(arr);
        long time1 = System.currentTimeMillis();
//        bubbleSort(arr);
        quickSort(arr,0,arr.length-1);
        long time2 = System.currentTimeMillis();
        System.out.println(time2-time1);
    }

    /**
     * 冒泡排序
     */
    public static void bubbleSort(int[] arr){
        int temp;
//        int count = 1;
        for (int i = 0; i < arr.length; i++) {
            for (int j = arr.length-1; j > i; j--) {
                if(arr[j] < arr[j-1]){
                    swap(arr,j,j-1);
                }
//                printLn(arr,count);
//                count++;
            }
        }
    }

    /**
     *
     * @param arr
     */
    public static void selectSort(int[] arr){
        int position;
        int count = 1;
        for (int i = 0; i < arr.length; i++) {
            position = i;
            for (int j = i+1; j < arr.length; j++) {
                if(arr[j] < arr[position]){
                    position = j;
                }
            }
            swap(arr,i,position);
            printLn(arr,count);
            count++;
        }
    }

    /**
     * 插入排序
     * 原理：每一遍找出前
     * @param arr
     */
    public static void insertSort(int[] arr){
        int j,m=1;
        for (int i = 1; i < arr.length; i++) {
            if(arr[i] < arr[i-1]){
                int temp = arr[i];
                for (j = i-1; j >=0 && arr[j] > temp; j--) {
                    arr[j+1] = arr[j];
                }
                arr[j+1] = temp;
            }
            printLn(arr,m);
            m++;
        }
    }

    /**
     * 希尔排序，又叫缩小增量排序
     */
    public static void shellSort(int[] arr){
        int step = arr.length/2;
        while (step != 0){
            for (int i = 0; i < step; i++) {
                for (int j = i+step; j < arr.length; j += step) {
                    int temp = arr[j];
                    int k;
                    for ( k = j - step; k >= 0 && arr[k] > temp; k -=step) {
                        arr[k+step] = arr[k];
                    }
                    arr[k+step] = temp;
                }
                printLn(arr,0);
            }
            step = step/2;
        }
    }

    public static void quickSort(int[] arr,int startIndex,int endIndex){
        if(startIndex >= endIndex){
            return;
        }
        int pivot = arr[startIndex];
        int left = startIndex;
        int right = endIndex;
        while( left != right) {
            //控制right指针比较并左移
            while(left<right && arr[right] > pivot){
                right--;
            }
            //控制right指针比较并右移
            while( left<right && arr[left] <= pivot) {
                left++;
            }
            //交换left和right指向的元素
            if(left<right) {
                int p = arr[left];
                arr[left] = arr[right];
                arr[right] = p;
            }
        }
//pivot和指针重合点交换
        int p = arr[left];
        arr[left] = arr[startIndex];
        arr[startIndex] = p;
//        printLn(arr,0);
        quickSort(arr,startIndex,left - 1);
        quickSort(arr,left+1,endIndex);
    }



    public static void printLn(int[] arr,int times){
        StringBuffer buffer = new StringBuffer();
        String time;
        if(times < 10){
            time = "  "+times;
        }else if(times < 100){
            time = " "+times;
        }else {
            time = String.valueOf(times);
        }

        Map map;
        buffer.append("第"+time+"遍：  ");
        buffer.append("[");
        for (int i = 0; i < arr.length; i++) {
            if(arr[i] < 10){
                buffer.append(" "+arr[i]);
            }else{
                buffer.append(arr[i]);
            }
            buffer.append(" , ");
        }
        buffer.append("]");
        System.out.println(buffer.toString());
    }

    public static void swap(int[] arr,int i,int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
