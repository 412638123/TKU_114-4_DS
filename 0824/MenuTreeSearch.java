import java.util.ArrayList;
import java.util.List;

class MenuItem {
    String name;
    List<MenuItem> children;

    public MenuItem(String name) {
        this.name = name;
        this.children = new ArrayList<>();
    }

    public void addChild(MenuItem child) {
        this.children.add(child);
    }
}

public class MenuTreeSearch {

    public static boolean contains(MenuItem root, String target) {
        if (root == null || target == null) {
            return false;
        }
        if (root.name.equals(target)) {
            return true;
        }
        for (MenuItem child : root.children) {
            if (contains(child, target)) {
                return true;
            }
        }
        return false;
    }

    public static int findDepth(MenuItem root, String target) {
        if (root == null || target == null) {
            return -1;
        }
        return findDepthHelper(root, target, 0);
    }

    private static int findDepthHelper(MenuItem node, String target, int currentDepth) {
        if (node == null) {
            return -1;
        }
        if (node.name.equals(target)) {
            return currentDepth;
        }
        for (MenuItem child : node.children) {
            int depth = findDepthHelper(child, target, currentDepth + 1);
            if (depth != -1) {
                return depth;
            }
        }
        return -1;
    }

    public static int countLeaves(MenuItem root) {
        if (root == null) {
            return 0;
        }
        if (root.children.isEmpty()) {
            return 1;
        }
        int totalLeaves = 0;
        for (MenuItem child : root.children) {
            totalLeaves += countLeaves(child);
        }
        return totalLeaves;
    }

    public static void displayPreorder(MenuItem root) {
        displayPreorderHelper(root, 0);
    }

    private static void displayPreorderHelper(MenuItem node, int indentLevel) {
        if (node == null) {
            return;
        }
        for (int i = 0; i < indentLevel; i++) {
            System.out.print("  ");
        }
        System.out.println("- " + node.name);
        for (MenuItem child : node.children) {
            displayPreorderHelper(child, indentLevel + 1);
        }
    }

    public static void main(String[] args) {
        MenuItem menu = new MenuItem("Main Menu");
        
        MenuItem file = new MenuItem("File");
        file.addChild(new MenuItem("New"));
        file.addChild(new MenuItem("Open"));
        
        MenuItem edit = new MenuItem("Edit");
        MenuItem find = new MenuItem("Find");
        find.addChild(new MenuItem("Find in File"));
        edit.addChild(find);

        menu.addChild(file);
        menu.addChild(edit);
        menu.addChild(new MenuItem("Help"));

        System.out.println("=== Preorder Display ===");
        displayPreorder(menu);

        System.out.println("\n=== Tree Search Tests ===");
        System.out.println("Contains 'Open': " + contains(menu, "Open"));
        System.out.println("Contains 'Settings': " + contains(menu, "Settings"));

        System.out.println("\n=== Depth Search Tests ===");
        System.out.println("Depth of 'Main Menu': " + findDepth(menu, "Main Menu"));
        System.out.println("Depth of 'File': " + findDepth(menu, "File"));
        System.out.println("Depth of 'Find in File': " + findDepth(menu, "Find in File"));
        System.out.println("Depth of 'NotFound': " + findDepth(menu, "NotFound"));

        System.out.println("\n=== Leaf Count ===");
        System.out.println("Total Leaves: " + countLeaves(menu));
    }
}