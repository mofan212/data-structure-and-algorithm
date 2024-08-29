package com.yang;

import com.yang.Tree.BST;
import com.yang.printer.BinaryTrees;

/**
 * @author 默烦
 * @date 2020/7/3
 */
public class Main {
    static void test() {
        Integer[] data = new Integer[]{
                7, 4, 9, 2, 5, 8, 11, 3, 12, 1
        };
        BST<Integer> BST = new BST<>();
        for (int i = 0; i < data.length; i++) {
            BST.add(data[i]);
        }
        BinaryTrees.println(BST);
        BST.remove(7);
        BinaryTrees.println(BST);
    }

    public static void main(String[] args) {
        test();
    }
}
