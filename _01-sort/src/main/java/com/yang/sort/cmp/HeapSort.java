package com.yang.sort.cmp;

import com.yang.sort.Sort;

/**
 * @author 默烦
 * @date 2020/8/11
 */
public class HeapSort<E extends Comparable<E>> extends Sort<E> {
    private int heapSize;

    @Override
    protected void sort() {
        // 原地建堆 批量建堆
        heapSize = array.length;
        for (int i = (heapSize >> 1) - 1; i >= 0; i--) {
            siftDown(i);
        }
        while (heapSize > 1) {
            // 交换堆顶和尾部元素
            swap(0, --heapSize);
            // 对0位置元素进行下滤（恢复堆的性质）
            siftDown(0);
        }
    }

    /**
     * 让索引为 index 的元素进行下滤
     *
     * @param index
     */
    private void siftDown(int index) {
        E element = array[index];
        int half = heapSize >> 1;
        // 第一个叶子节点的索引 = 非叶子节点的数量
        while (index < half) { // 必须保证index位置是非叶子节点
            /**
             * index 位置的节点的子节点有两种情况
             * 1. 只有左子节点
             * 2. 同时有左子节点、右子节点
             */
            // 默认为左子节点的索引
            int childIndex = (index << 1) + 1;
            E child = array[childIndex];

            // 右子节点索引
            int rightIndex = childIndex + 1;
            // 选出最大的子节点
            if (rightIndex < heapSize && cmp(array[rightIndex], child) > 0) {
//                childIndex = rightIndex;
                child = array[childIndex = rightIndex];
            }

            if (cmp(element, child) >= 0) break;
            // 将子节点存放到index位置
            array[index] = child;
            // 重新设置index
            index = childIndex;
        }
        array[index] = element;
    }
}
