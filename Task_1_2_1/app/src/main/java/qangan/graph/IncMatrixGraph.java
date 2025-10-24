package qangan.graph;

import java.util.*;

/**
 * Incidence matrix graph implementation.
 */
public class IncMatrixGraph implements Graph {

    private final Set<Integer> vertices = new HashSet<>();
    private final Map<Integer, Integer> idToIndex = new HashMap<>();
    private final List<Integer> indexToId = new ArrayList<>();
    private final List<List<Integer>> matrix = new ArrayList<>();
    private final List<int[]> edges = new ArrayList<>();

    @Override
    public void addVertex(int v) {
        if (vertices.contains(v)) {
            return;
        }
        vertices.add(v);
        int idx = indexToId.size();
        idToIndex.put(v, idx);
        indexToId.add(v);
        for (List<Integer> row : matrix) {
            row.add(0);
        }
    }

    @Override
    public void removeVertex(int v) throws IllegalArgumentException {
        Integer idxObj = idToIndex.get(v);
        if (idxObj == null) {
            throw new IllegalArgumentException("Vertex does not exist: " + v);
        }
        int idx = idxObj;
        vertices.remove(v);
        idToIndex.remove(v);
        indexToId.remove(idx);
        for (List<Integer> row : matrix) {
            row.remove(idx);
        }
        for (int i = edges.size() - 1; i >= 0; i--) {
            int[] e = edges.get(i);
            if (e[0] == v || e[1] == v) {
                edges.remove(i);
                matrix.remove(i);
            }
        }
        for (int i = idx; i < indexToId.size(); i++) {
            idToIndex.put(indexToId.get(i), i);
        }
    }

    @Override
    public void addEdge(int u, int v) {
        Integer ui = idToIndex.get(u);
        Integer vi = idToIndex.get(v);
        List<Integer> missing = new ArrayList<>();
        if (ui == null) {
            missing.add(u);
        }
        if (vi == null) {
            missing.add(v);
        }
        if (!missing.isEmpty()) {
            throw new IllegalArgumentException("Vertices must exist: missing " + missing);
        }

        int[] newEdge = new int[] {u, v};

        for (int[] edge : edges) {
            if (Arrays.equals(newEdge, edge)) {
                return;
            }
        }

        edges.add(newEdge);
        List<Integer> row = new ArrayList<>(Collections.nCopies(indexToId.size(), 0));
        row.set(ui, 1);
        row.set(vi, -1);
        matrix.add(row);
    }

    @Override
    public void removeEdge(int u, int v) {
        Integer ui = idToIndex.get(u);
        Integer vi = idToIndex.get(v);
        List<Integer> missing = new ArrayList<>();
        if (ui == null) {
            missing.add(u);
        }
        if (vi == null) {
            missing.add(v);
        }
        if (!missing.isEmpty()) {
            throw new IllegalArgumentException("Vertices must exist: missing " + missing);
        }
        int edgeIdx = -1;
        for (int i = 0; i < edges.size(); i++) {
            int[] e = edges.get(i);
            if (e[0] == u && e[1] == v) {
                edgeIdx = i;
                break;
            }
        }
        if (edgeIdx == -1) {
            return;
        }
        edges.remove(edgeIdx);
        matrix.remove(edgeIdx);
    }

    @Override
    public List<Integer> getNeighbors(int v) {
        Integer vi = idToIndex.get(v);
        if (vi == null) {
            return Collections.emptyList();
        }
        List<Integer> out = new ArrayList<>();
        for (List<Integer> row : matrix) {
            if (row.get(vi) == 1) {
                int toIdx = -1;
                for (int c = 0; c < row.size(); c++) {
                    if (row.get(c) == -1) {
                        toIdx = c;
                        break;
                    }
                }
                if (toIdx != -1) {
                    out.add(indexToId.get(toIdx));
                }
            }
        }
        return out;
    }

    @Override
    public Set<Integer> getVertices() {
        return vertices;
    }

    @Override
    public String toString() {
        List<Integer> vs = new ArrayList<>(vertices);
        Collections.sort(vs);

        List<int[]> edgesList = new ArrayList<>(edges);
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

}
