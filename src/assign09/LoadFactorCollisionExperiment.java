package assign09;

import timing.ArrayListGenerator;

import java.util.ArrayList;

public class LoadFactorCollisionExperiment extends CollisionExperiment<Integer, Integer>{
    private static String problemSizeDescription = "element count";
    private static int problemSizeMin = 1000;
    private static int problemSizeCount = 30;
    private static int problemSizeStep = 1000;

    // Counting collisions is more precise than collecting running times; therefore,
    // we need only a few iterations for each problem size.
    private static int experimentIterationCount = 5;

    private static final double loadFactor = 1000.0;
    private Integer keyToPut;

    public static void main(String[] args) {
        CollisionExperiment<Integer, Integer> collisionExperiment = new LoadFactorCollisionExperiment();
        collisionExperiment.printResults();
    }

    public LoadFactorCollisionExperiment() {
        super(problemSizeDescription, problemSizeMin, problemSizeCount, problemSizeStep, experimentIterationCount);
    }

    /**
     * Creates a hash table of problemSize StudentBadHash elements.
     *
     * @param problemSize - the number of unique students to fill the hash table
     */
    protected void setupExperiment(int problemSize) {
        // Generate enough unique integers to use for each student uid, firstName, lastName
        // to put in a hash table of problemSize, with one additional set for put in runComputation.
        ArrayList<Integer> uniqueIntegers = ArrayListGenerator.generatePermutedArrayListWithoutDuplicates(3 * (problemSize + 1));

        table = new HashTable<>(loadFactor);
        for (int i = 0; i < problemSize; i++) {
            table.put(i, i);
        }
        keyToPut = 114514;
    }

    /**
     * Runs the put method for a hash table containing problemSize elements.
     */
    protected void runComputation() {
        table.put(keyToPut, null);
    }
}
