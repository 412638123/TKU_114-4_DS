package final_exam;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Q10_UnweightedShortestPath {

    public static List<String> shortestPath(Map<String, List<String>> graph, String start, String target) {
        if (graph == null || start == null || target == null) {
            return Collections.emptyList();
        }

        if (!graph.containsKey(start) || !graph.containsKey(target)) {
            return Collections.emptyList();
        }

        if (start.equals(target)) {
            List<String> path = new ArrayList<>();
            path.add(start);
            return path;
        }

        Map<String, String> parentMap = new HashMap<>();
        Queue<String> queue = new ArrayDeque<>();

        parentMap.put(start, null);
        queue.add(start);

        boolean found = false;

        while (!queue.isEmpty()) {
            String current = queue.poll();

            if (current.equals(target)) {
                found = true;
                break;
            }

            List<String> neighbors = graph.get(current);
            if (neighbors != null) {
                for (String neighbor : neighbors) {
                    if (neighbor != null && !parentMap.containsKey(neighbor)) {
                        parentMap.put(neighbor, current);
                        queue.add(neighbor);
                    }
                }
            }
        }

        if (!found) {
            return Collections.emptyList();
        }

        List<String> path = new ArrayList<>();
        String curr = target;
        while (curr != null) {
            path.add(curr);
            curr = parentMap.get(curr);
        }

        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {
        Map<String, List<String>> graph = new HashMap<>();
        graph.put("A", List.of("B", "C"));
        graph.put("B", List.of("D"));
        graph.put("C", List.of("D"));
        graph.put("D", Collections.emptyList());

        System.out.println("Q10 最短路徑: " + shortestPath(graph, "A", "D"));
    }
}