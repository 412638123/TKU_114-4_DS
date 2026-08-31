import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class CourseDependencyGraph {

    private Map<String, Set<String>> nextCourses;
    private Map<String, Set<String>> prerequisites;

    public CourseDependencyGraph() {
        nextCourses = new HashMap<>();
        prerequisites = new HashMap<>();
    }

    public void addCourse(String course) {
        if (course != null) {
            nextCourses.putIfAbsent(course, new HashSet<>());
            prerequisites.putIfAbsent(course, new HashSet<>());
        }
    }

    public void addPrerequisite(String course, String prereq) {
        if (course == null || prereq == null || course.equals(prereq)) {
            return;
        }
        addCourse(course);
        addCourse(prereq);

        prerequisites.get(course).add(prereq);
        nextCourses.get(prereq).add(course);
    }

    public int getInDegree(String course) {
        if (!prerequisites.containsKey(course)) {
            return 0;
        }
        return prerequisites.get(course).size();
    }

    public int getOutDegree(String course) {
        if (!nextCourses.containsKey(course)) {
            return 0;
        }
        return nextCourses.get(course).size();
    }

    public List<String> getPrerequisites(String course) {
        if (!prerequisites.containsKey(course)) {
            return new ArrayList<>();
        }
        List<String> list = new ArrayList<>(prerequisites.get(course));
        Collections.sort(list);
        return list;
    }

    public List<String> getNextCourses(String course) {
        if (!nextCourses.containsKey(course)) {
            return new ArrayList<>();
        }
        List<String> list = new ArrayList<>(nextCourses.get(course));
        Collections.sort(list);
        return list;
    }

    public void printCourseReport() {
        System.out.println("=== Course Dependency Report ===");
        Set<String> allCourses = new TreeSet<>(nextCourses.keySet());

        for (String course : allCourses) {
            List<String> prereqList = getPrerequisites(course);
            List<String> nextList = getNextCourses(course);
            int inDegree = getInDegree(course);
            int outDegree = getOutDegree(course);

            System.out.println("Course: " + course);
            System.out.println("  In-Degree (Prereq Count): " + inDegree);
            System.out.println("  Out-Degree (Follow-up Count): " + outDegree);
            System.out.println("  Prerequisites: " + prereqList);
            System.out.println("  Follow-up Courses: " + nextList);
        }
    }

    public static void main(String[] args) {
        CourseDependencyGraph graph = new CourseDependencyGraph();

        graph.addPrerequisite("CS102", "CS101");
        graph.addPrerequisite("CS201", "CS102");
        graph.addPrerequisite("CS201", "MATH101");
        graph.addPrerequisite("CS301", "CS201");

        graph.addCourse("GENERAL101");

        graph.printCourseReport();
    }
}