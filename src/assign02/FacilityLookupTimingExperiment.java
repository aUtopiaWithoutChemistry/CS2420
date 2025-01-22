package assign02;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Random;

import timing.TimingExperiment;

/**
 * Experiment to measure the running time for the lookupByUHID method in 
 * facilities with various numbers of patients.
 *
 * @author CS 2420 course staff, Zifan Zuo and Mi Zeng
 * @version Jan 20, 2025
 */
public class FacilityLookupTimingExperiment extends TimingExperiment {
	private static String problemSizeDescription = "arrayListSize";  // TODO: fill in string appropriately
	private static int problemSizeMin = 100;  // TODO: initialize appropriately (do not use 0)
	private static int problemSizeCount = 100;  // TODO: initialize appropriately (do not use 0)
	private static int problemSizeStep = 100;  // TODO: initialize appropriately (do not use 0)
	private static int experimentIterationCount = 50;

	private Facility randomFacility;
	private UHealthID randomUHealthID;
	private final static Random rng = new Random();

	public static void main(String[] args) {
		TimingExperiment timingExperiment = new FacilityLookupTimingExperiment();

		System.out.println("\n---Computing timing results---\n");
		timingExperiment.printResults();
	}

	public FacilityLookupTimingExperiment() {
		super(problemSizeDescription, problemSizeMin, problemSizeCount, problemSizeStep, experimentIterationCount);
	}

	/**
	 * Fills the Facility with the given number of patients.
	 * 
	 * @param problemSize - the number of patients to add
	 */
	@Override
	protected void setupExperiment(int problemSize) {
		randomFacility = new Facility();
		ArrayList<UHealthID> ids = generateUHIDs(problemSize);
		for(UHealthID id : ids) {
			randomFacility.addPatient(new CurrentPatient("name", "name", id, 1234567, new GregorianCalendar(2025, 1, 1)));
		}
		randomUHealthID = generateUHIDs(1).get(0);
	}

	/**
	 * Runs the lookupByUHID method for the facility.
	 */
	@Override
	protected void runComputation() {
		randomFacility.lookupByUHID(randomUHealthID);
	}

	/**
	 * Generate unique UHealthIDs.
	 * 
	 * @param howMany - IDs to make
	 * @return a list of UHealthIDs
	 */
	private ArrayList<UHealthID> generateUHIDs(int howMany) {
		ArrayList<UHealthID> ids = new ArrayList<UHealthID>(howMany);
		HashSet<UHealthID> idSet = new HashSet<UHealthID>(howMany);
		char[] prefix = new char[4];
		while(idSet.size() < howMany) {
			prefix[0] = (char)('A' + rng.nextInt(26));
			prefix[1] = (char)('A' + rng.nextInt(26));
			prefix[2] = (char)('A' + rng.nextInt(26));
			prefix[3] = (char)('A' + rng.nextInt(26));
			idSet.add(new UHealthID(new String(prefix) + "-" + String.format("%04d", rng.nextInt(10000))));
		}
		for(UHealthID id : idSet) {
			ids.add(id);
		}
		return ids;
	}
}