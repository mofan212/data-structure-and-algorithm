package com.yang;

import java.util.HashMap;

/**
 * @author 默烦
 * @date 2020/8/7
 */
public class Trie<V> {
    private int size; // 元素（字符串）数量
    private Node<V> root;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        size = 0;
        root = null;
    }

    public V get(String key) {
        Node<V> node = node(key);
        return node != null && node.word ? node.value : null;
    }

    public boolean contains(String key) {
        Node<V> node = node(key);
        return node != null && node.word;
    }

    public V add(String key, V value) {
        keyCheck(key);

        // 创建根节点
        if (root == null) {
            root = new Node<>(null);
        }

        Node<V> node = root;
        int len = key.length();
        for (int i = 0; i < len; i++) {
            char c = key.charAt(i); // d o g
            boolean emptyChild = node.children == null;
            Node<V> childNode = emptyChild ? null : node.children.get(c);
            if (childNode == null) {
                childNode = new Node<>(node);
                // 将字符存入Node成员变量中
                childNode.character = c;
                node.children = emptyChild ? new HashMap<>() : node.children;
                node.children.put(c, childNode);
            }
            node = childNode;
        }

        if (node.word) { // 已经存在这个单词
            V oldValue = node.value;
            node.value = value;
            return oldValue;
        }

        node.word = true;
        node.value = value;
        size++;
        return null;
    }

    public V remove(String key) {
        // 找到最后一个节点
        Node<V> node = node(key);
        // 如果最后一个节点是空或不是单词结尾，不做任何处理
        if (node == null || !node.word) return null;
        size--;
        V oldValue = node.value;
        // 如果还有子节点
        if (node.children != null && !node.children.isEmpty()) {
            node.word = false;
            node.value = null;
            return oldValue;
        }
        // 如果没有子节点
        Node<V> parent = null;
        while ((parent = node.parent) != null) {
            parent.children.remove(node.character);
            if (parent.word || !parent.children.isEmpty()) break;
            node = parent;
        }
        return oldValue;
    }

    public boolean startsWith(String prefix) {
        return node(prefix) != null;
    }

    private Node<V> node(String key) {
//        if (root == null) return null;
        keyCheck(key);

        Node<V> node = root;
        int len = key.length();
        for (int i = 0; i < len; i++) {
            if (node == null || node.children == null || node.children.isEmpty()) return null;
            char c = key.charAt(i); // d o g
            node = node.children.get(c);
        }
        // 得判断node是否是单词结尾
//        return node.word ? node : null;
        return node; // 返回值可能为null
    }

    private void keyCheck(String key) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("key must not be empty");
        }
    }

    private static class Node<V> {
        Node<V> parent; // 父节点
        HashMap<Character, Node<V>> children;
        Character character; // 存入的字符
        V value;
        boolean word; // 是否为单词结尾

        public Node(Node<V> parent) {
            this.parent = parent;
        }
    }
}
