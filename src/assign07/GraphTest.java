package assign07;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {
    public Graph<Integer> emptyGraph, smallGraph, cyclicGraph;
    public Graph<String> largeGraph, graph10, graph20, graph30, graph50, graph100;
    @BeforeEach
    void setUp() {
        emptyGraph = new Graph<>();
        smallGraph = new Graph<>();
        largeGraph = new Graph<>();
        cyclicGraph = new Graph<>();
        graph10 = new Graph<>("src/assign07/TestGraph10.txt");
        graph20 = new Graph<>("src/assign07/TestGraph20.txt");
        graph30 = new Graph<>("src/assign07/TestGraph30.txt");
        graph50 = new Graph<>("src/assign07/TestGraph50.txt");
        graph100 = new Graph<>("src/assign07/TestGraph100.txt");

        smallGraph.addVertex(1);
        smallGraph.addVertex(5);
        smallGraph.addVertex(9);
        smallGraph.addEdge(1, 5);
        smallGraph.addEdge(5, 9);

        cyclicGraph.addEdge(1, 2);
        cyclicGraph.addEdge(2, 3);
        cyclicGraph.addEdge(3, 1);
        cyclicGraph.addEdge(3, 4);
        cyclicGraph.addEdge(5, 1);
    }

    @Test
    void testToString() {
//        System.out.println(smallGraph.toString());
    }

    @Test
    void testReadFile() {
        largeGraph.readGraph("RandomGraph.txt");
//        System.out.println(largeGraph.toString());
    }

    @Test
    void testDFSSmall() {
        assertTrue(smallGraph.depthFirstSearch(1, 9));
    }

    @Test
    void testDFSSCyclic() {
        assertTrue(cyclicGraph.depthFirstSearch(5, 4));
    }

    @Test
    void testIsCyclic() {
        assertTrue(cyclicGraph.isCyclic());
        assertTrue(graph100.isCyclic());
    }

    @Test
    void testIsCyclicFalse() {
        assertFalse(graph10.isCyclic());
        assertFalse(graph20.isCyclic());
        assertFalse(graph30.isCyclic());
        assertFalse(graph50.isCyclic());
    }

    @Test
    void testTopoCyclic() {
        List<Integer> sources = new ArrayList<>();
        List<Integer> destination = new ArrayList<>();
        sources.add(1);
        sources.add(2);
        sources.add(3);
        destination.add(2);
        destination.add(3);
        destination.add(1);
        assertThrows(IllegalArgumentException.class, () -> GraphUtility.sort(sources, destination));
    }

    @Test
    void testBFSSmall() {
        assertArrayEquals(new Integer[]{1, 5, 9}, smallGraph.breadthFirstSearch(1, 9).toArray());
    }

    @Test
    void testBFS10() {
        assertArrayEquals(new String[]{"v1", "v5", "v9"}, graph10.breadthFirstSearch("v1", "v9").toArray());
    }

    @Test
    void testBFS100() {
        assertArrayEquals(new String[]{"v14", "v44", "v86", "v93", "v96", "v99"}, graph100.breadthFirstSearch("v14", "v99").toArray());
    }

    @Test
    void testTopoSortSmall() {
        assertArrayEquals(new Integer[]{1, 5, 9}, smallGraph.topoSort().toArray());
    }

    @Test
    void testUtility() {
        List<String> sources = new ArrayList<>();
        List<String> destination = new ArrayList<>();
        sources.add("v0");
        sources.add("v1");
        sources.add("v1");
        sources.add("v1");
        sources.add("v2");
        sources.add("v3");
        sources.add("v4");
        sources.add("v5");
        sources.add("v6");
        sources.add("v7");
        sources.add("v8");
        destination.add("v4");
        destination.add("v3");
        destination.add("v5");
        destination.add("v2");
        destination.add("v8");
        destination.add("v6");
        destination.add("v9");
        destination.add("v9");
        destination.add("v7");
        destination.add("v9");
        destination.add("v9");
        assertTrue(GraphUtility.areConnected(sources, destination, "v1", "v9"));
        assertArrayEquals(new String[]{"v1", "v5", "v9"}, GraphUtility.shortestPath(sources, destination, "v1", "v9").toArray());
    }
}