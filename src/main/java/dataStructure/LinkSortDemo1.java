package dataStructure;

/**
 * 定义链表节点
 */
class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
        next = null;
    }
}

/**
 * 使用归并对链表进行排序
 */
public class LinkSortDemo1 {

    //找到中间点，然后分割
    public ListNode getMiddle(ListNode head) {
        if (head == null) {
            return head;
        }
        System.out.println("--------------------------");
        //快慢指针
        ListNode slow, fast;
        slow = fast = head;
        while (fast.next != null && fast.next.next != null) {
            System.out.println(fast.val+"----"+slow.val);
            slow = slow.next;
            fast = fast.next.next;

        }
        return slow;
    }

    // 合并排好序的链表
    public ListNode merge(ListNode a, ListNode b) {
        ListNode dummyHead, curr;
        dummyHead = new ListNode(0);
        curr = dummyHead;  //curr和dummyHead指向同一个对象地址， curr是当前比较的listNode元素 dummyHead:排好序的head节点
        while (a != null && b != null) { // 当a或者b链表元素比较完后就为空链表
            if (a.val <= b.val) { //a链表的每个元素和b链表的head比较大小
                curr.next = a;
                a = a.next; //改变a的值,下个循环将a的next节点和b节点比较
            } else {
                curr.next = b;
                b = b.next;
            }
            curr = curr.next; //将curr指向下一次循环需要比较的节点
        }
        curr.next = (a == null) ? b : a;
        return dummyHead.next;
    }


    //单链表的归并排序
    public ListNode merge_sort(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        //得到链表中间的数
        ListNode middle = getMiddle(head);
        System.out.println("mid:"+middle.val);
        ListNode sHalf = middle.next;
        //拆分链表
        middle.next = null;
        //递归调用
        return merge(merge_sort(head), merge_sort(sHalf));
    }


    public static void main(String[] args) {
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

        ListNode p = head;
        while (p.next != null) {
            System.out.print(p.val);
            p = p.next;
        }
        System.out.print(p.val);
        System.out.println();

        new LinkSortDemo1().merge_sort(head);

        p = head;
        while (p != null) {
            System.out.print(p.val);
            p = p.next;
        }
    }


}
