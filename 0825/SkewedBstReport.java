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

public class SkewedBstReport {

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

    public static int getSize(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + getSize(root.left) + getSize(root.right);
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

    public static TreeNode buildBalanced(int[] sortedArr, int start, int end) {
        if (start > end) {
            return null;
        }
        int mid = start + (end - start) / 2;
        TreeNode node = new TreeNode(sortedArr[mid]);
        node.left = buildBalanced(sortedArr, start, mid - 1);
        node.right = buildBalanced(sortedArr, mid + 1, end);
        return node;
    }

    public static void generateReport(String title, TreeNode root, int searchTarget) {
        System.out.println("=== " + title + " ===");
        System.out.println("Size: " + getSize(root));
        System.out.println("Height: " + getHeight(root));
        System.out.println("Search comparisons for (" + searchTarget + "): " + getSearchComparisons(root, searchTarget));
        System.out.println();
    }

    public static void main(String[] args) {
        int[] sortedData = {10, 20, 30, 40, 50, 60, 70};

        TreeNode skewedRoot = null;
        for (int v : sortedData) {
            skewedRoot = insert(skewedRoot, v);
        }

        TreeNode balancedRoot = buildBalanced(sortedData, 0, sortedData.length - 1);

        int target = 70;
        generateReport("Skewed Tree (Sequential Insert)", skewedRoot, target);
        generateReport("Balanced Tree (Balanced Insert)", balancedRoot, target);
    }
}