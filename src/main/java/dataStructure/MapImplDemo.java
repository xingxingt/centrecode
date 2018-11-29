package dataStructure;

/**
 * 数据节点
 * @param <K>
 * @param <V>
 */
class Node<K, V> {
    K key;
    V value;

    public Node(K key, V value) {
        this.key = key;
        this.value = value;
    }
}

/**
 * 自己实现一个map
 *
 * @param <K>
 * @param <V>
 */
public class MapImplDemo<K, V> {
    private Node<K, V>[] nodes;  //map存储
    private int size;  //map大小

    /**
     * 初始化map大小
     *
     * @param size
     */
    public MapImplDemo(int size) {
        nodes = new Node[size];
    }

    /**
     * 对外提供put方法
     *
     * @param key
     * @param value
     */
    public void put(K key, V value) {
        //判断map容量
        if (nodes.length == 0) {
            nodes = new Node[16];
        }
        //判断是否需要扩容
        if (size == nodes.length) {
            resize();
        }
        //判断此key是否已经存在
        int index = getIndexOfKey(key);
        if (index == -1) {
            nodes[size] = new Node(key, value);
            size++;
        } else {
            nodes[index].value = value;
        }
    }

    /**
     * 对外提供获取数据的方法
     *
     * @param key
     * @return
     */
    public V get(K key) {
        if (key != null) {
            for (int index = 0; index < size; index++) {
                if (key.equals(nodes[index].key)) {
                    return nodes[index].value;
                }
            }
        }
        return (V) "";
    }

    /**
     * 对外提供获取map大小的方法
     *
     * @return
     */
    public int getSize() {
        return nodes.length;
    }

    /**
     * 根据key来获取key所在的数组下标，如果没有就返回-1
     *
     * @param key
     * @return
     */
    public int getIndexOfKey(K key) {
        if (key != null) {
            for (int index = 0; index < size; index++) {
                if (key.equals(nodes[index].key)) {
                    return index;
                }
            }
        }
        return -1;
    }

    /**
     * 扩容
     */
    public void resize() {
        int newSize = nodes.length * 2;
        Node<K, V>[] tmp = new Node[newSize];
        for (int index = 0; index < nodes.length; index++) {
            K key = nodes[index].key;
            V value = nodes[index].value;
            tmp[index] = new Node<K, V>(key, value);
        }
        nodes = null;
        nodes = tmp;
        tmp = null;
    }


    public static void main(String[] args) {

        MapImplDemo mapImplDemo = new MapImplDemo(2);
        mapImplDemo.put("1", "one");
        mapImplDemo.put("2", "two");
        mapImplDemo.put("3", "three");
        mapImplDemo.put("4", "four");
        mapImplDemo.put("5", "five");
        mapImplDemo.put("6", "six");

        for (int index = 0; index < mapImplDemo.size; index++) {
            System.out.println("key:" + (index + 1) + "---value:" + mapImplDemo.get("" + (index + 1)));
        }


    }


}
