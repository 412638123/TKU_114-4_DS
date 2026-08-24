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

public class BinaryTreeStructureReport {

    public static Integer getRootValue(TreeNode root) {
        return root == null ? null : root.val;
    }

    public static int size(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + size(root.left) + size(root.right);
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

    public static void printLeaves(TreeNode root) {
        if (root == null) {
            System.out.println("None");
            return;
        }
        printLeavesHelper(root);
        System.out.println();
    }

    private static void printLeavesHelper(TreeNode node) {
        if (node == null) {
            return;
        }
        if (node.left == null && node.right == null) {
            System.out.print(node.val + " ");
            return;
        }
        printLeavesHelper(node.left);
        printLeavesHelper(node.right);
    }

    public static void printReport(String testName, TreeNode root) {
        System.out.println("=== " + testName + " ===");
        System.out.println("Root: " + (getRootValue(root) == null ? "None" : getRootValue(root)));
        System.out.print("Leaves: ");
        printLeaves(root);
        System.out.println("Size: " + size(root));
        System.out.println("Leaf count: " + leafCount(root));
        System.out.println("Height: " + height(root));
        System.out.println("----------------------------------------\n");
    }

    public static void main(String[] args) {
        TreeNode emptyTree = null;
        printReport("Empty Tree", emptyTree);

        TreeNode singleNodeTree = new TreeNode(100);
        printReport("Single-node Tree", singleNodeTree);

        TreeNode normalTree = new TreeNode(1);
        normalTree.left = new TreeNode(2);
        normalTree.right = new TreeNode(3);
        normalTree.left.left = new TreeNode(4);
        normalTree.left.right = new TreeNode(5);
        normalTree.right.left = new TreeNode(6);
        normalTree.right.right = new TreeNode(7);

        printReport("7-node Binary Tree", normalTree);
    }
}