package final_exam;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Q09_DfsPathSearch {

    public static List<String> dfs(Map<String, List<String>> graph, String start) {
        if (graph == null || start == null || !graph.containsKey(start)) {
            return Collections.emptyList();
        }

        List<String> result = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        dfsHelper(graph, start, visited, result);
        return result;
    }

    private static void dfsHelper(Map<String, List<String>> graph, String current, Set<String> visited, List<String> result) {
        visited.add(current);
        result.add(current);

        List<String> neighbors = graph.get(current);
        if (neighbors != null) {
            for (String neighbor : neighbors) {
                if (neighbor != null && !visited.contains(neighbor)) {
                    dfsHelper(graph, neighbor, visited, result);
                }
            }
        }
    }

    public static boolean reachable(Map<String, List<String>> graph, String start, String target) {
        if (graph == null || start == null || target == null) {
            return false;
        }

        if (!graph.containsKey(start) || !graph.containsKey(target)) {
            return false;
        }

        Set<String> visited = new HashSet<>();
        return reachableHelper(graph, start, target, visited);
    }

    private static boolean reachableHelper(Map<String, List<String>> graph, String current, String target, Set<String> visited) {
        if (current.equals(target)) {
            return true;
        }

        visited.add(current);

        List<String> neighbors = graph.get(current);
        if (neighbors != null) {
            for (String neighbor : neighbors) {
                if (neighbor != null && !visited.contains(neighbor)) {
                    if (reachableHelper(graph, neighbor, target, visited)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static void main(String[] args) {
        Map<String, List<String>> graph = new HashMap<>();
        graph.put("A", List.of("B", "C"));
        graph.put("B", List.of("D"));
        graph.put("C", Collections.emptyList());
        graph.put("D", Collections.emptyList());

        System.out.println("Q09 DFS 走訪結果: " + dfs(graph, "A"));
        System.out.println("Q09 A 是否可達 D: " + reachable(graph, "A", "D"));
    }
}