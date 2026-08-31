import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

public class EnrollmentConflictSet {

    public static class EnrollmentKey {
        private String studentId;
        private String courseId;

        public EnrollmentKey(String studentId, String courseId) {
            this.studentId = studentId;
            this.courseId = courseId;
        }

        public String getStudentId() {
            return studentId;
        }

        public String getCourseId() {
            return courseId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            EnrollmentKey that = (EnrollmentKey) o;
            return Objects.equals(studentId, that.studentId) && Objects.equals(courseId, that.courseId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(studentId, courseId);
        }

        @Override
        public String toString() {
            return "(" + studentId + ", " + courseId + ")";
        }
    }

    public static void processEnrollments(List<EnrollmentKey> records) {
        if (records == null || records.isEmpty()) {
            System.out.println("No enrollment records to process.");
            return;
        }

        Set<EnrollmentKey> uniqueEnrollments = new HashSet<>();
        List<EnrollmentKey> duplicates = new ArrayList<>();

        Map<String, Set<String>> studentCourses = new TreeMap<>();
        Map<String, Set<String>> courseStudents = new TreeMap<>();

        for (EnrollmentKey record : records) {
            if (record == null || record.getStudentId() == null || record.getCourseId() == null) {
                continue;
            }

            if (!uniqueEnrollments.add(record)) {
                duplicates.add(record);
            } else {
                studentCourses.putIfAbsent(record.getStudentId(), new HashSet<>());
                studentCourses.get(record.getStudentId()).add(record.getCourseId());

                courseStudents.putIfAbsent(record.getCourseId(), new HashSet<>());
                courseStudents.get(record.getCourseId()).add(record.getStudentId());
            }
        }

        System.out.println("=== Duplicate Enrollment Records ===");
        if (duplicates.isEmpty()) {
            System.out.println("None");
        } else {
            for (EnrollmentKey dup : duplicates) {
                System.out.println(dup);
            }
        }

        System.out.println("\n=== Student Enrolled Courses ===");
        for (Map.Entry<String, Set<String>> entry : studentCourses.entrySet()) {
            List<String> sortedCourses = new ArrayList<>(entry.getValue());
            Collections.sort(sortedCourses);
            System.out.println("Student " + entry.getKey() + ": " + sortedCourses);
        }

        System.out.println("\n=== Course Enrollment Count ===");
        for (Map.Entry<String, Set<String>> entry : courseStudents.entrySet()) {
            System.out.println("Course " + entry.getKey() + ": " + entry.getValue().size() + " student(s)");
        }
    }

    public static void main(String[] args) {
        List<EnrollmentKey> records = List.of(
            new EnrollmentKey("S001", "CS101"),
            new EnrollmentKey("S001", "CS102"),
            new EnrollmentKey("S002", "CS101"),
            new EnrollmentKey("S001", "CS101"), // Duplicate
            new EnrollmentKey("S003", "MATH201"),
            new EnrollmentKey("S002", "CS102"),
            new EnrollmentKey("S002", "CS101")  // Duplicate
        );

        processEnrollments(records);
    }
}