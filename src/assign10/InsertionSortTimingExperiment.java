package assign10;

import timing.ArrayListGenerator;
import timing.TimingExperiment;

import java.util.List;

public class InsertionSortTimingExperiment extends TimingExperiment {
    private static String problemSizeDescription = "delta size";
    private static int problemSizeMin = 1000;
    private static int problemSizeCount = 100;
    private static int problemSizeStep = 1000;
    private static int experimentIterationCount = 50;

    private static List<Integer> list;

    public static void main(String[] args) {
        InsertionSortTimingExperiment deltaSortTimingExperiment = new InsertionSortTimingExperiment();
        deltaSortTimingExperiment.printResults();
    }

    public InsertionSortTimingExperiment() {
        super(problemSizeDescription, problemSizeMin, problemSizeCount, problemSizeStep, experimentIterationCount);
    }

    /**
     * Creates a hash table of problemSize StudentBadHash elements.
     *
     * @param problemSize - the number of unique students to fill the hash table
     */
    protected void setupExperiment(int problemSize) {
        list = ArrayListGenerator.generateDescendingDeltaSortedArrayList(problemSize, problemSize / 10);
    }

    /**
     * Runs the put method for a hash table containing problemSize elements.
     */
    protected void runComputation() {
        DeltaSorter.insertionSort(list, (i1, i2) -> i2 - i1);
    }
}
