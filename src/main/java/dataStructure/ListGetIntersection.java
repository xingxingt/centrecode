package dataStructure;

import java.util.HashSet;
import java.util.Set;

/**
 * 求两个有序集合的交集
 */
public class ListGetIntersection {


    /**
     * 用两个集合实现
     *
     * @param a
     * @param b
     * @return
     */
    private static Set<Integer> setMethod1(int[] a, int[] b) {
        Set<Integer> set = new HashSet<Integer>();
        Set<Integer> set2 = new HashSet<Integer>();
        for (int i = 0; i < a.length; i++) {
            set.add(a[i]);
        }
        for (int j = 0; j < b.length; j++) {
            if (!set.add(b[j])) //add重复数据返回false
                set2.add(b[j]);
        }
        return set2;
    }


    /**
     * 用一个set实现
     *
     * @param a
     * @param b
     * @return
     */
    private static Set<Integer> forMethod2(int[] a, int[] b) {
        Set<Integer> set = new HashSet<Integer>();
        int i = 0, j = 0;
        while (i < a.length && j < b.length) {
            if (a[i] < b[j])
                i++;
            else if (a[i] > b[j])
                j++;
            else {
                set.add(a[i]);
                i++;
                j++;
            }
        }
        return set;
    }

    private static int[] intersect(int[] a, int[] b) {
        if (a[0] > b[b.length - 1] || b[0] > a[a.length - 1]) {
            return new int[0];
        }
        int[] intersection = new int[Math.max(a.length, b.length)];
        int offset = 0;
        for (int i = 0, s = i; i < a.length && s < b.length; i++) {
            while (a[i] > b[s]) {
                s++;
            }
            if (a[i] == b[s]) {
                intersection[offset++] = b[s++];
            }
            while (i < (a.length - 1) && a[i] == a[i + 1]) {
                i++;
            }
        }
        if (intersection.length == offset) {
            return intersection;
        }
        int[] duplicate = new int[offset];
        System.arraycopy(intersection, 0, duplicate, 0, offset);
        return duplicate;
    }

    public static void main(String[] args) {

        int[] arrya = new int[10];
        int[] arryb = new int[10];
        for (int i = 0; i < 10; i++) {
            arrya[i] = i;
        }
        for (int i = 0; i < 20; i++) {
            if (i < 10) arryb[i] = i + 2;
        }
        forMethod2(arrya, arryb);

    }

}
