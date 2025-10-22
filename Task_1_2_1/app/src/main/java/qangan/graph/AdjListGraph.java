package qangan.graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AdjListGraph implements Graph {
    private final List<Integer> vertices = new ArrayList<>();
    private final Map<Integer, List<Integer>> adjacency = new HashMap<>();

    @Override
    public void addVertex(int v) {
        if (vertices.contains(v)) return;
        vertices.add(v);
        adjacency.put(v, new ArrayList<>());
    }

    @Override
    public void removeVertex(int v) throws IllegalArgumentException {
        if (!vertices.contains(v)) throw new IllegalArgumentException("Vertex does not exist");
        vertices.remove((Integer) v);
        adjacency.remove(v);

        for (List<Integer> neighbors : adjacency.values()) {
            neighbors.remove((Integer) v);
        }
    }

    @Override
    public void addEdge(int u, int v) {
        if (!vertices.contains(u) || !vertices.contains(v)) {
            throw new IllegalArgumentException("Vertices must exist");
        }
        adjacency.get(u).add(v);
    }

    @Override
    public void removeEdge(int u, int v) {
        if (!vertices.contains(u) || !vertices.contains(v)) {
            return;
        }
        adjacency.get(u).remove((Integer) v);
    }

    @Override
    public List<Integer> getNeighbors(int v) {
        if (!vertices.contains(v)) return Collections.emptyList();
        return new ArrayList<>(adjacency.get(v));
    }

    @Override
    public List<Integer> getVertices() {
        return new ArrayList<>(vertices);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int v : vertices) {
            sb.append(v).append(": ").append(adjacency.get(v)).append("\n");
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
