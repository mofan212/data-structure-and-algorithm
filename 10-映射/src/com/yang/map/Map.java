package com.yang.map;

/**
 * @author 默烦
 * @date 2020/7/20
 */
public interface Map<K, V> {
    int size();
    boolean isEmpty();
    void clear();
    V put(K key, V value);
    V get(K key);
    V remove(K key);
    boolean containsKey(K key);
    boolean containsValue(V value);
    void traversal(Visitor<K, V> visitor);

    public static abstract class Visitor<K, V>{
        boolean stop;
        // 记得设置成公共的方法
        public abstract boolean visit(K key, V value);
    }
}
