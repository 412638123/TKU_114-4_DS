class Student {
    int studentId;
    String name;

    public Student(int studentId, String name) {
        this.studentId = studentId;
        this.name = name;
    }

    @Override
    public String toString() {
        return "[" + studentId + ": " + name + "]";
    }
}

class TreeNode {
    Student student;
    TreeNode left;
    TreeNode right;

    public TreeNode(Student student) {
        this.student = student;
        this.left = null;
        this.right = null;
    }
}

public class StudentBstIndex {

    public static boolean insert(TreeNode[] rootWrapper, Student student) {
        if (student == null) {
            return false;
        }
        if (rootWrapper[0] == null) {
            rootWrapper[0] = new TreeNode(student);
            return true;
        }

        TreeNode current = rootWrapper[0];
        TreeNode parent = null;

        while (current != null) {
            parent = current;
            if (student.studentId == current.student.studentId) {
                return false;
            } else if (student.studentId < current.student.studentId) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        if (student.studentId < parent.student.studentId) {
            parent.left = new TreeNode(student);
        } else {
            parent.right = new TreeNode(student);
        }
        return true;
    }

    public static Student search(TreeNode root, int studentId) {
        TreeNode current = root;
        while (current != null) {
            if (studentId == current.student.studentId) {
                return current.student;
            } else if (studentId < current.student.studentId) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return null;
    }

    public static boolean delete(TreeNode[] rootWrapper, int studentId) {
        if (rootWrapper[0] == null) {
            return false;
        }

        if (search(rootWrapper[0], studentId) == null) {
            return false;
        }

        rootWrapper[0] = deleteHelper(rootWrapper[0], studentId);
        return true;
    }

    private static TreeNode deleteHelper(TreeNode root, int studentId) {
        if (root == null) {
            return null;
        }

        if (studentId < root.student.studentId) {
            root.left = deleteHelper(root.left, studentId);
        } else if (studentId > root.student.studentId) {
            root.right = deleteHelper(root.right, studentId);
        } else {
            if (root.left == null && root.right == null) {
                return null;
            } else if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            } else {
                TreeNode successor = findMin(root.right);
                root.student = successor.student;
                root.right = deleteHelper(root.right, successor.student.studentId);
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

    public static void printInorder(TreeNode root) {
        if (root == null) {
            return;
        }
        printInorder(root.left);
        System.out.print(root.student + " ");
        printInorder(root.right);
    }

    public static void main(String[] args) {
        TreeNode[] rootWrapper = new TreeNode[1];

        System.out.println("=== Insert Tests ===");
        System.out.println("Insert 101: " + insert(rootWrapper, new Student(101, "Alice")));
        System.out.println("Insert 105: " + insert(rootWrapper, new Student(105, "Bob")));
        System.out.println("Insert 102: " + insert(rootWrapper, new Student(102, "Charlie")));
        System.out.println("Insert 101 (Duplicate): " + insert(rootWrapper, new Student(101, "David")));

        System.out.print("\nCurrent Tree Inorder: ");
        printInorder(rootWrapper[0]);
        System.out.println();

        System.out.println("\n=== Search Tests ===");
        System.out.println("Search 102: " + search(rootWrapper[0], 102));
        System.out.println("Search 999: " + search(rootWrapper[0], 999));

        System.out.println("\n=== Delete Tests ===");
        System.out.println("Delete 102: " + delete(rootWrapper, 102));
        System.out.println("Delete 999 (Non-existing): " + delete(rootWrapper, 999));

        System.out.print("\nFinal Tree Inorder: ");
        printInorder(rootWrapper[0]);
        System.out.println();
    }
}