package algorithm.sort;
//冒泡排序
//时间复杂度：O(n^2)
//空间复杂度：O(1)
//算法步骤：相邻的元素两两比较，大的放右边，小的放左边
//第一轮比较完毕之后，最大值就已经确定，第二轮可以少循环一次，后面以此类推
//如果数组中有n个数据，总共我们只要执行n-1轮的代码就可以完成排序
public class Bubble {
    public static void main(String[] args) {
        int [] arr = {16, 5, 9, 12, 21, 18,
                32, 23, 37, 26, 45, 34,
                50, 48, 61, 52, 73, 66, 80,32, 23, 37, 26, 45, 34};
        long startTime =  System.nanoTime();
        bubbleSort(arr);
        long endTime =  System.nanoTime();
        System.out.println("冒泡排序耗时："+(endTime-startTime)+"ns");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]+" ");
        }
    }

    private static void bubbleSort(int[] arr) {
        for (int j = 0; j < arr.length-1; j++) {
            for (int i = 0; i < arr.length-1-j; i++) {
                if (arr[i] > arr[i + 1]) {
                    int temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                }
            }
        }
    }
}
