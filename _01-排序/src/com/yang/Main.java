package com.yang;

import com.yang.sort.*;
import com.yang.sort.cmp.*;
import com.yang.tools.Asserts;
import com.yang.tools.Integers;

import java.util.Arrays;

/**
 * @author 默烦
 * @date 2020/8/10
 */
public class Main {
    public static void main(String[] args) {

//        int[] array = {2, 4, 8, 8, 8, 12, 14};
//        Asserts.test(BinarySearch.search(array, 5) == 2);
//        Asserts.test(BinarySearch.search(array, 1) == 0);
//        Asserts.test(BinarySearch.search(array, 15) == 7);
//        Asserts.test(BinarySearch.search(array, 8) == 5);

        Integer[] array = Integers.random(10, 1, 30000);
//        Integer[] array = {7, 3, 5, 8, 6, 7, 4, 5};
        testSorts(array,
//                new InsertionSort1<>(),
//                new InsertionSort2<>(),
//                new InsertionSort3<>(),
//                new SelectionSort<>(),
//                new HeapSort<>(),
//                new MergeSort<>(),
//                new BubbleSort3<>(),
//                new QuickSort<>(),
//                new ShellSort<>(),
//                new CountingSort(),
                new RadixSort()
        );
    }

    @SafeVarargs // 消除警告
    static void testSorts(Integer[] array, Sort<Integer>... sorts) {
        for (Sort<Integer> sort : sorts) {
            // 将原数据拷贝一份
            Integer[] newArray = Integers.copy(array);
            sort.sort(newArray);
            // 验证排序是否正确 结果是否升序
            Asserts.test(Integers.isAscOrder(newArray));
            Integers.println(newArray);
        }
        // 结果排序
        Arrays.sort(sorts);
        // 打印结果
        for (Sort<Integer> sort : sorts) {
            System.out.println(sort);
        }
    }

    static void selectionSort(Integer[] array) {
        for (int end = array.length - 1; end > 0; end--) {
            int maxIndex = 0;
            for (int begin = 1; begin <= end; begin++) {
                // 稳定的排序算法 注意等号的存在
                if (array[maxIndex] <= array[begin]) {
                    maxIndex = begin;
                }
            }
            int tmp = array[maxIndex];
            array[maxIndex] = array[end];
            array[end] = tmp;
        }
    }

    static void bubbleSort1(Integer[] array) {
        for (int end = array.length - 1; end > 0; end--) {
            for (int begin = 1; begin <= end; begin++) {
                if (array[begin] < array[begin - 1]) {
                    int tmp = array[begin];
                    array[begin] = array[begin - 1];
                    array[begin - 1] = tmp;
                }
            }
        }
    }

    static void bubbleSort2(Integer[] array) {
        for (int end = array.length - 1; end > 0; end--) {
            boolean sorted = true;
            for (int begin = 1; begin <= end; begin++) {
                if (array[begin] < array[begin - 1]) {
                    int tmp = array[begin];
                    array[begin] = array[begin - 1];
                    array[begin - 1] = tmp;
                    sorted = false;
                }
            }
            if (sorted) break;
        }
    }

    static void bubbleSort3(Integer[] array) {
        for (int end = array.length - 1; end > 0; end--) {
            // sortedIndex的初始值在数组完全有序的时候有用
            int sortedIndex = 1;
            for (int begin = 1; begin <= end; begin++) {
                if (array[begin] < array[begin - 1]) {
                    int tmp = array[begin];
                    array[begin] = array[begin - 1];
                    array[begin - 1] = tmp;
                    // 记录最后一次交换的位置
                    sortedIndex = begin;
                }
            }
            end = sortedIndex;
        }
    }
}
