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

public class BinaryTreeStatistics {

    public static int size(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + size(root.left) + size(root.right);
    }

    public static int sum(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return root.val + sum(root.left) + sum(root.right);
    }

    public static Integer maximum(TreeNode root) {
        if (root == null) {
            return null;
        }
        int max = root.val;
        Integer leftMax = maximum(root.left);
        Integer rightMax = maximum(root.right);

        if (leftMax != null) {
            max = Math.max(max, leftMax);
        }
        if (rightMax != null) {
            max = Math.max(max, rightMax);
        }
        return max;
    }

    public static int leafCount(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return 1;
        }
        return leafCount(root.left) + leafCount(root.right);
    }

    public static int height(TreeNode root) {
        if (root == null) {
            return -1;
        }
        return 1 + Math.max(height(root.left), height(root.right));
    }

    public static boolean contains(TreeNode root, int target) {
        if (root == null) {
            return false;
        }
        if (root.val == target) {
            return true;
        }
        return contains(root.left, target) || contains(root.right, target);
    }

    public static void printReport(String label, TreeNode root) {
        System.out.println("=== " + label + " ===");
        System.out.println("Size: " + size(root));
        System.out.println("Sum: " + sum(root));
        System.out.println("Maximum: " + (maximum(root) == null ? "None (Empty Tree)" : maximum(root)));
        System.out.println("Leaf count: " + leafCount(root));
        System.out.println("Height: " + height(root));
        System.out.println("Contains 20: " + contains(root, 20));
        System.out.println("Contains 99: " + contains(root, 99));
        System.out.println("----------------------------------------\n");
    }

    public static void main(String[] args) {
        TreeNode emptyTree = null;
        printReport("Empty Tree", emptyTree);

        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(-5);
        root.right = new TreeNode(20);
        root.left.left = new TreeNode(3);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(25);

        printReport("Normal Binary Tree", root);
    }
}