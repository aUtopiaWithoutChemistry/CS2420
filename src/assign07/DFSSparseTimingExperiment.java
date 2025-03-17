package assign07;

import timing.TimingExperiment;

import java.util.Random;

/**
 * Experiment to measure the running time of topological sort for random acyclic
 * sparse graphs with a range of vertex counts.
 *
 * @author CS 2420 course staff
 * @version February 27, 2025
 */
public class DFSSparseTimingExperiment extends TimingExperiment {
    private static String problemSizeDescription = "vertexCount";
    private static int problemSizeMin = 1000;
    private static int problemSizeCount = 50;
    private static int problemSizeStep = 1000;
    private static int experimentIterationCount = 300;
    private static int destinationVertexData;

    private final Random rng = new Random();

    private Graph<Integer> acyclicRandomGraph;

    public static void main(String[] args) {
        TimingExperiment timingExperiment = new DFSSparseTimingExperiment();
        timingExperiment.printResults();
    }

    public DFSSparseTimingExperiment() {
        super(problemSizeDescription, problemSizeMin, problemSizeCount, problemSizeStep, experimentIterationCount);
    }

    /**
     * Setup experiment for problemSize by generating a sparse acyclic random graph.
     * 
     * @param problemSize - number of vertices
     */
    protected void setupExperiment(int problemSize) {
        // V = {0, 1, ..., problemSize - 1}
        acyclicRandomGraph = new Graph<Integer>();

        // |E| = 2 * problemSize
        for(int i = 0; i < 2 * problemSize; i++) {
            // Add random edge between two random vertices.
            int sourceVertexData = rng.nextInt(problemSize);
            int destinationVertexData = rng.nextInt(problemSize);
            acyclicRandomGraph.addEdge(sourceVertexData, destinationVertexData);
        }
        destinationVertexData = rng.nextInt();
        acyclicRandomGraph.addEdge(rng.nextInt(problemSize), destinationVertexData);
        acyclicRandomGraph.addEdge(0, rng.nextInt(problemSize));
    }

    /**
     * Run the topoSort method.
     */
    protected void runComputation() {
    	acyclicRandomGraph.depthFirstSearch(0, destinationVertexData);
    }
}