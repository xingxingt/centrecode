package dataStructure;

/**
 * 【二分查找算法】
 * 时间复杂度为O(logn) 指数级的复杂度，甚至比O(1)的算法更高效.O(1)也有可能是执行10000次的常量级
 * 二分查找的局限性很大:
 * 1,二分查找依赖顺序表表结构，也就是数组,因为他要通过下标来计算，如果是链表的话链表的随机访问时间复杂度为O(n)
 * 2,二分查找针对有序的数据
 * 3,数据量太大或者太小都不适合用二分查找,因为数据量小用顺序遍历即可，数据太大分配连续的内存空间比较困难
 * 4,适合处理静态数据，也就是没有频繁插入和修改,删除的数据操作
 */
public class BinarySearchDemo {


    /**
     * 二分查找算法实现--数组中无重复数据,最简单的写法
     * 注意点: 1,while循环退出条件 2,mid的取值 3,low和hight的更新
     *
     * @param array 有序的数组
     * @param value 要查找的值
     * @return int
     */
    public static int binarySearch1(int[] array, int value) {
        int low = 0;  //定义mid左边指标
        int hight = array.length - 1;  //定义mid右边指标

        while (low <= hight) {
            int mid = (low + hight) / 2; //计算mid，即中间值的下标
//        int mid=low + (hight - low) / 2; //为了防止low+hight数值太大溢出

            System.out.println("mid index:" + mid + "--mid value:" + array[mid]);
            if (array[mid] == value) {
                return array[mid];
            } else if (array[mid] > value) {
                hight = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }


    /**
     * 二分查找算法实现--数组中有重复数据 查找第一个值等于给定值的元素
     * 注意点: 1,while循环退出条件 2,mid的取值 3,low和hight的更新
     *
     * @param array 有序的数组
     * @param value 要查找的值
     * @return int
     */
    public static int binarySearch2(int[] array, int value) {
        int low = 0;  //定义mid左边指标
        int hight = array.length - 1;  //定义mid右边指标

        while (low <= hight) {
            int mid = (low + hight) / 2; //计算mid，即中间值的下标
//        int mid=low + (hight - low) / 2; //为了防止low+hight数值太大溢出
            System.out.println("mid index:" + mid + "--mid value:" + array[mid]);
            if (array[mid] == value) {
                //判断array[mid]的下标是否是0，或者array[mid]前面一个元素的大小是否等于value
                if (mid == 0 || array[mid - 1] != value) {
                    return array[mid];
                } else {
                    hight = mid - 1;
                }
            } else if (array[mid] > value) {
                hight = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }

    /**
     * 二分查找算法实现--数组中有重复数据 查找最后一个值等于给定值的元素
     * 注意点: 1,while循环退出条件 2,mid的取值 3,low和hight的更新
     *
     * @param array 有序的数组
     * @param value 要查找的值
     * @return int
     */
    public static int binarySearch3(int[] array, int value) {
        int low = 0;  //定义mid左边指标
        int hight = array.length - 1;  //定义mid右边指标

        while (low <= hight) {
            int mid = (low + hight) / 2; //计算mid，即中间值的下标
//        int mid=low + (hight - low) / 2; //为了防止low+hight数值太大溢出

            System.out.println("mid index:" + mid + "--mid value:" + array[mid]);
            if (array[mid] == value) {
                //判断array[mid]的下标是否是array.length - 1，或者array[mid]后面一个元素的大小是否等于value
                if (mid == (array.length - 1) || array[mid + 1] != value) {
                    return array[mid];
                } else {
                    low = mid + 1;
                }
            } else if (array[mid] > value) {
                hight = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }


    /**
     * 二分查找算法实现--数组中有重复数据 查找第一个大于等于给定值的元素
     * 注意点: 1,while循环退出条件 2,mid的取值 3,low和hight的更新
     *
     * @param array 有序的数组
     * @param value 要查找的值
     * @return int
     */
    public static int binarySearch4(int[] array, int value) {
        int low = 0;  //定义mid左边指标
        int hight = array.length - 1;  //定义mid右边指标

        while (low <= hight) {
            int mid = (low + hight) / 2; //计算mid，即中间值的下标
//        int mid=low + (hight - low) / 2; //为了防止low+hight数值太大溢出

            System.out.println("mid index:" + mid + "--mid value:" + array[mid]);
            if (array[mid] >= value) {
                //判断array[mid]的下标是否是0，或者array[mid-1]后面一个元素的大小是否小于value
                if (mid == 0 || array[mid - 1] < value) {
                    return array[mid];
                } else {
                    hight = mid - 1;
                }
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }


    /**
     * 二分查找算法实现--数组中有重复数据 查找最后一个小于等于给定值的元素
     * 注意点: 1,while循环退出条件 2,mid的取值 3,low和hight的更新
     *
     * @param array 有序的数组
     * @param value 要查找的值
     * @return int
     */
    public static int binarySearch5(int[] array, int value) {
        int low = 0;  //定义mid左边指标
        int hight = array.length - 1;  //定义mid右边指标

        while (low <= hight) {
            int mid = (low + hight) / 2; //计算mid，即中间值的下标
//        int mid=low + (hight - low) / 2; //为了防止low+hight数值太大溢出

            System.out.println("mid index:" + mid + "--mid value:" + array[mid]);
            if (array[mid] <= value) {
                //判断array[mid]的下标是否是array.length - 1，或者array[mid+1]后面一个元素的大小是否大于value
                if (mid == array.length - 1 || array[mid + 1] > value) {
                    return array[mid];
                } else {
                    low = mid + 1;
                }
            } else {
                hight = mid - 1;
            }
        }
        return -1;
    }


    public static void main(String[] args) {
        int[] arrry1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        int[] arrry2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 9, 9, 10, 11, 12, 13, 14, 15, 15, 15};
//        binarySearch1(arrry1, 15);
//        binarySearch2(arrry2, 15);
//        binarySearch3(arrry2, 15);
//        binarySearch4(arrry2, 15);
        binarySearch5(arrry2, 15);
    }


}
