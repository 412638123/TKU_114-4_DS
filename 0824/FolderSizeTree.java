import java.util.ArrayList;
import java.util.List;

class FolderNode {
    String name;
    int ownSize;
    FolderNode left;
    FolderNode right;

    public FolderNode(String name, int ownSize) {
        this.name = name;
        this.ownSize = ownSize;
        this.left = null;
        this.right = null;
    }
}

public class FolderSizeTree {

    private static FolderNode maxSubtreeNode = null;
    private static int maxSubtreeSize = -1;

    public static int calculateTotalSize(FolderNode root) {
        if (root == null) {
            return 0;
        }

        int leftSize = calculateTotalSize(root.left);
        int rightSize = calculateTotalSize(root.right);
        int totalSize = root.ownSize + leftSize + rightSize;

        if (totalSize > maxSubtreeSize) {
            maxSubtreeSize = totalSize;
            maxSubtreeNode = root;
        }

        return totalSize;
    }

    public static List<String> getLeafFolders(FolderNode root) {
        List<String> leaves = new ArrayList<>();
        findLeafFoldersHelper(root, leaves);
        return leaves;
    }

    private static void findLeafFoldersHelper(FolderNode node, List<String> leaves) {
        if (node == null) {
            return;
        }
        if (node.left == null && node.right == null) {
            leaves.add(node.name);
            return;
        }
        findLeafFoldersHelper(node.left, leaves);
        findLeafFoldersHelper(node.right, leaves);
    }

    public static void printReport(String label, FolderNode root) {
        maxSubtreeNode = null;
        maxSubtreeSize = -1;

        int totalSize = calculateTotalSize(root);
        List<String> leaves = getLeafFolders(root);

        System.out.println("=== " + label + " ===");
        System.out.println("Total Size: " + totalSize + " KB");
        if (maxSubtreeNode != null) {
            System.out.println("Max Subtree Folder: " + maxSubtreeNode.name + " (Size: " + maxSubtreeSize + " KB)");
        } else {
            System.out.println("Max Subtree Folder: None");
        }
        System.out.println("Leaf Folders: " + leaves);
        System.out.println("----------------------------------------\n");
    }

    public static void main(String[] args) {
        FolderNode emptyTree = null;
        printReport("1. Empty Tree", emptyTree);

        FolderNode root = new FolderNode("Root", 100);
        root.left = new FolderNode("Documents", 200);
        root.right = new FolderNode("Pictures", 150);
        
        root.left.left = new FolderNode("Work", 500);
        root.left.right = new FolderNode("Personal", 300);
        
        root.right.left = new FolderNode("Vacation", 400);
        root.right.right = new FolderNode("Family", 250);

        printReport("2. Multi-Level Folder System", root);
    }
}