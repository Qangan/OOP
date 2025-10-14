package qangan.graph;
/** Entry point. */

public class Main {
    /** Example usage. */
    public static void main(String[] args) {
        Graph AMGraph = new AdjMatrixGraph();
        AMGraph.addVertex(0);
        AMGraph.addVertex(1);
        AMGraph.addVertex(2);
        AMGraph.addVertex(3);
        AMGraph.addVertex(4);
        AMGraph.addVertex(5);
        AMGraph.addEdge(0, 1);
        AMGraph.addEdge(0, 2);
        AMGraph.addEdge(1, 2);
        AMGraph.addEdge(2, 3);
        System.out.println(AMGraph);
        System.out.println(AMGraph.topologicalSort());
        AMGraph.removeVertex(2);
        System.out.println(AMGraph);  
        AMGraph.addVertex(2);
        System.out.println(AMGraph); 
        System.out.println(AMGraph.topologicalSort());
    }
}
