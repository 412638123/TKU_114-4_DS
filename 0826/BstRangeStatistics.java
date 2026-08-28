import java.util.ArrayList;
import java.util.List;

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

public class BstRangeStatistics {

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

    public static List<Integer> valuesBetween(TreeNode root, int low, int high) {
        List<Integer> result = new ArrayList<>();
        if (low > high) {
            return result;
        }
        valuesBetweenHelper(root, low, high, result);
        return result;
    }

    private static void valuesBetweenHelper(TreeNode node, int low, int high, List<Integer> result) {
        if (node == null) {
            return;
        }

        if (node.val > low) {
            valuesBetweenHelper(node.left, low, high, result);
        }

        if (node.val >= low && node.val <= high) {
            result.add(node.val);
        }

        if (node.val < high) {
            valuesBetweenHelper(node.right, low, high, result);
        }
    }

    public static int countBetween(TreeNode root, int low, int high) {
        if (root == null || low > high) {
            return 0;
        }
        return countBetweenHelper(root, low, high);
    }

    private static int countBetweenHelper(TreeNode node, int low, int high) {
        if (node == null) {
            return 0;
        }

        int count = 0;
        if (node.val >= low && node.val <= high) {
            count = 1;
        }

        if (node.val > low) {
            count += countBetweenHelper(node.left, low, high);
        }

        if (node.val < high) {
            count += countBetweenHelper(node.right, low, high);
        }

        return count;
    }

    public static int sumBetween(TreeNode root, int low, int high) {
        if (root == null || low > high) {
            return 0;
        }
        return sumBetweenHelper(root, low, high);
    }

    private static int sumBetweenHelper(TreeNode node, int low, int high) {
        if (node == null) {
            return 0;
        }

        int sum = 0;
        if (node.val >= low && node.val <= high) {
            sum = node.val;
        }

        if (node.val > low) {
            sum += sumBetweenHelper(node.left, low, high);
        }

        if (node.val < high) {
            sum += sumBetweenHelper(node.right, low, high);
        }

        return sum;
    }

    public static void runReport(TreeNode root, int low, int high) {
        System.out.println("=== Range Search [" + low + ", " + high + "] ===");
        if (low > high) {
            System.out.println("Invalid Range: low > high");
        }
        List<Integer> vals = valuesBetween(root, low, high);
        int count = countBetween(root, low, high);
        int sum = sumBetween(root, low, high);

        System.out.println("Values: " + vals);
        System.out.println("Count : " + count);
        System.out.println("Sum   : " + sum);
        System.out.println("----------------------------------------\n");
    }

    public static void main(String[] args) {
        TreeNode root = null;
        int[] values = {50, 30, 70, 20, 40, 60, 80, 10, 25, 35, 45};
        for (int v : values) {
            root = insert(root, v);
        }

        runReport(root, 25, 65);
        runReport(root, 30, 70);
        runReport(root, 100, 200);
        runReport(root, 70, 30);

        TreeNode emptyTree = null;
        System.out.println("=== Test Empty Tree ===");
        runReport(emptyTree, 10, 50);
    }
}