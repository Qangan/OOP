package qangan.graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IncMatrixGraph implements Graph {
    private final List<Integer> vertices = new ArrayList<>();
    private final List<List<Integer>> matrix = new ArrayList<>();
    private final List<int[]> edges = new ArrayList<>();

    @Override
    public void addVertex(int v) {
        if (vertices.contains(v)) return;
        vertices.add(v);
        for (List<Integer> row : matrix) {
            row.add(0);
        }
    }

    @Override
    public void removeVertex(int v) throws IllegalArgumentException {
        int idx = vertices.indexOf(v);
        if (idx == -1) throw new IllegalArgumentException("Vertex does not exist");
        vertices.remove(idx);

        for (List<Integer> row : matrix) {
            row.remove(idx);
        }

        for (int i = matrix.size() - 1; i >= 0; i--) {
            if (edges.get(i)[0] == v || edges.get(i)[1] == v) {
                matrix.remove(i);
                edges.remove(i);
            }
        }
    }

    @Override
    public void addEdge(int u, int v) {
        int uIdx = vertices.indexOf(u);
        int vIdx = vertices.indexOf(v);
        if (uIdx == -1 || vIdx == -1) throw new IllegalArgumentException("Vertices must exist");
        edges.add(new int[] {u, v});
        List<Integer> row = new ArrayList<>(Collections.nCopies(vertices.size(), 0));
        row.set(uIdx, 1);
        row.set(vIdx, -1);
        matrix.add(row);
    }

    @Override
    public void removeEdge(int u, int v) throws IllegalArgumentException {
        int edgeIdx = -1;
        for (int i = 0; i < edges.size(); i++) {
            int[] edge = edges.get(i);
            if ((edge[0] == u && edge[1] == v) || (edge[0] == v && edge[1] == u)) {
                edgeIdx = i;
                break;
            }
        }
        if (edgeIdx == -1) return;

        edges.remove(edgeIdx);
        for (List<Integer> row : matrix) {
            row.remove(edgeIdx);
        }
    }

    @Override
    public List<Integer> getNeighbors(int v) {
        int vIdx = vertices.indexOf(v);
        if (vIdx == -1) return Collections.emptyList();
        List<Integer> neighbors = new ArrayList<>();
        for (int i = 0; i < matrix.size(); i++) {
            if (matrix.get(i).get(vIdx) == 1) {
                int toIdx = matrix.get(i).indexOf(-1);
                if (toIdx != -1) {
                    neighbors.add(vertices.get(toIdx));
                }
            }
        }
        return neighbors;
    }

    @Override
    public List<Integer> getVertices() {
        return new ArrayList<>(vertices);
    }

    @Override
    public String toString() {
        StringBuilder sb =
                new StringBuilder(
                        "Vertices: " + vertices + "\nEdges: " + edges.size() + "\nMatrix:\n");
        for (List<Integer> row : matrix) {
            for (Integer val : row) {
                sb.append(val).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Graph other)) {
            return false;
        }
        Set<Integer> thisV = new HashSet<>(getVertices());
        Set<Integer> otherV = new HashSet<>(other.getVertices());
        if (!thisV.equals(otherV)) {
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

    public void readFromFile(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            int numVertices = Integer.parseInt(br.readLine().trim());

            String[] vertexIds = br.readLine().trim().split("\\s+");
            for (int i = 0; i < numVertices; i++) {
                addVertex(Integer.parseInt(vertexIds[i]));
            }

            int numEdges = Integer.parseInt(br.readLine().trim());

            for (int i = 0; i < numEdges; i++) {
                String[] edge = br.readLine().trim().split("\\s+");
                int from = Integer.parseInt(edge[0]);
                int to = Integer.parseInt(edge[1]);
                addEdge(from, to);
            }
        }
    }
}
