package dataStructure;

/**
 * 实现一个用数组存储的栈
 * 【后进者先出，先进者后出】
 */
public class StackByArrayDemo {

    //数据存储
    private String[] items;
    //元素总数
    private int count;


    //初始化
    public StackByArrayDemo(int capacity) {
        //初始化栈的容量
        this.items = new String[capacity];
        this.count = 0;
    }

    /**
     * 入栈
     *
     * @param element
     * @return
     */
    public boolean put(String element) {
        //判断数组是否已满
        if (count == items.length) {
            return false;
        }
        items[count] = element;
        count++;
        return true;
    }


    /**
     * 出栈
     *
     * @return
     */
    public String pop() {
        //判断数据是否为空
        if (count == 0) {
            return null;
        }
        String popEle = items[count - 1];
        count--;
        return popEle;
    }


    public static void main(String[] args) {
        StackByArrayDemo stack = new StackByArrayDemo(6);

        stack.put("1");
        stack.put("2");
        String element = stack.pop();
        System.out.println(element);

    }


}
