package qangan.graph;

/**
 * Tests for adjacency matrix graph.
 */
public class AdjMatrixGraphTest extends GraphTest {
    @Override
    protected Graph createGraph() {
        return new AdjMatrixGraph();
    }
}
