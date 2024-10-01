package com.yang.sort.cmp;


import com.yang.sort.Sort;

/**
 * @author mofan
 * @date 2024/10/1 17:32
 */
public class ThreeWayQuickSort<E extends Comparable<E>> extends Sort<E> {
    @Override
    protected void sort() {
        sort(0, array.length);
    }

    /**
     * 对 [begin, end) 范围的元素进行快速排序
     *
     * @param begin 序列开始位置索引
     * @param end   序列长度
     */
    private void sort(int begin, int end) {
        if (end - begin < 2) return;

        // 随机选择一个元素跟 begin 位置进行交换
        swap(begin, begin + (int) (Math.random() * (end - begin)));

        E pivot = array[begin];
        int i = begin;
        int p = begin + 1;
        int j = end;

        while (p < j) {
            int cmp = cmp(array[p], pivot);
            if (cmp < 0) {
                swap(i++, p++);
            } else if (cmp > 0) {
                swap(p, --j);
            } else {
                p++;
            }
        }

        sort(begin, i);
        sort(j, end);
    }
}
