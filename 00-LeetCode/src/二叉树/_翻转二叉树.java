package 二叉树;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author 默烦
 * @date 2020/7/5
 */
public class _翻转二叉树 {

    // 前序遍历
    public TreeNode invertTree_1(TreeNode root) {
        if (root == null) return null;

        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;

        invertTree_1(root.left);
        invertTree_1(root.right);

        return root;
    }

    // 后序遍历
    public TreeNode invertTree_2(TreeNode root) {
        if (root == null) return null;

        invertTree_2(root.left);
        invertTree_2(root.right);

        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;

        return root;
    }

    // 中序遍历
    public TreeNode invertTree_3(TreeNode root) {
        if (root == null) return null;

        invertTree_3(root.left);
        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;
        invertTree_3(root.left);

        return root;
    }

    // 层序遍历
    public TreeNode invertTree_4(TreeNode root) {
        if (root == null) return null;
        Queue<TreeNode> queue = new LinkedList<>();
        // 根节点入队
        queue.offer(root);
        while (!queue.isEmpty()) {
            // 头结点出队
            TreeNode node = queue.poll();

            TreeNode tmp = node.left;
            node.left = node.right;
            node.right = tmp;

            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        return root;
    }
}
