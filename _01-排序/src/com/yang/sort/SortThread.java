package com.yang.sort;

/**
 * @author 默烦
 * @date 2020/8/15
 */
public class SortThread extends Thread {
    private int value;

    public SortThread(int value) {
        this.value = value;
    }

    public void run() {
        try {
            Thread.sleep(value);
            System.out.println(value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
