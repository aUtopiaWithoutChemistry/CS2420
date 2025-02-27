package assign06;

import timing.TimingExperiment;

import java.util.Random;

public class ArrayStackPushTimingExperiment extends TimingExperiment {
    private static String problemSizeDescription = "List Size";  // TODO
    private static int problemSizeMin = 10000;  // TODO
    private static int problemSizeCount = 50;  // TODO
    private static int problemSizeStep = 10000;  // TODO
    private static int experimentIterationCount = 500;  // TODO

    private final Random rng = new Random();
    protected Stack<Integer> stack;

    public static void main(String[] args) {
        ArrayStackPushTimingExperiment timingExperiment = new ArrayStackPushTimingExperiment();
        timingExperiment.printResults();
    }

    public ArrayStackPushTimingExperiment() {
        super(problemSizeDescription, problemSizeMin, problemSizeCount, problemSizeStep, experimentIterationCount);
    }

    /**
     * Fills the SinglyLinkedList with the given number of integers.
     *
     * @param problemSize - the number of integers to fill the linked list
     */
    protected void setupExperiment(int problemSize) {
        stack = new ArrayStack<>();
        for (int i = 0; i < problemSize; i++) {
            stack.push(rng.nextInt());
        }
    }

    /**
     * Runs the filterOutEvenNumbersFromLinkedList method.
     */
    @Override
    protected void runComputation() {
        stack.push(rng.nextInt());
    }
}