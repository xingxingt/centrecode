package dataStructure;

import java.util.Random;

/**
 * 【 冒泡排序 】
 * 冒泡排序只会操作相邻的两个数据，每次冒泡操作都会对相邻的两个元素进行比较，
 * 看是否满足大小关系要求，如果不满足就让它互换；
 * 是稳定的排序算法  时间复杂度:O(n平方)
 */
public class BubbleSortExemple {
    public static void bubbleSort(int[] a) {
        long startTime = System.currentTimeMillis();
        int size = a.length;
        if (size <= 1) return;

        for (int i = 0; i < size; i++) {
            //设置一个提前退出冒泡排序的flag，以便减少循环的次数
            boolean flag = false;
            for (int j = 0; j < size - i - 1; j++) {
                if (a[j] > a[j + 1]) {
                    //数据交换
                    int temp = a[j + 1];
                    a[j + 1] = a[j];
                    a[j] = temp;
                }
                //表示有排序
                flag = true;
            }
            //如果没有排序就退出
            if (!flag) break;
        }
        long endTime = System.currentTimeMillis();
        System.out.println(":算法所用时间: " + (endTime - startTime) + "ms");
    }

    public static void show(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.println(i + "---------" + arr[i]);
        }
    }

    public static void main(String[] args) {
        int[] array = new int[10000];

        for (int i = 0; i < 10000; i++) {
            int random = new Random().nextInt(10000);
            array[i] = random;
        }
        bubbleSort(array);


    }

}
