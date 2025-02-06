package assign04;

import timing.TimingExperiment;

import java.util.Comparator;
import java.util.Random;

/**
 * Experiment to measure the running time for the containsAll method for
 * SimplePriorityQueue for various problem sizes.
 *
 * @author CS 2420 course staff
 * @version January 23, 2025
 */
public class InsertionSortWorstCaseTimingExperiment extends TimingExperiment {

    private static String problemSizeDescription = "Array size";
    private static final int problemSizeMin = 100;
    private static final int problemSizeCount = 49;
    private static final int problemSizeStep = 50;
    private static final int experimentIterationCount = 20;

    private final static Random rng = new Random();

    private Integer[] arr;
    private final Comparator<Integer> cmp = Comparator.comparingInt(anInt -> anInt);

    /**
     * Constructs a general timing experiment.
     */
    public InsertionSortWorstCaseTimingExperiment() {
        super(problemSizeDescription, problemSizeMin, problemSizeCount, problemSizeStep, experimentIterationCount);
    }

    public static void main(String[] args) {
        TimingExperiment timingExperiment = new InsertionSortWorstCaseTimingExperiment();

        System.out.println("\n---Computing timing results---\n");
        timingExperiment.printResults();
    }

    /**
     * Fills the priority queue and targets list with the given number of integers.
     *
     * @param problemSize - the number of integers to fill the priority queue
     */
    @Override
    protected void setupExperiment(int problemSize) {
        // initialize the arr
        arr = ArrayGenerator.generateDescendingArray(problemSize);
    }

    /**
     * Runs the containsAll method for the priority queue.
     */
    @Override
    protected void runComputation() {
        LargestNumberSolver.insertionSort(arr, cmp);
    }
}