package algorithm.search;
//基本查找
public class BasicSearch {
    public static void main(String[] args) {
        int [] arr = {1,2,3,4,5,6,7,8,9,10};
        int num = 5;
        boolean result = basicSearch(arr, num);
        System.out.println(result);
    }

    private static boolean  basicSearch(int[] arr, int num) {
        for (int i = 0; i < arr.length; i++) {
            if(arr[i] == num){
                return true;
            }
        }
        return false;
    }
}
