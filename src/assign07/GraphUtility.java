package assign07;

import java.util.List;

/**
 * GraphUtility class, provides three methods, to check if two vertex of a graph are connected,
 * to find the shortest path from src to dst, to sort the graph according to topological order.
 *
 * @author Zifan Zuo and Xinrui Ou
 * @version 2025-03-05
 */
public class GraphUtility {
    /**
     * to check if srcVertex is connected to dstVertex, calling the Graph's dfs algorithm.
     *
     * @param sources a list of data, are source of an edge
     * @param destinations a list of data, are destination of an edge
     * @param srcData the data of source vertex
     * @param dstData the data of destination vertex
     * @return true if found a path, false if it doesn't
     */
    public static <Type> boolean areConnected(List<Type> sources, List<Type> destinations, Type srcData, Type dstData) {
        if (InputInvalid(sources, destinations)) throw new IllegalArgumentException();
        Graph<Type> graph = buildGraph(sources, destinations);
        if (!graph.contains(srcData) || !graph.contains(dstData)) throw new IllegalArgumentException();
        return graph.depthFirstSearch(srcData, dstData);
    }

    /**
     * to find the shortest path from source to destination.
     *
     * @param sources a list of data, are source of an edge
     * @param destinations a list of data, are destination of an edge
     * @param srcData the data of source vertex
     * @param dstData the data of destination vertex
     * @return a list of data that is the shortest path from source to the destination
     */
    public static <Type> List<Type> shortestPath(List<Type> sources, List<Type> destinations, Type srcData, Type dstData) {
        if (InputInvalid(sources, destinations)) throw new IllegalArgumentException();
        Graph<Type> graph = buildGraph(sources, destinations);
        if (!areConnected(sources, destinations, srcData, dstData)) throw new IllegalArgumentException();
        return graph.breadthFirstSearch(srcData, dstData);
    }

    /**
     * to sort the given graph according to topological sort.
     *
     * @param sources a list of data, are source of an edge
     * @param destinations a list of data, are destination of an edge
     * @return a list of sorted data according to topological sort.
     */
    public static <Type> List<Type> sort(List<Type> sources, List<Type> destinations) {
        if (InputInvalid(sources, destinations)) throw new IllegalArgumentException();
        Graph<Type> graph = buildGraph(sources, destinations);
        if (graph.isCyclic()) throw new IllegalArgumentException();
        return graph.topoSort();
    }

    /**
     * to check if the input lists of sources and destinations are valid
     *
     * @param sources a list of data, are source of an edge
     * @param destinations a list of data, are destination of an edge
     * @return true if input is valid, false if it doesn't
     */
    private static <Type> boolean InputInvalid(List<Type> sources, List<Type> destinations) {
        return sources.isEmpty() || destinations.isEmpty() || sources.size() != destinations.size();
    }

    /**
     * to build a graph by using the lists of sources and destinations
     *
     * @param sources a list of data, are source of an edge
     * @param destinations a list of data, are destination of an edge
     * @return a graph object that contains all the vertex and edges.
     */
    private static <Type> Graph<Type> buildGraph(List<Type> sources, List<Type> destinations) {
        Graph<Type> graph = new Graph<>();
        int size = sources.size();
        for (int i = 0; i < size; i++) {
            graph.addEdge(sources.get(i), destinations.get(i));
        }
        return graph;
    }
}
