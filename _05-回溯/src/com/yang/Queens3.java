package com.yang;

/**
 * @author 默烦
 * 2020/9/12
 */
public class Queens3 {

    public static void main(String[] args) {
        new Queens3().placeQueens();
    }

    // 数组索引是行号，数组元素是列号
    int[] queens;
    // 标记着某一列是否有皇后
    byte cols;
    // 标记着某一对角线是否有皇后（左上角 --> 右下角）
    short leftTop;
    // 标记着某一对角线是否有皇后（右上角 --> 左下角）
    short rightTop;
    // 摆法
    int ways;

    void placeQueens() {
        queens = new int[8];
        place(0);
        System.out.println("8皇后一共有" + ways + "种摆法");
    }

    /**
     * 从第 row 行开始摆放皇后棋子
     *
     * @param row 行号
     */
    void place(int row) {
        if (row == 8) {
            ways++;
            show();
            return;
        }
        for (int col = 0; col < 8; col++) {
            int cv = 1 << col;
            if ((cols & cv) != 0) continue;

            int lv = 1 << (row - col + 7);
            if ((leftTop & lv) != 0) continue;

            int rv = 1 << (row + col);
            if ((rightTop & rv) != 0) continue;

            queens[row] = col;
            // 在对应的位置摆放一个皇后
            cols |= (1 << col);
            leftTop |= lv;
            rightTop |= rv;

            place(row + 1);

            // 回溯时数据重置
            cols &= ~cv;
            leftTop &= ~lv;
            rightTop &= ~rv;
            // 上述数据重置也可直接使用异或^
        }
    }

    void show() {
        for (int row = 0; row < 7; row++) {
            for (int col = 0; col < 7; col++) {
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
