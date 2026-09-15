package algorithm.search;

//分块查找
public class BlockSearch {
    public static void main(String[] args) {
        int[] arr = {16, 5, 9, 12, 21, 18,
                32, 23, 37, 26, 45, 34,
                50, 48, 61, 52, 73, 66};
        Block b1 = new Block(16, 0, 5);
        Block b2 = new Block(32, 6, 11);
        Block b3 = new Block(50, 12, 15);
        Block[] blocks = {b1, b2, b3};
        System.out.println(blocksSearch(arr, blocks, 50));
    }

    private static int  blocksSearch(int[] arr, Block[] blocks, int num) {
        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i].getMax() >= num) {
                for (int j = blocks[i].getStartIndex(); j <= blocks[i].getEndIndex(); j++) {
                    if(arr[j] == num){
                        return j;
                    }
                }
            }
        }
        return -1;
    }
}
