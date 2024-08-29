package com.yang;

/**
 * @author 默烦
 * @date 2020/6/17
 */
// 打印斐波那契数
public class Main {
    /*
     *  0 1 1 2 3 5 8 13 ...
     * */

    // 递归 （存在性能问题）
    public static int fib1(int n) {
        if (n <= 1) {
            return n;
        }
        return fib1(n - 1) + fib1(n - 2);
    }

    // 循环
    public static int fib2(int n) {
        if (n <= 1) {
            return n;
        }
        int first = 0;
        int second = 1;
        for (int i = 0; i < n - 1; i++) {
            int sum = first + second;
            first = second;
            second = sum;
        }
        return second;
    }

    public static void main(String[] args) {
        System.out.println(fib2(70));
    }
}