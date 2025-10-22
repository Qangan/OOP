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
        graph.addVertex(2);
        System.out.println(graph);
        System.out.println(graph.topologicalSort());
    }

    /** Example usage. */
    public static void main(String[] args) {
        Graph AMGraph = new AdjMatrixGraph();
        Graph IMGraph = new IncMatrixGraph();
        Graph ALGraph = new AdjListGraph();
        example_usage(AMGraph);
        example_usage(IMGraph);
        example_usage(ALGraph);
    }
}