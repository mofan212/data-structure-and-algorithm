package com.yang.circle;

import com.yang.AbstractList;

/**
 * @author 默烦
 * @date 2020/6/24
 */
public class Josephus<E> extends AbstractList<E> {

    private Node<E> first;
    private Node<E> last;
    private Node<E> current;

    /* gc root 对象
     * 1. 被栈指针（局部变量）指向的对象
     * */
    @Override
    public void clear() {
        size = 0;
        first = null;
        last = null;
    }

    // 获取某个位置节点的值
    @Override
    public E get(int index) {
        return node(index).element;
    }

    // 设置某个位置节点的值
    @Override
    public E set(int index, E element) {
        Node<E> node = node(index);
        E old = node.element;
        node.element = element;
        return old;
    }

    @Override
    public void add(int index, E element) {
        rangeCheckForAdd(index);
        if (index == size) { // 向最后一个位置插入节点
            Node<E> oldLast = last;
            last = new Node<>(element, first, oldLast);
            if (oldLast == null) { // 插入第一个元素时
                first = last;
                first.next = first;
                first.prev = first;
            } else {
                oldLast.next = last;
                first.prev = last;
            }
        } else {
            // 插入新节点后的下一个节点
            Node<E> next = node(index);
            Node<E> prev = next.prev;
            Node<E> node = new Node<>(element, next, prev);
            next.prev = node;
            prev.next = node;
            if (index == 0) { // index == 0
                first = node;
            }
        }
        size++;
    }

    @Override
    public E remove(int index) {
        rangeCheck(index);
        return remove(node(index));
    }

    private E remove(Node<E> node){
        if (size == 1){
            first = null;
            last = null;
        } else {
            Node<E> next = node.next;
            Node<E> prev = node.prev;
            prev.next = next;
            next.prev = prev;
            if (node == first) {  // index == 0
                first = next;
            }
            if (node == last) {  // index = size - 1
                last = prev;
            }
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
        if (index < (size >> 1)) {
            Node<E> node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        } else {
            Node<E> node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
            return node;
        }
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
            // builder.append(node.element);
            builder.append(node);
            node = node.next;
        }
        builder.append("]");
        return builder.toString();
    }

    public void reset(){
        current = first;
    }

    public E next(){
        if (current == null) return null;
        current = current.next;
        return current.element;
    }

    public E remove(){
        if (current == null) return null;
        Node<E> next = current.next;
        E element = remove(current);
        if (size == 0){ // 仅有一个节点时
            current = null;
        } else {
            current = next;
        }
        return element;
    }

    private static class Node<E> {
        E element;
        Node<E> next;
        Node<E> prev;

        public Node(E element, Node<E> next, Node<E> prev) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }

        // 新增内部类的toString()方法
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (prev != null) {
                sb.append(prev.element);
            } else {
                sb.append("null");
            }
            sb.append("_").append(element).append("_");
            if (next != null) {
                sb.append(next.element);
            } else {
                sb.append("null");
            }
            return sb.toString();
        }
    }
}
