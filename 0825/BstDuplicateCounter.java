class TreeNode {
    int val;
    int count;
    TreeNode left;
    TreeNode right;

    public TreeNode(int val) {
        this.val = val;
        this.count = 1;
        this.left = null;
        this.right = null;
    }
}

public class BstDuplicateCounter {

    public static TreeNode insert(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        }

        if (val == root.val) {
            root.count++;
        } else if (val < root.val) {
            root.left = insert(root.left, val);
        } else {
            root.right = insert(root.right, val);
        }

        return root;
    }

    public static void printInOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        printInOrder(root.left);
        System.out.print(root.val + "(" + root.count + ") ");
        printInOrder(root.right);
    }

    public static void main(String[] args) {
        TreeNode root = null;
        int[] elements = {50, 30, 70, 30, 20, 50, 70, 50, 40, 10, 30};

        System.out.println("Inserting elements: 50, 30, 70, 30, 20, 50, 70, 50, 40, 10, 30");
        for (int val : elements) {
            root = insert(root, val);
        }

        System.out.print("Inorder Output: ");
        printInOrder(root);
        System.out.println();
    }
}