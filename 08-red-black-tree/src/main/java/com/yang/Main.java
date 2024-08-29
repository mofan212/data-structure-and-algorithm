package com.yang;

import com.yang.Tree.AVLTree;
import com.yang.Tree.RBTree;
import com.yang.printer.BinaryTrees;


/**
 * @author 默烦
 * @date 2020/7/15
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
            System.out.println("【" + data[i] + "】");
            BinaryTrees.println(avl);
            System.out.println("---------------------------------");
        }
        //BinaryTrees.println(avl);
    }

    static void test2() {
        Integer[] data = new Integer[]{
                55, 87, 56, 74, 96, 22, 62, 20, 70, 68, 90, 50
        };
        RBTree<Integer> rb = new RBTree<>();
        for (int i = 0; i < data.length; i++) {
            rb.add(data[i]);
            System.out.println("【" + data[i] + "】");
            BinaryTrees.println(rb);
            System.out.println("---------------------------------");
        }
        // BinaryTrees.println(rb);
    }

    static void test3() {
        Integer[] data = new Integer[]{
                55, 87, 56, 74, 96, 22, 62, 20, 70, 68, 90, 50
        };
        RBTree<Integer> rb = new RBTree<>();
        for (int i = 0; i < data.length; i++) {
            rb.add(data[i]);
        }
        BinaryTrees.println(rb);
        for (int i = 0; i < data.length; i++) {
            rb.remove(data[i]);
            System.out.println("---------------------------------");
            System.out.println("【" + data[i] + "】");
            BinaryTrees.println(rb);
        }
    }

    public static void main(String[] args) {
        test3();
    }
}


