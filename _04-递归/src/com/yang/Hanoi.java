package com.yang;

/**
 * @author 默烦
 * 2020/9/3
 */
public class Hanoi {

    public static void main(String[] args) {
        new Hanoi().hanoi(3, "A", "B", "C");
    }

    /**
     * 将 n 个碟子从 p1 移动到 p3
     *
     * @param p2 中间的柱子
     */
    void hanoi(int n, String p1, String p2, String p3) {
        if (n == 1) {
            move(n, p1, p3);
            return;
        }
        hanoi(n - 1, p1, p3, p2);
        move(n, p1, p3);
        hanoi(n - 1, p2, p1, p3);
    }

    /**
     * 将 no 号盘子从 from 移动到 to
     *
     * @param no   盘子的编号
     * @param from 起始柱子
     * @param to   终点柱子
     */
    void move(int no, String from, String to) {
        System.out.println("将" + no + "号盘子从" + from + "移动到" + to);
    }
}
