package dataStructure;


/**
 * 【快速排序】
 * 非稳定的排序算法  时间复杂度:O(nlogn)  空间复杂度O(1):原地排序算法
 * <p>
 * 归并排序的处理过程是由下到上的，先处理子问题，然后再合并。而快排正好相反,从上到下,先分区再处理子问题
 */
public class QuickSortDemo {

    //快速排序
    public void quick_sort(int[] array) {
        quick_sort_imp(array, 0, array.length - 1);
        printArray(array, "原始数组");
    }


    /**
     * 快速排序实现
     *
     * @param array 数组
     * @param p     指针
     * @param r     指针
     */
    public void quick_sort_imp(int[] array, int p, int r) {
        if (p >= r) return; //终止条件

        int pivot = partition(array, p, r);
        //递归调用
        quick_sort_imp(array, p, (pivot - 1));
        quick_sort_imp(array, (pivot + 1), r);
    }


    /**
     * partition函数就是随机选择一个元素作为 pivot分区点（一般情况下，可以选择p..r之间的最后一个元素）
     * 然后对array[p,r]进行分区，并返回一个分区点
     * <p>
     * 为了达到快排是原地排序即空间复杂度为O(1),我们可以像选择排序一样，将array[p,r]使用指针i,j分为两个区间
     * 已排序区间和未排序区间，然后拿着未排序区间的元素和分区点pivot做对比，如果比分区点小则放入到已排序区间(如果使用
     * 插入的方式必然会造成array中元素的移动，所以这里使用交换的方法将array[i]和array[j]进行交换)
     * <p>
     * 如果数组中有两个相同个元素，那么在排序后两个相同的元素位置会发生改变，所以快排是非稳定的排序算法
     *
     * @param array 数组
     * @param p     指针
     * @param r     指针
     * @return 分区点
     */
    public int partition(int[] array, int p, int r) {
        int pivot = array[r];
        int i = p;
        int j = p;

        for (; j <= r - 1; j++) {
            //比较，交换
            if (array[j] < pivot) {
                //判断i和j是否是同一个元素
                if (i == j) {
                    i++;
                } else {
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                    i++;
                }
            }
        }

        //交换i和r(分区点pivot)  因为经过上面交换后[i,j]之间都是比分区点pivot大的,所以将分区点和i的元素进行交换
        int temp = array[i];
        array[i] = array[r];
        array[r] = temp;

        printArray(array, "partition:" + array[i]);

        //因为i和r即pivot发生了互换所以这时的分区点下标为i
        return i;
    }

    public void printArray(int[] array, String name) {
        System.out.println(name);
        for (int k = 0; k < array.length; k++) {
            System.out.print(array[k] + "--");
        }
        System.out.println();

    }


    public static void main(String[] args) {
        int[] array = {51, 46, 20, 18, 65, 97, 82, 30, 77, 50};
        new QuickSortDemo().quick_sort(array);
    }

}
