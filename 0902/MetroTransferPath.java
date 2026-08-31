import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class MetroTransferPath {

    public static class PathResult {
        private List<String> path;
        private int edgeCount;

        public PathResult(List<String> path, int edgeCount) {
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

    public static PathResult findShortestPath(Map<String, List<String>> adjList, String start, String end) {
        if (adjList == null || start == null || end == null || !adjList.containsKey(start) || !adjList.containsKey(end)) {
            return new PathResult(Collections.emptyList(), -1);
        }

        if (start.equals(end)) {
            return new PathResult(List.of(start), 0);
        }

        Queue<String> queue = new ArrayDeque<>();
        Map<String, String> parentMap = new HashMap<>();
        Set<String> visited = new HashSet<>();

        queue.offer(start);
        visited.add(start);

        boolean found = false;

        while (!queue.isEmpty()) {
            String current = queue.poll();

            if (current.equals(end)) {
                found = true;
                break;
            }

            List<String> neighbors = adjList.getOrDefault(current, Collections.emptyList());
            for (String neighbor : neighbors) {
                if (neighbor != null && !visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parentMap.put(neighbor, current);
                    queue.offer(neighbor);
                }
            }
        }

        if (!found) {
            return new PathResult(Collections.emptyList(), -1);
        }

        List<String> path = new ArrayList<>();
        String curr = end;
        while (curr != null) {
            path.add(curr);
            curr = parentMap.get(curr);
        }
        Collections.reverse(path);

        return new PathResult(path, path.size() - 1);
    }

    public static void main(String[] args) {
        System.out.println("=== Test Case 1: Standard Metro Path ===");
        Map<String, List<String>> metro = new HashMap<>();
        metro.put("A", List.of("B", "C"));
        metro.put("B", List.of("A", "D", "E"));
        metro.put("C", List.of("A", "F"));
        metro.put("D", List.of("B"));
        metro.put("E", List.of("B", "F"));
        metro.put("F", List.of("C", "E"));
        metro.put("G", List.of()); // Disconnected

        System.out.println(findShortestPath(metro, "A", "F"));

        System.out.println("\n=== Test Case 2: Boundary Cases ===");
        System.out.println("No Path: " + findShortestPath(metro, "A", "G"));
        System.out.println("Same Station: " + findShortestPath(metro, "A", "A"));
        System.out.println("Null Graph: " + findShortestPath(null, "A", "B"));
        System.out.println("Non-existent Station: " + findShortestPath(metro, "X", "Y"));
    }
}