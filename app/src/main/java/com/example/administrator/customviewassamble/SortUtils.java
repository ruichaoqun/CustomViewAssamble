package com.example.administrator.customviewassamble;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description.</p>
 * <p>
 * <b>Maintenance History</b>:
 * <table>
 * <tr>
 * <th>Date</th>
 * <th>Developer</th>
 * <th>Target</th>
 * <th>Content</th>
 * </tr>
 * <tr>
 * <td>2018-06-09 19:24</td>
 * <td>Rui chaoqun</td>
 * <td>All</td>
 * <td>Created.</td>
 * </tr>
 * </table>
 */
public class SortUtils {
    public static void bubbleSort(final List<Integer> list, final OnExchangedListener onExchangedListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int temp;
                for (int i = list.size() - 1; i > 0; i--) {
                    for (int j = 0; j < i; j++) {
                        if (list.get(j) > list.get(j + 1)) {
                            temp = list.get(j);
                            list.set(j, list.get(j + 1));
                            list.set(j + 1, temp);
                            try {
                                Thread.sleep(20);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (onExchangedListener != null) {
                                onExchangedListener.onExchanged(list);
                            }
                        }
                    }
                }
            }
        }).start();
    }

    public static void selectionSort(final List<Integer> list, final OnExchangedListener onExchangedListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int temp;
                for (int i = 0; i < list.size(); i++) {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int maxIndex = 0;
                    for (int j = 0; j < list.size() - i; j++) {
                        if (list.get(maxIndex) < list.get(j)) {
                            maxIndex = j;
                        }
                    }
                    temp = list.get(maxIndex);
                    list.set(maxIndex, list.get(list.size() - i - 1));
                    list.set(list.size() - i - 1, temp);
                    onExchangedListener.onExchanged(list);
                }
            }
        }).start();
    }

    public static void insertSort(final List<Integer> list, final OnExchangedListener onExchangedListener) {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        int temp;
                        int targetPosition;
                        for (int i = 0; i < list.size(); i++) {
                            temp = list.get(i);
                            int j = i - 1;
                            while (j >= 0 && list.get(j) > temp) {
                                list.set(j + 1, list.get(j));
                                j--;
                                try {
                                    Thread.sleep(20);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                onExchangedListener.onExchanged(list);
                            }
                            try {
                                Thread.sleep(20);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            list.set(j + 1, temp);
                            onExchangedListener.onExchanged(list);
                        }
                    }
                }
        ).start();
    }

    public static void quickSort(List<Integer> arr, int left, int right, OnExchangedListener onExchangedListener) {
        if (left >= right) {
            return;
        }
        int povit = arr.get(right);
        int targetIndex = left;
        for (int i = left; i < right; i++) {
            if (arr.get(i) < povit) {
                swap(arr, targetIndex, i);
                targetIndex++;
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                onExchangedListener.onExchanged(arr);
            }
        }
        swap(arr, targetIndex, right);
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onExchangedListener.onExchanged(arr);

        quickSort(arr, left, targetIndex - 1, onExchangedListener);
        quickSort(arr, targetIndex + 1, right, onExchangedListener);
    }

    public static void shellSort(final List<Integer> arr, final OnExchangedListener onExchangedListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int d = arr.size();
                while (d > 1) {
                    d = d / 2;
                    for (int i = 0; i < d; i++) {
                        for (int j = i + d; j < arr.size(); j = j + d) {
                            int temp = arr.get(j);
                            int k;
                            for (k = j - d; k >= 0 && arr.get(k) > temp; k = k - d) {
                                arr.set((k + d), arr.get(k));
                                try {
                                    Thread.sleep(20);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                onExchangedListener.onExchanged(arr);
                            }
                            arr.set((k + d), temp);
                            try {
                                Thread.sleep(20);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            onExchangedListener.onExchanged(arr);
                        }
                    }
                }
            }
        }).start();

    }

    /**
     * 归并排序
     * @param arr
     * @param left
     * @param right
     * @param onExchangedListener
     */
    public static void mergeSort(final List<Integer> arr,int left,int right,final OnExchangedListener onExchangedListener){
        List<Integer> temp = new ArrayList<>(arr.size());
        mergeSort(arr,left,right,temp,onExchangedListener);
    }

    /**
     * 归并排序
     * @param arr
     * @param left
     * @param right
     * @param temp
     * @param onExchangedListener
     */
    public static void mergeSort(final List<Integer> arr,int left,int right,final List<Integer> temp,final OnExchangedListener onExchangedListener){
        if(left == right){
            return;
        }
        int mid = (left+right)/2;
        mergeSort(arr,left,mid,temp,onExchangedListener);
        mergeSort(arr,mid+1,right,temp,onExchangedListener);
        merge(arr,left,right,mid,temp,onExchangedListener);
    }

    public static void merge(final List<Integer> arr,int left,int right,int mid,final List<Integer> temp,final OnExchangedListener onExchangedListener){
        temp.clear();
        int p1 = left;
        int p2 = mid+1;
        while (p1 <= mid && p2 <=  right){
            temp.add(arr.get(p1) <= arr.get(p2)?arr.get(p1++):arr.get(p2++));
        }
        while (p1 <= mid){
            temp.add(arr.get(p1++));
        }
        while (p2 <= right){
            temp.add(arr.get(p2++));
        }
        for (int i = 0; i < temp.size(); i++) {
            arr.set(left+i,temp.get(i));
        }
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onExchangedListener.onExchanged(arr);
    }

    public static void swap(List<Integer> arr, int i, int j) {
        int temp = arr.get(i);
        arr.set(i, arr.get(j));
        arr.set(j, temp);
    }


    public interface OnExchangedListener {
        void onExchanged(List<Integer> list);
    }

}
