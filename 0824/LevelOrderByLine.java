import java.util.ArrayDeque;
import java.util.Queue;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    public TreeNode(int val) {
        this.val = val;
        this.left = null;
        this.right = null;
    }
}

public class LevelOrderByLine {

    public static void printLevelOrder(TreeNode root) {
        if (root == null) {
            System.out.println("Empty tree.");
            return;
        }

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        int level = 1;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            System.out.print("Level " + level + " (Nodes: " + levelSize + "): ");

            for (int i = 0; i < levelSize; i++) {
                TreeNode current = queue.poll();
                System.out.print(current.val + " ");

                if (current.left != null) {
                    queue.offer(current.left);
                }
                if (current.right != null) {
                    queue.offer(current.right);
                }
            }

            System.out.println();
            level++;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Test 1: Empty Tree ===");
        TreeNode emptyTree = null;
        printLevelOrder(emptyTree);

        System.out.println("\n=== Test 2: Standard Binary Tree ===");
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.right = new TreeNode(6);

        printLevelOrder(root);
    }
}