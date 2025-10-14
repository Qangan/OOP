package qangan.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AdjMatrixGraph implements Graph {
    private List<Integer> vertices = new ArrayList<>();
    private int[][] matrix = new int[0][0];

    @Override
    public void addVertex(int v) {
        if (vertices.contains(v)) return;
        vertices.add(v);
        int n = vertices.size();
        int[][] newMatrix = new int[n][n];
        for (int i = 0; i < n - 1; i++)
            System.arraycopy(matrix[i], 0, newMatrix[i], 0, n - 1);
        matrix = newMatrix;
    }

    @Override
    public void removeVertex(int v) {
        int idx = vertices.indexOf(v);
        if (idx == -1) return;
        vertices.remove(idx);
        int n = vertices.size();
        int[][] newMatrix = new int[n][n];
        for (int i = 0, ni = 0; i < n + 1; i++) {
            if (i == idx) continue;
            for (int j = 0, nj = 0; j < n + 1; j++) {
                if (j == idx) continue;
                newMatrix[ni][nj++] = matrix[i][j];
            }
            ni++;
        }
        matrix = newMatrix;
    }

    @Override
    public void addEdge(int u, int v) {
        int i = vertices.indexOf(u);
        int j = vertices.indexOf(v);
        if (i == -1 || j == -1) throw new IllegalArgumentException("Vertices must exist");
        matrix[i][j] = 1;
    }

    @Override
    public void removeEdge(int u, int v) {
        int i = vertices.indexOf(u);
        int j = vertices.indexOf(v);
        if (i == -1 || j == -1) return;
        matrix[i][j] = 0;
    }

    @Override
    public List<Integer> getNeighbors(int v) {
        int i = vertices.indexOf(v);
        if (i == -1) return Collections.emptyList();
        List<Integer> neighbors = new ArrayList<>();
        for (int j = 0; j < vertices.size(); j++) {
            if (matrix[i][j] == 1) neighbors.add(vertices.get(j));
        }
        return neighbors;
    }

    @Override
    public List<Integer> getVertices() {
        return new ArrayList<>(vertices);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int[] row : matrix) {
            for (int x : row) sb.append(x).append(" ");
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Graph)){ 
            return false;
        }
        Graph other = (Graph) o;
        Set<Integer> thisV = new HashSet<>(getVertices());
        Set<Integer> otherV = new HashSet<>(other.getVertices());
        if (!thisV.equals(otherV)){ 
            return false;
        }
        for (int v : thisV) {
            Set<Integer> thisN = new HashSet<>(getNeighbors(v));
            Set<Integer> otherN = new HashSet<>(other.getNeighbors(v));
            if (!thisN.equals(otherN)) {
                return false;
            }
        }
        return true;
    }

}