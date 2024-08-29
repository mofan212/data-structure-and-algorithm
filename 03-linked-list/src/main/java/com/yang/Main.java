package com.yang;

import com.yang.circle.CircleLinkedList;
import com.yang.circle.Josephus;

/**
 * @author 默烦
 * @date 2020/6/24
 */
public class Main {

    static void josephusProblem(){
        Josephus<Integer> list = new Josephus<>();
        for (int i = 1; i <= 8; i++) {
            list.add(i);
        }
        // 指向头节点
        list.reset();
        while (!list.isEmpty()){
            list.next();
            list.next();
            System.out.print(list.remove()+" ");
        }
    }

    static void test(){
        List<Integer> list = new LinkedList<>();
        list.add(10);
        list.add(12);
        list.add(13);
        list.add(14);
        list.add(15);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
            System.out.println(list.indexOf(list.get(i)));
            System.out.println("------");
        }
    }

    public static void main(String[] args) {
//        List<Integer> list = new LinkedList<>();
//        list.add(11);
//        list.add(22);
//        list.add(33);
//        list.add(44);
//        System.out.println(list);
//        list.add(0,55);
//        list.add(2,66);
//        list.add(list.size(),77);
//        System.out.println(list);
//
//        list.remove(0);
//        list.remove(2);
//        list.remove(list.size() - 1);
//        System.out.println(list);
//        list.clear();
//        System.out.println(list);
//        CircleLinkedList<Object> list = new CircleLinkedList<>();
//        list.add(11);
//        list.add(22);
//        list.add(33);
//        list.add(44);
//        System.out.println(list);
//        list.add(0, 55);
//        list.add(2, 66);
//        list.add(list.size(), 77);
//        System.out.println(list);
//
//        list.remove(0);
//        list.remove(2);
//        list.remove(list.size() - 1);
//        System.out.println(list);
//        list.clear();
//        System.out.println(list);
        //josephusProblem();
        test();
    }
}
