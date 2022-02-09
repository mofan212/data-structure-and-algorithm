package com.yang.map;

import com.yang.tree.BinaryTree;
import com.yang.tree.RBTree;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author 默烦
 * @date 2020/7/20
 */
public class TreeMap<K, V> implements Map<K, V> {
    //    private static class KV<K, V>{
//        K key;
//        V value;
//    }
//    private RBTree<KV<K, V>> rbTree = new RBTree<>();
    private static final boolean RED = false;
    private static final boolean BLACK = true;
    private int size;   // 节点个数
    private Node<K, V> root; //根节点
    private Comparator<K> comparator;

    public TreeMap() {
        this(null);
    }

    public TreeMap(Comparator<K> comparator) {
        this.comparator = comparator;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public V put(K key, V value) { // 使用Key进行比较
        keyNotNullCheck(key);
        // 添加第一个节点 （根节点）
        if (root == null) {
            root = new Node<>(key, value, null);
            size++;
            // 添加节点后的处理 传入参数类型为Node
            afterPut(root);
            return null;
        }
        // 添加的不是第一个节点
        // 找到插入节点的父节点
        Node<K, V> parent = root;
        Node<K, V> node = root;
        int cmp = 0;
        while (node != null) {
            cmp = compare(key, node.key);
            parent = node;  // 保存父节点
            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else { // 相等
                node.key = key; // 相等时覆盖
                V oldValue = node.value;
                node.value = value;
                return oldValue;
            }
        }
        // 看看插入到父节点的哪个位置
        Node<K, V> newNode = new Node<>(key, value, parent);
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        size++;
        // 添加节点后的处理 传入参数类型为Node
        afterPut(newNode);
        return null;
    }

    @Override
    public V get(K key) {
        Node<K, V> node = node(key);
        return node != null ? node.value : null;
    }

    @Override
    public V remove(K key) {
        return remove(node(key));
    }

    @Override
    public boolean containsKey(K key) {
        return node(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        /** 由于 value 不具备可比较性，同时允许为 null
         *  因此，只能遍历红黑树节点，一个一个找
         *  在这里使用层序遍历
         * */
        if (root == null) {
            return false;
        }
        Queue<Node<K, V>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node<K, V> node = queue.poll();
            if (valEquals(value, node.value)) return true;
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        return false;
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {
        if (visitor == null) return;
        traversal(root, visitor);
    }

    // 使用中序遍历
    private void traversal(Node<K, V> node, Visitor<K, V> visitor) {
        if (node == null || visitor.stop) return;
        traversal(node.left, visitor);
        if (visitor.stop) return;
        visitor.visit(node.key, node.value);
        traversal(node.right, visitor);
    }

    private boolean valEquals(V v1, V v2) {
        return v1 == null ? v2 == null : v1.equals(v2);
    }

    private V remove(Node<K, V> node) {
        if (node == null) return null;
        size--;
        V oldValue = node.value;
        if (node.hasTwoChildren()) { // 度为2的节点
            // 找到后继节点
            Node<K, V> s = successor(node);
            // 用后继节点的值覆盖被删除节点的值
            node.key = s.key;
            node.value = s.value;
            // 变量node指向其后继节点，等待后续删除
            node = s;
        }
        // 删除node节点（node的度必然为1或0）
        Node<K, V> replacement = node.left != null ? node.left : node.right;
        if (replacement != null) {   // node度为1
            // 更改parent
            replacement.parent = node.parent;
            // 更改node的parent的left、right的指向
            if (node.parent == null) { // node度为1，且为根节点
                root = replacement;
            } else if (node == node.parent.left) {
                node.parent.left = replacement;
            } else { // node == node.parent.right
                node.parent.right = replacement;
            }

            afterRemove(replacement);
        } else if (node.parent == null) {     // node度为0，是叶子节点，并且是根节点
            root = null;
            // 被删除的节点
            afterRemove(node);
        } else { // node是叶子节点，但不是根节点
            if (node == node.parent.left) {
                node.parent.left = null;
            } else {
                node.parent.right = null;
            }
            // 被删除的节点
            afterRemove(node);
        }
        return oldValue;
    }

    private void afterRemove(Node<K, V> node) {
        if (isRed(node)) {
            black(node);
            return;
        }
        // 获取被删除节点的父节点
        Node<K, V> parent = node.parent;
        // 被删除节点是根节点
        if (parent == null) return;

        boolean left = parent.left == null || node.isLeftChild();
        Node<K, V> sibling = left ? parent.right : parent.left;

        if (left) { // 被删除节点在左边，兄弟节点在右边
            if (isRed(sibling)) { // 被删除节点兄弟节点是红色
                black(sibling);
                red(parent);
                rotateLeft(parent);
                // 更换兄弟
                sibling = parent.right;
            }
            // 此时兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // 兄弟节点一个红色子节点都没，父节点向下跟兄弟节点合并
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    // 父节点为黑色时，下溢，进行递归
                    afterRemove(parent);
                }
            } else { // 兄弟节点至少有一个红色子节点，向兄弟节点借元素
                // 兄弟节点左子节点是黑色，先对兄弟进行旋转
                if (isBlack(sibling.right)) {
                    rotateRight(sibling);
                    // 旋转后，重置兄弟节点位置
                    sibling = parent.right;
                }
                // 先染色兄弟节点，再染色父节点：兄弟节点跟随父节点染色
                color(sibling, colorOf(parent));
                black(sibling.right);
                black(parent);
                rotateLeft(parent);

            }
        } else { // 被删除节点在右边，兄弟节点在左边
            if (isRed(sibling)) { // 被删除节点兄弟节点是红色
                black(sibling);
                red(parent);
                rotateRight(parent);
                // 更换兄弟
                sibling = parent.left;
            }
            // 此时兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // 兄弟节点一个红色子节点都没，父节点向下跟兄弟节点合并
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    afterRemove(parent);
                }
            } else { // 兄弟节点至少有一个红色子节点，向兄弟节点借元素
                // 兄弟节点左子节点是黑色，先对兄弟进行旋转
                if (isBlack(sibling.left)) {
                    rotateLeft(sibling);
                    sibling = parent.left;
                }
                color(sibling, colorOf(parent));
                black(sibling.left);
                black(parent);
                rotateRight(parent);
            }
        }
    }

    private Node<K, V> predecessor(Node<K, V> node) {
        if (node == null) return null;
        // 前驱节点在左子树中
        Node<K, V> p = node.left;
        if (p != null) {
            while (p.right != null) {
                p = p.right;
            }
            return p;
        }
        // 从“祖宗节点”中寻找前驱节点
        while (node.parent != null && node == node.parent.left) {
            node = node.parent;
        }

        // node.parent == null || node == node.parent.right
        return node.parent;
    }

    private Node<K, V> successor(Node<K, V> node) {
        if (node == null) return null;
        // 后继节点在右子树中
        Node<K, V> p = node.right;
        if (p != null) {
            while (p.left != null) {
                p = p.left;
            }
            return p;
        }
        // 从“祖宗节点”中寻找后继节点
        while (node.parent != null && node == node.parent.right) {
            node = node.parent;
        }

        // node.parent == null || node == node.parent.left
        return node.parent;
    }

    // 根据元素找寻节点
    private Node<K, V> node(K key) {
        Node<K, V> node = root;
        while (node != null) {
            int cmp = compare(key, node.key);
            if (cmp == 0) return node;
            if (cmp > 0) {
                node = node.right;
            } else { // cmp < 0
                node = node.left;
            }
        }
        return null;
    }

    private void afterPut(Node<K, V> node) {
        Node<K, V> parent = node.parent;
        // 添加节点是根节点时 或 上溢到根节点
        if (parent == null) {
            black(node);
            return;
        }
        // 如果添加节点的父节点是黑色，直接返回
        if (isBlack(parent)) return;
        // 获取叔父节点
        Node<K, V> uncle = parent.sibling();
        // 获取祖父节点
        Node<K, V> grand = red(parent.parent);
        if (isRed(uncle)) { // 叔父节点是红色时[B树节点上溢]
            black(parent);
            black(uncle);
            // 把祖父节点当作新添加的节点
            afterPut(grand);
            return;
        }
        // 叔父节点不是红色时
        if (parent.isLeftChild()) { // L
            if (node.isLeftChild()) { // LL
                black(parent);
            } else { // LR
                black(node);
                rotateLeft(parent);
            }
            rotateRight(grand);
        } else { // R
            if (node.isLeftChild()) { // RL
                black(node);
                rotateRight(parent);
            } else { // RR
                black(parent);
            }
            rotateLeft(grand);
        }
    }

    // 左旋转
    private void rotateLeft(Node<K, V> grand) {
        Node<K, V> parent = grand.right;
        Node<K, V> child = parent.left;
        // 旋转
        grand.right = child;
        parent.left = grand;
        // 旋转后
        afterRotate(grand, parent, child);
    }

    // 右旋转
    private void rotateRight(Node<K, V> grand) {
        Node<K, V> parent = grand.left;
        Node<K, V> child = parent.right;
        // 旋转
        grand.left = child;
        parent.right = grand;
        // 旋转后
        afterRotate(grand, parent, child);
    }

    private void afterRotate(Node<K, V> grand, Node<K, V> parent, Node<K, V> child) {
        // 让 parent 成为根节点
        parent.parent = grand.parent;
        if (grand.isLeftChild()) {
            grand.parent.left = parent;
        } else if (grand.isRightChild()) {
            grand.parent.right = parent;
        } else { // 没有父节点， grand是根节点
            root = parent;
        }
        // 更新其他节点的父节点
        if (child != null) {
            child.parent = grand;
        }
        grand.parent = parent;
    }

    /****辅助方法****/
    // 节点染色
    private Node<K, V> color(Node<K, V> node, boolean color) {
        if (node == null) return node;
        node.color = color;
        return node;
    }

    // 节点染成红色
    private Node<K, V> red(Node<K, V> node) {
        return color(node, RED);
    }

    // 节点染成黑色
    private Node<K, V> black(Node<K, V> node) {
        return color(node, BLACK);
    }

    // 查看某一节点的颜色
    private boolean colorOf(Node<K, V> node) {
        return node == null ? BLACK : node.color;
    }

    // 判断节点颜色是否是黑色
    private boolean isBlack(Node<K, V> node) {
        return colorOf(node) == BLACK;
    }

    // 判断节点颜色是否是红色
    private boolean isRed(Node<K, V> node) {
        return colorOf(node) == RED;
    }


    private int compare(K k1, K k2) {
        if (comparator != null) {
            return comparator.compare(k1, k2);
        }
        // 未创建比较器时，强制节点拥有比较性
        return ((Comparable<K>) k1).compareTo(k2);
    }

    // Key 空值检查
    private void keyNotNullCheck(K key) {
        if (key == null) {
            throw new IllegalArgumentException("element must not be null");
        }
    }

    private static class Node<K, V> {
        K key;
        V value;
        boolean color = RED;
        Node<K, V> left;   // 左节点
        Node<K, V> right;  // 右节点
        Node<K, V> parent; // 父节点

        public Node(K key, V value, Node<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }


        public boolean isLeaf() {
            return left == null && right == null;
        }

        public boolean hasTwoChildren() {
            return left != null && right != null;
        }

        // 判断当前节点是否是其父节点的左子节点
        public boolean isLeftChild() {
            return parent != null && this == parent.left;
        }

        // 判断当前节点是否是其父节点的右子节点
        public boolean isRightChild() {
            return parent != null && this == parent.right;
        }

        // 获取兄弟节点
        public Node<K, V> sibling() {
            if (isLeftChild()) {
                return parent.right;
            }
            if (isRightChild()) {
                return parent.left;
            }
            return null;
        }
    }
}
