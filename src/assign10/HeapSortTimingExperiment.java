package assign10;

import timing.ArrayListGenerator;
import timing.TimingExperiment;

import java.util.List;

public class HeapSortTimingExperiment extends TimingExperiment {
    private static String problemSizeDescription = "delta size";
    private static int problemSizeMin = 1000;
    private static int problemSizeCount = 100;
    private static int problemSizeStep = 1000;
    private static int experimentIterationCount = 50;

    private static List<Integer> list;
    private static int problemSize;

    public static void main(String[] args) {
        HeapSortTimingExperiment deltaSortTimingExperiment = new HeapSortTimingExperiment();
        deltaSortTimingExperiment.printResults();
    }

    public HeapSortTimingExperiment() {
        super(problemSizeDescription, problemSizeMin, problemSizeCount, problemSizeStep, experimentIterationCount);
    }

    /**
     * Creates a hash table of problemSize StudentBadHash elements.
     *
     * @param problemSize - the number of unique students to fill the hash table
     */
    protected void setupExperiment(int problemSize) {
        list = ArrayListGenerator.generateDescendingDeltaSortedArrayList(problemSize, problemSize / 10);
        HeapSortTimingExperiment.problemSize = problemSize;
    }

    /**
     * Runs the put method for a hash table containing problemSize elements.
     */
    protected void runComputation() {
        DeltaSorter.sort(list, problemSize / 10);
    }
}
