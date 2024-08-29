package com.yang.sort.cmp;

import com.yang.sort.Sort;

/**
 * @author 默烦
 * @date 2020/8/12
 */
public class InsertionSort3<E extends Comparable<E>> extends Sort<E> {
//    @Override
//    protected void sort() {
//        for (int begin = 1; begin < array.length; begin++) {
//            E v = array[begin];
//            int insertIndex = search(begin);
//            // 将[insertIndex, begin)范围内的元素往右边挪动一个单位
////            for (int i = begin - 1; i >= insertIndex; i--) {
////                array[i + 1] = array[i];
////            }
//            for (int i = begin; i > insertIndex; i--) {
//                array[i] = array[i - 1];
//            }
//            array[insertIndex] = v;
//        }
//    }


    @Override
    protected void sort() {
        for (int begin = 1; begin < array.length; begin++) {
            insert(begin, search(begin));
        }
    }

    /**
     * 将source位置的元素插入到dest位置
     * @param source 待插入元素位置
     * @param dest 插入位置
     */
    private void insert(int source, int dest){
        E v = array[source];
        for (int i = source; i > dest; i--) {
            array[i] = array[i - 1];
        }
        array[dest] = v;
    }

    /**
     * 利用二分搜索找到 index 位置待插入元素的插入位置
     * 已经排好序数组的区间范围是 [0, index)
     * @param index 待插入元素索引
     * @return 插入位置
     */
    private int search(int index) {
        int begin = 0;
        int end = index;
        while (begin < end) {
            int mid = (begin + end) >> 1;
            if (cmp(array[index], array[mid]) < 0) {
                end = mid;
            } else {
                begin = mid + 1;
            }
        }
        return begin;
    }
}
