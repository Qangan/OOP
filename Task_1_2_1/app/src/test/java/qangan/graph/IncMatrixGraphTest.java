package qangan.graph;

/**
 * Tests for incidence list graph.
 */
public class IncMatrixGraphTest extends GraphTest {
    @Override
    protected Graph createGraph() {
        return new IncMatrixGraph();
    }
}
