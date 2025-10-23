package qangan.graph;

import java.util.*;

/**
 * Adjacency matrix graph implementation.
 */
public class AdjMatrixGraph implements Graph {
    private final Set<Integer> vertices = new HashSet<>();
    private final Map<Integer, Integer> idToIndex = new HashMap<>();
    private final List<Integer> indexToId = new ArrayList<>();
    private final List<List<Integer>> matrix = new ArrayList<>();

    @Override
    public void addVertex(int v) {
        if (vertices.contains(v)) {
            return;
        }
        vertices.add(v);
        int idx = indexToId.size();
        idToIndex.put(v, idx);
        indexToId.add(v);

        List<Integer> row = new ArrayList<>(Collections.nCopies(idx + 1, 0));
        matrix.add(row);
        for (int i = 0; i < idx; i++) {
            matrix.get(i).add(0);
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
        matrix.remove(idx);
        for (List<Integer> row : matrix) {
            row.remove(idx);
        }
        for (int i = idx; i < indexToId.size(); i++) {
            idToIndex.put(indexToId.get(i), i);
        }
    }

    @Override
    public void addEdge(int u, int v) {
        Integer i = idToIndex.get(u);
        Integer j = idToIndex.get(v);
        List<Integer> missing = new ArrayList<>();
        if (i == null) {
            missing.add(u);
        }
        if (j == null) {
            missing.add(v);
        }
        if (!missing.isEmpty()) {
            throw new IllegalArgumentException("Vertices must exist: missing " + missing);
        }
        matrix.get(i).set(j, 1);
    }

    @Override
    public void removeEdge(int u, int v) {
        Integer i = idToIndex.get(u);
        Integer j = idToIndex.get(v);
        List<Integer> missing = new ArrayList<>();
        if (i == null) {
            missing.add(u);
        }
        if (j == null) {
            missing.add(v);
        }
        if (!missing.isEmpty()) {
            throw new IllegalArgumentException("Vertices must exist: missing " + missing);
        }
        matrix.get(i).set(j, 0);
    }

    @Override
    public List<Integer> getNeighbors(int v) {
        Integer i = idToIndex.get(v);
        if (i == null) {
            return Collections.emptyList();
        }
        List<Integer> ns = new ArrayList<>();
        List<Integer> row = matrix.get(i);
        for (int j = 0; j < row.size(); j++) {
            if (row.get(j) == 1) {
                ns.add(indexToId.get(j));
            }
        }
        return ns;
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
        for (int ui = 0; ui < indexToId.size(); ui++) {
            int u = indexToId.get(ui);
            for (int vi = 0; vi < indexToId.size(); vi++) {
                if ((matrix.get(ui).get(vi)) == 1) {
                    int v = indexToId.get(vi);
                    edgesList.add(new int[]{u, v});
                }
            }
        }
        edgesList.sort((a, b) -> {
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
        Set<Integer> thisV = getVertices();
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
