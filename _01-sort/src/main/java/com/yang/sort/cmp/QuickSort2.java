package com.yang.sort.cmp;

import com.yang.sort.Sort;

/**
 * @author 默烦
 * @date 2020/8/16
 */
public class QuickSort2<E extends Comparable<E>> extends Sort<E> {
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
        // 确定轴点位置
        int p = pivot(begin, end);
        // 对子序列进行快速排序
        sort(begin, p);
        sort(p + 1, end);
    }

    /**
     * 构造出 [begin, end) 范围的轴点元素
     *
     * @param begin 序列开始位置索引
     * @param end   序列长度
     * @return 轴点元素的最终位置
     */
    private int pivot(int begin, int end) {
        // 随机选择一个元素跟begin位置进行交换
        swap(begin, begin + (int) (Math.random() * (end - begin)));

        // 备份 begin 位置的元素
        E pivot = array[begin];
        // end 指向最后一个元素
        end--;
        while (begin < end) {
            while (begin < end) {
                // 添加了相等性判断
                if (cmp(pivot, array[end]) <= 0) { // 右边元素 > 轴点元素
                    end--;
                } else { // 右边元素 <= 轴点元素
                    array[begin++] = array[end];
                    // 赋值后换方向遍历
                    break;
                }
            }

            while (begin < end) {
                // 添加了相等性判断
                if (cmp(pivot, array[begin]) >= 0) { // 左边元素 < 轴点元素
                    begin++;
                } else { // 左边元素 >= 轴点元素
                    array[end--] = array[begin];
                    // 赋值后换方向遍历
                    break;
                }
            }
        }

        // 将轴点元素放入
        array[begin] = pivot;
        // 返回轴点元素的位置
        return begin;
    }
}
