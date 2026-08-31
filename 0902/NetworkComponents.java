import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

public class NetworkComponents {

    public static class ComponentReport {
        private List<List<Integer>> components;
        private int componentCount;
        private List<Integer> largestComponent;

        public ComponentReport(List<List<Integer>> components, int componentCount, List<Integer> largestComponent) {
            this.components = components;
            this.componentCount = componentCount;
            this.largestComponent = largestComponent;
        }

        public List<List<Integer>> getComponents() { return components; }
        public int getComponentCount() { return componentCount; }
        public List<Integer> getLargestComponent() { return largestComponent; }

        @Override
        public String toString() {
            return "Component Count: " + componentCount +
                   "\nAll Components: " + components +
                   "\nLargest Component: " + largestComponent;
        }
    }

    public static ComponentReport analyzeComponents(Map<Integer, List<Integer>> adjList) {
        if (adjList == null || adjList.isEmpty()) {
            return new ComponentReport(Collections.emptyList(), 0, Collections.emptyList());
        }

        Set<Integer> visited = new HashSet<>();
        List<List<Integer>> allComponents = new ArrayList<>();
        List<Integer> largestComponent = Collections.emptyList();

        List<Integer> allNodes = new ArrayList<>(adjList.keySet());
        Collections.sort(allNodes);

        for (Integer node : allNodes) {
            if (!visited.contains(node)) {
                List<Integer> currentComponent = new ArrayList<>();
                Queue<Integer> queue = new ArrayDeque<>();

                queue.offer(node);
                visited.add(node);

                while (!queue.isEmpty()) {
                    int curr = queue.poll();
                    currentComponent.add(curr);

                    List<Integer> neighbors = adjList.getOrDefault(curr, Collections.emptyList());
                    for (Integer neighbor : neighbors) {
                        if (neighbor != null && !visited.contains(neighbor)) {
                            visited.add(neighbor);
                            queue.offer(neighbor);
                        }
                    }
                }

                Collections.sort(currentComponent);
                allComponents.add(currentComponent);

                if (currentComponent.size() > largestComponent.size()) {
                    largestComponent = currentComponent;
                }
            }
        }

        return new ComponentReport(allComponents, allComponents.size(), largestComponent);
    }

    public static void main(String[] args) {
        System.out.println("=== Test Case 1: Multiple Connected Components ===");
        Map<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(1, List.of(2));
        graph.put(2, List.of(1, 3));
        graph.put(3, List.of(2));

        graph.put(4, List.of(5));
        graph.put(5, List.of(4));

        graph.put(6, List.of());

        ComponentReport report = analyzeComponents(graph);
        System.out.println(report);

        System.out.println("\n=== Test Case 2: Boundary Cases ===");
        System.out.println("Null Input: " + analyzeComponents(null));
        System.out.println("Empty Graph: " + analyzeComponents(Collections.emptyMap()));
    }
}