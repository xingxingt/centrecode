package dataStructure;

import java.util.Random;

/**
 * 【插入排序】
 * 插入排序的两个操作：1，元素的比较 2，元素的移动
 * 会将一个待排序数组分为已排序区间和未排序区间
 * 冒泡排序10000个元素 用时:18ms  插入排序 用时:123ms
 * 是稳定的排序算法  时间复杂度:O(n平方)  是原地排序算法
 */
public class InsertionSortDemo {


    public static void insertionSort(int[] arr) {
        long startTime = System.currentTimeMillis();

        if (arr.length <= 1) {
            return;
        }

        for (int i = 1; i < arr.length; i++) {
            //第i个值
            int value = arr[i];
            //i前面有几个元素,这里定义一个全局变量方便获取插入数据的索引位置
            int j = i - 1;
            //遍历i前面的元素 查找插入的位置
            for (; j >= 0; j--) {
                //判断value和当前值j的值大小
                if (arr[j] > value) {
                    //j的值大于value值，则进行数据移动其实就是将 arr[j]的值赋值给 arr[j + 1]
                    //例如:{4, 5, 6, 1, 3, 2}  => {4, 5, 6, 6, 3, 2}
                    arr[j + 1] = arr[j];
                } else {
                    break;
                }
            }
            //在数组的第j+1的位置插入元素，也就是在比value小的元素前面插入元素
            arr[j + 1] = value;

        }
        long endTime = System.currentTimeMillis();
        System.out.println(":算法所用时间: " + (endTime - startTime) + "ms");
    }


    public static void main(String[] args) {
        int[] array = new int[10000];

        for (int i = 0; i < 10000; i++) {
            int random = new Random().nextInt(10000);
            array[i] = random;
        }

        insertionSort(array);
    }


}
