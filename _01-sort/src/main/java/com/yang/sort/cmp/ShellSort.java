package com.yang.sort.cmp;

import com.yang.sort.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 默烦
 * @date 2020/8/16
 */
public class ShellSort<E extends Comparable<E>> extends Sort<E> {

    @Override
    protected void sort() {
        List<Integer> stepSequence = shellStepSequence();
        for (Integer step : stepSequence) {
            sort(step);
        }
    }

    /**
     * 分成 step 列进行排序
     *
     * @param step 列数
     */
    private void sort(int step) {
        // col : 第几列, column
        for (int col = 0; col < step; col++) { // 对第col列进行排序
            // col、col+step、col+2*step、col+3*step......
            for (int begin = col + step; begin < array.length; begin += step) {
                int cur = begin;
                while (cur > col && cmp(cur, cur - step) < 0) {
                    swap(cur, cur - step);
                    cur -= step;
                }
            }
        }
    }

    /**
     * 获取步长序列
     */
    private List<Integer> shellStepSequence() {
        List<Integer> stepSequence = new ArrayList<>();
        int step = array.length;
        while ((step >>= 1) > 0) {
            stepSequence.add(step);
        }
        return stepSequence;
    }
}
