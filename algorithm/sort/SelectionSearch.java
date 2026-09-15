package algorithm.sort;
//选择排序
//时间复杂度：O(n^2)
//空间复杂度：O(1)
//算法步骤：从零索引开始，跟后面的元素一一比较，每次从数组中选择最小的元素，将其放到数组的最前面

public class SelectionSearch {
    public static void main(String[] args) {
        int [] arr = {16, 5, 9, 12, 21, 18,
                32, 23, 37, 26, 45, 34,
                50, 48, 61, 52, 73, 66, 80,32, 23, 37, 26, 45, 34};
        long startTime =  System.nanoTime();
        selectionSort(arr);
        long endTime =  System.nanoTime();
        System.out.println("选择排序耗时："+(endTime-startTime)+"ns");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]+" ");
        }
    }

    private static void selectionSort(int[] arr) {
        for (int j = 0; j < arr.length-1; j++) {
            for (int i = j; i < arr.length; i++) {
                if(arr[j]>arr[i]){
                    int temp = arr[j];
                    arr[j] = arr[i];
                    arr[i] = temp;
                }
            }
        }
    }
}
