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

public class TreeBugLab {

    public static boolean buggySearch(TreeNode root, int target) {
        TreeNode current = root;
        while (current != null) {
            if (target == current.val) {
                return true;
            } else if (target < current.val) {
                current = current.right;
            } else {
                current = current.left;
            }
        }
        return false;
    }

    public static boolean fixedSearch(TreeNode root, int target) {
        TreeNode current = root;
        while (current != null) {
            if (target == current.val) {
                return true;
            } else if (target < current.val) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return false;
    }

    public static void buggyInorder(TreeNode root) {
        if (root == null) {
            return;
        }
        buggyInorder(root.right);
        System.out.print(root.val + " ");
        buggyInorder(root.left);
    }

    public static void fixedInorder(TreeNode root) {
        if (root == null) {
            return;
        }
        fixedInorder(root.left);
        System.out.print(root.val + " ");
        fixedInorder(root.right);
    }

    public static TreeNode buggyDelete(TreeNode root, int key) {
        if (root == null) {
            return null;
        }
        if (key < root.val) {
            root.left = buggyDelete(root.left, key);
        } else if (key > root.val) {
            root.right = buggyDelete(root.right, key);
        } else {
            return null;
        }
        return root;
    }

    public static TreeNode fixedDelete(TreeNode root, int key) {
        if (root == null) {
            return null;
        }
        if (key < root.val) {
            root.left = fixedDelete(root.left, key);
        } else if (key > root.val) {
            root.right = fixedDelete(root.right, key);
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
                root.right = fixedDelete(root.right, successor.val);
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

    public static boolean buggyIsValid(TreeNode root) {
        if (root == null) {
            return true;
        }
        if (root.left != null && root.left.val >= root.val) {
            return false;
        }
        if (root.right != null && root.right.val <= root.val) {
            return false;
        }
        return buggyIsValid(root.left) && buggyIsValid(root.right);
    }

    public static boolean fixedIsValid(TreeNode root) {
        return fixedIsValidHelper(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private static boolean fixedIsValidHelper(TreeNode node, long min, long max) {
        if (node == null) {
            return true;
        }
        if (node.val <= min || node.val >= max) {
            return false;
        }
        return fixedIsValidHelper(node.left, min, node.val) && fixedIsValidHelper(node.right, node.val, max);
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(50);
        root.left = new TreeNode(30);
        root.right = new TreeNode(70);

        System.out.println("=== 1. Search Direction Bug ===");
        System.out.println("Buggy Search (30): " + buggySearch(root, 30));
        System.out.println("Fixed Search (30): " + fixedSearch(root, 30));

        System.out.println("\n=== 2. Inorder Order Bug ===");
        System.out.print("Buggy Inorder: ");
        buggyInorder(root);
        System.out.println();
        System.out.print("Fixed Inorder: ");
        fixedInorder(root);
        System.out.println();

        System.out.println("\n=== 3. Delete Child Loss Bug ===");
        TreeNode deleteTreeBuggy = new TreeNode(50);
        deleteTreeBuggy.left = new TreeNode(30);
        deleteTreeBuggy.left.left = new TreeNode(20);
        buggyDelete(deleteTreeBuggy, 30);
        System.out.print("Buggy Delete (lost child 20): ");
        fixedInorder(deleteTreeBuggy);
        System.out.println();

        TreeNode deleteTreeFixed = new TreeNode(50);
        deleteTreeFixed.left = new TreeNode(30);
        deleteTreeFixed.left.left = new TreeNode(20);
        fixedDelete(deleteTreeFixed, 30);
        System.out.print("Fixed Delete (kept child 20): ");
        fixedInorder(deleteTreeFixed);
        System.out.println();

        System.out.println("\n=== 4. Direct Child Only Validation Bug ===");
        TreeNode deepBadTree = new TreeNode(50);
        deepBadTree.left = new TreeNode(30);
        deepBadTree.left.right = new TreeNode(60);
        System.out.println("Buggy Validation (False Positive): " + buggyIsValid(deepBadTree));
        System.out.println("Fixed Validation (Correct): " + fixedIsValid(deepBadTree));
    }
}