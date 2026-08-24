import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

class TreeNode {
    String val;
    TreeNode left;
    TreeNode right;

    public TreeNode(String val) {
        this.val = val;
        this.left = null;
        this.right = null;
    }
}

public class TraversalTestReport {

    public static List<String> preorder(TreeNode root) {
        List<String> result = new ArrayList<>();
        preorderHelper(root, result);
        return result;
    }

    private static void preorderHelper(TreeNode node, List<String> result) {
        if (node == null) return;
        result.add(node.val);
        preorderHelper(node.left, result);
        preorderHelper(node.right, result);
    }

    public static List<String> inorder(TreeNode root) {
        List<String> result = new ArrayList<>();
        inorderHelper(root, result);
        return result;
    }

    private static void inorderHelper(TreeNode node, List<String> result) {
        if (node == null) return;
        inorderHelper(node.left, result);
        result.add(node.val);
        inorderHelper(node.right, result);
    }

    public static List<String> postorder(TreeNode root) {
        List<String> result = new ArrayList<>();
        postorderHelper(root, result);
        return result;
    }

    private static void postorderHelper(TreeNode node, List<String> result) {
        if (node == null) return;
        postorderHelper(node.left, result);
        postorderHelper(node.right, result);
        result.add(node.val);
    }

    public static List<String> levelOrder(TreeNode root) {
        List<String> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();
            result.add(current.val);
            if (current.left != null) queue.offer(current.left);
            if (current.right != null) queue.offer(current.right);
        }

        return result;
    }

    public static void runTest(String testName, TreeNode root,
                                List<String> expectedPre,
                                List<String> expectedIn,
                                List<String> expectedPost,
                                List<String> expectedLevel) {

        System.out.println("=== " + testName + " ===");

        List<String> actualPre = preorder(root);
        List<String> actualIn = inorder(root);
        List<String> actualPost = postorder(root);
        List<String> actualLevel = levelOrder(root);

        printResult("Preorder  ", expectedPre, actualPre);
        printResult("Inorder   ", expectedIn, actualIn);
        printResult("Postorder ", expectedPost, actualPost);
        printResult("LevelOrder", expectedLevel, actualLevel);

        System.out.println("--------------------------------------------------\n");
    }

    private static void printResult(String type, List<String> expected, List<String> actual) {
        boolean match = Objects.equals(expected, actual);
        System.out.println(type + " -> Expected: " + expected + " | Actual: " + actual + " | Match: " + match);
    }

    public static void main(String[] args) {

        TreeNode emptyTree = null;
        runTest("1. Empty Tree", emptyTree,
                List.of(),
                List.of(),
                List.of(),
                List.of());

        TreeNode singleNode = new TreeNode("A");
        runTest("2. Single-Node Tree", singleNode,
                List.of("A"),
                List.of("A"),
                List.of("A"),
                List.of("A"));

        TreeNode onlyLeft = new TreeNode("A");
        onlyLeft.left = new TreeNode("B");
        onlyLeft.left.left = new TreeNode("C");
        runTest("3. Only-Left Tree", onlyLeft,
                List.of("A", "B", "C"),
                List.of("C", "B", "A"),
                List.of("C", "B", "A"),
                List.of("A", "B", "C"));

        TreeNode onlyRight = new TreeNode("A");
        onlyRight.right = new TreeNode("B");
        onlyRight.right.right = new TreeNode("C");
        runTest("4. Only-Right Tree", onlyRight,
                List.of("A", "B", "C"),
                List.of("A", "B", "C"),
                List.of("C", "B", "A"),
                List.of("A", "B", "C"));

        TreeNode completeTree = new TreeNode("A");
        completeTree.left = new TreeNode("B");
        completeTree.right = new TreeNode("C");
        completeTree.left.left = new TreeNode("D");
        completeTree.left.right = new TreeNode("E");
        completeTree.right.left = new TreeNode("F");
        completeTree.right.right = new TreeNode("G");
        runTest("5. Complete Binary Tree", completeTree,
                List.of("A", "B", "D", "E", "C", "F", "G"),
                List.of("D", "B", "E", "A", "F", "C", "G"),
                List.of("D", "E", "B", "F", "G", "C", "A"),
                List.of("A", "B", "C", "D", "E", "F", "G"));

        TreeNode irregularTree = new TreeNode("A");
        irregularTree.left = new TreeNode("B");
        irregularTree.right = new TreeNode("C");
        irregularTree.left.right = new TreeNode("D");
        irregularTree.right.left = new TreeNode("E");
        irregularTree.right.left.right = new TreeNode("F");
        runTest("6. Irregular Tree", irregularTree,
                List.of("A", "B", "D", "C", "E", "F"),
                List.of("B", "D", "A", "E", "F", "C"),
                List.of("D", "B", "F", "E", "C", "A"),
                List.of("A", "B", "C", "D", "E", "F"));
    }
}