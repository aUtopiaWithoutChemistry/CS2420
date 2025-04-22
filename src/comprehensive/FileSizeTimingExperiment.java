package comprehensive;

import timing.TimingExperiment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class FileSizeTimingExperiment extends TimingExperiment {
    private static String problemSizeDescription = "delta size";
    private static int problemSizeMin = 1000;
    private static int problemSizeCount = 100;
    private static int problemSizeStep = 1000;
    private static int experimentIterationCount = 50;

    private static String path = "src/comprehensive/test.txt";
    private static final int distinctWordCount = 500;
    private static MarkovChain ai;

    public static void main(String[] args) {
        FileSizeTimingExperiment time = new FileSizeTimingExperiment();
        time.printResults();
    }

    public FileSizeTimingExperiment() {
        super(problemSizeDescription, problemSizeMin, problemSizeCount, problemSizeStep, experimentIterationCount);
    }

    /**
     * Creates a hash table of problemSize StudentBadHash elements.
     *
     * @param problemSize - the number of unique students to fill the hash table
     */
    protected void setupExperiment(int problemSize) {
        File file = new File(path);
        try {
            FileWriter fw = new FileWriter(file);
            Random rng = new Random();
            fw.write("1 ");
            for (int i = 0; i < problemSize; i++) {
                fw.write(rng.nextInt(distinctWordCount) + " ");
            }
            ai = new MarkovChain(path);
        } catch (IOException e) {
            System.out.println("file not found");
        }
    }

    /**
     * Runs the put method for a hash table containing problemSize elements.
     */
    protected void runComputation() {
        ai.generate("1", 2000, "random");
    }
}
