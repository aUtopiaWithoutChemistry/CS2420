package assign07;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EnhancedGraphTest {
    public Graph<Integer> emptyGraph, smallGraph, cyclicGraph, singleVertexGraph, disconnectedGraph;
    public Graph<String> largeGraph, graph10, graph20, graph30, graph50, graph100;

    @BeforeEach
    void setUp() {
        emptyGraph = new Graph<>();
        smallGraph = new Graph<>();
        largeGraph = new Graph<>();
        cyclicGraph = new Graph<>();
        singleVertexGraph = new Graph<>();
        disconnectedGraph = new Graph<>();

        graph10 = new Graph<>("src/assign07/TestGraph10.txt");
        graph20 = new Graph<>("src/assign07/TestGraph20.txt");
        graph30 = new Graph<>("src/assign07/TestGraph30.txt");
        graph50 = new Graph<>("src/assign07/TestGraph50.txt");
        graph100 = new Graph<>("src/assign07/TestGraph100.txt");

        // Simple connected path 1->5->9
        smallGraph.addVertex(1);
        smallGraph.addVertex(5);
        smallGraph.addVertex(9);
        smallGraph.addEdge(1, 5);
        smallGraph.addEdge(5, 9);

        // Cyclic graph
        cyclicGraph.addEdge(1, 2);
        cyclicGraph.addEdge(2, 3);
        cyclicGraph.addEdge(3, 1);
        cyclicGraph.addEdge(3, 4);
        cyclicGraph.addEdge(5, 1);

        // Single vertex graph
        singleVertexGraph.addVertex(42);

        // Graph with disconnected components
        disconnectedGraph.addEdge(1, 2);
        disconnectedGraph.addEdge(2, 3);
        disconnectedGraph.addEdge(4, 5);
        disconnectedGraph.addEdge(5, 6);
    }

    // ----- VERTEX AND EDGE TESTS -----

    @Test
    void testAddVertexAndEdge() {
        Graph<String> g = new Graph<>();
        g.addVertex("A");
        g.addVertex("B");
        g.addEdge("A", "B");
        g.addEdge("B", "C"); // Should add C automatically

        assertTrue(g.depthFirstSearch("A", "C"));
        assertFalse(g.depthFirstSearch("C", "A")); // Directed graph
    }

    @Test
    void testAddDuplicateVertex() {
        Graph<Integer> g = new Graph<>();
        g.addVertex(1);
        g.addVertex(1); // Adding same vertex again shouldn't cause issues

        // Adding an edge from a vertex to itself
        g.addEdge(1, 1);
        assertTrue(g.isCyclic()); // Self-loop is a cycle
    }

    // ----- GRAPH STRUCTURE TESTS -----

    @Test
    void testEmptyGraph() {
        assertFalse(emptyGraph.isCyclic());
        assertEquals(0, emptyGraph.topoSort().size());
    }

    @Test
    void testSingleVertexGraph() {
        assertFalse(singleVertexGraph.isCyclic());
        List<Integer> expected = new ArrayList<>();
        expected.add(42);
        assertEquals(expected, singleVertexGraph.topoSort());

        // BFS and DFS on single vertex
        assertFalse(singleVertexGraph.depthFirstSearch(42, 99)); // Non-existent destination
        assertTrue(singleVertexGraph.depthFirstSearch(42, 42)); // Same vertex

        List<Integer> path = singleVertexGraph.breadthFirstSearch(42, 42);
        assertEquals(1, path.size());
        assertEquals(Integer.valueOf(42), path.get(0));
    }

    @Test
    void testDisconnectedComponents() {
        assertFalse(disconnectedGraph.isCyclic());
        assertFalse(disconnectedGraph.depthFirstSearch(1, 6)); // Can't reach from one component to another
        assertTrue(disconnectedGraph.depthFirstSearch(1, 3));
        assertTrue(disconnectedGraph.depthFirstSearch(4, 6));

        // Paths within components
        assertArrayEquals(new Integer[]{1, 2, 3}, disconnectedGraph.breadthFirstSearch(1, 3).toArray());
        assertArrayEquals(new Integer[]{4, 5, 6}, disconnectedGraph.breadthFirstSearch(4, 6).toArray());

        // BFS from one component to disconnected component should return empty list
        assertTrue(disconnectedGraph.breadthFirstSearch(1, 6).isEmpty());
    }

    // ----- DFS TESTS -----

    @Test
    void testDFSNonExistentVertex() {
        assertFalse(smallGraph.depthFirstSearch(1, 99)); // 99 doesn't exist
        assertFalse(smallGraph.depthFirstSearch(99, 1)); // 99 doesn't exist
    }

    @Test
    void testDFSSameVertex() {
        // Path from vertex to itself
        assertTrue(smallGraph.depthFirstSearch(1, 1));
        assertTrue(cyclicGraph.depthFirstSearch(3, 3));
    }

    @Test
    void testDFSCyclicPath() {
        // Test paths in a cyclic graph
        assertTrue(cyclicGraph.depthFirstSearch(1, 4));
        assertTrue(cyclicGraph.depthFirstSearch(5, 3));
        assertTrue(cyclicGraph.depthFirstSearch(2, 2)); // Returns to itself via cycle
    }

    // ----- BFS TESTS -----

    @Test
    void testBFSNonExistentVertex() {
        // Empty list when destination doesn't exist
        assertTrue(smallGraph.breadthFirstSearch(1, 99).isEmpty());
    }

    @Test
    void testBFSSameVertex() {
        // Path from vertex to itself should be just that vertex
        List<Integer> path = smallGraph.breadthFirstSearch(1, 1);
        assertEquals(1, path.size());
        assertEquals(Integer.valueOf(1), path.get(0));
    }

    @Test
    void testBFSMultiplePaths() {
        // Create a graph with multiple paths from A to D
        Graph<String> multiPathGraph = new Graph<>();
        multiPathGraph.addEdge("A", "B");
        multiPathGraph.addEdge("A", "C");
        multiPathGraph.addEdge("B", "D");
        multiPathGraph.addEdge("C", "D");

        // BFS should find the shortest path (both A->B->D and A->C->D are same length, but one will be chosen)
        List<String> path = multiPathGraph.breadthFirstSearch("A", "D");
        assertEquals(3, path.size());
        assertEquals("A", path.get(0));
        assertEquals("D", path.get(2));
        // Middle element could be either B or C depending on implementation details
        assertTrue(path.get(1).equals("B") || path.get(1).equals("C"));
    }

    @Test
    void testBFSCyclicGraph() {
        // Even in a cyclic graph, BFS should find shortest path
        List<Integer> path = cyclicGraph.breadthFirstSearch(5, 4);
        assertArrayEquals(new Integer[]{5, 1, 2, 3, 4}, path.toArray());

        // Try a more complex path with a cycle
        List<Integer> cyclicPath = cyclicGraph.breadthFirstSearch(3, 5);
        assertTrue(cyclicPath.isEmpty()); // No path from 3 to 5
    }

    // ----- TOPOLOGICAL SORT TESTS -----

    @Test
    void testTopoSortDirectedAcyclicGraph() {
        Graph<String> dag = new Graph<>();
        dag.addEdge("A", "B");
        dag.addEdge("A", "C");
        dag.addEdge("B", "D");
        dag.addEdge("C", "D");

        List<String> sortResult = dag.topoSort();
        assertEquals(4, sortResult.size());

        // A must come before B and C
        // B and C must come before D
        int aIndex = sortResult.indexOf("A");
        int bIndex = sortResult.indexOf("B");
        int cIndex = sortResult.indexOf("C");
        int dIndex = sortResult.indexOf("D");

        assertTrue(aIndex < bIndex);
        assertTrue(aIndex < cIndex);
        assertTrue(bIndex < dIndex);
        assertTrue(cIndex < dIndex);
    }

    @Test
    void testTopoSortDisconnectedGraph() {
        List<Integer> sortResult = disconnectedGraph.topoSort();
        assertEquals(6, sortResult.size());

        // Check relative ordering within components
        int pos1 = sortResult.indexOf(1);
        int pos2 = sortResult.indexOf(2);
        int pos3 = sortResult.indexOf(3);
        int pos4 = sortResult.indexOf(4);
        int pos5 = sortResult.indexOf(5);
        int pos6 = sortResult.indexOf(6);

        assertTrue(pos1 < pos2);
        assertTrue(pos2 < pos3);
        assertTrue(pos4 < pos5);
        assertTrue(pos5 < pos6);
    }

    // ----- GRAPHUTILITY TESTS -----

    @Test
    void testGraphUtilityInvalidInput() {
        List<String> sources = new ArrayList<>();
        List<String> destinations = new ArrayList<>();

        // Empty lists
        assertThrows(IllegalArgumentException.class, () -> GraphUtility.areConnected(sources, destinations, "A", "B"));

        // Uneven lists
        sources.add("A");
        assertThrows(IllegalArgumentException.class, () -> GraphUtility.areConnected(sources, destinations, "A", "B"));

        // Valid but source/destination not in graph
        destinations.add("B");
        assertThrows(IllegalArgumentException.class, () -> GraphUtility.areConnected(sources, destinations, "C", "D"));
    }

    @Test
    void testGraphUtilityShortestPathException() {
        List<String> sources = Arrays.asList("A", "B");
        List<String> destinations = Arrays.asList("B", "C");

        // No path exists
        assertThrows(IllegalArgumentException.class, () -> GraphUtility.shortestPath(sources, destinations, "C", "A"));
    }

    @Test
    void testGraphUtilitySameSourceDestination() {
        List<String> sources = Arrays.asList("A", "B", "C");
        List<String> destinations = Arrays.asList("B", "C", "D");

        // Test path from node to itself
        assertTrue(GraphUtility.areConnected(sources, destinations, "A", "A"));

        List<String> path = GraphUtility.shortestPath(sources, destinations, "A", "A");
        assertEquals(1, path.size());
        assertEquals("A", path.get(0));
    }

    @Test
    void testGraphUtilityComplexGraph() {
        List<String> sources = Arrays.asList("A", "A", "B", "C", "C", "D", "E");
        List<String> destinations = Arrays.asList("B", "C", "D", "E", "F", "F", "F");

        // Test connections
        assertTrue(GraphUtility.areConnected(sources, destinations, "A", "F"));
        assertFalse(GraphUtility.areConnected(sources, destinations, "F", "A"));

        // Test multiple paths
        List<String> path1 = GraphUtility.shortestPath(sources, destinations, "A", "F");
        assertArrayEquals(new String[]{"A", "C", "F"}, path1.toArray()); // A->C->F is shorter than A->B->D->F

        // Test topological sort
        List<String> sortResult = GraphUtility.sort(sources, destinations);
        assertEquals(6, sortResult.size());

        // Verify basic ordering: A must come before both B and C, etc.
        int aIndex = sortResult.indexOf("A");
        int bIndex = sortResult.indexOf("B");
        int cIndex = sortResult.indexOf("C");
        int dIndex = sortResult.indexOf("D");
        int eIndex = sortResult.indexOf("E");
        int fIndex = sortResult.indexOf("F");

        assertTrue(aIndex < bIndex);
        assertTrue(aIndex < cIndex);
        assertTrue(bIndex < dIndex);
        assertTrue(cIndex < eIndex);
        assertTrue(dIndex < fIndex);
        assertTrue(eIndex < fIndex);
    }
}