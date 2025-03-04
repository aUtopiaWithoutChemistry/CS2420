package assign07;

import java.util.List;

public class GraphUtility {
    public static <Type> boolean areConnected(List<Type> sources, List<Type> destinations, Type srcData, Type dstData) {
        if (InputInvalid(sources, destinations)) throw new IllegalArgumentException();
        Graph<Type> graph = buildGraph(sources, destinations);
        if (!graph.contains(srcData) || !graph.contains(dstData)) throw new IllegalArgumentException();
        return graph.depthFirstSearch(srcData, dstData);
    }

    public static <Type> List<Type> shortestPath(List<Type> sources, List<Type> destinations, Type srcData, Type dstData) {
        if (InputInvalid(sources, destinations)) throw new IllegalArgumentException();
        Graph<Type> graph = buildGraph(sources, destinations);
        if (!areConnected(sources, destinations, srcData, dstData)) throw new IllegalArgumentException();
        return graph.breadthFirstSearch(srcData, dstData);
    }

    public static <Type> List<Type> sort(List<Type> sources, List<Type> destinations) {
        if (InputInvalid(sources, destinations)) throw new IllegalArgumentException();
        Graph<Type> graph = buildGraph(sources, destinations);
        if (graph.isCyclic()) throw new IllegalArgumentException();
        return graph.topoSort();
    }

    private static <Type> boolean InputInvalid(List<Type> sources, List<Type> destinations) {
        return sources.isEmpty() || destinations.isEmpty() || sources.size() != destinations.size();
    }

    private static <Type> Graph<Type> buildGraph(List<Type> sources, List<Type> destinations) {
        Graph<Type> graph = new Graph<>();
        int size = sources.size();
        for (int i = 0; i < size; i++) {
            graph.addEdge(sources.get(i), destinations.get(i));
        }
        return graph;
    }
}
