package dataStructure;


class LinkNode {

    public String data;//数据
    public LinkNode next;//下一个元素的指针

    public LinkNode(String data) {
        this.data = data;
    }

}


/**
 * 使用链表实现一个栈
 */
public class StackByLinkDemo {

    private LinkNode top;//定义第一个结点


    /**
     * 入栈
     *
     * @param data
     * @return
     */
    public boolean put(String data) {
        LinkNode node = new LinkNode(data);
        if (top == null) {//判断top是否为空，如果为空则创建第一个节点
            top = node;
            top.next = top;
        } else {
            node.next = top;
            top = node;
        }
        return true;

    }

    /**
     * 出栈
     *
     * @return
     */
    public String pop() {
        if (top == null) {
            return null;
        }

        String data = top.data;
        top = top.next;
//        top.next = top.next.next;
        return data;
    }

    public static void main(String[] args) {
        StackByLinkDemo stack = new StackByLinkDemo();
        stack.put("1");
        stack.put("2");
        stack.put("3");

        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());

    }


}


