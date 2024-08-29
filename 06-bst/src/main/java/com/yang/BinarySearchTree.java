package com.yang;

import com.yang.printer.BinaryTreeInfo;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author 默烦
 * @date 2020/7/3
 */
public class BinarySearchTree<E> implements BinaryTreeInfo {
    private int size;   // 节点个数
    private Node<E> root; //根节点
    private Comparator<E> comparator;

    public BinarySearchTree() {
        this(null);
    }

    public BinarySearchTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

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

    public void add(E element) {
        elementNotNullCheck(element);
        // 添加第一个节点 （根节点）
        if (root == null) {
            root = new Node<>(element, null);
            size++;
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
        Node<E> newNode = new Node<>(element, parent);
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        size++;
    }

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
            // 更改noded的parent的left、right的指向
            if (node.parent == null) { // node度为1，且为根节点
                root = replacement;
            } else if (node == node.parent.left) {
                node.parent.left = replacement;
            } else { // node == node.parent.right
                node.parent.right = replacement;
            }

        } else if (node.parent == null) {     // node度为0，是叶子节点，并且是根节点
            root = null;
        } else { // node是叶子节点，但不是根节点
            if (node == node.parent.left) {
                node.parent.left = null;
            } else {
                node.parent.right = null;
            }
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
/*    // 前序遍历
    public void preorderTraversal() {
        // root 是二叉搜索树的根节点(一个成员变量)
        preorderTraversal(root);
    }

    private void preorderTraversal(Node<E> node) {
        if (node == null) return;
        System.out.println(node.element);
        preorderTraversal(node.left);
        preorderTraversal(node.right);
    }

    // 中序遍历
    public void inorderTraversal() {
        // root 是二叉搜索树的根节点(一个成员变量)
        inorderTraversal(root);
    }

    private void inorderTraversal(Node<E> node) {
        if (node == null) return;
        inorderTraversal(node.left);
        System.out.println(node.element);
        inorderTraversal(node.right);
    }

    // 后序遍历
    public void postorderTraversal() {
        // root 是二叉搜索树的根节点(一个成员变量)
        postorderTraversal(root);
    }

    private void postorderTraversal(Node<E> node) {
        if (node == null) return;
        postorderTraversal(node.left);
        postorderTraversal(node.right);
        System.out.println(node.element);
    }

    // 层序遍历
    public void levelOrderTraversal() {
        if (root == null) return;
        Queue<Node<E>> queue = new LinkedList<>();
        // 根节点入队
        queue.offer(root);
        while (!queue.isEmpty()){
            // 头结点出队
            Node<E> node = queue.poll();
            System.out.println(node.element);

            if (node.left != null){
                queue.offer(node.left);
            }
            if (node.right != null){
                queue.offer(node.right);
            }
        }
    }*/

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

/*    public boolean isComplete() {
        if (root == null) return false;
        Queue<Node<E>> queue = new LinkedList<>();
        // 根节点入队
        queue.offer(root);
        boolean leaf = false;
        while (!queue.isEmpty()) {
            // 头结点出队
            Node<E> node = queue.poll();
            if (leaf && !node.isLeaf()) return false;
            if (node.hasTwoChildren()) {
                queue.offer(node.left);
                queue.offer(node.right);
            } else if (node.left == null && node.right != null) {
                return false;
            } else { // 后面遍历的节点都是
                leaf = true;
                if (node.left!=null){
                    queue.offer(node.left);
                }
            }
        }
        return true;
    }*/

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        toString(root, sb, "");
        return sb.toString();
    }

    private void toString(Node<E> node, StringBuilder sb, String prefix) {
        if (node == null) return;
        sb.append(prefix).append(node.element).append("\n");
        toString(node.left, sb, prefix + "【L】--");
        toString(node.right, sb, prefix + "【R】--");
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

    private Node<E> predecessor(Node<E> node) {
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

    private Node<E> successor(Node<E> node) {
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
        Node<E> myNode = ((Node<E>) node);
        String parentString = "null";
        if (myNode.parent != null) {
            parentString = myNode.parent.element.toString();
        }
        return myNode.element + "_p(" + parentString+")";
    }

    public static abstract class Visitor<E> {
        // 遍历终止标志 true：终止 false：全遍历
        boolean stop;

        // 如果返回true，表示停止遍历
        abstract boolean visit(E element);
    }

    // 一个节点就是一个Node
    private static class Node<E> {
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
    }
}
