import java.util.ArrayList;
import java.util.List;

class StudentScore {
    int score;
    int studentId;
    String name;

    public StudentScore(int score, int studentId, String name) {
        this.score = score;
        this.studentId = studentId;
        this.name = name;
    }

    public int compareTo(StudentScore other) {
        if (this.score != other.score) {
            return Integer.compare(this.score, other.score);
        }
        return Integer.compare(this.studentId, other.studentId);
    }

    @Override
    public String toString() {
        return "[" + score + "分 | ID:" + studentId + " | " + name + "]";
    }
}

class TreeNode {
    StudentScore data;
    TreeNode left;
    TreeNode right;

    public TreeNode(StudentScore data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}

public class ScoreRangeBst {

    public static TreeNode insert(TreeNode root, StudentScore data) {
        if (root == null) {
            return new TreeNode(data);
        }

        int cmp = data.compareTo(root.data);
        if (cmp < 0) {
            root.left = insert(root.left, data);
        } else if (cmp > 0) {
            root.right = insert(root.right, data);
        }

        return root;
    }

    public static List<StudentScore> findRange(TreeNode root, int minScore, int maxScore) {
        List<StudentScore> result = new ArrayList<>();
        findRangeHelper(root, minScore, maxScore, result);
        return result;
    }

    private static void findRangeHelper(TreeNode node, int minScore, int maxScore, List<StudentScore> result) {
        if (node == null) {
            return;
        }

        if (node.data.score > minScore) {
            findRangeHelper(node.left, minScore, maxScore, result);
        }

        if (node.data.score >= minScore && node.data.score <= maxScore) {
            result.add(node.data);
        }

        if (node.data.score < maxScore) {
            findRangeHelper(node.right, minScore, maxScore, result);
        }
    }

    public static void main(String[] args) {
        TreeNode root = null;
        StudentScore[] students = {
            new StudentScore(85, 101, "Alice"),
            new StudentScore(92, 102, "Bob"),
            new StudentScore(85, 103, "Charlie"),
            new StudentScore(70, 104, "David"),
            new StudentScore(95, 105, "Eve"),
            new StudentScore(85, 100, "Frank")
        };

        for (StudentScore s : students) {
            root = insert(root, s);
        }

        System.out.println("=== Range Search [80, 90] ===");
        List<StudentScore> range1 = findRange(root, 80, 90);
        for (StudentScore s : range1) {
            System.out.println(s);
        }

        System.out.println("\n=== Range Search [85, 85] ===");
        List<StudentScore> range2 = findRange(root, 85, 85);
        for (StudentScore s : range2) {
            System.out.println(s);
        }

        System.out.println("\n=== Range Search [60, 100] ===");
        List<StudentScore> range3 = findRange(root, 60, 100);
        for (StudentScore s : range3) {
            System.out.println(s);
        }
    }
}