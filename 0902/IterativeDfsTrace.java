import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class IterativeDfsTrace {

    public static List<Integer> runIterativeDfsTrace(Map<Integer, List<Integer>> adjList, Integer start) {
        List<Integer> visitOrder = new ArrayList<>();
        if (adjList == null || start == null || !adjList.containsKey(start)) {
            System.out.println("[Trace] Empty, null, or invalid start node provided.");
            return visitOrder;
        }

        Deque<Integer> stack = new ArrayDeque<>();
        Set<Integer> visited = new LinkedHashSet<>();

        stack.push(start);
        System.out.println("[PUSH " + start + "] Stack: " + stack + " | Visited: " + visited);

        while (!stack.isEmpty()) {
            int current = stack.pop();
            System.out.println("[POP  " + current + "] Stack: " + stack + " | Visited: " + visited);

            if (!visited.contains(current)) {
                visited.add(current);
                visitOrder.add(current);
                System.out.println("[VISIT " + current + "] Visited Updated: " + visited);

                List<Integer> neighbors = new ArrayList<>(adjList.getOrDefault(current, Collections.emptyList()));
                Collections.reverse(neighbors);

                for (Integer neighbor : neighbors) {
                    if (neighbor != null && !visited.contains(neighbor)) {
                        stack.push(neighbor);
                        System.out.println("  [PUSH " + neighbor + "] Stack: " + stack + " | Visited: " + visited);
                    }
                }
            }
        }

        return visitOrder;
    }

    public static void main(String[] args) {
        System.out.println("=== Test Case 1: Standard Graph Trace ===");
        Map<Integer, List<Integer>> adjList = new HashMap<>();
        adjList.put(1, List.of(2, 3));
        adjList.put(2, List.of(1, 4));
        adjList.put(3, List.of(1, 5));
        adjList.put(4, List.of(2));
        adjList.put(5, List.of(3));

        List<Integer> order = runIterativeDfsTrace(adjList, 1);
        System.out.println("Final Order: " + order);

        System.out.println("\n=== Test Case 2: Boundary & Invalid Cases ===");
        runIterativeDfsTrace(null, 1);
        runIterativeDfsTrace(adjList, null);
        runIterativeDfsTrace(adjList, 999);
    }
}