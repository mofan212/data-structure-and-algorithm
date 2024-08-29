package com.yang;

/**
 * @author 默烦
 * 2020/9/12
 */
@SuppressWarnings("all")
public class Queens {

    public static void main(String[] args) {
        new Queens().placeQueens(4);
    }

    /**
     * 数组索引是行号，数组元素是列号
     * 比如 cols[4] = 5 表示第四行第五列放置了一个皇后
     */
    int[] cols;

    /**
     * 摆法
     */
    int ways;

    void placeQueens(int n) {
        if (n < 1) return;
        cols = new int[n];
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
            if (isValid(row, col)) {
                // 在第 row 行第 col 列摆放一个皇后
                cols[row] = col;
                // 摆放好一个棋子后去下一行摆放棋子
                place(row + 1);
                // 因为上一步的递归调用，其实回溯也完成了
            }
        }
    }

    /**
     * 判断第 row 行 第 col 列是否可以摆放皇后
     *
     * @param row 行号
     * @param col 列号
     */
    boolean isValid(int row, int col) {
        for (int i = 0; i < row; i++) {
            // 第 col 列已有皇后
            if (cols[i] == col) {
                System.out.println("[" + row + "][" + col + "]= false");
                return false;
            }
            // 选中格子的斜线上已有皇后
            if (row - i == Math.abs(col - cols[i])) {
                System.out.println("[" + row + "][" + col + "]= false");
                return false;
            }
        }
        System.out.println("[" + row + "][" + col + "]= true");
        return true;
    }

    void show() {
        for (int row = 0; row < cols.length; row++) {
            for (int col = 0; col < cols.length; col++) {
                if (cols[row] == col) {
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
