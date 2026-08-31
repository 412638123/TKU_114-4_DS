import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class LogisticsWeightedGraph {

    private Set<String> vertices;
    private Map<String, Map<String, Double>> adjList;

    public LogisticsWeightedGraph() {
        vertices = new HashSet<>();
        adjList = new HashMap<>();
    }

    public boolean addVertex(String vertex) {
        if (vertex == null || vertices.contains(vertex)) {
            return false;
        }
        vertices.add(vertex);
        adjList.put(vertex, new HashMap<>());
        return true;
    }

    public boolean addOrUpdateEdge(String from, String to, double weight) {
        if (from == null || to == null || weight < 0) {
            return false;
        }
        if (!vertices.contains(from) || !vertices.contains(to)) {
            return false;
        }
        adjList.get(from).put(to, weight);
        return true;
    }

    public boolean removeEdge(String from, String to) {
        if (from == null || to == null || !vertices.contains(from) || !vertices.contains(to)) {
            return false;
        }
        if (adjList.get(from).containsKey(to)) {
            adjList.get(from).remove(to);
            return true;
        }
        return false;
    }

    public double getCost(String from, String to) {
        if (from == null || to == null || !vertices.contains(from) || !vertices.contains(to)) {
            return -1.0;
        }
        return adjList.get(from).getOrDefault(to, -1.0);
    }

    public Map<String, Double> getOutgoingEdges(String from) {
        if (from == null || !vertices.contains(from)) {
            return new HashMap<>();
        }
        return new HashMap<>(adjList.get(from));
    }

    public void printLogisticsReport() {
        System.out.println("=== Logistics Weighted Graph Report ===");
        List<String> sortedVertices = new ArrayList<>(vertices);
        Collections.sort(sortedVertices);

        for (String v : sortedVertices) {
            System.out.println("Hub: " + v);
            Map<String, Double> edges = adjList.get(v);
            if (edges.isEmpty()) {
                System.out.println("  No outgoing routes.");
            } else {
                Set<String> sortedTargets = new TreeSet<>(edges.keySet());
                for (String target : sortedTargets) {
                    System.out.println("  -> " + target + " (Cost: " + edges.get(target) + ")");
                }
            }
        }
    }

    public static void main(String[] args) {
        LogisticsWeightedGraph graph = new LogisticsWeightedGraph();

        graph.addVertex("Taipei");
        graph.addVertex("Taoyuan");
        graph.addVertex("Taichung");
        graph.addVertex("Kaohsiung");

        graph.addOrUpdateEdge("Taipei", "Taoyuan", 150.0);
        graph.addOrUpdateEdge("Taoyuan", "Taichung", 300.0);
        graph.addOrUpdateEdge("Taichung", "Kaohsiung", 450.0);
        graph.addOrUpdateEdge("Taipei", "Taichung", 500.0);

        System.out.println("Try adding negative weight (-50): " + graph.addOrUpdateEdge("Taipei", "Taoyuan", -50.0));
        System.out.println("Try adding edge to non-existent vertex: " + graph.addOrUpdateEdge("Taipei", "Tainan", 200.0));

        graph.printLogisticsReport();

        System.out.println("\nUpdate cost Taipei -> Taichung to 420.0...");
        graph.addOrUpdateEdge("Taipei", "Taichung", 420.0);
        System.out.println("New cost Taipei -> Taichung: " + graph.getCost("Taipei", "Taichung"));

        System.out.println("\nRemoving route Taoyuan -> Taichung...");
        graph.removeEdge("Taoyuan", "Taichung");
        System.out.println("Cost Taoyuan -> Taichung after removal: " + graph.getCost("Taoyuan", "Taichung"));
    }
}