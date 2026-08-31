import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CourseGradeMap {

    private Map<String, List<Integer>> courseGrades;

    public CourseGradeMap() {
        this.courseGrades = new HashMap<>();
    }

    public void addGrade(String courseId, int grade) {
        courseGrades.putIfAbsent(courseId, new ArrayList<>());
        courseGrades.get(courseId).add(grade);
    }

    public double getAverageGrade(String courseId) {
        List<Integer> grades = courseGrades.get(courseId);
        if (grades == null || grades.isEmpty()) {
            return 0.0;
        }
        int sum = 0;
        for (int grade : grades) {
            sum += grade;
        }
        return (double) sum / grades.size();
    }

    public int getMaxGrade(String courseId) {
        List<Integer> grades = courseGrades.get(courseId);
        if (grades == null || grades.isEmpty()) {
            return -1;
        }
        return Collections.max(grades);
    }

    public void printSortedReport() {
        System.out.println("=== Course Grade Report (Sorted by Course ID) ===");
        Map<String, List<Integer>> sortedMap = new TreeMap<>(courseGrades);

        for (Map.Entry<String, List<Integer>> entry : sortedMap.entrySet()) {
            String courseId = entry.getKey();
            List<Integer> grades = entry.getValue();
            double avg = getAverageGrade(courseId);
            int max = getMaxGrade(courseId);

            System.out.println("Course: " + courseId);
            System.out.println("  Grades: " + grades);
            System.out.printf("  Average: %.2f\n", avg);
            System.out.println("  Max Grade: " + max);
        }
    }

    public static void main(String[] args) {
        CourseGradeMap gradeMap = new CourseGradeMap();

        gradeMap.addGrade("CS102", 85);
        gradeMap.addGrade("CS102", 90);
        gradeMap.addGrade("CS102", 78);

        gradeMap.addGrade("CS101", 95);
        gradeMap.addGrade("CS101", 88);

        gradeMap.addGrade("MATH201", 60);
        gradeMap.addGrade("MATH201", 72);
        gradeMap.addGrade("MATH201", 100);

        gradeMap.printSortedReport();
    }
}