package assign07;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {
    public Graph<Integer> emptyGraph, smallGraph;
    public Graph<String> largeGraph;
    @BeforeEach
    void setUp() {
        emptyGraph = new Graph<>();
        smallGraph = new Graph<>();
        largeGraph = new Graph<>();

        smallGraph.addVertex(1);
        smallGraph.addVertex(5);
        smallGraph.addVertex(9);
        smallGraph.addNeighbor(1, 5);
        smallGraph.addNeighbor(5, 9);
    }

    @Test
    void testToString() {
        System.out.println(smallGraph.toString());
    }

    @Test
    void testReadFile() {
        largeGraph.readGraph("RandomGraph.txt");
        System.out.println(largeGraph.toString());
    }

    @Test
    void testDFSSmall() {
        assertTrue(smallGraph.depthFirstSearch(1, 9));
    }
}