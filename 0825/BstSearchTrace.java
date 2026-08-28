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

public class BstSearchTrace {

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

    public static boolean searchWithTrace(TreeNode root, int target) {
        System.out.println("--- Search Target: " + target + " ---");
        TreeNode current = root;
        int count = 0;

        while (current != null) {
            count++;
            if (target == current.val) {
                System.out.println("Step " + count + ": Current = " + current.val + " -> Match found!");
                System.out.println("Result: Found | Total Comparisons: " + count + "\n");
                return true;
            } else if (target < current.val) {
                System.out.println("Step " + count + ": Current = " + current.val + " -> " + target + " < " + current.val + " (Go Left)");
                current = current.left;
            } else {
                System.out.println("Step " + count + ": Current = " + current.val + " -> " + target + " > " + current.val + " (Go Right)");
                current = current.right;
            }
        }

        System.out.println("Step " + (count + 1) + ": Current = null");
        System.out.println("Result: Not Found | Total Comparisons: " + count + "\n");
        return false;
    }

    public static void main(String[] args) {
        TreeNode root = null;
        int[] values = {50, 30, 70, 20, 40, 60, 80};
        for (int v : values) {
            root = insert(root, v);
        }

        searchWithTrace(root, 50);
        searchWithTrace(root, 30);
        searchWithTrace(root, 20);
        searchWithTrace(root, 99);
    }
}