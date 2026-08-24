import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

class TreeNode {
    String val;
    TreeNode left;
    TreeNode right;

    public TreeNode(String val) {
        this.val = val;
        this.left = null;
        this.right = null;
    }
}

public class TraversalResultCollector {

    public static List<String> preorder(TreeNode root) {
        List<String> result = new ArrayList<>();
        preorderHelper(root, result);
        return result;
    }

    private static void preorderHelper(TreeNode node, List<String> result) {
        if (node == null) return;
        result.add(node.val);
        preorderHelper(node.left, result);
        preorderHelper(node.right, result);
    }

    public static List<String> inorder(TreeNode root) {
        List<String> result = new ArrayList<>();
        inorderHelper(root, result);
        return result;
    }

    private static void inorderHelper(TreeNode node, List<String> result) {
        if (node == null) return;
        inorderHelper(node.left, result);
        result.add(node.val);
        inorderHelper(node.right, result);
    }

    public static List<String> postorder(TreeNode root) {
        List<String> result = new ArrayList<>();
        postorderHelper(root, result);
        return result;
    }

    private static void postorderHelper(TreeNode node, List<String> result) {
        if (node == null) return;
        postorderHelper(node.left, result);
        postorderHelper(node.right, result);
        result.add(node.val);
    }

    public static List<String> levelOrder(TreeNode root) {
        List<String> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();
            result.add(current.val);
            if (current.left != null) queue.offer(current.left);
            if (current.right != null) queue.offer(current.right);
        }

        return result;
    }

    public static void test(String label, TreeNode root) {
        System.out.println("=== " + label + " ===");
        System.out.println("Preorder   : " + preorder(root));
        System.out.println("Inorder    : " + inorder(root));
        System.out.println("Postorder  : " + postorder(root));
        System.out.println("LevelOrder : " + levelOrder(root));
        System.out.println("----------------------------------------\n");
    }

    public static void main(String[] args) {
        TreeNode emptyTree = null;
        test("1. Empty Tree", emptyTree);

        TreeNode singleNode = new TreeNode("A");
        test("2. Single-Node Tree", singleNode);

        TreeNode leftSkewed = new TreeNode("A");
        leftSkewed.left = new TreeNode("B");
        leftSkewed.left.left = new TreeNode("C");
        test("3. Left-Skewed Tree", leftSkewed);

        TreeNode completeTree = new TreeNode("A");
        completeTree.left = new TreeNode("B");
        completeTree.right = new TreeNode("C");
        completeTree.left.left = new TreeNode("D");
        completeTree.left.right = new TreeNode("E");
        completeTree.right.left = new TreeNode("F");
        completeTree.right.right = new TreeNode("G");
        test("4. Complete Binary Tree", completeTree);
    }
}