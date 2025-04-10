package assign10;

import timing.ArrayListGenerator;
import timing.TimingExperiment;

import java.util.List;

public class DeltaSortTimingExperiment extends TimingExperiment {
    private static String problemSizeDescription = "delta size";
    private static int problemSizeMin = 10;
    private static int problemSizeCount = 100;
    private static int problemSizeStep = 10;
    private static int problemSize;
    private static int experimentIterationCount = 50;
    private static int listSize = 100000;

    private static List<Integer> list;

    public static void main(String[] args) {
        DeltaSortTimingExperiment deltaSortTimingExperiment = new DeltaSortTimingExperiment();
        deltaSortTimingExperiment.printResults();
    }

    public DeltaSortTimingExperiment() {
        super(problemSizeDescription, problemSizeMin, problemSizeCount, problemSizeStep, experimentIterationCount);
    }

    /**
     * Creates a hash table of problemSize StudentBadHash elements.
     *
     * @param problemSize - the number of unique students to fill the hash table
     */
    protected void setupExperiment(int problemSize) {
        list = ArrayListGenerator.generateDescendingDeltaSortedArrayList(listSize, problemSize);
        DeltaSortTimingExperiment.problemSize = problemSize;
    }

    /**
     * Runs the put method for a hash table containing problemSize elements.
     */
    protected void runComputation() {
        DeltaSorter.sort(list, problemSize);
    }
}
