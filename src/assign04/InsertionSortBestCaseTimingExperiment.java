package assign04;

import timing.TimingExperiment;

import java.util.Comparator;

/**
 * Experiment to measure the running time for the containsAll method for
 * SimplePriorityQueue for various problem sizes.
 *
 * @author CS 2420 course staff
 * @version January 23, 2025
 */
public class InsertionSortBestCaseTimingExperiment extends TimingExperiment {

    private static final String problemSizeDescription = "Array size";
    private static final int problemSizeMin = 10000;
    private static final int problemSizeCount = 100;
    private static final int problemSizeStep = 10000;
    private static final int experimentIterationCount = 20;

    public Integer[] array;
    private final Comparator<Integer> cmp = Comparator.comparingInt(anInt -> anInt);

    /**
     * Constructs a general timing experiment.
     */
    public InsertionSortBestCaseTimingExperiment() {
        super(problemSizeDescription, problemSizeMin, problemSizeCount, problemSizeStep, experimentIterationCount);
    }

    public static void main(String[] args) {
        TimingExperiment timingExperiment = new InsertionSortBestCaseTimingExperiment();

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
        array = ArrayGenerator.generateNearlyAscendingArray(problemSize);
    }

    /**
     * Runs the containsAll method for the priority queue.
     */
    @Override
    protected void runComputation() {
        LargestNumberSolver.insertionSort(array, cmp);
    }
}