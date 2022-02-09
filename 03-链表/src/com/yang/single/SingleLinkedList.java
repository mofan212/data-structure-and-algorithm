package com.yang.single;

import com.yang.AbstractList;

/**
 * @author 默烦
 * @date 2020/6/24
 */
public class SingleLinkedList<E> extends AbstractList<E> {

    private Node<E> first;

    @Override
    public void clear() {
        size = 0;
        first = null;
    }

    // 获取某个位置节点的值
    @Override
    public E get(int index) {
        /*
         * 最好:O(1)
         * 最坏:O(n)
         * 平均:O(n)
         * */
        return node(index).element;
    }

    // 设置某个位置节点的值
    @Override
    public E set(int index, E element) {
        /*
        * 最好:O(1)
        * 最坏:O(n)
        * 平均:O(n)
        * */
        Node<E> node = node(index);
        E old = node.element;
        node.element = element;
        return old;
    }

    @Override
    public void add(int index, E element) {
        /*
         * 最好:O(1)
         * 最坏:O(n)
         * 平均:O(n)
         * */
        rangeCheckForAdd(index);
        if (index == 0) {
            first = new Node<>(element, first);
        } else {
            // 获取添加位置上一个节点
            Node<E> prev = node(index - 1);
            // 创建需要添加的节点，并将前一个节点的next指向新节点
            prev.next = new Node<>(element, prev.next);
        }
        size++;
    }

    @Override
    public E remove(int index) {
        /*
         * 最好:O(1)
         * 最坏:O(n)
         * 平均:O(n)
         * */
        rangeCheck(index);
        Node<E> node = first;
        if (index == 0) {
            first = first.next;
        } else {
            Node<E> prev = node(index - 1);
            node = prev.next;
            prev.next = node.next;
        }
        size--;
        // 返回删除的节点
        return node.element;
    }

    @Override
    public int indexOf(E element) {
        // 处理空值
        if (element == null) {
            Node<E> node = first;
            for (int i = 0; i < size; i++) {
                if (node.element == null) return i;
                node = node.next;
            }
        } else {
            Node<E> node = first;
            for (int i = 0; i < size; i++) {
                if (element.equals(node.element)) return i;
                node = node.next;
            }
        }
        return ELEMENT_NOT_FOUND;
    }

    // 获取某个位置的节点对象
    private Node<E> node(int index) {
        rangeCheck(index);
        Node<E> node = first;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    @Override
    public String toString() {
        // 打印效果 size = 3, [99,88,77]
        StringBuilder builder = new StringBuilder();
        builder.append("size=").append(size).append(", [");
        Node<E> node = first;
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                builder.append(", ");
            }
            builder.append(node.element);
            node = node.next;
        }
        builder.append("]");
        return builder.toString();
    }

    private static class Node<E> {
        E element;
        Node<E> next;

        public Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }
    }
}
