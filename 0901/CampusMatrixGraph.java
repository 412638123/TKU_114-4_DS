import java.util.ArrayList;
import java.util.List;

public class CampusMatrixGraph {

    private boolean[][] matrix;
    private int numVertices;
    private int edgeCount;

    public CampusMatrixGraph(int numVertices) {
        if (numVertices <= 0) {
            numVertices = 1;
        }
        this.numVertices = numVertices;
        this.matrix = new boolean[numVertices][numVertices];
        this.edgeCount = 0;
    }

    private boolean isValidVertex(int v) {
        return v >= 0 && v < numVertices;
    }

    public boolean addEdge(int u, int v) {
        if (!isValidVertex(u) || !isValidVertex(v) || u == v) {
            return false;
        }
        if (!matrix[u][v]) {
            matrix[u][v] = true;
            matrix[v][u] = true;
            edgeCount++;
            return true;
        }
        return false;
    }

    public boolean removeEdge(int u, int v) {
        if (!isValidVertex(u) || !isValidVertex(v)) {
            return false;
        }
        if (matrix[u][v]) {
            matrix[u][v] = false;
            matrix[v][u] = false;
            edgeCount--;
            return true;
        }
        return false;
    }

    public int getDegree(int v) {
        if (!isValidVertex(v)) {
            return -1;
        }
        int degree = 0;
        for (int i = 0; i < numVertices; i++) {
            if (matrix[v][i]) {
                degree++;
            }
        }
        return degree;
    }

    public List<Integer> getNeighbors(int v) {
        List<Integer> neighbors = new ArrayList<>();
        if (!isValidVertex(v)) {
            return neighbors;
        }
        for (int i = 0; i < numVertices; i++) {
            if (matrix[v][i]) {
                neighbors.add(i);
            }
        }
        return neighbors;
    }

    public int getEdgeCount() {
        return edgeCount;
    }

    public int getNumVertices() {
        return numVertices;
    }

    public static void main(String[] args) {
        CampusMatrixGraph graph = new CampusMatrixGraph(5);

        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);

        System.out.println("Add duplicate edge (0, 1): " + graph.addEdge(0, 1));
        System.out.println("Total Edge Count: " + graph.getEdgeCount());

        System.out.println("Degree of Vertex 0: " + graph.getDegree(0));
        System.out.println("Neighbors of Vertex 3: " + graph.getNeighbors(3));

        System.out.println("\nRemoving edge (0, 1)...");
        graph.removeEdge(0, 1);
        System.out.println("Total Edge Count: " + graph.getEdgeCount());
        System.out.println("Neighbors of Vertex 0: " + graph.getNeighbors(0));
    }
}