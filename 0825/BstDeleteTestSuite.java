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

public class BstDeleteTestSuite {

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

    public static TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return null;
        }

        if (key < root.val) {
            root.left = deleteNode(root.left, key);
        } else if (key > root.val) {
            root.right = deleteNode(root.right, key);
        } else {
            if (root.left == null && root.right == null) {
                return null;
            } else if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            } else {
                TreeNode successor = findMin(root.right);
                root.val = successor.val;
                root.right = deleteNode(root.right, successor.val);
            }
        }
        return root;
    }

    private static TreeNode findMin(TreeNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public static int getSize(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + getSize(root.left) + getSize(root.right);
    }

    public static boolean isValidBST(TreeNode root) {
        return isValidBSTHelper(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private static boolean isValidBSTHelper(TreeNode node, long min, long max) {
        if (node == null) {
            return true;
        }
        if (node.val <= min || node.val >= max) {
            return false;
        }
        return isValidBSTHelper(node.left, min, node.val) && isValidBSTHelper(node.right, node.val, max);
    }

    public static void printInorder(TreeNode root) {
        if (root == null) {
            System.out.print("(empty)");
            return;
        }
        printInorder(root.left);
        System.out.print(root.val + " ");
        printInorder(root.right);
    }

    public static void printStatus(String testName, TreeNode root) {
        System.out.println("=== " + testName + " ===");
        System.out.print("Inorder: ");
        printInorder(root);
        System.out.println();
        System.out.println("Size: " + getSize(root));
        System.out.println("Valid BST: " + isValidBST(root));
        System.out.println("----------------------------------------\n");
    }

    public static void main(String[] args) {
        TreeNode root = null;

        root = deleteNode(root, 10);
        printStatus("1. Delete Empty Tree", root);

        root = insert(root, 50);
        root = deleteNode(root, 999);
        printStatus("2. Delete Missing Value (999)", root);

        root = deleteNode(root, 50);
        printStatus("3. Delete Single Root (50)", root);

        root = insert(root, 50);
        root = insert(root, 30);
        root = deleteNode(root, 50);
        printStatus("4. Delete Root With One Child (50 -> child 30)", root);

        root = null;
        int[] vals = {50, 30, 70, 20, 40, 60, 80};
        for (int v : vals) {
            root = insert(root, v);
        }
        root = deleteNode(root, 50);
        printStatus("5. Delete Root With Two Children (50)", root);

        System.out.println("=== 6. Sequential Delete To Empty ===");
        int[] deleteOrder = {30, 70, 20, 40, 60, 80};
        for (int val : deleteOrder) {
            root = deleteNode(root, val);
            System.out.print("After deleting " + val + " -> Inorder: ");
            printInorder(root);
            System.out.println(" | Size: " + getSize(root) + " | Valid: " + isValidBST(root));
        }
    }
}