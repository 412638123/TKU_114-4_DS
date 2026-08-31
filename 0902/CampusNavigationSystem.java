import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class CampusNavigationSystem {

    public static class NavigationResult {
        private List<String> path;
        private int edgeCount;

        public NavigationResult(List<String> path, int edgeCount) {
            this.path = path;
            this.edgeCount = edgeCount;
        }

        public List<String> getPath() { return path; }
        public int getEdgeCount() { return edgeCount; }

        @Override
        public String toString() {
            return "Path: " + path + " | Edge Count: " + edgeCount;
        }
    }

    private Map<String, String> locations; // id -> name
    private Map<String, List<String>> adjList; // id -> list of neighbor ids

    public CampusNavigationSystem() {
        locations = new HashMap<>();
        adjList = new HashMap<>();
    }

    public void addLocation(String id, String name) {
        if (id != null && name != null) {
            locations.put(id, name);
            adjList.putIfAbsent(id, new ArrayList<>());
        }
    }

    public void addRoad(String id1, String id2) {
        if (id1 == null || id2 == null || id1.equals(id2)) return;
        if (!locations.containsKey(id1) || !locations.containsKey(id2)) return;

        if (!adjList.get(id1).contains(id2)) {
            adjList.get(id1).add(id2);
        }
        if (!adjList.get(id2).contains(id1)) {
            adjList.get(id2).add(id1);
        }
    }

    public NavigationResult findShortestPath(String startId, String targetId) {
        if (startId == null || targetId == null || !locations.containsKey(startId) || !locations.containsKey(targetId)) {
            return new NavigationResult(Collections.emptyList(), -1);
        }

        if (startId.equals(targetId)) {
            return new NavigationResult(List.of(locations.get(startId)), 0);
        }

        Queue<String> queue = new ArrayDeque<>();
        Map<String, String> parentMap = new HashMap<>();
        Set<String> visited = new HashSet<>();

        queue.offer(startId);
        visited.add(startId);

        boolean found = false;

        while (!queue.isEmpty()) {
            String current = queue.poll();

            if (current.equals(targetId)) {
                found = true;
                break;
            }

            for (String neighbor : adjList.getOrDefault(current, Collections.emptyList())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parentMap.put(neighbor, current);
                    queue.offer(neighbor);
                }
            }
        }

        if (!found) {
            return new NavigationResult(Collections.emptyList(), -1);
        }

        List<String> pathNames = new ArrayList<>();
        String curr = targetId;
        while (curr != null) {
            pathNames.add(locations.get(curr));
            curr = parentMap.get(curr);
        }
        Collections.reverse(pathNames);

        return new NavigationResult(pathNames, pathNames.size() - 1);
    }

    public static void main(String[] args) {
        CampusNavigationSystem campus = new CampusNavigationSystem();

        campus.addLocation("L1", "Main Gate");
        campus.addLocation("L2", "Library");
        campus.addLocation("L3", "Student Center");
        campus.addLocation("L4", "Engineering Building");
        campus.addLocation("L5", "Dormitory");
        campus.addLocation("L6", "Isolated Park");

        campus.addRoad("L1", "L2");
        campus.addRoad("L1", "L3");
        campus.addRoad("L2", "L4");
        campus.addRoad("L3", "L4");
        campus.addRoad("L4", "L5");

        System.out.println("=== General Case ===");
        System.out.println(campus.findShortestPath("L1", "L5"));

        System.out.println("\n=== Boundary Cases ===");
        System.out.println("Unreachable target: " + campus.findShortestPath("L1", "L6"));
        System.out.println("Same start and target: " + campus.findShortestPath("L1", "L1"));
        System.out.println("Non-existent node: " + campus.findShortestPath("L1", "INVALID"));
        System.out.println("Null input: " + campus.findShortestPath(null, "L2"));
    }
}