package qangan.graph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Abstract graph tests.
 */
public abstract class GraphTest {

    protected Graph graph;

    @TempDir Path tempDir;

    protected abstract Graph createGraph();

    @BeforeEach
    void setup() {
        graph = createGraph();
    }

    @Test
    public void testAddVertex() {
        graph.addVertex(1);
        assertTrue(graph.getVertices().contains(1));
    }

    @Test
    public void testRemoveVertex() {
        graph.addVertex(1);
        graph.removeVertex(1);
        assertFalse(graph.getVertices().contains(1));
    }

    @Test
    void testRemoveVertexWithIncidentEdges_minimal() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 2);

        graph.removeVertex(2);

        assertFalse(graph.getVertices().contains(2));
        for (int u : graph.getVertices()) {
            assertFalse(graph.getNeighbors(u).contains(2), "edge incident to removed");
        }
    }

    @Test
    public void testRemoveNonExistentVertex() {
        assertThrows(IllegalArgumentException.class, () -> graph.removeVertex(999));
    }

    @Test
    public void testAddEdgeAndNeighbors() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);
        assertTrue(graph.getNeighbors(1).contains(2));
    }

    @Test
    public void testAddEdgeWithNonExistentVertices() {
        graph.addVertex(1);
        assertThrows(IllegalArgumentException.class, () -> graph.addEdge(1, 99));
        assertThrows(IllegalArgumentException.class, () -> graph.addEdge(99, 1));
    }

    @Test
    public void testRemoveEdge() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);
        graph.removeEdge(1, 2);
        assertFalse(graph.getNeighbors(1).contains(2));
    }

    @Test
    public void testRemoveNonExistentEdge() {
        graph.addVertex(1);
        graph.addVertex(2);
        assertDoesNotThrow(() -> graph.removeEdge(1, 2));
    }

    @Test
    public void testGetNeighborsForNonExistentVertex() {
        List<Integer> neighbors = graph.getNeighbors(999);
        assertNotNull(neighbors);
        assertTrue(neighbors.isEmpty());
    }

    @Test
    public void testGraphEqualsReflexive() {
        assertEquals(graph, graph);
    }

    @Test
    public void testGraphEqualsDifferentGraphsSameContent() {
        Graph g1 = createGraph();
        g1.addVertex(1);
        g1.addVertex(2);
        g1.addEdge(1, 2);
        Graph g2 = createGraph();
        g2.addVertex(2);
        g2.addVertex(1);
        g2.addEdge(1, 2);
        assertEquals(g1, g2);
    }

    @Test
    public void testGraphNotEqualsDifferentVertices() {
        Graph g1 = createGraph();
        Graph g2 = createGraph();
        g1.addVertex(1);
        g2.addVertex(2);
        assertNotEquals(g1, g2);
    }

    @Test
    public void testGraphNotEqualsDifferentEdges() {
        Graph g1 = createGraph();
        g1.addVertex(1);
        g1.addVertex(2);
        Graph g2 = createGraph();
        g2.addVertex(1);
        g2.addVertex(2);
        g1.addEdge(1, 2);
        assertNotEquals(g1, g2);
    }

    @Test
    public void testToString() {
        graph.addVertex(1);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);
        graph.addEdge(2, 1);
        String repr = graph.toString();
        assertNotNull(repr);
        assertEquals("2\n1 2\n2\n1 2\n2 1\n", repr);
    }

    @Test
    public void testGraphsDifferentImplementationsSameContent() {
        Graph g1 = new AdjListGraph();
        g1.addVertex(1);
        g1.addVertex(2);
        g1.addEdge(1, 2);
        Graph g2 = new AdjMatrixGraph();
        g2.addVertex(1);
        g2.addVertex(2);
        g2.addEdge(1, 2);
        Graph g3 = new IncMatrixGraph();
        g3.addVertex(1);
        g3.addVertex(2);
        g3.addEdge(1, 2);
        assertEquals(g1, g2);
        assertEquals(g2, g3);
        assertEquals(g1, g3);
        assertEquals(g1.toString(), g2.toString());
        assertEquals(g1.toString(), g3.toString());
        assertEquals(g2.toString(), g3.toString());
    }

    @Test
    public void testReadFromFile() throws IOException {
        Path testFile = tempDir.resolve("test_graph.txt");
        String content = "3\n1 2 3\n2\n1 2\n2 3\n";
        Files.writeString(testFile, content);

        graph.readFromFile(testFile.toString());

        assertEquals(3, graph.getVertices().size());
        assertTrue(graph.getVertices().contains(1));
        assertTrue(graph.getVertices().contains(2));
        assertTrue(graph.getVertices().contains(3));

        assertTrue(graph.getNeighbors(1).contains(2));
        assertTrue(graph.getNeighbors(2).contains(3));
    }

    @Test
    public void testReadFromNonExistentFile() {
        assertThrows(
                IOException.class,
                () -> {
                    graph.readFromFile("/if/you/have/this/you/must/be/crazy");
                });
    }

    @Test
    public void testOutOfOrderVertices() {
        graph.addVertex(100500);
        graph.addVertex(7);
        graph.addVertex(42);
        graph.addEdge(100500, 7);
        graph.addEdge(7, 42);
        assertEquals(Set.of(7), new HashSet<>(graph.getNeighbors(100500)));
        assertEquals(Set.of(42), new HashSet<>(graph.getNeighbors(7)));
        assertTrue(graph.getNeighbors(42).isEmpty());
    }

    @Test
    public void testRepetitiveAdds() {
        graph.addVertex(10);
        graph.addVertex(10);
        graph.addVertex(0);
        graph.addVertex(0);
        graph.addVertex(0);
        graph.addVertex(7);
        graph.addVertex(7);
        graph.addEdge(10, 0);
        graph.addEdge(10, 0);
        graph.addEdge(0, 7);
        graph.addEdge(0, 7);
        assertEquals(3, graph.getVertices().size());
        assertEquals(List.of(0), graph.getNeighbors(10));
        graph.removeEdge(0, 7);
        assertEquals(List.of(0), graph.getNeighbors(10));
    }
    }
