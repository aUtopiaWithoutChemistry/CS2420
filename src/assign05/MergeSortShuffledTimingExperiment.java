package assign05;

import timing.ArrayListGenerator;
import timing.TimingExperiment;
import java.util.ArrayList;

public class MergeSortShuffledTimingExperiment extends TimingExperiment {
    protected static int threshold = 20;
    private static final String problemSizeDescription = "Array size";
    private static final int problemSizeMin = 1000;
    private static final int problemSizeCount = 100;
    private static final int problemSizeStep = 1000;
    private static final int experimentIterationCount = 50;
    public ArrayList<Integer> list;

    /**
     * Constructs a general timing experiment.
     */
    public MergeSortShuffledTimingExperiment() {
        super(problemSizeDescription, problemSizeMin, problemSizeCount, problemSizeStep, experimentIterationCount);
    }

    public static void main(String[] args) {
        TimingExperiment timingExperiment = new MergeSortShuffledTimingExperiment();

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
        list = ArrayListGenerator.generatePermutedArray(problemSize);
    }

    /**
     * Runs the containsAll method for the priority queue.
     */
    @Override
    protected void runComputation() {
        MergeSorter<Integer> sorter = new MergeSorter<>(threshold);
        sorter.sort(list);
    }
}
