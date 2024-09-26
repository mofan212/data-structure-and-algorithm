package com.yang.sort;

/**
 * @author 默烦
 * @date 2020/8/17
 */
public class RadixSort extends Sort<Integer> {
    @Override
    protected void sort() {
        // 找出待排序列中的最大值
        int max = array[0];
        for (Integer integer : array) {
            if (integer > max) {
                max = integer;
            }
        }

        // 对于整数 593
        // 个位数：array[i] / 1 % 10 = 3
        // 十位数：array[i] / 10 % 10 = 9
        // 百位数：array[i] / 100 % 10 = 5
        // 千位数：array[i] / 1000 % 10 = ...

        for (int divider = 1; divider < max; divider++) {
            countingSort(divider);
        }
    }

    protected void countingSort(int divider) {
        // 开辟内存空间，存储次数
        int[] counts = new int[10];
        // 统计每个整数出现的次数
        for (Integer integer : array) {
            counts[integer / divider % 10]++;
        }
        // 累加次数
        for (int i = 1; i < counts.length; i++) {
            counts[i] += counts[i - 1];
        }

        // 从后往前遍历元素，将它放到有序数组中合适的位置
        int[] newArray = new int[array.length];
        for (int i = array.length - 1; i >= 0; i--) {
            newArray[--counts[array[i] / divider % 10]] = array[i];
        }
        // 将有序数组赋值到 array
        for (int i = 0; i < newArray.length; i++) {
            array[i] = newArray[i];
        }
    }
}
