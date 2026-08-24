import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

class OrgNode {
    String name;
    List<OrgNode> children;

    public OrgNode(String name) {
        this.name = name;
        this.children = new ArrayList<>();
    }

    public void addChild(OrgNode child) {
        if (child != null) {
            this.children.add(child);
        }
    }
}

public class OrganizationTreeReport {

    public static String findParent(OrgNode root, String target) {
        if (root == null || target == null || root.name.equals(target)) {
            return null;
        }
        for (OrgNode child : root.children) {
            if (child.name.equals(target)) {
                return root.name;
            }
            String parent = findParent(child, target);
            if (parent != null) {
                return parent;
            }
        }
        return null;
    }

    public static int findDepth(OrgNode root, String target) {
        if (root == null || target == null) {
            return -1;
        }
        return findDepthHelper(root, target, 0);
    }

    private static int findDepthHelper(OrgNode node, String target, int currentDepth) {
        if (node.name.equals(target)) {
            return currentDepth;
        }
        for (OrgNode child : node.children) {
            int depth = findDepthHelper(child, target, currentDepth + 1);
            if (depth != -1) {
                return depth;
            }
        }
        return -1;
    }

    public static List<String> pathFromRoot(OrgNode root, String target) {
        List<String> path = new ArrayList<>();
        if (root == null || target == null) {
            return path;
        }
        if (findPathHelper(root, target, path)) {
            return path;
        }
        return new ArrayList<>();
    }

    private static boolean findPathHelper(OrgNode node, String target, List<String> path) {
        path.add(node.name);
        if (node.name.equals(target)) {
            return true;
        }
        for (OrgNode child : node.children) {
            if (findPathHelper(child, target, path)) {
                return true;
            }
        }
        path.remove(path.size() - 1);
        return false;
    }

    public static void printByLevel(OrgNode root) {
        if (root == null) {
            System.out.println("Organization tree is empty.");
            return;
        }

        Queue<OrgNode> queue = new ArrayDeque<>();
        queue.offer(root);
        int level = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            System.out.print("Level " + level + ": ");
            for (int i = 0; i < size; i++) {
                OrgNode current = queue.poll();
                System.out.print("[" + current.name + "] ");
                for (OrgNode child : current.children) {
                    queue.offer(child);
                }
            }
            System.out.println();
            level++;
        }
    }

    public static void main(String[] args) {
        OrgNode company = new OrgNode("CEO");
        OrgNode cto = new OrgNode("CTO");
        OrgNode cfo = new OrgNode("CFO");

        OrgNode devDept = new OrgNode("Dev Dept");
        OrgNode qaDept = new OrgNode("QA Dept");
        OrgNode backend = new OrgNode("Backend Team");
        OrgNode frontend = new OrgNode("Frontend Team");

        devDept.addChild(backend);
        devDept.addChild(frontend);
        cto.addChild(devDept);
        cto.addChild(qaDept);

        company.addChild(cto);
        company.addChild(cfo);

        System.out.println("=== Level-Order Structure ===");
        printByLevel(company);

        System.out.println("\n=== Parent Search Tests ===");
        System.out.println("Parent of 'Backend Team': " + findParent(company, "Backend Team"));
        System.out.println("Parent of 'CTO': " + findParent(company, "CTO"));
        System.out.println("Parent of 'CEO' (Root): " + findParent(company, "CEO"));
        System.out.println("Parent of 'HR' (NotFound): " + findParent(company, "HR"));

        System.out.println("\n=== Depth Search Tests ===");
        System.out.println("Depth of 'CEO': " + findDepth(company, "CEO"));
        System.out.println("Depth of 'Dev Dept': " + findDepth(company, "Dev Dept"));
        System.out.println("Depth of 'Frontend Team': " + findDepth(company, "Frontend Team"));
        System.out.println("Depth of 'HR' (NotFound): " + findDepth(company, "HR"));

        System.out.println("\n=== Path From Root Tests ===");
        System.out.println("Path to 'Backend Team': " + pathFromRoot(company, "Backend Team"));
        System.out.println("Path to 'CFO': " + pathFromRoot(company, "CFO"));
        System.out.println("Path to 'HR' (NotFound): " + pathFromRoot(company, "HR"));

        System.out.println("\n=== Edge Cases (Empty Tree) ===");
        OrgNode emptyTree = null;
        printByLevel(emptyTree);
        System.out.println("Parent in empty tree: " + findParent(emptyTree, "CTO"));
        System.out.println("Depth in empty tree: " + findDepth(emptyTree, "CTO"));
        System.out.println("Path in empty tree: " + pathFromRoot(emptyTree, "CTO"));
    }
}