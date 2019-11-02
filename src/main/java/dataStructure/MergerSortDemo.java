package dataStructure;

import java.util.concurrent.atomic.AtomicInteger;

/***
 * 【归并排序】
 *  是稳定的排序算法  时间复杂度:O(nlogn)  空间复杂度O(n):非原地排序算法，因为在merge的时候需要申请临时的内存空间
 */
public class MergerSortDemo {
    private AtomicInteger accu = new AtomicInteger(1);

    public void merge_sort(int[] array) {
        int array_length = array.length;
        merger_c(array, 0, array_length - 1, "");
    }

    /**
     * 归并算法
     *
     * @param array 数组
     * @param p     开始位置
     * @param r     结束位置
     */
    public void merger_c(int[] array, int p, int r, String flag) {
//        System.out.println("---flag:" + flag);
        //结束条件
        if (p >= r) return;

        //获取p，r的中间位置q
        int q = (p + r) / 2; //通过 / 取整
//        int q = p + (r -p) / 2;

        //分而治21`
        merger_c(array, p, q, "1");
        merger_c(array, q + 1, r, "2");

        //合并
        merge(array, p, q, r);

    }

    /**
     * @param array 输入数组
     * @param p     左边位置
     * @param q     中间位置
     * @param r     右边位置
     */
    public void merge(int[] array, int p, int q, int r) {
        //生成两个索引
        int i = p; //左边
        int j = q + 1; //右边
        int k = 0;
        //生成一个和array[p...r]同样大小的array
        int[] tmp = new int[r - p + 1];

        while (i <= q & j <= r) {
            if (array[i] > array[j]) { //左右两边进行比较 如果i>j则将j对应的值放入tmp数组中，并且j的下标向右移动一位，否则，反之
                tmp[k] = array[j];
                k++;
                j++;
            } else {
                tmp[k] = array[i];
                k++;
                i++;
            }
        }


        //判断子数组中是否有剩余的数据[i q] and [j r]
        int start = i;
        int end = q;
        if (j <= r) {
            start = j;
            end = r;
        }

        //将剩余的数据copy到tmp数组中
        while (start <= end) {
            tmp[k] = array[start];
            k++;
            start++;
        }

        //将tmp临时数组中的数据copy到原来的array数组中
        for (int w = 0; w <= (r - p); w++) {
            array[p + w] = tmp[w];
        }

        System.out.println(p + "--" + q + "---" + r + "排序后:" + accu.getAndAdd(1));
        for (int n = 0; n < tmp.length; n++) {
            System.out.print(tmp[n] + ",");
        }
        System.out.println();


    }

    public static void main(String[] args) {
        int[] array = new int[]{3, 1, 5, 6, 4, 8, 7, 8, 11};
        new MergerSortDemo().merge_sort(array);

        //执行结果:
        /**
         0--0---1排序后:1
         1,3,
         0--1---2排序后:2
         1,3,5,
         3--3---4排序后:3
         4,6,
         0--2---4排序后:4
         1,3,4,5,6,
         5--5---6排序后:5
         7,8,
         7--7---8排序后:6
         8,11,
         5--6---8排序后:7
         7,8,8,11,
         0--4---8排序后:8
         1,3,4,5,6,7,8,8,11
         */
    }


}
