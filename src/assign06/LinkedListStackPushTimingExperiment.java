package assign06;

import timing.TimingExperiment;

import java.util.Random;

public class LinkedListStackPushTimingExperiment extends TimingExperiment {
    private static String problemSizeDescription = "List Size";  // TODO
    private static int problemSizeMin = 10000;  // TODO
    private static int problemSizeCount = 100;  // TODO
    private static int problemSizeStep = 10000;  // TODO
    private static int experimentIterationCount = 200;  // TODO

    private final Random rng = new Random();
    protected Stack<Integer> stack;

    public static void main(String[] args) {
        LinkedListStackPushTimingExperiment timingExperiment = new LinkedListStackPushTimingExperiment();
        timingExperiment.printResults();
    }

    public LinkedListStackPushTimingExperiment() {
        super(problemSizeDescription, problemSizeMin, problemSizeCount, problemSizeStep, experimentIterationCount);
    }

    /**
     * Fills the SinglyLinkedList with the given number of integers.
     *
     * @param problemSize - the number of integers to fill the linked list
     */
    @Override
    protected void setupExperiment(int problemSize) {
        stack = new LinkedListStack<>();
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
