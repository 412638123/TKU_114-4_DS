package midterm_exam;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Q06_EnrollmentIndex {

    private final Map<String, Set<String>> courseMap = new HashMap<>();

    public boolean enroll(String courseCode, String studentId) {
        if (courseCode == null || courseCode.trim().isEmpty() || studentId == null || studentId.trim().isEmpty()) {
            return false;
        }

        String code = courseCode.trim();
        String id = studentId.trim();

        Set<String> students = courseMap.computeIfAbsent(code, k -> new HashSet<>());
        if (students.contains(id)) {
            return false;
        }

        students.add(id);
        return true;
    }

    public boolean drop(String courseCode, String studentId) {
        if (courseCode == null || courseCode.trim().isEmpty() || studentId == null || studentId.trim().isEmpty()) {
            return false;
        }

        String code = courseCode.trim();
        String id = studentId.trim();

        Set<String> students = courseMap.get(code);
        if (students == null || !students.contains(id)) {
            return false;
        }

        students.remove(id);
        if (students.isEmpty()) {
            courseMap.remove(code);
        }
        return true;
    }

    public int courseSize(String courseCode) {
        if (courseCode == null || courseCode.trim().isEmpty()) {
            return 0;
        }

        Set<String> students = courseMap.get(courseCode.trim());
        return students == null ? 0 : students.size();
    }

    public List<String> studentsOf(String courseCode) {
        if (courseCode == null || courseCode.trim().isEmpty()) {
            return new ArrayList<>();
        }

        Set<String> students = courseMap.get(courseCode.trim());
        if (students == null) {
            return new ArrayList<>();
        }

        List<String> list = new ArrayList<>(students);
        Collections.sort(list);
        return list;
    }

    public List<String> coursesOf(String studentId) {
        if (studentId == null || studentId.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String id = studentId.trim();
        List<String> courses = new ArrayList<>();

        for (Map.Entry<String, Set<String>> entry : courseMap.entrySet()) {
            if (entry.getValue().contains(id)) {
                courses.add(entry.getKey());
            }
        }

        Collections.sort(courses);
        return courses;
    }

    public Map<String, Integer> summary() {
        List<String> sortedCourses = new ArrayList<>(courseMap.keySet());
        Collections.sort(sortedCourses);

        Map<String, Integer> summaryMap = new java.util.LinkedHashMap<>();
        for (String code : sortedCourses) {
            summaryMap.put(code, courseMap.get(code).size());
        }

        return summaryMap;
    }
}