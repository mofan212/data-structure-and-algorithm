package com.yang.map;

import com.yang.printer.BinaryTreeInfo;
import com.yang.printer.BinaryTrees;

import java.lang.annotation.ElementType;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.jar.Pack200;

/**
 * @author 默烦
 * @date 2020/7/24
 */
public class HashMap_v0<K, V> implements Map<K, V> {
    private static final boolean RED = false;
    private static final boolean BLACK = true;
    private int size;
    private Node<K, V>[] table; // 存储的红黑树根节点
    // 哈希表数组默认容量 16
    private static final int DEFAULT_CAPACITY = 1 << 4;

    public HashMap_v0() {
        table = new Node[DEFAULT_CAPACITY];
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
        if (size == 0) return;
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }
        size = 0;
    }

    @Override
    public V put(K key, V value) {
        int index = index(key);
        // 取出 index 位置的红黑树根节点
        Node<K, V> root = table[index];
        if (root == null) {
            root = new Node<>(key, value, null);
            table[index] = root;
            size++;
            afterPut(root);
            return null;
        }
        // 根节点不为空时，产生哈希冲突
        // 添加新的节点到红黑树
        Node<K, V> parent = root;
        Node<K, V> node = root;
        int cmp = 0;
        K k1 = key;
        int h1 = k1 == null ? 0 : k1.hashCode();
        Node<K, V> result = null;
        boolean searched = false; // 是否已经搜索过 key
        // 循环方式从 while 变为 do...while
        do {
            parent = node;  // 保存父节点
            K k2 = node.key;
            int h2 = node.hash;
            if (h1 > h2) {
                cmp = 1; // cmp > 0，向右遍历
            } else if (h1 < h2) {
                cmp = -1; // cmp < 0，向左遍历
            } else if (Objects.equals(k1, k2)) {
                cmp = 0;
            } else if (k1 != null && k2 != null
                    && k1.getClass() == k2.getClass()
                    && k1 instanceof Comparable
                    && (cmp = ((Comparable) k1).compareTo(k2)) != 0) {
                // 此时 k1、k2一定不为同一个对象，因此在这进行比大小
                // cmp 结果 > 0 < 0 == 0
                // cmp == 0 时，不进行操作，直接前往下一个判断
            } else if (searched) { // 已经扫描了
                cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
            } else { // searched == false 还没扫描，先扫描再根据内存地址大小左右
                if ((node.left != null
                        && (result = node(node.left, k1)) != null)
                        || (node.right != null
                        && (result = node(node.right, k1)) != null)) {
                    // 已经存在这个 key ，进行覆盖
                    // 需要覆盖的是 result，为了代码复用，使 node = result
                    node = result;
                    cmp = 0;
                } else { // 不存在这个 key 时，使用内存地址比较
                    searched = true;
                    cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
                }
            }

            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else { // 相等
                node.key = key; // 相等时覆盖
                V oldValue = node.value;
                node.value = value;
                /**
                 * 到这一步时，是两个 key equals
                 * 表明两个 key 的哈希值一定相等
                 * 因此，可以不覆盖哈希值：
                 * node.hash = h1;
                 */
                return oldValue;
            }
        } while (node != null);
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
        if (size == 0) return false;
        Queue<Node<K, V>> queue = new LinkedList<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) continue;
            queue.offer(table[i]);
            while (!queue.isEmpty()) {
                Node<K, V> node = queue.poll();
                if (Objects.equals(value, node.value)) return true;
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
        return false;
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {
        if (size == 0 || visitor == null) return;
        Queue<Node<K, V>> queue = new LinkedList<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) continue;
            queue.offer(table[i]);
            while (!queue.isEmpty()) {
                Node<K, V> node = queue.poll();
                if (visitor.visit(node.key, node.value)) return;
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
    }

    public void print() {
        if (size == 0) return;
        for (int i = 0; i < table.length; i++) {
            final Node<K, V> root = table[i];
            System.out.println("【index" + i + "】");
            BinaryTrees.println(new BinaryTreeInfo() {
                @Override
                public Object root() {
                    return root;
                }

                @Override
                public Object left(Object node) {
                    return ((Node<K, V>) node).left;
                }

                @Override
                public Object right(Object node) {
                    return ((Node<K, V>) node).right;
                }

                @Override
                public Object string(Object node) {
                    return node;
                }
            });
            System.out.println("---------------------------------");
        }
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
            // 节点的哈希值也要覆盖，不然还是以前节点的哈希值
            node.hash = s.hash;
            // 变量node指向其后继节点，等待后续删除
            node = s;
        }
        // 删除node节点（node的度必然为1或0）
        Node<K, V> replacement = node.left != null ? node.left : node.right;
        // 获取红黑树节点在哈希表中的索引
        int index = index(node);
        if (replacement != null) {   // node度为1
            // 更改parent
            replacement.parent = node.parent;
            // 更改node的parent的left、right的指向
            if (node.parent == null) { // node度为1，且为根节点
                table[index] = replacement;
            } else if (node == node.parent.left) {
                node.parent.left = replacement;
            } else { // node == node.parent.right
                node.parent.right = replacement;
            }

            afterRemove(replacement);
        } else if (node.parent == null) { // node度为0，是叶子节点，并且是根节点
            table[index] = null;
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

    // 主要使用的方法
    private Node<K, V> node(K key) {
        Node<K, V> root = table[index(key)];
        // 递归查找
        return root == null ? null : node(root, key);
    }

    // 递归的方法
    // 根据 key 查询节点
    private Node<K, V> node(Node<K, V> node, K k1) {
        int h1 = k1 == null ? 0 : k1.hashCode();
        // 存储查找结果
        Node<K, V> result = null;
        int cmp = 0;
        while (node != null) {
            int h2 = node.hash;
            K k2 = node.key;
            // 先比较哈希值
            /**
             * int cmp = h1 - h2;
             * 使用减法比较大小是不靠谱的，因为 int 可能会溢出，减去一个负数
             */
            if (h1 > h2) {
                node = node.right;
            } else if (h1 < h2) {
                node = node.left;
                // 哈希值相等，是否 equals
            } else if (Objects.equals(k1, k2)) {
                return node;
                // 哈希值相等，但不equals，是否有可比较性
            } else if (k1 != null && k2 != null
                    && k1.getClass() == k2.getClass()
                    && k1 instanceof Comparable
                    && (cmp = ((Comparable) k1).compareTo(k2)) != 0) {
                // 此时已排除 cmp == 0 的情况
                node = cmp > 0 ? node.right : node.left;
                // cmp == 0 时，不进行操作，直接前往下一个判断
                // 哈希值相等，不equals，也不具备可比较性，使用扫描（递归）
            } else if (node.right != null
                    && (result = node(node.right, k1)) != null) {
                return result;
            } else { // 只能往左边找
                node = node.left;
            }
//            } else if (node.left != null
//                    && (result = node(node.left, k1)) != null) {
//                return result;
//            } else {
//                return null;
//            }
        }
        return null;
    }

    private int index(K key) {
        if (key == null) return 0;
        int hash = key.hashCode();
        /**
         * 高低16位混合运算再次生成哈希值
         * 因为 hashCode() 是用户实现，无法保证是否均匀使用了高低16位运算
         * */
        return (hash ^ (hash >>> 16)) & (table.length - 1);
    }

    private int index(Node<K, V> node) {
        return (node.hash ^ (node.hash >>> 16)) & (table.length - 1);
    }

    /**
     * @param k1
     * @param k2
     * @param h1 k1 的哈希值
     * @param h2 k2 的哈希值
     * @return 返回值大于 0，表示 k1更大
     */
/*    private int compare(K k1, K k2, int h1, int h2) {
        // 比较哈希值 且 不相等
        int result = h1 - h2;
        if (result != 0) return result;
        // 哈希值相等时，比较 equals()
        if (Objects.equals(k1, k2)) return 0;
        // 哈希值相等，但并不 equals
        // 比较类名
        if (k1 != null && k2 != null
                && k1.getClass() == k2.getClass()
                && k1 instanceof Comparable) {
            // 同一种类型 判断是否具有可比较性
            if (k1 instanceof Comparable) {
                return ((Comparable) k1).compareTo(k2);
            }
        }
        // 同一种类型，哈希值一样，但不具备可比较性
        */

    /**
     * 不为 null的对象哈希值也有可能为 0
     * 1. k1 不为 null，k2 为 null
     * 2. k1 为 null，k2 不为 null
     *//*
        return System.identityHashCode(k1) - System.identityHashCode(k2); // 利用内存地址算出的哈希值
    }*/
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
            // root = parent
            table[index(grand)] = parent;
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

    private static class Node<K, V> {
        int hash; // key 的哈希值
        K key;
        V value;
        boolean color = RED;
        Node<K, V> left;   // 左节点
        Node<K, V> right;  // 右节点
        Node<K, V> parent; // 父节点

        public Node(K key, V value, Node<K, V> parent) {
            this.key = key;
            this.hash = key == null ? 0 : key.hashCode();
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

        @Override
        public String toString() {
            return "Node_" + key + "_" + value;
        }
    }
}
