package final_exam;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Q06_AdjacencyMatrixGraph {

    private final List<String> vertexList;
    private final Map<String, Integer> vertexIndexMap;
    private final boolean[][] matrix;

    public Q06_AdjacencyMatrixGraph(List<String> vertices) {
        if (vertices == null) {
            this.vertexList = new ArrayList<>();
        } else {
            this.vertexList = new ArrayList<>(vertices);
        }

        this.vertexIndexMap = new HashMap<>();
        for (int i = 0; i < this.vertexList.size(); i++) {
            this.vertexIndexMap.put(this.vertexList.get(i), i);
        }

        int n = this.vertexList.size();
        this.matrix = new boolean[n][n];
    }

    public boolean addEdge(String first, String second) {
        if (first == null || second == null || first.equals(second)) {
            return false;
        }

        Integer u = vertexIndexMap.get(first);
        Integer v = vertexIndexMap.get(second);

        if (u == null || v == null) {
            return false;
        }

        if (matrix[u][v]) {
            return false;
        }

        matrix[u][v] = true;
        matrix[v][u] = true;
        return true;
    }

    public boolean removeEdge(String first, String second) {
        if (first == null || second == null) {
            return false;
        }

        Integer u = vertexIndexMap.get(first);
        Integer v = vertexIndexMap.get(second);

        if (u == null || v == null) {
            return false;
        }

        if (!matrix[u][v]) {
            return false;
        }

        matrix[u][v] = false;
        matrix[v][u] = false;
        return true;
    }

    public boolean hasEdge(String first, String second) {
        if (first == null || second == null) {
            return false;
        }

        Integer u = vertexIndexMap.get(first);
        Integer v = vertexIndexMap.get(second);

        if (u == null || v == null) {
            return false;
        }

        return matrix[u][v];
    }

    public int degree(String vertex) {
        if (vertex == null) {
            return 0;
        }

        Integer u = vertexIndexMap.get(vertex);
        if (u == null) {
            return 0;
        }

        int count = 0;
        for (int j = 0; j < matrix[u].length; j++) {
            if (matrix[u][j]) {
                count++;
            }
        }
        return count;
    }

    public List<String> neighbors(String vertex) {
        if (vertex == null) {
            return Collections.emptyList();
        }

        Integer u = vertexIndexMap.get(vertex);
        if (u == null) {
            return Collections.emptyList();
        }

        List<String> result = new ArrayList<>();
        for (int j = 0; j < matrix[u].length; j++) {
            if (matrix[u][j]) {
                result.add(vertexList.get(j));
            }
        }
        return result;
    }
}