package com.yang.circle;


/**
 * @author 默烦
 * @date 2020/7/2
 */
public class CircleQueue<E> {
    private int front;  // 队头指针
    private int size;
    private E[] elements;
    // 默认容量
    private static final int DEFAULT_CAPACITY = 10;

    public CircleQueue() {
        elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear(){
        for (int i = 0; i < size; i++) {
            elements[index(i)] = null;
        }
        size = 0;
        front = 0;
    }

    public void enQueue(E element) {
        // 动态扩容
        ensureCapacity(size + 1);
        // 进行模运算 体现循环
        elements[index(size)] = element;
        size++;
    }

    public E deQueue() {
        E frontElement = elements[front];
        elements[front] = null;
        // 进行模运算 体现循环
        front = index(1);
        size--;
        return frontElement;
    }

    public E front() {
        return elements[front];
    }

    private int index(int index){
        index += front;
        // 未考虑负数情况  循环队列中不会出现负数
        return index - (index >= elements.length ? elements.length : 0);
    }

    // 保证要有capacity的容量(不考虑线程安全)
    private void ensureCapacity(int capacity){
        // 当前elements.length 为 10，因为默认容量为10
        int oldCapacity = elements.length;
        if (oldCapacity >= capacity) return;
        // 新容量为新容量的1.5倍
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        E[] newElements = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[index(i)];
        }
        elements = newElements;
        // 重置front
        front = 0;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("capacity=").append(elements.length)
                .append(" size=").append(size)
                .append(" front=").append(front)
                .append(", [");
        for (int i = 0; i < elements.length; i++) {
            if (i != 0) {
                builder.append(", ");
            }
            builder.append(elements[i]);

        }
        builder.append("]");
        return builder.toString();
    }
}
