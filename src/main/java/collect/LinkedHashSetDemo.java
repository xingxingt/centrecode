package collect;


import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 * 【LinkedHashSet】
 * LinkedHashSet继承Hashset,底层使用LinkedHashMap来实现
 * <p>
 * 支持对规则集内的元素排序。HashSet中的元素是没有被排序的，
 * 而LinkedHashSet中的元素可以按照它们插入规则集的顺序提取。
 */
public class LinkedHashSetDemo {

    public static void main(String[] args) {
        HashSet<Integer> set = new LinkedHashSet<Integer>();
        set.add(1);
        set.add(2);
        set.add(5);
        set.add(3);
        set.add(4);
        set.add(2);
        Iterator<Integer> it = set.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());

        }

    }
}
