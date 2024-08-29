package com.yang.tree;

import java.util.Comparator;

/**
 * @author 默烦
 * @date 2020/7/16
 */
public class RBTree<E> extends BBST<E> {
    private static final boolean RED = false;
    private static final boolean BLACK = true;

    public RBTree() {
        this(null);
    }

    public RBTree(Comparator<E> comparator) {
        super(comparator);
    }

    @Override
    protected void afterAdd(BinaryTree.Node<E> node) {
        BinaryTree.Node<E> parent = node.parent;
        // 添加节点是根节点时 或 上溢到根节点
        if (parent == null) {
            black(node);
            return;
        }
        // 如果添加节点的父节点是黑色，直接返回
        if (isBlack(parent)) return;
        // 获取叔父节点
        BinaryTree.Node<E> uncle = parent.sibling();
        // 获取祖父节点
        BinaryTree.Node<E> grand = red(parent.parent);
        if (isRed(uncle)) { // 叔父节点是红色时[B树节点上溢]
            black(parent);
            black(uncle);
            // 把祖父节点当作新添加的节点
            afterAdd(grand);
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

    @Override
    protected void afterRemove(BinaryTree.Node<E> node) {
        /**
         * 更改BST中删除度为1的代码 afterRemove(replacement);
         * 传入的是 replacement，而不是 node
         * 但 replacement 如果是红色就会直接返回，显然不对
         * 因此，直接注释，使用下面的代码
         */
        // if (isRed(node)) return;
        /**
         * 1. 用于被删除节点是红色 --> 将删除节点又染成了黑色没印象吗？
         * 答： 人都死了，黑就黑了，没影响
         * 2. 或者 用于取代被删除节点的子节点是红色
         */
        if (isRed(node)) {
            black(node);
            return;
        }
        // 获取被删除节点的父节点
        BinaryTree.Node<E> parent = node.parent;
        // 被删除节点是根节点
        if (parent == null) return;

        // 被删除节点是黑色叶子节点[下溢]
        /**Node<E> sibling = node.sibling();
         * 不能直接这样获取兄弟节点，无法判断被删除节点是左节点还是右节点
         * 在执行 afterRemove()方法前，被删除节点已被清空
         * 导致无法判断被删除节点是左右节点的哪一个
         * 最终就导致 sibling() 获取的总是为 null
         */
        // 判断被删除的节点是左子节点还是右子节点
        /**
         * 被删除节点已经被清空，设置为 null
         * 如果删除的是左节点（左节点为空），那么其兄弟节点就是右节点
         * 如果删除节点不是叶子节点时（递归传入的节点），需要更换判断
         */
        boolean left = parent.left == null || node.isLeftChild();
        BinaryTree.Node<E> sibling = left ? parent.right : parent.left;
        /**
         * 删除有两种情况，这两种情况是对称的
         * 1. 被删除节点在左边，兄弟节点在右边
         * 2. 被删除节点在右边，兄弟节点在左边
         */
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

/*    protected void afterRemove(Node<E> node, Node<E> replacement) {
        // 被删除节点是红色
        if (isRed(node)) return;
        // 用于取代node的子节点是红色
        if (isRed(replacement)){
            black(replacement);
            return;
        }
        // 获取被删除节点的父节点
        Node<E> parent = node.parent;
        // 被删除节点是根节点
        if (parent == null) return;

        // 被删除节点是黑色叶子节点[下溢]
        *//**Node<E> sibling = node.sibling();
     * 不能直接这样获取兄弟节点，无法判断被删除节点是左节点还是右节点
     * 在执行 afterRemove()方法前，被删除节点已被清空
     * 导致无法判断被删除节点是左右节点的哪一个
     * 最终就导致 sibling() 获取的总是为 null
     * *//*
        // 判断被删除的节点是左子节点还是右子节点
        boolean left = parent.left == null || node.isLeftChild();
        Node<E> sibling = left ? parent.right : parent.left;
        if (left){ // 被删除节点在左边，兄弟节点在右边
            if (isRed(sibling)){ // 被删除节点兄弟节点是红色
                black(sibling);
                red(parent);
                rotateLeft(parent);
                // 更换兄弟
                sibling = parent.right;
            }
            // 此时兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)){
                // 兄弟节点一个红色子节点都没，父节点向下跟兄弟节点合并
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack){
                    afterRemove(parent,null);
                }
            } else { // 兄弟节点至少有一个红色子节点，向兄弟节点借元素
                // 兄弟节点左子节点是黑色，先对兄弟进行旋转
                if (isBlack(sibling.right)){
                    rotateRight(sibling);
                    sibling = parent.right;
                }
                color(sibling, colorOf(parent));
                black(sibling.right);
                black(parent);
                rotateLeft(parent);

            }
        } else { // 被删除节点在右边，兄弟节点在左边
            if (isRed(sibling)){ // 被删除节点兄弟节点是红色
                black(sibling);
                red(parent);
                rotateRight(parent);
                // 更换兄弟
                sibling = parent.left;
            }
            // 此时兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)){
                // 兄弟节点一个红色子节点都没，父节点向下跟兄弟节点合并
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack){
                    afterRemove(parent,null);
                }
            } else { // 兄弟节点至少有一个红色子节点，向兄弟节点借元素
                // 兄弟节点左子节点是黑色，先对兄弟进行旋转
                if (isBlack(sibling.left)){
                    rotateLeft(sibling);
                    sibling = parent.left;
                }
                color(sibling, colorOf(parent));
                black(sibling.left);
                black(parent);
                rotateRight(parent);
            }
        }
    }*/

    /****辅助方法****/
    // 节点染色
    private BinaryTree.Node<E> color(BinaryTree.Node<E> node, boolean color) {
        if (node == null) return node;
        ((RBNode<E>) node).color = color;
        return node;
    }

    // 节点染成红色
    private BinaryTree.Node<E> red(BinaryTree.Node<E> node) {
        return color(node, RED);
    }

    // 节点染成黑色
    private BinaryTree.Node<E> black(BinaryTree.Node<E> node) {
        return color(node, BLACK);
    }

    // 查看某一节点的颜色
    private boolean colorOf(BinaryTree.Node<E> node) {
        return node == null ? BLACK : ((RBNode<E>) node).color;
    }

    // 判断节点颜色是否是黑色
    private boolean isBlack(BinaryTree.Node<E> node) {
        return colorOf(node) == BLACK;
    }

    // 判断节点颜色是否是红色
    private boolean isRed(BinaryTree.Node<E> node) {
        return colorOf(node) == RED;
    }

    @Override
    protected BinaryTree.Node<E> createNode(E element, BinaryTree.Node<E> parent) {
        return new RBNode<>(element, parent);
    }

    private static class RBNode<E> extends BinaryTree.Node<E> {
        boolean color = RED;

        public RBNode(E element, BinaryTree.Node<E> parent) {
            super(element, parent);
        }

        @Override
        public String toString() {
            String str = "";
            if (color == RED) {
                str = "R_";
            }
            return str + element.toString();
        }
    }

}
