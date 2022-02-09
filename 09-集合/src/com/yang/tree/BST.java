package com.yang.tree;

import java.util.Comparator;

/**
 * @author 默烦
 * @date 2020/7/3
 */
public class BST<E> extends BinaryTree<E> {
    private Comparator<E> comparator;

    public BST() {
        this(null);
    }

    public BST(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    public void add(E element) {
        elementNotNullCheck(element);
        // 添加第一个节点 （根节点）
        if (root == null) {
            root = createNode(element,null);
            size++;
            // 添加节点后的处理 传入参数类型为Node
            afterAdd(root);
            return;
        }
        // 添加的不是第一个节点
        // 找到插入节点的父节点
        Node<E> parent = root;
        Node<E> node = root;
        int cmp = 0;
        while (node != null) {
            cmp = compare(element, node.element);
            parent = node;  // 保存父节点
            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else { // 相等
                node.element = element; // 相等时覆盖
                return;
            }
        }
        // 看看插入到父节点的哪个位置
        Node<E> newNode = createNode(element, parent);
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        size++;
        // 添加节点后的处理 传入参数类型为Node
        afterAdd(newNode);
    }

    // 添加节点之后的调整 node是新添加的节点
    // 在其他代码中实现
    protected void afterAdd(Node<E> node){ }

    /**
     *  删除节点之后的调整
     *  node是被删除的节点
     *  或者用以取代被删除节点的子节点（当被删除节点的度为 1，红黑树中使用）
     * */
    // 在其他代码中实现
    protected void afterRemove(Node<E> node){ }

    // 删除某一元素
    public void remove(E element) {
        remove(node(element));
    }

    public boolean contains(E element) {
        return node(element) != null;
    }

    // 删除某一节点
    private void remove(Node<E> node) {
        if (node == null) return;
        size--;
        if (node.hasTwoChildren()) { // 度为2的节点
            // 找到后继节点
            Node<E> s = successor(node);
            // 用后继节点的值覆盖被删除节点的值
            node.element = s.element;
            // 变量node指向其后继节点，等待后续删除
            node = s;
        }
        // 删除node节点（node的度必然为1或0）
        Node<E> replacement = node.left != null ? node.left : node.right;
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

            // 被删除的节点
            /** 传递 replacement
             * 1. 对AVL树无影响
             * AVL树进行删除时会根据 node 的 parent 进行递归
             * 但前面已 replacement.parent = node.parent;
             * 所以传递 node 还是 replacement 对于AVL树都是一样的
             * 2. 而对于红黑树恰好需要传递 replacement 才行
             * 如果传递的是 node ，还得在红黑树的代码中找到被删除节点的代替节点
             * 但是在红黑树中又不能直接使用 node.left 或 node.right 获取
             * 因为后续代码存在递归，如果使用会造成错误
             * 因此对于红黑树，我们必须在此传递 replacement
             * 直接从参数中获取被删除节点的代替节点
             * */
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
    }

    // 根据元素找寻节点
    private Node<E> node(E element) {
        Node<E> node = root;
        while (node != null) {
            int cmp = compare(element, node.element);
            if (cmp == 0) return node;
            if (cmp > 0) {
                node = node.right;
            } else { // cmp < 0
                node = node.left;
            }
        }
        return null;
    }

    /**
     * @return 返回值等于0，表示e1和e2相等；返回值大于0，表示e1大于e2；返回值小于0，表示e1小于e2
     */
    private int compare(E e1, E e2) {
        if (comparator != null) {
            return comparator.compare(e1, e2);
        }
        // 未创建比较器时，强制节点拥有比较性
        return ((Comparable<E>) e1).compareTo(e2);
    }

    // 节点空值检查
    private void elementNotNullCheck(E element) {
        if (element == null) {
            throw new IllegalArgumentException("element must not be null");
        }
    }
}
