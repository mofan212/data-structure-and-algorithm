package com.yang;

import java.util.function.BiFunction;
import java.util.function.Predicate;

/**
 * @author 默烦
 * @date 2020/8/13
 */
public class BinarySearch {
    /**
     * 查找 v 在有序数组array中的位置
     */
    public static int indexOf(int[] array, int v) {
        if (array == null || array.length == 0) return -1;
        int begin = 0;
        int end = array.length;
        while (begin < end) {
            int mid = (begin + end) >> 1;
            if (v < array[mid]) {
                end = mid;
            } else if (v > array[mid]) {
                begin = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    /**
     * 查找 v 在有序数组array中的待插入位置
     */
    public static int search(int[] array, int v) {
        if (array == null || array.length == 0) return -1;
        int begin = 0;
        int end = array.length;
        while (begin < end) {
            int mid = (begin + end) >> 1;
            if (v < array[mid]) {
                end = mid;
            } else {
                begin = mid + 1;
            }
        }
        return begin;
    }

    /**
     * 根据指定条件，使用二分搜索获取目标元素的下标
     *
     * @param array       数组
     * @param isBlue      中值判断条件
     * @param returnValue 返回值
     * @param <T>         数组类型
     * @return 目标元素下标
     */
    public static <T> int binarySearchTemplate(T[] array,
                                               Predicate<T> isBlue,
                                               BiFunction<Integer, Integer, Integer> returnValue) {
        if (array == null || array.length == 0) return -1;
        int l = -1, r = array.length;
        while (l + 1 != r) {
            int m = (l + r) >> 1;
            if (isBlue.test(array[m])) {
                l = m;
            } else {
                r = m;
            }
        }
        return returnValue.apply(l, r);
    }

    public static void main(String[] args) {
        Integer[] array = {1, 2, 3, 5, 5, 5, 8, 9};
        // 第一个大于等于 5 的元素 --> 3
        System.out.println(binarySearchTemplate(array, i -> i < 5, (l, r) -> r));
        // 最后一个小于 5 的元素 --> 2
        System.out.println(binarySearchTemplate(array, i -> i < 5, (l, r) -> l));
        // 第一个大于 5 的元素 --> 6
        System.out.println(binarySearchTemplate(array, i -> i <= 5, (l, r) -> r));
        // 最后一个小于等于 5 的元素 --> 5
        System.out.println(binarySearchTemplate(array, i -> i <= 5, (l, r) -> l));
    }
}
