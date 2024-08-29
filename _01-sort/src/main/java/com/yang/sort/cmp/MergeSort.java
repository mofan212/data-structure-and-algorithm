package com.yang.sort.cmp;

import com.yang.sort.Sort;

/**
 * @author 默烦
 * @date 2020/8/14
 */
public class MergeSort<E extends Comparable<E>> extends Sort<E> {
    private E[] leftArray;

    @Override
    protected void sort() {
        leftArray = (E[]) new Comparable[array.length >> 1];
        sort(0, array.length);
    }

    /**
     * 对 [begin, end) 范围的数据进行归并排序
     *
     * @param begin 开始索引
     * @param end   排序数组的长度
     */
    private void sort(int begin, int end) {
        // end - begin 是元素的数量
        if (end - begin < 2) return;
        int mid = (begin + end) >> 1;
        sort(begin, mid);
        sort(mid, end);
        merge(begin, mid, end);
    }

    /**
     * 将 [begin,end) 和 [mid,end) 范围的序列合并成一个有序数列
     *
     * @param begin 开始
     * @param mid   中间
     * @param end   结束
     */
    private void merge(int begin, int mid, int end) {
        int li = 0, le = mid - begin;
        int ri = mid, re = end;
        int ai = begin;

        // 备份左边数组
        for (int i = li; i < le; i++) {
            // 大数组范围是 [begin, end)，而非从索引0开始
            leftArray[i] = array[begin + i];
        }
        while (li < le) { // 左边还没结束
            // 注意顺序与稳定性的关系
            if (ri < re && cmp(array[ri], leftArray[li]) < 0) {
                array[ai++] = array[ri++];
            } else {
                array[ai++] = leftArray[li++];
            }
        }
    }
}
