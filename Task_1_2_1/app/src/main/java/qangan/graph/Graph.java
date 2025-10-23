package qangan.graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/** Graph interface. */
public interface Graph {

    /**
     * Add vertex.
     *
     * @param v - vertex id. If already exists does nothing.
     */
    void addVertex(int v);

    /**
     * Remove vertex.
     *
     * @param v - vertex id.
     * @throws IllegalArgumentException if vertex doesnt exist
     */
    void removeVertex(int v) throws IllegalArgumentException;

    /**
     * Add edge.
     *
     * @param u - to.
     * @param v - from.
     * @throws IllegalArgumentException if one of edges does not exist.
     */
    void addEdge(int u, int v) throws IllegalArgumentException;

    /**
     * Remove edge.
     *
     * @param u - from.
     * @param v - to.
     * @throws IllegalArgumentException if one of vertices does not exist.
     */
    void removeEdge(int u, int v);

    /**
     * Get neighbours of vertex v.
     *
     * @param v - vertex.
     * @return List of neighbours
     */
    List<Integer> getNeighbors(int v);

    /**
     * String representation.
     *
     * @return String representation of graph.
     */
    @Override
    String toString();

    /**
     * Test objects for equalness.
     *
     * @param o the reference object with which to compare.
     * @return True or False.
     */
    @Override
    boolean equals(Object o);
    
    /**
     * Return vertices.
     */
    Set<Integer> getVertices();

    /**
     * Read graph from file with given name.
     *
     * @param filename file name.
     * @throws IOException if file doesnt exist.
     */
    default void readFromFile(String filename) throws IOException {
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

    /**
     * Topological sort implementation.
     *
     * @return Topological sort of graph.
     * @throws RuntimeException if graph has cycles.
     */
    default List<Integer> topologicalSort() throws RuntimeException {
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

        List<Integer> result = new ArrayList<>();
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
