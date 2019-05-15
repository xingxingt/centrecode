package leetcode;

import java.util.List;

class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }
}

public class SingleLinkedSort {

    public ListNode createLinked() {
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

    public void sort() {
        merge_sort(createLinked());
    }

    /**
     * 获取链表的中间位置
     *
     * @param head
     * @return
     */
    public ListNode getMiddle(ListNode head) {
        //使用快慢指针找中间位置,slow就是中间位置
        ListNode fast = head, slow = head;
        while (fast != null && fast.next != null) {
            slow = fast.next;
            fast = fast.next.next;
        }
        return slow;
    }


    /**
     * 排序
     *
     * @param head
     * @return
     */
    public ListNode merge_sort(ListNode head) {
        if (head == null || head.next == null) return head;

        //获取中间位置
        ListNode middle = getMiddle(head);
        ListNode midright = middle.next;
        //切断链表
        middle.next = null;
        //分治左边的链表:merge_sort(middle),右边的链表merge_sort(midright)
        return merge(merge_sort(middle), merge_sort(midright));
    }

    /**
     * 合并
     *
     * @param left
     * @param right
     * @return
     */
    public ListNode merge(ListNode left, ListNode right) {
        ListNode dummpHead = new ListNode(0);//起始点
        ListNode curr = dummpHead;

        //判断左右大小,然后进行子集排序
        while (left != null && right != null) {
            if (left.val <= right.val) {
                curr.next = left;
                left = left.next;
//                left.next =
            } else {

            }
        }

        return curr;

    }


}
