package indi.mofan;

import java.util.Comparator;

/**
 * @author mofan
 * @date 2022/12/4 17:24
 */
public class SkipList<K, V> {
    /**
     * 跳表的最高层数，参考 Redis
     */
    private static final int MAX_LEVEL = 32;
    private static final double P = 0.25;
    private int size;
    private Comparator<K> comparator;
    /**
     * 有效层数
     */
    private int level;
    /**
     * 不存放任何 K-V 的虚拟节点作为首节点
     */
    private Node<K, V> first;

    @SuppressWarnings({"unchecked"})
    public SkipList(Comparator<K> comparator) {
        this.comparator = comparator;
        this.first = new Node<>(null, null, MAX_LEVEL);
    }

    public SkipList() {
        this(null);
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    @SuppressWarnings("unchecked")
    public V put(K key, V value) {
        this.keyCheck(key);

        Node<K, V> node = first;
        // 添加节点的前驱节点
        Node<K, V>[] pres = (Node<K, V>[]) new Node[level];
        for (int i = level - 1; i >= 0; i--) {
            int cmp = -1;
            while (node.nextNodes[i] != null && (cmp = compare(key, node.nextNodes[i].key)) > 0) {
                node = node.nextNodes[i];
            }
            // 添加的节点存在，新值覆盖旧值
            if (cmp == 0) {
                V oldV = node.nextNodes[i].value;
                node.nextNodes[i].value = value;
                return oldV;
            }
            // 保留前驱节点
            pres[i] = node;
        }

        // 新节点的层数
        int newLevel = randomLevel();
        // 添加的新节点
        Node<K, V> newNode = new Node<>(key, value, newLevel);
        // 设置新节点的前驱与后继
        for (int i = 0; i < newLevel; i++) {
            // 新节点的层数比当前跳表的有效层数还要高
            if (i >= this.level) {
                // 首节点成为新节点的前驱
                first.nextNodes[i] = newNode;
            } else {
                // 添加节点的后继为其前驱节点原本的后继节点
                newNode.nextNodes[i] = pres[i].nextNodes[i];
                // 设置前驱节点
                pres[i].nextNodes[i] = newNode;
            }
        }
        // 计算新的有效层数
        this.level = Math.max(this.level, newLevel);
        size++;
        return null;
    }

    public V get(K key) {
        this.keyCheck(key);

        Node<K, V> node = first;
        for (int i = level - 1; i >= 0; i--) {
            int cmp = -1;
            while (node.nextNodes[i] != null && (cmp = compare(key, node.nextNodes[i].key)) > 0) {
                node = node.nextNodes[i];
            }
            if (cmp == 0) return node.nextNodes[i].value;
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public V remove(K key) {
        this.keyCheck(key);

        Node<K, V> node = first;
        // 添加节点的前驱节点
        Node<K, V>[] pres = (Node<K, V>[]) new Node[level];
        boolean exist = false;
        for (int i = level - 1; i >= 0; i--) {
            int cmp = -1;
            while (node.nextNodes[i] != null && (cmp = compare(key, node.nextNodes[i].key)) > 0) {
                node = node.nextNodes[i];
            }
            if (cmp == 0) exist = true;
            pres[i] = node;
        }
        // 被删除的节点不存在，直接返回
        if (!exist) return null;
        // 需要被删除的节点
        Node<K, V> removeNode = node.nextNodes[0];

        // 设置后继
        for (int i = 0; i < removeNode.nextNodes.length; i++) {
            pres[i].nextNodes[i] = removeNode.nextNodes[i];
        }
        // 更新跳表的层数
        int newLevel = this.level;
        while (--newLevel >= 0 && first.nextNodes[newLevel] == null) {
            this.level = newLevel;
        }
        // 数量减少
        this.size--;
        return removeNode.value;
    }


    private int randomLevel() {
        int level = 1;
        // 四分之一的概率增加层次，最高层次 32
        while (Math.random() < P && level < MAX_LEVEL) {
            level++;
        }
        return level;
    }

    private void keyCheck(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key must not be null");
        }
    }

    @SuppressWarnings("unchecked")
    private int compare(K k1, K k2) {
        return comparator != null
                ? comparator.compare(k1, k2)
                : ((Comparable<K>) k1).compareTo(k2);
    }

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V>[] nextNodes;

        @SuppressWarnings("unchecked")
        private Node(K key, V value, int level) {
            this.key = key;
            this.value = value;
            nextNodes = (Node<K, V>[]) new Node[level];
        }

        @Override
        public String toString() {
            return key + ":" + value + "_" + nextNodes.length;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("一共").append(level).append("层").append("\n");
        for (int i = level - 1; i >= 0; i--) {
            Node<K, V> node = first;
            while (node.nextNodes[i] != null) {
                sb.append(node.nextNodes[i]);
                sb.append("\t");
                node = node.nextNodes[i];
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
