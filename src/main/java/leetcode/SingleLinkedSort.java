package leetcode;

/**
 * 参考：Sort List——经典（链表中的归并排序） https://www.cnblogs.com/qiaozhoulin/p/4585401.html
 * <p>
 * 归并排序法：在动手之前一直觉得空间复杂度为常量不太可能，因为原来使用归并时，都是 O(N)的，
 * 需要复制出相等的空间来进行赋值归并。对于链表，实际上是可以实现常数空间占用的（链表的归并
 * 排序不需要额外的空间）。利用归并的思想，递归地将当前链表分为两段，然后merge，分两段的方
 * 法是使用 fast-slow 法，用两个指针，一个每次走两步，一个走一步，知道快的走到了末尾，然后
 * 慢的所在位置就是中间位置，这样就分成了两段。merge时，把两段头部节点值比较，用一个 p 指向
 * 较小的，且记录第一个节点，然后 两段的头一步一步向后走，p也一直向后走，总是指向较小节点，
 * 直至其中一个头为NULL，处理剩下的元素。最后返回记录的头即可。
 * <p>
 * 主要考察3个知识点，
 * 知识点1：归并排序的整体思想
 * 知识点2：找到一个链表的中间节点的方法
 * 知识点3：合并两个已排好序的链表为一个新的有序链表
 */
class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }
}

public class SingleLinkedSort {

    public static ListNode createLinked() {
        ListNode head = new ListNode(0);
        ListNode l1 = new ListNode(2);
        ListNode l2 = new ListNode(5);
        ListNode l3 = new ListNode(3);
        ListNode l4 = new ListNode(8);
        ListNode l5 = new ListNode(4);
        ListNode l6 = new ListNode(2);
        ListNode l7 = new ListNode(1);

        head.next = l1;
        l1.next = l2;
        l2.next = l3;
        l3.next = l4;
        l4.next = l5;
        l5.next = l6;
        l6.next = l7;
        l7.next = null;

        return head;
    }

    public static ListNode sort() {
        return mergeSort(createLinked());
    }

    /**
     * 拆分
     *
     * @param head
     * @return
     */
    private static ListNode mergeSort(ListNode head) {
        if (head.next == null) {
            return head;
        }
        //使用快慢指针寻找中间点 p:slow,q:fast
        ListNode p = head, q = head, pre = null;
        while (q != null && q.next != null) {
            pre = p;
            p = p.next;
            q = q.next.next;
        }
        //分割链表
        pre.next = null;
        //分治左边的链表:merge_sort(middle),右边的链表merge_sort(midright)
        ListNode l = mergeSort(head);
        ListNode r = mergeSort(p);
        //分治左边的链表:merge(l),右边的链表merge(r)
        return merge(l, r);
    }

    /**
     * 合并
     *
     * @param l
     * @param r
     * @return
     */
    public static ListNode merge(ListNode l, ListNode r) {
        ListNode dummyHead = new ListNode(0);
        ListNode cur = dummyHead;
        while (l != null && r != null) {
            if (l.val <= r.val) {
                cur.next = l;
                cur = cur.next;
                l = l.next;
            } else {
                cur.next = r;
                cur = cur.next;
                r = r.next;
            }
        }
        if (l != null) {
            cur.next = l;
        }
        if (r != null) {
            cur.next = r;
        }
        return dummyHead.next;
    }


    public static void main(String[] args) {

        ListNode head = sort();
        while (head != null) {
            System.out.println(head.val);
            head = head.next;
        }


    }


}
