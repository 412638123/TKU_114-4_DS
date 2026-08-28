import java.util.ArrayList;
import java.util.List;

class Course {
    String courseCode;
    String courseName;
    int credit;

    public Course(String courseCode, String courseName, int credit) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credit = credit;
    }

    @Override
    public String toString() {
        return "[" + courseCode + " | " + courseName + " | " + credit + " 學分]";
    }
}

class TreeNode {
    Course course;
    TreeNode left;
    TreeNode right;

    public TreeNode(Course course) {
        this.course = course;
        this.left = null;
        this.right = null;
    }
}

public class CourseBstIndex {

    public static boolean add(TreeNode[] rootWrapper, Course course) {
        if (course == null || course.courseCode == null || course.courseCode.trim().isEmpty()) {
            return false;
        }
        if (course.credit < 1 || course.credit > 6) {
            return false;
        }

        if (rootWrapper[0] == null) {
            rootWrapper[0] = new TreeNode(course);
            return true;
        }

        TreeNode current = rootWrapper[0];
        TreeNode parent = null;

        while (current != null) {
            parent = current;
            int cmp = course.courseCode.compareTo(current.course.courseCode);
            if (cmp == 0) {
                return false;
            } else if (cmp < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        if (course.courseCode.compareTo(parent.course.courseCode) < 0) {
            parent.left = new TreeNode(course);
        } else {
            parent.right = new TreeNode(course);
        }
        return true;
    }

    public static Course find(TreeNode root, String courseCode) {
        if (courseCode == null) {
            return null;
        }
        TreeNode current = root;
        while (current != null) {
            int cmp = courseCode.compareTo(current.course.courseCode);
            if (cmp == 0) {
                return current.course;
            } else if (cmp < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return null;
    }

    public static boolean updateCredit(TreeNode root, String courseCode, int newCredit) {
        if (newCredit < 1 || newCredit > 6) {
            return false;
        }
        Course course = find(root, courseCode);
        if (course == null) {
            return false;
        }
        course.credit = newCredit;
        return true;
    }

    public static boolean remove(TreeNode[] rootWrapper, String courseCode) {
        if (rootWrapper[0] == null || find(rootWrapper[0], courseCode) == null) {
            return false;
        }
        rootWrapper[0] = removeHelper(rootWrapper[0], courseCode);
        return true;
    }

    private static TreeNode removeHelper(TreeNode root, String courseCode) {
        if (root == null) {
            return null;
        }

        int cmp = courseCode.compareTo(root.course.courseCode);
        if (cmp < 0) {
            root.left = removeHelper(root.left, courseCode);
        } else if (cmp > 0) {
            root.right = removeHelper(root.right, courseCode);
        } else {
            if (root.left == null && root.right == null) {
                return null;
            } else if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            } else {
                TreeNode successor = findMin(root.right);
                root.course = successor.course;
                root.right = removeHelper(root.right, successor.course.courseCode);
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

    public static List<Course> codeRangeQuery(TreeNode root, String minCode, String maxCode) {
        List<Course> result = new ArrayList<>();
        if (minCode == null || maxCode == null || minCode.compareTo(maxCode) > 0) {
            return result;
        }
        rangeQueryHelper(root, minCode, maxCode, result);
        return result;
    }

    private static void rangeQueryHelper(TreeNode node, String minCode, String maxCode, List<Course> result) {
        if (node == null) {
            return;
        }

        if (node.course.courseCode.compareTo(minCode) > 0) {
            rangeQueryHelper(node.left, minCode, maxCode, result);
        }

        if (node.course.courseCode.compareTo(minCode) >= 0 && node.course.courseCode.compareTo(maxCode) <= 0) {
            result.add(node.course);
        }

        if (node.course.courseCode.compareTo(maxCode) < 0) {
            rangeQueryHelper(node.right, minCode, maxCode, result);
        }
    }

    public static void printSortedReport(TreeNode root) {
        System.out.println("=== Course Inorder Sorted Report ===");
        if (root == null) {
            System.out.println("(No courses indexed)");
        } else {
            inorderHelper(root);
        }
        System.out.println("------------------------------------\n");
    }

    private static void inorderHelper(TreeNode node) {
        if (node == null) {
            return;
        }
        inorderHelper(node.left);
        System.out.println(node.course);
        inorderHelper(node.right);
    }

    public static void main(String[] args) {
        TreeNode[] rootWrapper = new TreeNode[1];

        System.out.println("=== 1. Add Course Tests ===");
        System.out.println("Add CS101 (3): " + add(rootWrapper, new Course("CS101", "Intro to CS", 3)));
        System.out.println("Add CS301 (3): " + add(rootWrapper, new Course("CS301", "Algorithms", 3)));
        System.out.println("Add CS201 (4): " + add(rootWrapper, new Course("CS201", "Data Structures", 4)));
        System.out.println("Add EE101 (2): " + add(rootWrapper, new Course("EE101", "Circuit Analysis", 2)));

        System.out.println("Add CS101 (Duplicate): " + add(rootWrapper, new Course("CS101", "Duplicate CS", 3)));
        System.out.println("Add MATH101 (Invalid Credit 0): " + add(rootWrapper, new Course("MATH101", "Calculus", 0)));
        System.out.println("Add MATH102 (Invalid Credit 7): " + add(rootWrapper, new Course("MATH102", "Linear Algebra", 7)));
        System.out.println();

        printSortedReport(rootWrapper[0]);

        System.out.println("=== 2. Find & Update Credit Tests ===");
        System.out.println("Find CS201: " + find(rootWrapper[0], "CS201"));
        System.out.println("Update CS201 Credit -> 5: " + updateCredit(rootWrapper[0], "CS201", 5));
        System.out.println("Update CS201 Credit -> 8 (Invalid): " + updateCredit(rootWrapper[0], "CS201", 8));
        System.out.println("Find CS201 After Update: " + find(rootWrapper[0], "CS201"));
        System.out.println();

        System.out.println("=== 3. Code Range Query [CS100 to CS250] ===");
        List<Course> rangeResults = codeRangeQuery(rootWrapper[0], "CS100", "CS250");
        for (Course c : rangeResults) {
            System.out.println(c);
        }
        System.out.println();

        System.out.println("=== 4. Remove Course Tests ===");
        System.out.println("Remove CS201: " + remove(rootWrapper, "CS201"));
        System.out.println("Remove NONEXIST: " + remove(rootWrapper, "NONEXIST"));
        System.out.println();

        printSortedReport(rootWrapper[0]);
    }
}