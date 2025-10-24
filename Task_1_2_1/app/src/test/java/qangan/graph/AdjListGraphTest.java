package qangan.graph;

/**
 * Tests for adjacency list graph.
 */
public class AdjListGraphTest extends GraphTest {
    @Override
    protected Graph createGraph() {
        return new AdjListGraph();
    }
}
