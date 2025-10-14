package qangan.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

interface Graph {
    void addVertex(int v);
    void removeVertex(int v);
    void addEdge(int u, int v) throws IllegalArgumentException;
    void removeEdge(int u, int v);
    List<Integer> getNeighbors(int v);
    @Override
    String toString();
    @Override
    boolean equals(Object o);
    List<Integer> getVertices();

    default List<Integer> topologicalSort() {
        List<Integer> result = new ArrayList<>();
        Map<Integer, Integer> inDegree = new HashMap<>();

        for (int vertex : getVertices()) {
            inDegree.put(vertex, 0);
        }

        for (int vertex : getVertices()) {
            for (int neighbor : getNeighbors(vertex)) {
                inDegree.put(neighbor, inDegree.get(neighbor) + 1);
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        for (Map.Entry<Integer, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.add(entry.getKey());
            }
        }

        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            result.add(vertex);

            for (int neighbor : getNeighbors(vertex)) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }

        if (result.size() != inDegree.size()) {
            throw new RuntimeException("Graph contains a cycle.");
        }

        return result;
    }
}