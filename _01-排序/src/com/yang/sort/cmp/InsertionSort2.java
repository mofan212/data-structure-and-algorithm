package com.yang.sort.cmp;

import com.yang.sort.Sort;

/**
 * @author 默烦
 * @date 2020/8/12
 */
public class InsertionSort2<E extends Comparable<E>> extends Sort<E> {
    @Override
    protected void sort() {
        for (int begin = 1; begin < array.length; begin++) {
            int cur = begin;
            E v = array[cur]; // 备份元素
            while (cur > 0 && cmp(v, array[cur  - 1]) < 0) {
                array[cur] = array[cur - 1]; // 元素多动
                cur--;
            }
            // 元素插入
            array[cur] = v;
        }
    }
}
