package dataStructure;

/**
 * 【计数排序】
 * 计数排序是一个特殊的桶排序，它的思想和桶排序很类似，要排序n个数据时，在数据范围不大的情况下，假如最大值是k，
 * 那么就分k个桶，每个桶里面数据都是一样的，这样就省去了每个桶里面排序的步骤;
 * 计数排序只能用在数据范围不大的场景，如果数据范围k远大于n,那就不适合用基数排序,而且计数排序只能给非负整数排序;
 * <p>
 * 【桶排序,计数排序，基数排序的时间复杂度都为O(n)，但是局限性很大】
 */
public class CountingSortDemo {


    public static void countingSortImp(int[] array) {

        //1,取array中最大范围值,即最大值k
        int max = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] > max) max = array[i];
        }

        //2，生成一个数组，数组大小范围为max,元素出现的次数为value,下标为0-k,相当于分桶
        //其实在这里根据最大范围的值进行分桶后就相当于对array已经排了序,因为桶的下标是有序的0-k
        int[] tmp_1 = new int[max + 1];
        for (int i = 0; i < array.length; i++) {
            tmp_1[array[i]] = tmp_1[array[i]] + 1;
        }

        //3,数组tmp_1顺序求和
        for (int i = 0; i < tmp_1.length; i++) {
            if (i > 0) tmp_1[i] = tmp_1[i] + tmp_1[i - 1];
        }

        //4,遍历数组array，计算
        int[] result_array = new int[array.length];//此数组就是排完序的数组
        for (int i = 0; i < array.length; i++) {
            //从array数组中取值，即值就作为tem_1的下标,并将结果放入result_array
            int index = array[i];
            //计算array[i]在tem_1的下标,即array[i]该放在result_array的哪个位置
            int result_index = (tmp_1[index] - 1);//这就是array[i]在result_array的位置下标
            result_array[result_index] = array[i];
            //修改index对应的tem_1的元素值
            tmp_1[index] = tmp_1[index] - 1;
        }

        printArray(result_array, "");

    }

    public static void printArray(int[] array, String name) {
        System.out.println(name);
        for (int k = 0; k < array.length; k++) {
            System.out.print(array[k] + "--");
        }
        System.out.println();

    }


    public static void main(String[] args) {

        int[] array = {2, 5, 3, 0, 2, 3, 0, 3, 11};
        countingSortImp(array);

    }


}
