package com.yang.sort;


import com.yang.Student;
import com.yang.sort.cmp.SelectionSort;
import com.yang.sort.cmp.ShellSort;

import java.text.DecimalFormat;

/**
 * @author 默烦
 * @date 2020/8/11
 */
public abstract class Sort<E extends Comparable<E>> implements Comparable<Sort<E>> {
    protected E[] array;
    private int cmpCount; // 比较次数
    private int swapCount; // 交换次数
    private long time; // 排序所用时间
    // 格式化 保留两位小数
    private final DecimalFormat fmt = new DecimalFormat("#.00");

    public void sort(E[] array) {
        if (array == null || array.length < 2) return;
        this.array = array;
        long begin = System.currentTimeMillis();
        sort();
        time = System.currentTimeMillis() - begin;
    }

    protected abstract void sort();

    @Override
    public int compareTo(Sort<E> o) {
        int result = (int) (time - o.time);
        if (result != 0) return result;
        result = cmpCount - o.cmpCount;
        if (result != 0) return result;
        return swapCount - o.swapCount;
    }

    /**
     * 返回值等于0，代表array[i1] == array[i2]
     * 返回值小于0，代表array[i1] < array[i2]
     * 返回值大于0，代表array[i1] > array[i2]
     */
    protected int cmp(int i1, int i2) {
        cmpCount++;
        return array[i1].compareTo(array[i2]);
    }

    protected int cmp(E v1, E v2) {
        cmpCount++;
        return v1.compareTo(v2);
    }

    protected void swap(int i1, int i2) {
        swapCount++;
        E tmp = array[i1];
        array[i1] = array[i2];
        array[i2] = tmp;
    }

    @Override
    public String toString() {
        String timeStr = "耗时：" + (time / 1000.0) + "s(" + time + "ms)";
        String compareCountStr = "比较：" + numberString(cmpCount);
        String swapCountStr = "交换：" + numberString(swapCount);
        /**
         * 注意这里是将isStable()方法放在最后来计算的
         * 因为调用isStable()方法时会进行排序
         * 排序的时候就会对cmpCount、swapCount的值产生影响
         * cmpCount、swapCount会在前面进行拼接
         * 等到拼接完，再调用isStable()就不会打印出变化了的比较、交换次数了
         */
        String stableStr = "稳定性：" + isStable();
        return "【" + getClass().getSimpleName() + "】\n"
                + stableStr + " \t"
                + timeStr + " \t"
                + compareCountStr + "\t "
                + swapCountStr + "\n"
                + "------------------------------------------------------------------";
    }

    private String numberString(int number) {
        if (number < 10000) return "" + number;
        if (number < 100000000) return fmt.format(number / 10000.0) + "万";
        return fmt.format(number / 100000000.0) + "亿";
    }

    private boolean isStable() {
        if (this instanceof SelectionSort) return false;
        if (this instanceof ShellSort) return false;
        if (this instanceof CountingSort) return true;
        if (this instanceof RadixSort) return true;
        Student[] students = new Student[20];
        for (int i = 0; i < students.length; i++) {
            // 创建有序的序列
            students[i] = new Student(i * 10, 10);
        }
        sort(((E[]) students)); // 进行排序
        for (int i = 1; i < students.length; i++) {
            int score = students[i].score;
            int prevScore = students[i - 1].score;
            // 检查序列顺序是否发生改变
            if (score != prevScore + 10) return false;
        }
        return true;
    }
}
