package com.yang.Tree;

import com.yang.printer.BinaryTreeInfo;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author 默烦
 * @date 2020/7/6
 */
public class BinaryTree<E> implements BinaryTreeInfo {
    // 使用访问修饰符 protected 让不同包的子类可以访问这个变量
    protected int size;   // 节点个数
    protected Node<E> root; //根节点

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        root = null;
        size = 0;
    }

    // 前序
    public void preorder(Visitor<E> visitor) {
        if (visitor == null) return;
        preorder(root, visitor);
    }

    private void preorder(Node<E> node, Visitor<E> visitor) {
        // 判断停止递归 并且 也可以判断停止打印
        if (node == null || visitor.stop) return;
        visitor.stop = visitor.visit(node.element);
        preorder(node.left, visitor);
        preorder(node.right, visitor);
    }

    // 中序
    public void inorder(Visitor<E> visitor) {
        if (visitor == null) return;
        inorder(root, visitor);
    }

    private void inorder(Node<E> node, Visitor<E> visitor) {
        // 判断停止递归
        if (node == null || visitor.stop) return;
        inorder(node.left, visitor);
        // 判断停止打印
        if (visitor.stop) return;
        visitor.stop = visitor.visit(node.element);
        inorder(node.right, visitor);
    }

    // 后序
    public void postorder(Visitor<E> visitor) {
        if (visitor == null) return;
        postorder(root, visitor);
    }

    private void postorder(Node<E> node, Visitor<E> visitor) {
        // 判断停止递归
        if (node == null || visitor.stop) return;
        postorder(node.left, visitor);
        postorder(node.right, visitor);
        // 如果上一步执行完后，visitor恰好为true，应该也不打印
        // 判断停止打印
        if (visitor.stop) return;
        visitor.stop = visitor.visit(node.element);
    }

    public void levelOrder(Visitor<E> visitor) {
        if (root == null || visitor == null) return;
        Queue<Node<E>> queue = new LinkedList<>();
        // 根节点入队
        queue.offer(root);
        while (!queue.isEmpty()) {
            // 头结点出队
            Node<E> node = queue.poll();
            // 访问逻辑 （已修改） 返回值为true，停止遍历
            if (visitor.visit(node.element)) return;

            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
    }

    public boolean isComplete() {
        if (root == null) return false;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        boolean leaf = false;
        while (!queue.isEmpty()) {
            // 头结点出队
            Node<E> node = queue.poll();
            if (leaf && !node.isLeaf()) return false;
            if (node.left != null) {
                queue.offer(node.left);
            } else if (node.right != null) {
                // node.left == null && node.right != null
                return false;
            }

            if (node.right != null) {
                queue.offer(node.right);
            } else { // node.right == null
                leaf = true;
            }
        }
        return true;
    }

    public int height() {
        if (root == null) return 0;
        // 树的高度
        int height = 0;
        // 存储每一层的元素数量 根节点一定会访问
        int levelSize = 1;
        Queue<Node<E>> queue = new LinkedList<>();
        // 根节点入队
        queue.offer(root);
        while (!queue.isEmpty()) {
            // 头结点出队
            Node<E> node = queue.poll();
            levelSize--;
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
            // 意味着即将访问下一层
            if (levelSize == 0) {
                levelSize = queue.size();
                height++;
            }
        }
        return height;
    }

    // 获取整棵树的高度
    public int height2() {
        return height(root);
    }

    // 获取某一节点的高度
    private int height(Node<E> node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    // 访问修饰符 protected 让该类的子类可以使用
    protected Node<E> createNode(E element, Node<E> parent) {
        return new Node<>(element, parent);
    }

    protected Node<E> predecessor(Node<E> node) {
        if (node == null) return null;
        // 前驱节点在左子树中
        Node<E> p = node.left;
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

    protected Node<E> successor(Node<E> node) {
        if (node == null) return null;
        // 后继节点在右子树中
        Node<E> p = node.right;
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

    public static abstract class Visitor<E> {
        // 遍历终止标志 true：终止 false：全遍历
        boolean stop;

        // 如果返回true，表示停止遍历
        abstract boolean visit(E element);
    }

    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((Node<E>) node).left;
    }

    @Override
    public Object right(Object node) {
        return ((Node<E>) node).right;
    }

    @Override
    public Object string(Object node) {
//        Node<E> myNode = ((Node<E>) node);
//        String parentString = "null";
//        if (myNode.parent != null) {
//            parentString = myNode.parent.element.toString();
//        }
//        return myNode.element + "_p(" + parentString + ")";
        return node;
    }

    // 一个节点就是一个Node
    protected static class Node<E> {
        E element;  // 节点元素值
        Node<E> left;   // 左节点
        Node<E> right;  // 右节点
        Node<E> parent; // 父节点

        public Node(E element, Node<E> parent) {
            this.element = element;
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
        public Node<E> sibling(){
            if (isLeftChild()){
                return parent.right;
            }
            if (isRightChild()){
                return parent.left;
            }
            return null;
        }
    }
}
