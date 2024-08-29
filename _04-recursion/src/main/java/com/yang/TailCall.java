package com.yang;

/**
 * @author 默烦
 * 2020/9/3
 */
public class TailCall {
    public static void main(String[] args) {
        System.out.println(factorial(0));
    }

/*    // 尾调用
    void test1() {
        int a = 10;
        int b = a + 20;
        test2(b);
    }

    // 尾递归
    void test2(int n) {
        if (n < 10) return;
        test2(n - 1);
    }*/

    static int factorial(int n) {
        return factorial(n, 1);
    }

    static int factorial(int n, int result) {
        if (n <= 1) return result;
        return factorial(n - 1, n * result);
    }
}

