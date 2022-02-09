package com.yang;

import com.yang.printer.BinaryTrees;

import java.util.Comparator;

/**
 * @author 默烦
 * @date 2020/7/3
 */
public class Main {

    public static class PersonComparator implements Comparator<Person> {
        @Override
        public int compare(Person e1, Person e2) {
            return e1.getAge() - e2.getAge();
        }
    }

    public static class PersonComparator2 implements Comparator<Person> {
        @Override
        public int compare(Person e1, Person e2) {
            return e2.getAge() - e1.getAge();
        }
    }

    static void test() {
        BinarySearchTree<Person> bst1 = new BinarySearchTree<>(new PersonComparator());
        bst1.add(new Person(12));
        bst1.add(new Person(15));
        BinarySearchTree<Person> bst2 = new BinarySearchTree<>();
        bst2.add(new Person(12));
        bst2.add(new Person(15));
    }

    static void test1() {
        Integer[] data = new Integer[]{
                7, 4, 9, 2, 5, 8, 11, 3, 12, 1
        };
        BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<>();
        for (int i = 0; i < data.length; i++) {
            binarySearchTree.add(data[i]);
        }
        BinaryTrees.println(binarySearchTree);
    }

    static void test2() {
        Integer[] data = new Integer[]{
                7, 4, 9, 2, 5, 8, 11, 3, 12, 1
        };
        BinarySearchTree<Person> bst1 = new BinarySearchTree<>();
        for (int i = 0; i < data.length; i++) {
            bst1.add(new Person(data[i]));
        }
        BinaryTrees.println(bst1);
        BinarySearchTree<Person> bst2 = new BinarySearchTree<>(new PersonComparator2());
        for (int i = 0; i < data.length; i++) {
            bst2.add(new Person(data[i]));
        }
        BinaryTrees.println(bst2);
    }

    static void test3() {
        Integer[] data = new Integer[]{
                7, 4, 2, 1, 3, 5, 9, 8, 11, 10, 12
        };
        BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<>();
        for (int i = 0; i < data.length; i++) {
            binarySearchTree.add(data[i]);
        }
        BinaryTrees.println(binarySearchTree);
        binarySearchTree.levelOrder(new BinarySearchTree.Visitor<Integer>() {
            @Override
            public boolean visit(Integer element) {
                System.out.print(element + "_ ");
                return element == 8;    // 节点值为8时停止遍历
            }
        });
        System.out.println("");
        binarySearchTree.preorder(new BinarySearchTree.Visitor<Integer>() {
            @Override
            public boolean visit(Integer element) {
                System.out.print(element + "_ ");
                return element == 5;    // 节点值为8时停止遍历
            }
        });
        System.out.println("");
        binarySearchTree.inorder(new BinarySearchTree.Visitor<Integer>() {
            @Override
            public boolean visit(Integer element) {
                System.out.print(element + "_ ");
                return element == 7;
            }
        });
        System.out.println("");
        binarySearchTree.postorder(new BinarySearchTree.Visitor<Integer>() {
            @Override
            public boolean visit(Integer element) {
                System.out.print(element + "_ ");
                return element == 8;
            }
        });
    }

    static void test4() {
        Integer[] data = new Integer[]{
                7, 4, 2, 5, 9, 8, 11
        };
        BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<>();
        for (int i = 0; i < data.length; i++) {
            binarySearchTree.add(data[i]);
        }
        System.out.println(binarySearchTree.height());
        System.out.println(binarySearchTree.isComplete());
    }

    static void test5() {
        Integer[] data = new Integer[]{
                7, 4, 9, 2, 1
        };
        BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<>();
        for (int i = 0; i < data.length; i++) {
            binarySearchTree.add(data[i]);
        }
        System.out.println(binarySearchTree.isComplete());
    }

    static void test6() {
        Integer[] data = new Integer[]{
                7, 4, 9, 2, 5, 8, 11, 3, 12, 1
        };
        BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<>();
        for (int i = 0; i < data.length; i++) {
            binarySearchTree.add(data[i]);
        }
        BinaryTrees.println(binarySearchTree);
        binarySearchTree.remove(7);
        BinaryTrees.println(binarySearchTree);
    }

    public static void main(String[] args) {
        test6();
    }
}
