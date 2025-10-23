package qangan.graph;

/** Entry point. */
public class Main {
    /** Example usage. */
    public static void example_usage(Graph graph) {
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        System.out.println(graph);
        System.out.println(graph.topologicalSort());
        graph.removeVertex(2);
        System.out.println(graph);
        System.out.println(graph.topologicalSort());
        graph.addVertex(2);
    }

    /** Example usage. */
    public static void main(String[] args) {
        Graph adMatGraph = new AdjMatrixGraph();
        Graph inMatGraph = new IncMatrixGraph();
        Graph adListGraph = new AdjListGraph();
        example_usage(adMatGraph);
        example_usage(inMatGraph);
        example_usage(adListGraph);
    }
}