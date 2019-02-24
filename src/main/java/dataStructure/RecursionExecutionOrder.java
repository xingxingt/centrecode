package dataStructure;


/**
 * 【递归函数的执行顺序】
 * <p>
 * 首先， main() 使用参数 1 调用了函数 up_and_down() ，于是 up_and_down() 中形式参数 n 的值是 1, 故打印语句 #1 输出了 Level1 。
 * 然后，由于 n 的数值小于 4 ，所以 up_and_down() （第 1 级）使用参数 n+1 即数值 2 调用了 up_and_down()( 第 2 级 ). 使得 n 在第 2
 * 级调用中被赋值 2, 打印语句 #1 输出的是 Level2 。与之类似，下面的两次调用分别打印出 Level3 和 Level4 。
 * 当开始执行第 4 级调用时， n 的值是 4 ，因此 if 语句的条件不满足。这时候不再继续调用 up_and_down() 函数。第 4 级调用接
 * 着执行打印语句 #2 ，即输出 Level4 ，因为 n 的值是 4 。现在函数需要执行return 语句，此时第 4 级调用结束，把控制权返回给该
 * 函数的调用函数，也就是第 3级调用函数。第 3 级调用函数中前一个执行过的语句是在 if 语句中进行第 4 级调用。因此，它继
 * 续执行其后继代码，即执行打印语句 #2 ，这将会输出 Level3 ．当第 3 级调用结束后，第 2 级调用函数开始继续执行，即输出
 * Level2 ．依次类推．
 * <p>
 * <p>
 * 注意，每一级的递归都使用它自己的私有的变量 n ．可以查看地址的值来证明．
 * <p>
 * <p>
 * <p>
 * <p>
 * 递归的基本原理：
 * １  每一次函数调用都会有一次返回．当程序流执行到某一级递归的结尾处时，它会转移到前一级递归继续执行．
 * ２  递归函数中，位于递归调用前的语句和各级被调函数具有相同的顺序．如打印语句 #1 位于递归调用语句前，它按照递归调用的顺序被执行了 4 次．
 * ３　每一级的函数调用都有自己的私有变量．
 * ４　递归函数中，位于递归调用语句后的语句的执行顺序和各个被调用函数的顺序相反．
 * ５　虽然每一级递归有自己的变量，但是函数代码并不会得到复制．
 * ６　递归函数中必须包含可以终止递归调用的语
 */
public class RecursionExecutionOrder {

    public void up_and_down(int n) {
        System.out.println("Level 1  location " + n); //#1
        if (n < 5) {
            up_and_down(n + 1);
        }
        System.out.println("Level 2 location " + n); //#2
    }

    public void exe() {
        up_and_down(1);
    }


    public static void main(String[] args) {
        new RecursionExecutionOrder().exe();
    }
}
