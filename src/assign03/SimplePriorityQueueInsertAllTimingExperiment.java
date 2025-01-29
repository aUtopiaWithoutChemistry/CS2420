package assign03;

import timing.TimingExperiment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SimplePriorityQueueInsertAllTimingExperiment extends TimingExperiment {

    private static final String problemSizeDescription = "SPQ size";
    private static final int problemSizeMin = 100;
    private static final int problemSizeCount = 99;
    private static final int problemSizeStep = 50;
    private static final int experimentIterationCount = 100;

    private final static Random rng = new Random();
    private SimplePriorityQueue<Integer> priorityQueue;
    private ArrayList<Integer> targets;

    /**
     * Constructs a general timing experiment.
     */
    public SimplePriorityQueueInsertAllTimingExperiment() {
        super(problemSizeDescription, problemSizeMin, problemSizeCount, problemSizeStep, experimentIterationCount);
    }

    public static void main(String[] args) {
        TimingExperiment timingExperiment = new SimplePriorityQueueInsertAllTimingExperiment();

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
        // Populate targets list with problemSize integers.
        targets = new ArrayList<Integer>();
        for (int i = 0; i < problemSize; i++)
            targets.add(rng.nextInt(100000));

        // create an empty SimplePriorityQueue
        priorityQueue = new SimplePriorityQueue<Integer>();

        // Shuffle elements of the targets array for use in containsAll.
        Collections.shuffle(targets);
    }

    /**
     * Runs the containsAll method for the priority queue.
     */
    @Override
    protected void runComputation() {
        priorityQueue.insertAll(targets);
    }
}
