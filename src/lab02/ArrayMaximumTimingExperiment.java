package lab02;

import java.util.Arrays;
import java.util.Random;

/**
 * This class defines a timing experiment to measure the running time for
 * finding the maximum integer in arrays of various sizes.
 *
 * @author CS 2420 course staff
 * @version January 17, 2025
 */
public class ArrayMaximumTimingExperiment {

	private Integer[] array;
	private final static Random rng = new Random();
	private int arraySizeMin;
	private int arraySizeCount;
	private int arraySizeStep;
	private int experimentIterationCount;

	public static void main(String[] args) {
		int arraySizeMin = 200000;
		int arraySizeCount = 30;
		int arraySizeStep = 10000;
		int experimentIterationCount = 50;

		// Instantiate the timing experiment.
		ArrayMaximumTimingExperiment timingExperiment = new ArrayMaximumTimingExperiment(arraySizeMin, arraySizeCount, arraySizeStep, experimentIterationCount);
		// Instantiate the timing experiment.
//		ArrayMaximumTimingExperiment timingExperiment = new ArrayMaximumTimingExperiment();

		// Run the experiment and print the results.
		timingExperiment.printResults();
	}

	/**
	 * Constructs a timing experiment for computing the maximum integer in an array.
	 *
	 * @param arraySizeMin - minimum array size
	 * @param arraySizeCount - number of array sizes to use in the experiment
	 * @param arraySizeStep - step size between consecutive array sizes
	 * @param experimentIterationCount - number of times to run computation for a given array size
	 */
	public ArrayMaximumTimingExperiment(int arraySizeMin, int arraySizeCount, int arraySizeStep, int experimentIterationCount) {
		this.arraySizeMin = arraySizeMin;
		this.arraySizeCount = arraySizeCount;
		this.arraySizeStep = arraySizeStep;
		this.experimentIterationCount = experimentIterationCount;
	}

	/**
	 * Runs the timing experiment and prints the results.
	 */
	public void printResults() {
		System.out.println("array size\ttime (ns)");

		// Initialize the array size at the minimum value.
		int arraySize = arraySizeMin;

		// Iterate through the array sizes.
		for(int i = 0; i < arraySizeCount; i++) {
			// Compute the median elapsed time for the given array size.
			long medianElapsedTime = computeMedianElapsedTime(arraySize);

			// Print the results.
			System.out.println(arraySize + "\t\t" + medianElapsedTime);

			// Increment the array size.
			arraySize += arraySizeStep;
		}
	}

	/**
	 * Computes the median time elapsed for finding the maximum of an array.
	 *
	 * @param arraySize - the size of the array
	 * @return the median elapsed time
	 */
	private long computeMedianElapsedTime(int arraySize) {
		long[] elapsedTimes = new long[experimentIterationCount];
		for(int i = 0; i < experimentIterationCount; i++) {
			elapsedTimes[i] = computeElapsedTime(arraySize);
		}
		Arrays.sort(elapsedTimes);
		return elapsedTimes[experimentIterationCount / 2];
	}

	/**
	 * Computes the time elapsed for finding the maximum of an array.
	 *
	 * @param arraySize - size of the array
	 * @return the time elapsed
	 */
	private long computeElapsedTime(int arraySize) {
		setupArray(arraySize);

		long startTime = System.nanoTime();
		runComputation();
		long endTime = System.nanoTime();

		return endTime - startTime;
	}

	/**
	 * Populates the array with random integers.
	 * 
	 * @param arraySize - size of the array
	 */
	private void setupArray(int arraySize) {
		array = new Integer[arraySize];
		for(int i = 0; i < arraySize; i++) {
			array[i] = rng.nextInt();
		}
	}

	/**
	 * Runs the computeMaximum method for the array.
	 */
	private void runComputation() {
		ArrayUtility.computeMaximum(array);
	}
}