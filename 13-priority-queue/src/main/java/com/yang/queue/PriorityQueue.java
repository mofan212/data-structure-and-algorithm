package com.yang.queue;

import com.yang.heap.BinaryHeap;

import java.util.Comparator;

/**
 * @author 默烦
 * @date 2020/8/6
 */
public class PriorityQueue<E> {
    // 使用二叉堆实现优先级队列
    private BinaryHeap<E> heap;

    public PriorityQueue (Comparator<E> comparator){
        heap = new BinaryHeap<>(comparator);
    }

    public PriorityQueue() {
        this(null);
    }

    public int size(){
        return heap.size();
    }

    public boolean isEmpty(){
        return heap.isEmpty();
    }

    public void clear(){
        heap.clear();
    }

    public void enQueue(E element){
        heap.add(element);
    }

    public E deQueue(){
        return heap.remove();
    }

    public E front(){
        return heap.get();
    }
}
