package com.yang;

import com.yang.tools.Asserts;
import com.yang.tools.Times;
import com.yang.union.*;

/**
 * @author 默烦
 * @date 2020/8/18
 */
public class Main {
    static final int count = 1000000;

    public static void main(String[] args) {
//        test(new UnionFind_QF(12));
//        test(new UnionFind_QU(12));
//        test(new UnionFind_QU_S(12));
//        test(new UnionFind_QU_R(12));
//        test(new UnionFind_QU_R_PC(12));
//        test(new UnionFind_QU_R_PS(12));
//        test(new UnionFind_QU_R_PH(12));
//        testTime(new UnionFind_QF(count));
//        testTime(new UnionFind_QU(count));
//        testTime(new UnionFind_QU_S(count));
//        testTime(new UnionFind_QU_R(count));
        testTime(new UnionFind_QU_R_PC(count));
        testTime(new UnionFind_QU_R_PS(count));
        testTime(new UnionFind_QU_R_PH(count));
        testTime2(new GenericUnionFind<Integer>());
//        GenericUnionFind<Student> uf = new GenericUnionFind<>();
//        Student stu1 = new Student("jack", 1);
//        Student stu2 = new Student("rose", 2);
//        Student stu3 = new Student("jack", 3);
//        Student stu4 = new Student("rose", 4);
//        uf.makeSet(stu1);
//        uf.makeSet(stu2);
//        uf.makeSet(stu3);
//        uf.makeSet(stu4);
//
//        uf.union(stu1, stu2);
//        uf.union(stu3, stu4);
//        uf.union(stu1, stu4);
//
//       Asserts.test(uf.isSame(stu2, stu3));
//       Asserts.test(uf.isSame(stu3, stu4));
//       Asserts.test(!uf.isSame(stu1, stu3));
    }

    static void test(UnionFind uf) {
        uf.union(0, 1);
        uf.union(0, 3);
        uf.union(0, 4);
        uf.union(2, 3);
        uf.union(2, 5);

        uf.union(6, 7);

        uf.union(8, 10);
        uf.union(9, 10);
        uf.union(9, 11);


        Asserts.test(!uf.isSame(0, 6));
        Asserts.test(uf.isSame(0, 5));
        Asserts.test(!uf.isSame(2, 7));
        uf.union(4, 6);
        Asserts.test(uf.isSame(2, 7));
    }

    static void testTime2(GenericUnionFind<Integer> uf) {
        for (int i = 0; i < count; i++) {
            uf.makeSet(i);
        }

        uf.union(0, 1);
        uf.union(0, 3);
        uf.union(0, 4);
        uf.union(2, 3);
        uf.union(2, 5);

        uf.union(6, 7);

        uf.union(8, 10);
        uf.union(9, 10);
        uf.union(9, 11);


        Asserts.test(!uf.isSame(0, 6));
        Asserts.test(uf.isSame(0, 5));
        Asserts.test(!uf.isSame(2, 7));
        uf.union(4, 6);
        Asserts.test(uf.isSame(2, 7));

        Times.test(uf.getClass().getSimpleName(), () -> {
            for (int i = 0; i < count; i++) {
                uf.union((int) (Math.random() * count),
                        (int) (Math.random() * count));
            }
            for (int i = 0; i < count; i++) {
                uf.isSame((int) (Math.random() * count),
                        (int) (Math.random() * count));
            }
        });
    }

    static void testTime(UnionFind uf) {
        Times.test(uf.getClass().getSimpleName(), () -> {
            for (int i = 0; i < count; i++) {
                uf.union((int) (Math.random() * count),
                        (int) (Math.random() * count));
            }
            for (int i = 0; i < count; i++) {
                uf.isSame((int) (Math.random() * count),
                        (int) (Math.random() * count));
            }
        });
    }
}
