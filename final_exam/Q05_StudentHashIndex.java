package final_exam;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Q05_StudentHashIndex {

    private final Map<String, Set<String>> studentToCourses = new HashMap<>();
    private final Map<String, Set<String>> courseToStudents = new HashMap<>();
    private int totalEnrollmentCount = 0;

    private String sanitize(String input) {
        if (input == null) {
            return null;
        }
        String trimmed = input.trim();
        if (trimmed.isEmpty()) {
            return null;
        }
        return trimmed.toUpperCase();
    }

    public boolean enroll(String studentId, String courseId) {
        String sId = sanitize(studentId);
        String cId = sanitize(courseId);

        if (sId == null || cId == null) {
            return false;
        }

        Set<String> courses = studentToCourses.computeIfAbsent(sId, k -> new HashSet<>());
        if (courses.contains(cId)) {
            return false;
        }

        courses.add(cId);

        Set<String> students = courseToStudents.computeIfAbsent(cId, k -> new HashSet<>());
        students.add(sId);

        totalEnrollmentCount++;
        return true;
    }

    public boolean drop(String studentId, String courseId) {
        String sId = sanitize(studentId);
        String cId = sanitize(courseId);

        if (sId == null || cId == null) {
            return false;
        }

        Set<String> courses = studentToCourses.get(sId);
        if (courses == null || !courses.contains(cId)) {
            return false;
        }

        courses.remove(cId);
        if (courses.isEmpty()) {
            studentToCourses.remove(sId);
        }

        Set<String> students = courseToStudents.get(cId);
        if (students != null) {
            students.remove(sId);
            if (students.isEmpty()) {
                courseToStudents.remove(cId);
            }
        }

        totalEnrollmentCount--;
        return true;
    }

    public Set<String> coursesOf(String studentId) {
        String sId = sanitize(studentId);
        if (sId == null || !studentToCourses.containsKey(sId)) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(new HashSet<>(studentToCourses.get(sId)));
    }

    public Set<String> studentsIn(String courseId) {
        String cId = sanitize(courseId);
        if (cId == null || !courseToStudents.containsKey(cId)) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(new HashSet<>(courseToStudents.get(cId)));
    }

    public int enrollmentCount() {
        return totalEnrollmentCount;
    }

    public static void main(String[] args) {
        Q05_StudentHashIndex index = new Q05_StudentHashIndex();
        index.enroll(" s001 ", " cs101 ");
        index.enroll("S001", "CS102");

        System.out.println("Q05 S001 所選課程: " + index.coursesOf("s001"));
        System.out.println("Q05 總選課人次: " + index.enrollmentCount());
        
        index.drop("s001", "cs101");
        System.out.println("Q05 退選後總選課人次: " + index.enrollmentCount());
    }
}