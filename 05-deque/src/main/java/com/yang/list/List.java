package com.yang.list;

/**
 * @author 默烦
 * @date 2020/6/24
 */
public interface List<E> {
    // 接口中的变量与方法默认都是公共的
    // 为什么要将ELEMENT_NOT_FOUND放在接口中，因为放在接口中外界可以访问
    static final int ELEMENT_NOT_FOUND = -1;
    // 清除所有元素
    void clear();

    // 元素的数量
    int size();

    // 判断是否为空
    boolean isEmpty();

    // 判断是否包含某个元素
    boolean contains(E element);

    // 添加元素到尾部
    void add(E element);

    // 获取index位置的元素
    E get(int index);

    // 设置index位置的元素
    E set(int index, E element);

    // 向index位置添加一个元素 且默认支持插入空数据null
    void add(int index, E element);

    // 删除index位置的元素
    E remove(int index);

    // 查看元素的索引
    int indexOf(E element);

}
