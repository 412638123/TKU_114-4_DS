package midterm_exam;
import java.util.ArrayList;
import java.util.List;

public class Q10_BstDirectory {

    private static class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;
    private int size;

    public Q10_BstDirectory() {
        this.root = null;
        this.size = 0;
    }

    public boolean add(int value) {
        if (root == null) {
            root = new Node(value);
            size++;
            return true;
        }

        Node current = root;
        Node parent = null;

        while (current != null) {
            parent = current;
            if (value == current.value) {
                return false;
            } else if (value < current.value) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        if (value < parent.value) {
            parent.left = new Node(value);
        } else {
            parent.right = new Node(value);
        }
        size++;
        return true;
    }

    public boolean contains(int value) {
        Node current = root;
        while (current != null) {
            if (value == current.value) {
                return true;
            } else if (value < current.value) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return false;
    }

    public int size() {
        return size;
    }

    public List<Integer> searchPath(int target) {
        List<Integer> path = new ArrayList<>();
        Node current = root;
        while (current != null) {
            path.add(current.value);
            if (target == current.value) {
                break;
            } else if (target < current.value) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return path;
    }

    public List<Integer> inorder() {
        List<Integer> result = new ArrayList<>();
        inorderHelper(root, result);
        return result;
    }

    private void inorderHelper(Node node, List<Integer> result) {
        if (node == null) {
            return;
        }
        inorderHelper(node.left, result);
        result.add(node.value);
        inorderHelper(node.right, result);
    }

    public boolean isValid() {
        return isValidHelper(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean isValidHelper(Node node, long min, long max) {
        if (node == null) {
            return true;
        }
        if (node.value <= min || node.value >= max) {
            return false;
        }
        return isValidHelper(node.left, min, node.value) && isValidHelper(node.right, node.value, max);
    }
}