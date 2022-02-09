package com.yang.tree;

import java.util.Comparator;

/**
 * @author 默烦
 * @date 2020/7/17
 */
public class BBST<E> extends BST<E> {

    public BBST() {
        this(null);
    }

    public BBST(Comparator<E> comparator) {
        super(comparator);
    }

    // 左旋转
    protected void rotateLeft(Node<E> grand) {
        Node<E> parent = grand.right;
        Node<E> child = parent.left;
        // 旋转
        grand.right = child;
        parent.left = grand;
        // 旋转后
        afterRotate(grand, parent, child);
    }

    // 右旋转
    protected void rotateRight(Node<E> grand) {
        Node<E> parent = grand.left;
        Node<E> child = parent.right;
        // 旋转
        grand.left = child;
        parent.right = grand;
        // 旋转后
        afterRotate(grand, parent, child);
    }

    protected void afterRotate(Node<E> grand, Node<E> parent, Node<E> child) {
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

    protected void rotate(
            Node<E> r, // 根节点
            Node<E> a, Node<E> b, Node<E> c,
            Node<E> d,
            Node<E> e, Node<E> f, Node<E> g) {
        // 让 d 成为这棵子树的根节点
        d.parent = r.parent;
        if (r.isLeftChild()) {
            r.parent.left = d;
        } else if (r.isRightChild()) {
            r.parent.right = d;
        } else {
            root = d;
        }

        // a - b - c
        b.left = a;
        if (a != null) {
            a.parent = b;
        }
        b.right = c;
        if (c != null) {
            c.parent = b;
        }

        // e - f - g
        f.left = e;
        if (e != null) {
            e.parent = f;
        }
        f.right = g;
        if (g != null) {
            g.parent = f;
        }

        // b - d - f
        d.left = b;
        d.right = f;
        b.parent = d;
        f.parent = d;
    }
}
