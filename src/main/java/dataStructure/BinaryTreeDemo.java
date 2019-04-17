package dataStructure;

/**
 * 定义节点
 *
 * @param <T>
 */
class TreeNode<T> {

    T data;//数据
    TreeNode<T> leftTree;//左子树
    TreeNode<T> rightTree; //右子树

    public TreeNode(T data) {
        this.data = data;
        this.leftTree = null;
        this.rightTree = null;
    }

}

/**
 * 实现一个二叉树
 */
public class BinaryTreeDemo<T> {
    TreeNode<T> root;//二叉树的根节点

    //先定义出根节点
    public BinaryTreeDemo(T data, TreeNode<T> leftTree, TreeNode<T> rightTree) {
        root = new TreeNode<T>(data);
        this.root.data = data;
        this.root.leftTree = leftTree;
        this.root.rightTree = rightTree;
    }

    public int getHeight() {
        return computHeight(root);
    }

    public int getSize() {
        return computSize(root);
    }

    //获取树的高度
    public int computHeight(TreeNode<T> root) {
        if (root == null) {//这里是关键
            return 0;
        }
        System.out.println(root.data);
        int leftTree = computHeight(root.leftTree);//这里主要是看root的左右节点是否为空
        int rightTree = computHeight(root.rightTree);
//        System.out.println("................" + (leftTree > rightTree ? (leftTree + 1) : (rightTree + 1)));
        return leftTree > rightTree ? (leftTree + 1) : (rightTree + 1);
    }

    //获取树的大小
    public int computSize(TreeNode<T> root) {
        if (root == null) {//这里是关键
            return 0;
        }
        int leftTree = computSize(root.leftTree);//这里主要是看root的左右节点是否为空
        int rightTree = computSize(root.rightTree);
        return leftTree + rightTree + 1;
    }


    public static void main(String[] args) {
        //创建二叉树的节点
        TreeNode tb = new TreeNode("B");
        TreeNode tc = new TreeNode("C");
        TreeNode td = new TreeNode("D");
        TreeNode te = new TreeNode("E");
        TreeNode tf = new TreeNode("F");
        TreeNode tg = new TreeNode("G");


        //根据创建的节点创建二叉树
        BinaryTreeDemo tree = new BinaryTreeDemo("A", tb, tc);
        tb.leftTree = td;
        tb.rightTree = te;
        tc.leftTree = tf;
        tc.rightTree = tg;

        System.out.println("tree height：" + tree.getHeight());
        System.out.println("tree size：" + tree.getSize());


    }

}
