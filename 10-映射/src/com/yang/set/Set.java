package com.yang.set;

/**
 * @author 默烦
 * @date 2020/7/21
 */
public interface Set<E> {
    int size();
    boolean isEmpty();
    void clear();
    boolean contains(E element);
    void add(E element);
    void remove(E element);
    /**
     * 遍历接口
     * 集合内元素是无序、互斥的
     * 动态数组、链表有索引，可以直接循环遍历
     * 集合没有，只能提供接口
     * */
    void traversal(Visitor<E> visitor);

    public static abstract class Visitor<E>{
        boolean stop;
        public abstract boolean visit(E element);
    }
}
