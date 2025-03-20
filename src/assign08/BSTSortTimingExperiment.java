package assign08;

import java.util.ArrayList;

import timing.ArrayListGenerator;
import timing.TimingExperiment;

/**
 * Experiment to measure the running times of adding a nearly-ascending ordered collection
 * of elements to a BST via addAll, for various problem sizes.
 *
 * @author CS 2420 course staff
 * @version March 6, 2025
 */
public class BSTSortTimingExperiment extends TimingExperiment {

    private static String problemSizeDescription = "element count";
    private static int problemSizeMin = 1000;
    private static int problemSizeCount = 50;
    private static int problemSizeStep = 1000;
    // Due to larger running times, this iteration count can be less than for BSTAddAllPermutedTimingExperiment.
    private static int experimentIterationCount = 100;

    protected BinarySearchTree<Integer> sortedSet;
    protected ArrayList<Integer> elements;

    public static void main(String[] args) {
        TimingExperiment timingExperiment = new BSTSortTimingExperiment();
        timingExperiment.printResults();
    }

    public BSTSortTimingExperiment() {
        super(problemSizeDescription, problemSizeMin, problemSizeCount, problemSizeStep, experimentIterationCount);
    }

    /**
     * Creates an empty BST and generates an ArrayList with problemSize random integers in nearly
     * ascending order, with each element unique.
     *
     * @param problemSize - the number of integers to fill the ArrayList
     */
    protected void setupExperiment(int problemSize) {
        sortedSet = new BinarySearchTree<Integer>();
        elements = ArrayListGenerator.generatePermutedArrayListWithoutDuplicates(problemSize);
        sortedSet.addAll(elements);
    }

    /**
     * Runs the addAll method for the BST.
     */
    protected void runComputation() {
        for(int i = 0; i < elements.size(); i++) {
            Integer element = sortedSet.first();
            elements.set(i, element);
            sortedSet.remove(element);
        }
    }
}