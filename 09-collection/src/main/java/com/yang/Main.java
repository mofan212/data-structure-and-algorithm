package com.yang;

import com.yang.set.ListSet;
import com.yang.set.Set;
import com.yang.set.TreeSet;

/**
 * @author 默烦
 * @date 2020/7/19
 */
public class Main {
    public static void main(String[] args) {
//        Set<Integer> listSet = new ListSet<>();
//        listSet.add(10);
//        listSet.add(11);
//        listSet.add(11);
//        listSet.add(12);
//        listSet.add(10);
//        listSet.traversal(new Set.Visitor<Integer>() {
//            @Override
//            public boolean visit(Integer element) {
//                System.out.println(element);
//                return false;
//            }
//        });
        Set<Integer> treeSet = new TreeSet<>();
        treeSet.add(12);
        treeSet.add(10);
        treeSet.add(7);
        treeSet.add(11);
        treeSet.add(10);
        treeSet.add(11);
        treeSet.add(9);
        treeSet.traversal(new Set.Visitor<Integer>() {
            @Override
            public boolean visit(Integer element) {
                System.out.println(element);
                return false;
            }
        });
    }
}
