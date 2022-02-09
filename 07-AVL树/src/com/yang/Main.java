package com.yang;

import com.yang.Tree.AVLTree;
import com.yang.Tree.BST;
import com.yang.printer.BinaryTrees;

/**
 * @author 默烦
 * @date 2020/7/3
 */
public class Main {
    static void test() {
        Integer[] data = new Integer[]{
                85, 19, 69, 3, 7, 99, 95, 13, 63, 34, 82, 76, 54, 9, 68, 39
        };
        AVLTree<Integer> avl = new AVLTree<>();
        for (int i = 0; i < data.length; i++) {
            avl.add(data[i]);
            //System.out.println("【"+data[i]+"】");
            // BinaryTrees.println(avl);
            // System.out.println("---------------------------------");
        }
        for (int i = 0; i < data.length; i++) {
            avl.remove(data[i]);
            System.out.println("【"+data[i]+"】");
            BinaryTrees.println(avl);
            System.out.println("---------------------------------");
        }
        //BinaryTrees.println(avl);
    }

    public static void main(String[] args) {
        test();
    }
}
