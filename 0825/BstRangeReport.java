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

public class BstRangeReport {

    public static TreeNode insert(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        }
        if (val < root.val) {
            root.left = insert(root.left, val);
        } else if (val > root.val) {
            root.right = insert(root.right, val);
        }
        return root;
    }

    public static Integer min(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.val;
    }

    public static Integer max(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.val;
    }

    public static void printRange(TreeNode root, int low, int high) {
        System.out.print("Range [" + low + ", " + high + "]: ");
        if (low > high) {
            System.out.println("Invalid range (low > high).");
            return;
        }
        printRangeHelper(root, low, high);
        System.out.println();
    }

    private static void printRangeHelper(TreeNode node, int low, int high) {
        if (node == null) {
            return;
        }

        if (node.val > low) {
            printRangeHelper(node.left, low, high);
        }

        if (node.val >= low && node.val <= high) {
            System.out.print(node.val + " ");
        }

        if (node.val < high) {
            printRangeHelper(node.right, low, high);
        }
    }

    public static void main(String[] args) {
        TreeNode root = null;
        int[] values = {50, 30, 70, 20, 40, 60, 80};
        for (int v : values) {
            root = insert(root, v);
        }

        System.out.println("=== Min / Max Tests ===");
        System.out.println("Min: " + min(root));
        System.out.println("Max: " + max(root));

        System.out.println("\n=== Range Search Tests ===");
        printRange(root, 25, 65);
        printRange(root, 30, 70);
        printRange(root, 70, 30);

        System.out.println("\n=== Empty Tree Test ===");
        TreeNode emptyTree = null;
        System.out.println("Empty Tree Min: " + min(emptyTree));
        System.out.println("Empty Tree Max: " + max(emptyTree));
        printRange(emptyTree, 10, 50);
    }
}