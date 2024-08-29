package com.yang.sort.cmp;

import com.yang.sort.Sort;

/**
 * @author 默烦
 * @date 2020/8/11
 */
public class SelectionSort<E extends Comparable<E>> extends Sort<E> {

    @Override
    protected void sort() {
        for (int end = array.length - 1; end > 0; end--) {
            int maxIndex = 0;
            for (int begin = 1; begin <= end; begin++) {
//                if (array[maxIndex] <= array[begin]) {
                if (cmp(maxIndex,begin) < 0){
                    maxIndex = begin;
                }
            }
            swap(maxIndex, end);
        }
    }
}
