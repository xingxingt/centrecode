package dataStructure;


/**
 * 实现一个二叉树以及实现使用递归前中后序遍历
 */
public class BinaryTreeDemo1 {

    //树节点
    class TreeNode {
        String value;

        TreeNode left;//左子树

        TreeNode right;//右子树

        public TreeNode(String value) {
            this.value = value;
        }
    }


    /**
     * 前序遍历，先遍历本身节点，再遍历左子树，再遍历右子树
     *
     * @param root
     */
    public void preOrder(TreeNode root) {
        if (root != null) {
            System.out.println(root.value);
            preOrder(root.left);
            preOrder(root.right);
        }
    }

    /**
     * 中序遍历，先遍历左子树，再遍历本身节点，再遍历右子树
     *
     * @param root
     */
    public void inOrder(TreeNode root) {
        if (root != null) {
            inOrder(root.left);
            System.out.println(root.value);
            inOrder(root.right);
        }
    }

    /**
     * 后序遍历，先遍历右子树，再遍历左子树，再遍历本身节点
     *
     * @param root
     */
    public void endOrder(TreeNode root) {
        if (root != null) {
            endOrder(root.left);
            endOrder(root.right);
            System.out.println(root.value);
        }
    }

    public TreeNode createBinaryTree() {


        //构建二叉树
        TreeNode ta = new TreeNode("A");
        TreeNode tb = new TreeNode("B");
        TreeNode tc = new TreeNode("C");
        TreeNode td = new TreeNode("D");
        TreeNode te = new TreeNode("E");
        TreeNode tf = new TreeNode("F");
        TreeNode tg = new TreeNode("G");

        ta.left = tb;
        ta.right = tc;
        tb.left = td;
        tb.right = te;
        tc.left = tf;
        tc.right = tg;

        return ta;

    }


    public static void main(String[] args) {

        BinaryTreeDemo1 demo = new BinaryTreeDemo1();
        TreeNode root = demo.createBinaryTree();
        System.out.println("前序遍历---");
        demo.preOrder(root);
        System.out.println("中序遍历---");
        demo.inOrder(root);
        System.out.println("后序遍历---");
        demo.endOrder(root);

    }


}



