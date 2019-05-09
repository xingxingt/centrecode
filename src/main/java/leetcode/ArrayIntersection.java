package leetcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 求两个数组的交集
 */
public class ArrayIntersection {

    public static int[] intersection(int[] nums1, int[] nums2) {

        if (nums1.length == 0) return nums1;
        if (nums2.length == 0) return nums2;

        List<Integer> list1 = new ArrayList<Integer>();
        Set<Integer> set = new HashSet<Integer>(); //达到去重的效果

        for (int num : nums1) {
            list1.add(num);
        }

        for (int n : nums2) {
            int index = list1.indexOf(n);
            if (index != -1) {
                list1.remove(index);
                set.add(n);
            }
        }

        Object[] res = set.toArray(); //set to array
        int[] array = new int[res.length];
        for (int i = 0; i < res.length; i++) {
            array[i] = (int) res[i];
        }

        return array;

    }


    public static void main(String[] args) {

        int[] nums1 = new int[]{1, 2, 2, 1};
        int[] nums2 = new int[]{2, 2};
        int[] array = intersection(nums1, nums2);
        for (int num : array) {
            System.out.println(num);
        }

    }
}
