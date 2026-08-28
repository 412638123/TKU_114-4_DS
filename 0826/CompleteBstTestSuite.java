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

public class CompleteBstTestSuite {

    private static int passCount = 0;
    private static int failCount = 0;

    public static void check(String description, boolean condition) {
        if (condition) {
            System.out.println("[PASS] " + description);
            passCount++;
        } else {
            System.out.println("[FAIL] " + description);
            failCount++;
        }
    }

    public static boolean insert(TreeNode[] rootWrapper, int val) {
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

    public static boolean contains(TreeNode root, int val) {
        TreeNode current = root;
        while (current != null) {
            if (val == current.val) {
                return true;
            } else if (val < current.val) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return false;
    }

    public static boolean delete(TreeNode[] rootWrapper, int key) {
        if (rootWrapper[0] == null || !contains(rootWrapper[0], key)) {
            return false;
        }
        rootWrapper[0] = deleteHelper(rootWrapper[0], key);
        return true;
    }

    private static TreeNode deleteHelper(TreeNode root, int key) {
        if (root == null) {
            return null;
        }

        if (key < root.val) {
            root.left = deleteHelper(root.left, key);
        } else if (key > root.val) {
            root.right = deleteHelper(root.right, key);
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
                root.right = deleteHelper(root.right, successor.val);
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

    public static List<Integer> valuesBetween(TreeNode root, int low, int high) {
        List<Integer> result = new ArrayList<>();
        if (low <= high) {
            rangeHelper(root, low, high, result);
        }
        return result;
    }

    private static void rangeHelper(TreeNode node, int low, int high, List<Integer> result) {
        if (node == null) {
            return;
        }
        if (node.val > low) {
            rangeHelper(node.left, low, high, result);
        }
        if (node.val >= low && node.val <= high) {
            result.add(node.val);
        }
        if (node.val < high) {
            rangeHelper(node.right, low, high, result);
        }
    }

    public static void main(String[] args) {
        TreeNode[] rootWrapper = new TreeNode[1];

        check("1. Empty Tree Size is 0", getSize(rootWrapper[0]) == 0);
        check("2. Empty Tree is Valid BST", isValidBST(rootWrapper[0]));
        check("3. Search in Empty Tree returns false", !contains(rootWrapper[0], 50));
        check("4. Delete from Empty Tree returns false", !delete(rootWrapper, 50));

        check("5. Insert Root (50) returns true", insert(rootWrapper, 50));
        check("6. Size after single insert is 1", getSize(rootWrapper[0]) == 1);
        check("7. Search Root (50) returns true", contains(rootWrapper[0], 50));

        check("8. Duplicate Insert (50) returns false", !insert(rootWrapper, 50));
        check("9. Size remains 1 after duplicate insert", getSize(rootWrapper[0]) == 1);

        insert(rootWrapper, 30);
        insert(rootWrapper, 70);
        insert(rootWrapper, 20);
        insert(rootWrapper, 40);
        insert(rootWrapper, 60);
        insert(rootWrapper, 80);

        check("10. Size is 7 after multi insert", getSize(rootWrapper[0]) == 7);
        check("11. Tree is Valid BST after multi insert", isValidBST(rootWrapper[0]));

        check("12. Search Missing Value (99) returns false", !contains(rootWrapper[0], 99));
        check("13. Delete Missing Value (99) returns false", !delete(rootWrapper, 99));

        check("14. Delete Leaf Node (20) returns true", delete(rootWrapper, 20));
        check("15. Leaf deleted: 20 not found", !contains(rootWrapper[0], 20));
        check("16. Size is 6 after leaf deletion", getSize(rootWrapper[0]) == 6);
        check("17. Tree is Valid BST after leaf deletion", isValidBST(rootWrapper[0]));

        check("18. Delete One-Child Node (30) returns true", delete(rootWrapper, 30));
        check("19. One-Child deleted: child 40 still exists", contains(rootWrapper[0], 40));

        check("20. Delete Two-Child Node (50) returns true", delete(rootWrapper, 50));
        check("21. Two-Child deleted: successor/tree intact", contains(rootWrapper[0], 60) && contains(rootWrapper[0], 70));
        check("22. Tree is Valid BST after root deletion", isValidBST(rootWrapper[0]));

        List<Integer> range = valuesBetween(rootWrapper[0], 40, 70);
        check("23. Range Search [40, 70] contains correct items", range.size() == 3 && range.contains(40) && range.contains(60) && range.contains(70));
        List<Integer> invalidRange = valuesBetween(rootWrapper[0], 70, 40);
        check("24. Range Search with low > high returns empty", invalidRange.isEmpty());

        System.out.println("\n========================================");
        System.out.println("Test Suite Finished. PASS: " + passCount + " | FAIL: " + failCount);
        System.out.println("========================================");
    }
}