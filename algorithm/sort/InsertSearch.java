package algorithm.sort;
//插入排序
//时间复杂度：O(n^2)
//空间复杂度：O(1)
//算法步骤：
public class InsertSearch {
    public static void main(String[] args) {
        int [] arr = { 5, 9, 12, 16, 21, 18,
                32, 23, 37, 26, 45, 34,
                50, 48, 61, 52, 73, 66, 80,32, 23, 37, 26, 45, 34};
        long startTime =  System.nanoTime();
        insertSort(arr);
        long endTime =  System.nanoTime();
        System.out.println("插入排序耗时："+(endTime-startTime)+"ns");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]+" ");
        }
    }

    private static void insertSort(int[] arr) {
        //先找到无序序列的第一个元素
        int startIndex = 0;
        for (int i = 0; i < arr.length; i++) {
            if(arr[i]>arr[i+1]){
                startIndex = i+1;
                break;
            }
        }
        //从无序序列的第一个元素开始，与其后面的每个有序序列元素比较，若小于有序序列元素，则将其交换到有序序列中，
        //否则，直接跳过
        for (int i = startIndex; i < arr.length; i++) {
            int j=i;
            while(j>0&&arr[j]<arr[j-1]){
                    int temp=arr[j];
                    arr[j]=arr[j-1];
                    arr[j-1]=temp;
                    j--;
            }

        }
    }
}
