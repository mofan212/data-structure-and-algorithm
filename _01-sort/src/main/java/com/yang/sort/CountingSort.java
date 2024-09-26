package com.yang.sort;

/**
 * @author 默烦
 * @date 2020/8/17
 */
public class CountingSort extends Sort<Integer> {

    @Override
    protected void sort() {
        // 找出待排序列中的最值
        int max = array[0];
        int min = array[0];
        for (Integer integer : array) {
            if (integer > max) {
                max = integer;
            }
            if (integer < min) {
                min = integer;
            }
        }
        // 开辟内存空间，存储次数
        int[] counts = new int[max - min + 1];
        // 统计每个整数出现的次数
        for (Integer integer : array) {
            counts[integer - min]++;
        }
        // 累加次数
        for (int i = 1; i < counts.length; i++) {
            counts[i] += counts[i - 1];
        }

        // 从后往前遍历元素，将它放到有序数组中合适的位置
        int[] newArray = new int[array.length];
        for (int i = array.length - 1; i >= 0; i--) {
            newArray[--counts[array[i] - min]] = array[i];
        }
        // 将有序数组赋值到 array
        for (int i = 0; i < newArray.length; i++) {
            array[i] = newArray[i];
        }
    }

    private void sort_1() {
        // 找出最大值
        int max = array[0];
        for (Integer integer : array) {
            if (integer > max) {
                max = integer;
            }
        }
        // 开辟内存空间，存储每个整数出现的次数
        int[] counts = new int[1 + max];
        // 统计每个整数出现的次数
        for (Integer integer : array) {
            counts[integer]++;
        }
        // 根据整数出现次数对整数进行排序
        int index = 0;
        for (int i = 0; i < counts.length; i++) {
            while (counts[i]-- > 0) {
                array[index++] = i;
            }
        }
    }

}
