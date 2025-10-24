package qangan.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Adjacency list graph implementation.
 */
public class AdjListGraph implements Graph {
    private final Set<Integer> vertices = new HashSet<>();
    private final Map<Integer, Set<Integer>> adjacency = new HashMap<>();

    @Override
    public void addVertex(int v) {
        if (vertices.add(v)) {
            adjacency.put(v, new HashSet<>());
        }
    }

    @Override
    public void removeVertex(int v) throws IllegalArgumentException {
        if (!vertices.contains(v)) {
            throw new IllegalArgumentException("Vertex does not exist: " + v);
        }
        vertices.remove(v);
        adjacency.remove(v);
        for (Set<Integer> nbrs : adjacency.values()) {
            nbrs.remove(v);
        }
    }

    @Override
    public void addEdge(int u, int v) {
        List<Integer> missing = new ArrayList<>();
        if (!vertices.contains(u)) {
            missing.add(u);
        }
        if (!vertices.contains(v)) {
            missing.add(v);
        }
        if (!missing.isEmpty()) {
            throw new IllegalArgumentException("Vertices must exist: missing " + missing);
        }
        if (adjacency.get(u).contains(v)) {
            return;
        }
        adjacency.get(u).add(v);
    }

    @Override
    public void removeEdge(int u, int v) {
        List<Integer> missing = new ArrayList<>();
        if (!vertices.contains(u)) {
            missing.add(u);
        }
        if (!vertices.contains(v)) {
            missing.add(v);
        }
        if (!missing.isEmpty()) {
            throw new IllegalArgumentException("Vertices must exist: missing " + missing);
        }
        adjacency.get(u).remove(v);
    }

    @Override
    public List<Integer> getNeighbors(int v) {
        if (!vertices.contains(v)) {
            return Collections.emptyList();
        }
        return new ArrayList<>(adjacency.get(v));
    }

    @Override
    public Set<Integer> getVertices() {
        return vertices;
    }

    @Override
    public String toString() {
        List<Integer> vs = new ArrayList<>(vertices);
        Collections.sort(vs);

        List<int[]> edgesList = new ArrayList<>();
        for (int u : vs) {
            for (int v : adjacency.getOrDefault(u, Collections.emptySet())) {
                edgesList.add(new int[] {u, v});
            }
        }
        edgesList.sort(
                (a, b) -> {
                    if (a[0] != b[0]) {
                        return Integer.compare(a[0], b[0]);
                    }
                    return Integer.compare(a[1], b[1]);
                });

        StringBuilder sb = new StringBuilder();
        sb.append(vs.size()).append('\n');
        for (int i = 0; i < vs.size(); i++) {
            if (i > 0) {
                sb.append(' ');
            }
            sb.append(vs.get(i));
        }
        sb.append('\n');
        sb.append(edgesList.size()).append('\n');
        for (int[] e : edgesList) {
            sb.append(e[0]).append(' ').append(e[1]).append('\n');
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Graph other)) {
            return false;
        }
        Set<Integer> thisV = this.vertices;
        Set<Integer> otherV = other.getVertices();
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
}
