package com.yang.heap;

import java.util.Comparator;

/**
 * 二叉堆（最大堆）
 *
 * @author 默烦
 * @date 2020/8/4
 */
public class BinaryHeap<E> extends AbstractHeap<E>{
    private E[] elements;
    private static final int DEFAULT_CAPACITY = 10;

    public BinaryHeap(E[] elements, Comparator<E> comparator){
        super(comparator);
        if (elements == null || elements.length == 0){
            this.elements = (E[]) new Object[DEFAULT_CAPACITY];
        } else {
            // 记得将size复制，不然只会存储第一个元素
            size = elements.length;
            // 保证容量至少为 10
            int capacity = Math.max(elements.length, DEFAULT_CAPACITY);
//            this.elements = elements;
            this.elements = (E[]) new Object[capacity];
            for (int i = 0; i < elements.length; i++) {
                this.elements[i] = elements[i];
            }
            heapify();
        }
    }

    public BinaryHeap(E[] elements){
        this(elements,null);
    }

    public BinaryHeap(Comparator<E> comparator) {
        this(null,comparator);
    }

    public BinaryHeap() {
        this(null,null);
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    @Override
    public void add(E element) {
        elementNotNullCheck(element);
        ensureCapacity(size + 1);
        elements[size++] = element;
        siftUp(size - 1);
    }

    @Override
    public E get() { // 获取堆顶元素
        // 判断堆是否为空
        emptyCheck();
        return elements[0];
    }

    @Override
    public E remove() { // 删除堆顶元素
        emptyCheck();

        int lastIndex = --size;
        E root = elements[0];
        elements[0] = elements[lastIndex];
        elements[lastIndex] = null;

        siftDown(0);
        return root;
    }

    @Override
    public E replace(E element) {
        elementNotNullCheck(element);

        E root = null;
        if (size == 0) {
            elements[0] = element;
            size = 1;
        } else {
            root = elements[0];
            elements[0] = element;
            siftDown(0);
        }

        return root;
    }

    /**
     * 批量建堆 堆化
     */
    private void heapify(){
        // 自上而下的上滤
//        for (int i = 0; i < size; i++) {
//            siftUp(i);
//        }
        // 自下而上的下溢
        for (int i = (size >> 1) - 1; i >= 0; i--) {
            siftDown(i);
        }
    }

    /**
     * 让索引为 index 的元素进行下滤
     *
     * @param index
     */
    private void siftDown(int index) {
        E element = elements[index];
        int half = size >> 1;
        // 第一个叶子节点的索引 = 非叶子节点的数量
        while (index < half) { // 必须保证index位置是非叶子节点
            /**
             * index 位置的节点的子节点有两种情况
             * 1. 只有左子节点
             * 2. 同时有左子节点、右子节点
             */
            // 默认为左子节点的索引
            int childIndex = (index << 1) + 1;
            E child = elements[childIndex];

            // 右子节点索引
            int rightIndex = childIndex + 1;
            // 选出最大的子节点
            if (rightIndex < size && compare(elements[rightIndex], child) > 0) {
//                childIndex = rightIndex;
                child = elements[childIndex = rightIndex];
            }

            if (compare(element, child) >= 0) break;
            // 将子节点存放到index位置
            elements[index] = child;
            // 重新设置index
            index = childIndex;
        }
        elements[index] = element;
    }

    /**
     * 让索引为 index 的元素进行上滤
     *
     * @param index 数组索引
     */
    private void siftUp(int index) {
//        E e = elements[index];
//        while (index > 0) { // 索引位置节点有父节点时
//            int pIndex = (index - 1) >> 1; // 获取父节点索引
//            E p = elements[pIndex];
//            if (compare(e, p) <= 0) return;
//            // 交换 index、pIndex 位置的内容
//            E tmp = elements[index];
//            elements[index] = elements[pIndex];
//            elements[pIndex] = tmp;
//            // 重新赋值 index
//            index = pIndex;
//        }
        E e = elements[index];
        while (index > 0) { // 索引位置节点有父节点时
            int pIndex = (index - 1) >> 1; // 获取父节点索引
            E p = elements[pIndex];
            if (compare(e, p) <= 0) break;

            // 将父元素存储在 index 位置
            elements[index] = elements[pIndex];
            // 重新赋值 index
            index = pIndex;
        }
        elements[index] = e;
    }

    // 保证要有capacity的容量(不考虑线程安全)
    private void ensureCapacity(int capacity) {
        // 当前elements.length 为 10，因为默认容量为10
        int oldCapacity = elements.length;
        if (oldCapacity >= capacity) return;
        // 新容量为新容量的1.5倍
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        E[] newElements = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }
        elements = newElements;
    }

    // 检查二叉堆 size 是否为 0
    private void emptyCheck() {
        if (size == 0) {
            throw new IndexOutOfBoundsException("Heap is empty!");
        }
    }

    private void elementNotNullCheck(E element) {
        if (element == null) {
            throw new IllegalArgumentException("element must not be null!");
        }
    }
}
