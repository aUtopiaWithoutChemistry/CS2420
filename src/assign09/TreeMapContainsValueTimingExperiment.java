package assign09;

import java.util.ArrayList;
import java.util.TreeMap;

import timing.ArrayListGenerator;
import timing.TimingExperiment;

/**
 * Experiment to measure the running times of the containsValue method in
 * a hash table of Integer objects, for various problem sizes.
 *
 * @author CS 2420 course staff
 * @version March 20, 2025
 */
public class TreeMapContainsValueTimingExperiment extends TimingExperiment {

    private static String problemSizeDescription = "element count";
    private static int problemSizeMin = 1000;
    private static int problemSizeCount = 50;
    private static int problemSizeStep = 1000;
    private static int experimentIterationCount = 100;

    protected TreeMap<Integer, Integer> map;

    public static void main(String[] args) {
        TimingExperiment timingExperiment = new TreeMapContainsValueTimingExperiment();
        timingExperiment.printResults();
    }

    public TreeMapContainsValueTimingExperiment() {
        super(problemSizeDescription, problemSizeMin, problemSizeCount, problemSizeStep, experimentIterationCount);
    }

    /**
     * Creates a hash table of problemSize Integer elements.
     *
     * @param problemSize - the number of unique students to fill the hash table
     */
    protected void setupExperiment(int problemSize) {
        // Generate problemSize unique integers to put in a hash table of problemSize.
        ArrayList<Integer> uniqueIntegers = ArrayListGenerator.generatePermutedArrayListWithoutDuplicates(problemSize);

        map = new TreeMap<Integer, Integer>();
        for(int i = 0; i < problemSize; i++)
            map.put(uniqueIntegers.get(i), 0);
    }

    /**
     * Runs the containsValue method for a hash table containing problemSize elements.
     */
    protected void runComputation() {
        map.containsValue(1);
    }
}
