package final_exam;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Q11_BstHashDirectory {

    private static class Node {
        int id;
        Node left;
        Node right;

        Node(int id) {
            this.id = id;
        }
    }

    private Node root;
    private final Map<Integer, String> idToNameMap = new HashMap<>();

    public boolean add(int id, String name) {
        if (id <= 0 || name == null) {
            return false;
        }

        String trimmedName = name.trim();
        if (trimmedName.isEmpty()) {
            return false;
        }

        if (idToNameMap.containsKey(id)) {
            return false;
        }

        root = insertBst(root, id);
        idToNameMap.put(id, trimmedName);
        return true;
    }

    public String findName(int id) {
        return idToNameMap.get(id);
    }

    public boolean remove(int id) {
        if (!idToNameMap.containsKey(id)) {
            return false;
        }

        root = deleteBst(root, id);
        idToNameMap.remove(id);
        return true;
    }

    public List<Integer> idsBetween(int low, int high) {
        if (low > high) {
            return Collections.emptyList();
        }

        List<Integer> result = new ArrayList<>();
        rangeSearch(root, low, high, result);
        return result;
    }

    public int size() {
        return idToNameMap.size();
    }

    private Node insertBst(Node current, int id) {
        if (current == null) {
            return new Node(id);
        }

        if (id < current.id) {
            current.left = insertBst(current.left, id);
        } else if (id > current.id) {
            current.right = insertBst(current.right, id);
        }

        return current;
    }

    private Node deleteBst(Node current, int id) {
        if (current == null) {
            return null;
        }

        if (id < current.id) {
            current.left = deleteBst(current.left, id);
        } else if (id > current.id) {
            current.right = deleteBst(current.right, id);
        } else {
            if (current.left == null) {
                return current.right;
            } else if (current.right == null) {
                return current.left;
            }

            Node minNode = findMin(current.right);
            current.id = minNode.id;
            current.right = deleteBst(current.right, minNode.id);
        }

        return current;
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    private void rangeSearch(Node node, int low, int high, List<Integer> result) {
        if (node == null) {
            return;
        }

        if (low < node.id) {
            rangeSearch(node.left, low, high, result);
        }

        if (node.id >= low && node.id <= high) {
            result.add(node.id);
        }

        if (high > node.id) {
            rangeSearch(node.right, low, high, result);
        }
    }
}