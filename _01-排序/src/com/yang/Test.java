package com.yang;

import com.yang.sort.SortThread;
import com.yang.tools.Integers;

import java.sql.SQLOutput;

/**
 * @author 默烦
 * @date 2020/8/14
 */
public class Test {

    public static void main(String[] args) {
        int[] array = {10, 100, 50, 30, 60};
        for (int i = 0; i < array.length; i++) {
            new SortThread(array[i]).start();
        }
    }
}
