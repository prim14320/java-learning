package algorithm.search;
//二分查找
public class BinarySearch {
    public static void main(String[] args) {
        int [] arr = {1,2,3,4,5,6,7,8,9,10};
        System.out.println(binarySearch(arr, 5));
    }
    //二分查找
    //时间复杂度：O(logn)
    //空间复杂度：O(1)
    //进阶版：重复元素查找第一个或最后一个
    private static int binarySearch(int[] arr, int num) {

        int min = 0;
        int max = arr.length-1;
        int result = -1;
        while (true) {
            if(min>max){
                return -1;
            }
            //防止溢出
            int mid = (min+max)>>>1;
            if (arr[mid] > num) {
                max = mid-1;
            }else if (arr[mid] < num) {
                min = mid+1;
            }else{
                result = mid;
                max = mid-1;
           }//查找第一个
            return result;
//            else{
//               min = mid+1;
//           }//查找最后一个
        }

    }
}
