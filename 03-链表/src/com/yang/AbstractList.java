package com.yang;

/**
 * @author 默烦
 * @date 2020/6/24
 */
// 抽取公共代码
public abstract class AbstractList<E> implements List<E> {

    // 元素的数量
    protected int size;

    public int size() {
        return size;
    }


    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(E element) {
        return indexOf(element) != ELEMENT_NOT_FOUND ;
    }

    public void add(E element) {
        add(size,element);
    }

    // 越界异常封装
    protected void outOfBounds(int index){
        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    // 普通范围检查
    protected void rangeCheck(int index){
        if (index < 0 || index >= size) {
            outOfBounds(index);
        }
    }

    // 添加元素的范围检查
    // 允许index = size，当index等于size时，相当于在数组末尾添加元素
    protected void rangeCheckForAdd(int index){
        if (index < 0 || index > size) {
            outOfBounds(index);
        }
    }
}
