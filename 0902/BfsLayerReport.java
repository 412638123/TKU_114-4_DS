import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class BfsLayerReport {

    public static Map<Integer, Integer> getBfsDistances(Map<Integer, List<Integer>> adjList, Integer start) {
        Map<Integer, Integer> distances = new HashMap<>();
        if (adjList == null || start == null || !adjList.containsKey(start)) {
            return distances;
        }

        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(start);
        distances.put(start, 0);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            int currentDist = distances.get(current);

            List<Integer> neighbors = adjList.getOrDefault(current, Collections.emptyList());
            for (Integer neighbor : neighbors) {
                if (neighbor != null && !distances.containsKey(neighbor)) {
                    distances.put(neighbor, currentDist + 1);
                    queue.offer(neighbor);
                }
            }
        }

        return distances;
    }

    public static void main(String[] args) {
        System.out.println("=== Test Case 1: Standard Graph ===");
        Map<Integer, List<Integer>> adjList = new HashMap<>();
        adjList.put(0, List.of(1, 2));
        adjList.put(1, List.of(0, 3, 4));
        adjList.put(2, List.of(0, 5));
        adjList.put(3, List.of(1));
        adjList.put(4, List.of(1, 5));
        adjList.put(5, List.of(2, 4));

        Map<Integer, Integer> dist0 = getBfsDistances(adjList, 0);
        System.out.println("Distances from start 0: " + dist0);

        System.out.println("\n=== Test Case 2: Boundary & Invalid Cases ===");
        System.out.println("Null graph: " + getBfsDistances(null, 0));
        System.out.println("Null start: " + getBfsDistances(adjList, null));
        System.out.println("Non-existent start: " + getBfsDistances(adjList, 99));
        System.out.println("Disconnected node: " + getBfsDistances(Map.of(1, List.of()), 1));
    }
}