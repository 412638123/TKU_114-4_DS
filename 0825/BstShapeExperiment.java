import java.util.Arrays;

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

public class BstShapeExperiment {

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

    public static int getHeight(TreeNode root) {
        if (root == null) {
            return -1;
        }
        return 1 + Math.max(getHeight(root.left), getHeight(root.right));
    }

    public static int getSearchComparisons(TreeNode root, int target) {
        int count = 0;
        TreeNode current = root;
        while (current != null) {
            count++;
            if (target == current.val) {
                return count;
            } else if (target < current.val) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return count;
    }

    public static int getTotalSearchComparisons(TreeNode root, int[] elements) {
        int total = 0;
        for (int val : elements) {
            total += getSearchComparisons(root, val);
        }
        return total;
    }

    public static void runExperiment(String label, int[] insertOrder, int[] allElements) {
        TreeNode root = null;
        for (int v : insertOrder) {
            root = insert(root, v);
        }

        int height = getHeight(root);
        int totalComparisons = getTotalSearchComparisons(root, allElements);
        double avgComparisons = (double) totalComparisons / allElements.length;

        System.out.println("=== " + label + " ===");
        System.out.println("Insert Order: " + Arrays.toString(insertOrder));
        System.out.println("Tree Height: " + height);
        System.out.println("Total Search Comparisons: " + totalComparisons);
        System.out.printf("Average Search Comparisons: %.2f\n", avgComparisons);
        System.out.println("----------------------------------------\n");
    }

    public static void main(String[] args) {
        int[] sortedData = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        int[] reverseData = {15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        int[] balancedData = {8, 4, 12, 2, 6, 10, 14, 1, 3, 5, 7, 9, 11, 13, 15};

        runExperiment("1. Ascending Sorted Order", sortedData, sortedData);
        runExperiment("2. Descending Sorted Order", reverseData, sortedData);
        runExperiment("3. Balanced Order", balancedData, sortedData);
    }
}