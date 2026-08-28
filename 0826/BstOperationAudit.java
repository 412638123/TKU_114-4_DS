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

public class BstOperationAudit {

    public static boolean add(TreeNode[] rootWrapper, int val) {
        if (rootWrapper[0] == null) {
            rootWrapper[0] = new TreeNode(val);
            return true;
        }

        TreeNode current = rootWrapper[0];
        TreeNode parent = null;

        while (current != null) {
            parent = current;
            if (val == current.val) {
                return false;
            } else if (val < current.val) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        if (val < parent.val) {
            parent.left = new TreeNode(val);
        } else {
            parent.right = new TreeNode(val);
        }
        return true;
    }

    public static boolean remove(TreeNode[] rootWrapper, int key) {
        if (rootWrapper[0] == null || !contains(rootWrapper[0], key)) {
            return false;
        }
        rootWrapper[0] = removeHelper(rootWrapper[0], key);
        return true;
    }

    private static boolean contains(TreeNode root, int key) {
        TreeNode current = root;
        while (current != null) {
            if (key == current.val) {
                return true;
            } else if (key < current.val) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return false;
    }

    private static TreeNode removeHelper(TreeNode root, int key) {
        if (root == null) {
            return null;
        }

        if (key < root.val) {
            root.left = removeHelper(root.left, key);
        } else if (key > root.val) {
            root.right = removeHelper(root.right, key);
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
                root.right = removeHelper(root.right, successor.val);
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

    public static int getHeight(TreeNode root) {
        if (root == null) {
            return -1;
        }
        return 1 + Math.max(getHeight(root.left), getHeight(root.right));
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

    public static void auditAdd(TreeNode[] rootWrapper, int val) {
        boolean result = add(rootWrapper, val);
        System.out.println("Operation: ADD (" + val + ") | Result: " + result);
        printAuditStatus(rootWrapper[0]);
    }

    public static void auditRemove(TreeNode[] rootWrapper, int key) {
        boolean result = remove(rootWrapper, key);
        System.out.println("Operation: REMOVE (" + key + ") | Result: " + result);
        printAuditStatus(rootWrapper[0]);
    }

    private static void printAuditStatus(TreeNode root) {
        System.out.print("Inorder: ");
        printInorder(root);
        System.out.println();
        System.out.println("Size: " + getSize(root) + " | Height: " + getHeight(root) + " | Valid BST: " + isValidBST(root));
        System.out.println("----------------------------------------");
    }

    public static void main(String[] args) {
        TreeNode[] rootWrapper = new TreeNode[1];

        auditAdd(rootWrapper, 50);
        auditAdd(rootWrapper, 30);
        auditAdd(rootWrapper, 70);
        auditAdd(rootWrapper, 20);
        auditAdd(rootWrapper, 40);
        auditAdd(rootWrapper, 60);
        auditAdd(rootWrapper, 80);

        auditAdd(rootWrapper, 30);

        auditRemove(rootWrapper, 999);

        auditRemove(rootWrapper, 20);

        auditRemove(rootWrapper, 30);

        auditRemove(rootWrapper, 50);
    }
}