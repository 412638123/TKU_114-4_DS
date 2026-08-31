import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CoursePlanningGraph {

    private Map<String, List<String>> adjList; // prereq -> list of dependent courses

    public CoursePlanningGraph() {
        adjList = new HashMap<>();
    }

    public void addCourse(String course) {
        if (course != null) {
            adjList.putIfAbsent(course, new ArrayList<>());
        }
    }

    public void addPrerequisite(String prereq, String dependentCourse) {
        if (prereq == null || dependentCourse == null || prereq.equals(dependentCourse)) {
            return;
        }
        addCourse(prereq);
        addCourse(dependentCourse);

        if (!adjList.get(prereq).contains(dependentCourse)) {
            adjList.get(prereq).add(dependentCourse);
        }
    }

    public boolean isReachable(String startPrereq, String targetCourse) {
        if (startPrereq == null || targetCourse == null || !adjList.containsKey(startPrereq) || !adjList.containsKey(targetCourse)) {
            return false;
        }
        Set<String> visited = new HashSet<>();
        return dfsReachable(startPrereq, targetCourse, visited);
    }

    private boolean dfsReachable(String current, String target, Set<String> visited) {
        if (current.equals(target)) {
            return true;
        }
        visited.add(current);

        for (String neighbor : adjList.getOrDefault(current, Collections.emptyList())) {
            if (!visited.contains(neighbor)) {
                if (dfsReachable(neighbor, target, visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<String> getAllAffectedCourses(String cancelledPrereq) {
        if (cancelledPrereq == null || !adjList.containsKey(cancelledPrereq)) {
            return Collections.emptyList();
        }

        Set<String> affectedSet = new HashSet<>();
        Set<String> visited = new HashSet<>();

        dfsCollectAffected(cancelledPrereq, visited, affectedSet);
        affectedSet.remove(cancelledPrereq); // Remove self, only return downstream affected courses

        List<String> affectedList = new ArrayList<>(affectedSet);
        Collections.sort(affectedList);
        return affectedList;
    }

    private void dfsCollectAffected(String current, Set<String> visited, Set<String> affectedSet) {
        visited.add(current);
        affectedSet.add(current);

        for (String neighbor : adjList.getOrDefault(current, Collections.emptyList())) {
            if (!visited.contains(neighbor)) {
                dfsCollectAffected(neighbor, visited, affectedSet);
            }
        }
    }

    public static void main(String[] args) {
        CoursePlanningGraph planner = new CoursePlanningGraph();

        planner.addPrerequisite("CS101", "CS102");
        planner.addPrerequisite("CS102", "CS201");
        planner.addPrerequisite("CS201", "CS301");
        planner.addPrerequisite("CS102", "CS202");
        planner.addCourse("MATH101");

        System.out.println("=== General Case ===");
        System.out.println("Is CS301 reachable from CS101? " + planner.isReachable("CS101", "CS301"));
        System.out.println("Is MATH101 reachable from CS101? " + planner.isReachable("CS101", "MATH101"));
        System.out.println("Courses affected if CS102 is cancelled: " + planner.getAllAffectedCourses("CS102"));

        System.out.println("\n=== Boundary Cases ===");
        System.out.println("Affected courses for non-existent: " + planner.getAllAffectedCourses("INVALID"));
        System.out.println("Affected courses for leaf node (CS301): " + planner.getAllAffectedCourses("CS301"));
        System.out.println("Null reachability check: " + planner.isReachable(null, "CS101"));
    }
}