package dataStructure;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * 在ArrayList的循环中删除元素
 *
 * ArrayList删除元素方法:
 * 1,通过list的remove()方法删除元素,会存在删除重复元素不完整的问题
 * 2,通过list中的ltr中的remove方法，该方法避免了删除重复元素不完整的问题，但是会如果在循环中删除
 * 元素会出现ConcurrentModificationException异常;异常原因在于Next()取值时的checkForComodification();
 *
 * ref:https://juejin.im/post/5b92844a6fb9a05d290ed46c
 */
public class ArrayListHandleDemo {


    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<String>();
        list.add("aa");
        list.add("b1");
        list.add("bb");
        list.add("a1");
        list.add("cc");
        // 删除元素 bb
        remove(list, "bb");
        for (String str : list) {
            System.out.println(str);
        }
    }

    public static void remove(ArrayList<String> list, String elem) {
        // 五种不同的循环及删除方法
        // 方法一：普通for循环正序删除，删除过程中元素向左移动，不能删除重复的元素
//        for (int i = 0; i < list.size(); i++) {
//            if (list.get(i).equals(elem)) {
//                list.remove(list.get(i));
//            }
//        }
        // 方法二：普通for循环倒序删除，删除过程中元素向左移动，可以删除重复的元素
//        for (int i = list.size() - 1; i >= 0; i--) {
//            if (list.get(i).equals(elem)) {
//                list.remove(list.get(i));
//            }
//        }
        // 方法三：增强for循环删除，使用ArrayList的remove()方法删除，产生并发修改异常 ConcurrentModificationException
        for (String str : list) {  //todo 这里实际上使用的是ArrayList中ltr的next方法，这也是产生ConcurrentModificationException的原因
            if (str.equals(elem)) {
                list.remove(str);
            }
        }
        // 方法四：迭代器，使用ArrayList的remove()方法删除，产生并发修改异常 ConcurrentModificationException
//        Iterator iterator = list.iterator();
//        while (iterator.hasNext()) {
//            if (iterator.next().equals(elem)) {  //todo 主要是iterator.next()会checkForComodification
//                list.remove(iterator.next());
//            }
//        }

        // 方法五：迭代器，使用迭代器的remove()方法删除，可以删除重复的元素，但不推荐
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(elem)) {
                iterator.remove();//todo 调用了arrayList中的ltr的remove方法，该方法中expectedModCount = modCount;使该方法不会抛出ConcurrentModificationException
            }
        }
    }


}
