package collectionandmap;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 队列
 * 先进者先出，后进者后出
 * 元素在队尾添加，在队头删除
 * <p>
 * 方法offer表示向队列添加一个元素，poll()与remove()方法都是移除队列头部的元素，
 * 两者的区别在于如果队列为空，那么poll()返回的是null，而remove()会抛出一个异常。
 * 方法element()与peek()主要是获取头部元素，不删除。
 * <p>
 * ref:https://www.jianshu.com/p/63e76826e852
 */
public class QueueDemo {
    public static void main(String[] args) {

        Queue<String> queue = new LinkedList<>();

        queue.offer("aaaa");
        queue.offer("bbbb");
        queue.offer("cccc");
        queue.offer("dddd");

        while (queue.size() > 0) {
            System.out.println(queue.remove() + "");
        }
    }

}
