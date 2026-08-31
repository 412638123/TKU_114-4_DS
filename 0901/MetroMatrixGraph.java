import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MetroMatrixGraph {

    private String[] stationNames;
    private Map<String, Integer> stationIndexMap;
    private boolean[][] matrix;
    private int edgeCount;

    public MetroMatrixGraph(String[] stationNames) {
        if (stationNames == null || stationNames.length == 0) {
            this.stationNames = new String[0];
            this.matrix = new boolean[0][0];
            this.stationIndexMap = new HashMap<>();
        } else {
            this.stationNames = Arrays.copyOf(stationNames, stationNames.length);
            int n = stationNames.length;
            this.matrix = new boolean[n][n];
            this.stationIndexMap = new HashMap<>();
            for (int i = 0; i < n; i++) {
                stationIndexMap.put(stationNames[i], i);
            }
        }
        this.edgeCount = 0;
    }

    public boolean addConnection(String station1, String station2) {
        if (!stationIndexMap.containsKey(station1) || !stationIndexMap.containsKey(station2) || station1.equals(station2)) {
            return false;
        }
        int u = stationIndexMap.get(station1);
        int v = stationIndexMap.get(station2);

        if (!matrix[u][v]) {
            matrix[u][v] = true;
            matrix[v][u] = true;
            edgeCount++;
            return true;
        }
        return false;
    }

    public List<String> getNeighbors(String station) {
        List<String> neighbors = new ArrayList<>();
        if (!stationIndexMap.containsKey(station)) {
            return neighbors;
        }
        int u = stationIndexMap.get(station);
        for (int v = 0; v < stationNames.length; v++) {
            if (matrix[u][v]) {
                neighbors.add(stationNames[v]);
            }
        }
        return neighbors;
    }

    public int getDegree(String station) {
        if (!stationIndexMap.containsKey(station)) {
            return -1;
        }
        return getNeighbors(station).size();
    }

    public int getEdgeCount() {
        return edgeCount;
    }

    public void printMatrixReport() {
        System.out.println("=== Metro Matrix Graph Report ===");
        System.out.println("Total Stations (Vertices): " + stationNames.length);
        System.out.println("Total Connections (Edges): " + edgeCount);

        System.out.println("\nAdjacency Matrix:");
        System.out.print("      ");
        for (String name : stationNames) {
            System.out.printf("%-8s", name);
        }
        System.out.println();

        for (int i = 0; i < stationNames.length; i++) {
            System.out.printf("%-6s", stationNames[i]);
            for (int j = 0; j < stationNames.length; j++) {
                System.out.printf("%-8d", matrix[i][j] ? 1 : 0);
            }
            System.out.println();
        }

        System.out.println("\nStation Degrees & Neighbors:");
        for (String station : stationNames) {
            System.out.println(station + " (Degree " + getDegree(station) + "): " + getNeighbors(station));
        }
    }

    public static void main(String[] args) {
        String[] stations = {"MainStation", "Park", "Hospital", "Airport", "University"};
        MetroMatrixGraph metro = new MetroMatrixGraph(stations);

        metro.addConnection("MainStation", "Park");
        metro.addConnection("MainStation", "Hospital");
        metro.addConnection("Park", "Airport");
        metro.addConnection("Hospital", "University");
        metro.addConnection("Airport", "University");

        metro.printMatrixReport();
    }
}