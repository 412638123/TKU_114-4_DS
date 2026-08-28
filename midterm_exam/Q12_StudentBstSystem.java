package midterm_exam;
import java.util.ArrayList;
import java.util.List;

public class Q12_StudentBstSystem {

    public static class Student {
        private final int id;
        private final String name;
        private int score;

        public Student(int id, String name, int score) {
            if (id <= 0 || name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException();
            }
            this.id = id;
            this.name = name.trim();
            if (score < 0) {
                this.score = 0;
            } else if (score > 100) {
                this.score = 100;
            } else {
                this.score = score;
            }
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            if (score < 0) {
                this.score = 0;
            } else if (score > 100) {
                this.score = 100;
            } else {
                this.score = score;
            }
        }

        @Override
        public String toString() {
            return id + "|" + name + "|" + score;
        }
    }

    private static class Node {
        Student student;
        Node left;
        Node right;

        Node(Student student) {
            this.student = student;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;

    public Q12_StudentBstSystem() {
        this.root = null;
    }

    public boolean add(Student student) {
        if (student == null) {
            return false;
        }

        if (root == null) {
            root = new Node(student);
            return true;
        }

        Node current = root;
        Node parent = null;

        while (current != null) {
            parent = current;
            if (student.getId() == current.student.getId()) {
                return false;
            } else if (student.getId() < current.student.getId()) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        if (student.getId() < parent.student.getId()) {
            parent.left = new Node(student);
        } else {
            parent.right = new Node(student);
        }
        return true;
    }

    public Student find(int id) {
        Node current = root;
        while (current != null) {
            if (id == current.student.getId()) {
                return current.student;
            } else if (id < current.student.getId()) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return null;
    }

    public boolean updateScore(int id, int score) {
        Student student = find(id);
        if (student == null) {
            return false;
        }
        student.setScore(score);
        return true;
    }

    public boolean remove(int id) {
        if (find(id) == null) {
            return false;
        }
        root = removeHelper(root, id);
        return true;
    }

    private Node removeHelper(Node node, int id) {
        if (node == null) {
            return null;
        }

        if (id < node.student.getId()) {
            node.left = removeHelper(node.left, id);
        } else if (id > node.student.getId()) {
            node.right = removeHelper(node.right, id);
        } else {
            if (node.left == null && node.right == null) {
                return null;
            } else if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                Node successor = findMin(node.right);
                node.student = successor.student;
                node.right = removeHelper(node.right, successor.student.getId());
            }
        }
        return node;
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public List<Student> studentsBetween(int lowId, int highId) {
        List<Student> result = new ArrayList<>();
        if (lowId > highId) {
            return result;
        }
        studentsBetweenHelper(root, lowId, highId, result);
        return result;
    }

    private void studentsBetweenHelper(Node node, int lowId, int highId, List<Student> result) {
        if (node == null) {
            return;
        }

        if (node.student.getId() > lowId) {
            studentsBetweenHelper(node.left, lowId, highId, result);
        }

        if (node.student.getId() >= lowId && node.student.getId() <= highId) {
            result.add(node.student);
        }

        if (node.student.getId() < highId) {
            studentsBetweenHelper(node.right, lowId, highId, result);
        }
    }

    public List<Student> inorder() {
        List<Student> result = new ArrayList<>();
        inorderHelper(root, result);
        return result;
    }

    private void inorderHelper(Node node, List<Student> result) {
        if (node == null) {
            return;
        }
        inorderHelper(node.left, result);
        result.add(node.student);
        inorderHelper(node.right, result);
    }
}