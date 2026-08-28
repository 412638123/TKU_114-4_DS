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

public class BstInvariantChecker {

    public static boolean isValidBST(TreeNode root) {
        return validate(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private static boolean validate(TreeNode node, long min, long max) {
        if (node == null) {
            return true;
        }

        if (node.val <= min || node.val >= max) {
            return false;
        }

        return validate(node.left, min, node.val) && validate(node.right, node.val, max);
    }

    public static void printReport(String testName, TreeNode root) {
        System.out.println("=== " + testName + " ===");
        System.out.println("IsValidBST: " + isValidBST(root));
        System.out.println();
    }

    public static void main(String[] args) {
        TreeNode validTree = new TreeNode(50);
        validTree.left = new TreeNode(30);
        validTree.right = new TreeNode(70);
        validTree.left.left = new TreeNode(20);
        validTree.left.right = new TreeNode(40);
        validTree.right.left = new TreeNode(60);
        validTree.right.right = new TreeNode(80);
        printReport("1. Valid BST", validTree);

        TreeNode invalidTree1 = new TreeNode(50);
        invalidTree1.left = new TreeNode(30);
        invalidTree1.right = new TreeNode(70);
        invalidTree1.left.right = new TreeNode(60);
        printReport("2. Invalid BST (Deep Violation in Left Subtree: 60 > 50)", invalidTree1);

        TreeNode invalidTree2 = new TreeNode(50);
        invalidTree2.left = new TreeNode(30);
        invalidTree2.right = new TreeNode(70);
        invalidTree2.right.left = new TreeNode(40);
        printReport("3. Invalid BST (Deep Violation in Right Subtree: 40 < 50)", invalidTree2);

        TreeNode invalidTree3 = new TreeNode(50);
        invalidTree3.left = new TreeNode(30);
        invalidTree3.right = new TreeNode(70);
        invalidTree3.left.left = new TreeNode(20);
        invalidTree3.left.left.right = new TreeNode(35);
        printReport("4. Invalid BST (Deep Violation in Left-Left Subtree: 35 > 30)", invalidTree3);
    }
}