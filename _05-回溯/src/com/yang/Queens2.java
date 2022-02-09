package com.yang;

/**
 * @author 默烦
 * 2020/9/12
 */
public class Queens2 {

    public static void main(String[] args) {
        new Queens2().placeQueens(4);
    }

    // 数组索引是行号，数组元素是列号
    int[] queens;
    // 标记着某一列是否有皇后
    boolean[] cols;
    // 标记着某一对角线是否有皇后（左上角 --> 右下角）
    boolean[] leftTop;
    // 标记着某一对角线是否有皇后（右上角 --> 左下角）
    boolean[] rightTop;
    // 摆法
    int ways;

    void placeQueens(int n) {
        if (n < 1) return;
        queens = new int[n];
        cols = new boolean[n];
        leftTop = new boolean[(n << 1) - 1];
        rightTop = new boolean[leftTop.length];
        place(0);
        System.out.println(n + "皇后一共有" + ways + "种摆法");
    }

    /**
     * 从第 row 行开始摆放皇后棋子
     *
     * @param row 行号
     */
    void place(int row) {
        if (row == cols.length) {
            ways++;
            show();
            return;
        }
        for (int col = 0; col < cols.length; col++) {
            if (cols[col]) continue;
            int ltIndex = row - col + cols.length - 1;
            if (leftTop[ltIndex]) continue;
            int rtIndex = row + col;
            if (rightTop[rtIndex]) continue;

            queens[row] = col;
            // 在对应的位置摆放一个皇后
            cols[col] = true;
            leftTop[ltIndex] = true;
            rightTop[rtIndex] = true;
            place(row + 1);
            // 回溯时重置布尔值
            cols[col] = false;
            leftTop[ltIndex] = false;
            rightTop[rtIndex] = false;
        }
    }

    void show() {
        for (int row = 0; row < cols.length; row++) {
            for (int col = 0; col < cols.length; col++) {
                if (queens [row] == col) {
                    System.out.print("1 ");
                } else {
                    System.out.print("0 ");
                }
            }
            System.out.println();
        }
        System.out.println("------------------------");
    }
}
