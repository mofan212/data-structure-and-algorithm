package com.yang.set;

import com.yang.list.LinkedList;
import com.yang.list.List;

/**
 * @author 默烦
 * @date 2020/7/19
 */
// 使用链表实现集合
public class ListSet<E> implements Set<E> {
    private List<E> list = new LinkedList<>();

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public boolean contains(E element) { // 最坏 O(n)
        return list.contains(element);
    }

    @Override
    public void add(E element) { // O(n)
        // if (list.contains(element)) return;
        int index = list.indexOf(element);
        if (index != List.ELEMENT_NOT_FOUND) {
            // 存在相同元素时，新元素覆盖旧元素
            list.set(index, element);
        } else {
            // 不存在相同元素时，直接添加
            list.add(element);
        }
    }

    @Override
    public void remove(E element) { // O(n)
        int index = list.indexOf(element);
        if (index != List.ELEMENT_NOT_FOUND) {
            list.remove(index);
        }
    }

    @Override
    public void traversal(Visitor<E> visitor) {
        if (visitor==null) return;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (visitor.visit(list.get(i))) return;
        }
    }
}
