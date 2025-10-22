package qangan.graph;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public abstract class GraphTest {

    protected Graph graph;

    @TempDir
    Path tempDir;

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
        Graph g2 = createGraph();
        g1.addVertex(1);
        g1.addVertex(2);
        g1.addEdge(1, 2);
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
        Graph g2 = createGraph();
        g1.addVertex(1);
        g1.addVertex(2);
        g2.addVertex(1);
        g2.addVertex(2);
        g1.addEdge(1, 2);
        assertNotEquals(g1, g2);
    }

    @Test
    public void testToStringNotEmpty() {
        graph.addVertex(1);
        String repr = graph.toString();
        assertNotNull(repr);
        assertFalse(repr.isBlank());
    }

    @Test
    public void testGraphsDifferentImplementationsSameContent() {
        Graph g1 = new AdjListGraph();
        Graph g2 = new AdjMatrixGraph();
        Graph g3 = new IncMatrixGraph();

        g1.addVertex(1);
        g1.addVertex(2);
        g1.addEdge(1, 2);

        g2.addVertex(1);
        g2.addVertex(2);
        g2.addEdge(1, 2);

        g3.addVertex(1);
        g3.addVertex(2);
        g3.addEdge(1, 2);

        assertEquals(g1, g2);
        assertEquals(g2, g3);
        assertEquals(g1, g3);
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
        assertThrows(IOException.class, () -> {
            graph.readFromFile("/if/you/have/this/you/must/be/crazy");
        });
    }
}

