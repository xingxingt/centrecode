package dataStructure;

/**
 * 单链表反转
 * 可以通过日志分析反转过程
 */
public class SingleLinkedReverse {

    /**
     * 定义链表的节点
     */
    class Node {
        int data;
        Node next;

        public Node(int data) {
            this.data = data;
        }
    }


    public static void main(String[] args) {
        SingleLinkedReverse slr = new SingleLinkedReverse();
        Node head, tail;
        head = tail = slr.new Node(0);
        for (int i = 1; i < 10; i++) {
            Node p = slr.new Node(i);
            tail.next = p;
            tail = p;
        }
        tail = head;
        while (tail != null) {
            System.out.print(tail.data + "  ");
            tail = tail.next;
        }

        System.out.println("  ");

        head = reverse(head);

        System.out.println(" ");
        while (head != null) {
            System.out.print(head.data + "  ");
            head = head.next;
        }

    }


    public static Node reverse(Node head) {
        Node p1, p2 = null;
        p1 = head;

        while (head.next != null) {
            System.out.println("--------------------------");
            System.out.println("head:" + head.data + ":" + head.next.data);
            System.out.println("p1:" + p1.data + ":" + p1.next.data);
            if (p2 != null) System.out.println("p2:" + p2.data + ":" + p2.next.data);

            p2 = head.next;
            head.next = p2.next;//重置head的next指针
            p2.next = p1; //next指针反转
            p1 = p2; //把当前p2的值赋值给p1(记下当前p2到哪个位置了)

            System.out.println("head:" + head.data + ":" + head.next.data);
            System.out.println("p1:" + p1.data + ":" + p1.next.data);
            System.out.println("p2:" + p2.data + ":" + p2.next.data);
        }
        return p2;

    }


}
