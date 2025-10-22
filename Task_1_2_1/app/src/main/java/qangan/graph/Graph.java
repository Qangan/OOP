package qangan.graph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/** Graph interface. */
interface Graph {
    /**
     * Add vertex.
     *
     * @param v - vertex id
     */
    void addVertex(int v);

    /**
     * Remove vertex.
     *
     * @param v - vertex id
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
     * Read graph from file with given name.
     * @param filename file name.
     * @throws IOException if file doesnt exist.
     */
    void readFromFile(String filename) throws IOException;

    List<Integer> getVertices();

    /**
     * Topological sort implementation.
     *
     * @return Topological sort of graph.
     */
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
        System.out.println(result);
        System.out.println(inDegree);

        if (result.size() != inDegree.size()) {
            throw new RuntimeException("Graph contains a cycle.");
        }

        return result;
    }
}
