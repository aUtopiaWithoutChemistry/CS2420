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
public class TopoSortDenseTimingExperiment extends TimingExperiment {
    private static String problemSizeDescription = "vertexCount";
    private static int problemSizeMin = 100;
    private static int problemSizeCount = 50;
    private static int problemSizeStep = 100;
    private static int experimentIterationCount = 100;

    private final Random rng = new Random();

    private Graph<Integer> acyclicRandomGraph;

    public static void main(String[] args) {
        TimingExperiment timingExperiment = new TopoSortDenseTimingExperiment();
        timingExperiment.printResults();
    }

    public TopoSortDenseTimingExperiment() {
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
    	   	
    	// |E|
        for(int i = 0; i < problemSize; i++) {
        	for (int j = i + 1; j < problemSize; j++){
        	    acyclicRandomGraph.addEdge(i, j);
            }
        }  
    }

    /**
     * Run the topoSort method.
     */
    protected void runComputation() {
    	acyclicRandomGraph.topoSort();
    }
}