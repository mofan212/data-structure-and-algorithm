package com.yang;

/**
 * @author 默烦
 * 2020/9/2
 */
public class Fib {

    public static void main(String[] args) {
        Fib fib = new Fib();
        Times.test("fib0", () -> {
            System.out.println(fib.fib0(10));
        });
        Times.test("fib1", () -> {
            System.out.println(fib.fib1(10));
        });
        Times.test("fib2", () -> {
            System.out.println(fib.fib2(10));
        });
        Times.test("fib3", () -> {
            System.out.println(fib.fib3(10));
        });
        Times.test("fib4", () -> {
            System.out.println(fib.fib4(10));
        });
        Times.test("fib5", () -> {
            System.out.println(fib.fib5(10));
        });
    }

    int fib0(int n) {
        if (n <= 2) return 1;
        // 先递归调用 fib(n - 2)，等调用结束后，才可以调用fib(n - 1)
        return fib0(n - 2) + fib0(n - 1);
    }

    int fib1(int n) {
        if (n <= 2) return 1;
        int[] array = new int[n + 1];
        array[1] = array[2] = 1;
        return fib1(n, array);
    }

    int fib1(int n, int[] array) {
        if (array[n] == 0) {
            array[n] = fib1(n - 1, array) + fib1(n - 2, array);
        }
        return array[n];
    }

    int fib2(int n) {
        if (n <= 2) return 1;
        int[] array = new int[n + 1];
        array[1] = array[2] = 1;
        for (int i = 3; i <= n; i++) {
            array[i] = array[i - 1] + array[i - 2];
        }
        return array[n];
    }

    /**
     * 6 % 2 = 0 0b110 & 0b001 = 0
     * 5 % 2 = 1 0b101 & 0b001 = 1
     * 4 % 2 = 0 0b100 & 0b001 = 0
     * 3 % 2 = 1 0b011 & 0b001 = 1
     */
    int fib3(int n) {
        if (n <= 2) return 1;
        int[] array = new int[2];
        array[0] = array[1] = 1;
        for (int i = 3; i <= n; i++) {
            array[i & 1] = array[(i - 1) & 1] + array[(i - 2) & 1];
        }
        return array[n & 1];
    }

    int fib4(int n) {
        if (n <= 2) return 1;
        int first = 1;
        int second = 1;
        for (int i = 3; i <= n; i++) {
            second = first + second;
            first = second - first;
        }
        return second;
    }

    int fib5(int n) {
        double c = Math.sqrt(5);
        return (int) ((Math.pow((1 + c) / 2, n) - Math.pow((1 - c) / 2, n)) / c);
    }

    // 尾递归
    int fib(int n) {
        return fib(n, 1, 1);
    }

    public int fib(int n, int first, int second) {
        if (n <= 1) return first;
        return fib(n - 1, second, first + second);
    }
}
