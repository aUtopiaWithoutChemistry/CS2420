package assign02;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains tests for FacilityGeneric.
 *
 * @author CS 2420 course staff, Zifan Zuo and Mi Zeng
 * @version Jan 20, 2025
 */
public class FacilityGenericTester {

    private FacilityGeneric<Integer> uIDFacility, emptyFacility, phase3Facility;
    private FacilityGeneric<UHealthID> uHIDFacility;
    private FacilityGeneric<String> nameFacility;

    private UHealthID[] uHIDs;
    private GregorianCalendar[] dates;
    private String[] firstNames, lastNames, physicianNames;
    // For phase 3
    private UHealthID p3id1, p3id2, p3id3, p3id4;
    private GregorianCalendar p3date1, p3date2, p3date3, p3date4;

    @BeforeEach
    void setUp() throws Exception {
        // Modifying these numbers will affect the provided tests
        int nPatients = 20;
        int nPhysicians = 8;

        uHIDs = generateUHIDs(nPatients + nPhysicians);
        dates = generateDates(nPatients);
        firstNames = generateNames(nPatients);
        lastNames = generateNames(nPatients);
        physicianNames = generateNames(nPhysicians);

        uIDFacility = new FacilityGeneric<Integer>();
        uHIDFacility = new FacilityGeneric<UHealthID>();
        nameFacility = new FacilityGeneric<String>();
        emptyFacility = new FacilityGeneric<Integer>();
        phase3Facility = new FacilityGeneric<Integer>();

        for (int i = 0; i < nPatients; i++) {
            uIDFacility.addPatient(new CurrentPatientGeneric<Integer>(
                    firstNames[i], lastNames[i],
                    uHIDs[i], 1234567 + i % nPhysicians, dates[i]));
            uHIDFacility.addPatient(new CurrentPatientGeneric<UHealthID>(
                    firstNames[i], lastNames[i],
                    uHIDs[i], uHIDs[nPatients + i % nPhysicians], dates[i]));
            nameFacility.addPatient(new CurrentPatientGeneric<String>(
                    firstNames[i], lastNames[i],
                    uHIDs[i], physicianNames[i % nPhysicians], dates[i]));
        }

        p3id1 = new UHealthID("XXXX-1111");
        p3id2 = new UHealthID("BBBB-1111");
        p3id3 = new UHealthID("FFFF-1111");
        p3id4 = new UHealthID("BBBB-2222");
        p3date1 = new GregorianCalendar(2019, 1, 5);
        p3date2 = new GregorianCalendar(2019, 1, 4);
        p3date3 = new GregorianCalendar(2019, 1, 3);
        p3date4 = new GregorianCalendar(2019, 1, 2);

        phase3Facility.addPatient(new CurrentPatientGeneric<Integer>("A", "B", new UHealthID("XXXX-1111"), 7, new GregorianCalendar(2019, 1, 5)));
        phase3Facility.addPatient(new CurrentPatientGeneric<Integer>("A", "B", new UHealthID("BBBB-1111"), 7, new GregorianCalendar(2019, 1, 4)));
        phase3Facility.addPatient(new CurrentPatientGeneric<Integer>("A", "C", new UHealthID("FFFF-1111"), 7, new GregorianCalendar(2019, 1, 3)));
        phase3Facility.addPatient(new CurrentPatientGeneric<Integer>("R", "T", new UHealthID("BBBB-2222"), 7, new GregorianCalendar(2019, 1, 2)));

        // Extend this tester to add more tests for the facilities above,
        // as well as to create and test other facilities.
        // (HINT: For a larger facility, use the helpers at the end of this file to
        //        generate names, IDs, and dates.)
    }


    // addPaitent method tests --------------------------------------------------------
    @Test
    public void testAddPatient() {
        FacilityGeneric<String> facility = new FacilityGeneric<>();
        CurrentPatientGeneric<String> testPatient = new CurrentPatientGeneric("Mi", "Zeng", new UHealthID("ABCD-1234"), 7, new GregorianCalendar(2025, 1, 20));

        assertTrue(facility.addPatient(testPatient)); // Adding a new patient should return true
        assertFalse(facility.addPatient(testPatient)); // Adding the same patient again should return false
    }

    // addAll method tests --------------------------------------------------------
    @Test
    public void testAddAll() {
        FacilityGeneric<Integer> facility = new FacilityGeneric<>();
        ArrayList<CurrentPatientGeneric<Integer>> testPatients = new ArrayList<>();
        testPatients.add(new CurrentPatientGeneric("A", "E", new UHealthID("ABCC-1235"), 7, new GregorianCalendar(2025, 1, 20)));
        testPatients.add(new CurrentPatientGeneric("B", "F", new UHealthID("ABCF-1238"), 8, new GregorianCalendar(2025, 1, 21)));
        testPatients.add(new CurrentPatientGeneric("C", "G", new UHealthID("ABCG-1239"), 9, new GregorianCalendar(2025, 1, 22)));
        testPatients.add(new CurrentPatientGeneric("D", "H", new UHealthID("ABCH-1230"), 10, new GregorianCalendar(2025, 1, 23)));
        facility.addAll(testPatients);
        assertEquals(4, facility.getPhysicianList().size());

    }

    @Test
    public void testAddAllRepeatPaitent() {
        FacilityGeneric<Integer> facility = new FacilityGeneric<>();
        ArrayList<CurrentPatientGeneric<Integer>> testPatients = new ArrayList<>();
        testPatients.add(new CurrentPatientGeneric("A", "B", new UHealthID("ABCC-1235"), 7, new GregorianCalendar(2025, 1, 20)));
        testPatients.add(new CurrentPatientGeneric("A", "B", new UHealthID("ABCC-1235"), 7, new GregorianCalendar(2025, 1, 20)));
        testPatients.add(new CurrentPatientGeneric("C", "G", new UHealthID("ABCG-1239"), 7, new GregorianCalendar(2025, 1, 22)));
        testPatients.add(new CurrentPatientGeneric("D", "H", new UHealthID("ABCH-1230"), 7, new GregorianCalendar(2025, 1, 23)));
        facility.addAll(testPatients);
        assertEquals(1, facility.getPhysicianList().size());
    }
//    


    // empty Facility tests --------------------------------------------------------

    @Test
    public void testEmptyLookupUHID() {
        assertNull(emptyFacility.lookupByUHID(uHIDs[0]));
    }

    @Test
    public void testEmptyLookupPhysician() {
        ArrayList<CurrentPatientGeneric<Integer>> patients = emptyFacility.lookupByPhysician(1010101);
        assertEquals(0, patients.size());
    }

    @Test
    public void testEmptySetVisit() {
        // ensure no exceptions thrown
        emptyFacility.setLastVisit(uHIDs[0], dates[3]);
    }

    @Test
    public void testEmptySetPhysician() {
        // ensure no exceptions thrown
        emptyFacility.setPhysician(uHIDs[0], 1010101);
    }

    @Test
    public void testEmptyGetRecentPatients() {
        ArrayList<CurrentPatientGeneric<Integer>> patients = emptyFacility.getRecentPatients(dates[4]);
        assertEquals(0, patients.size());
    }

    @Test
    public void testEmptyAddPatient() {
        CurrentPatientGeneric<Integer> newPatient = new CurrentPatientGeneric<>(
                "Mi", "Zeng", new UHealthID("ABCD-8888"), 1234567, new GregorianCalendar(2025, 1, 22));
        emptyFacility.addPatient(newPatient);
        assertEquals(newPatient, emptyFacility.lookupByUHID(new UHealthID("ABCD-8888")));
    }

    // uID Facility tests --------------------------------------------------------

    @Test
    public void testUIDLookupPhysicianCount() {
        ArrayList<CurrentPatientGeneric<Integer>> actualPatients = uIDFacility.lookupByPhysician(1234568);
        assertEquals(3, actualPatients.size());
    }


    @Test
    public void testUIDLookupPhysicianPatient() {
        Patient expectedPatient = new Patient(firstNames[1], lastNames[1], new UHealthID(uHIDs[1].toString()));
        ArrayList<CurrentPatientGeneric<Integer>> actualPatients = uIDFacility.lookupByPhysician(1234568);
        assertEquals(expectedPatient, actualPatients.get(0));
    }


    @Test
    public void testUIDUpdateVisitDate() {
        uIDFacility.setLastVisit(uHIDs[0], new GregorianCalendar(2025, 1, 20));
        CurrentPatientGeneric<Integer> patient = uIDFacility.lookupByUHID(uHIDs[0]);
        assertEquals(new GregorianCalendar(2025, 1, 20), patient.getLastVisit());
    }


    // UHealthID facility tests ---------------------------------------------------

    @Test
    public void testUHIDLookupPhysicianCount() {
        ArrayList<CurrentPatientGeneric<UHealthID>> actualPatients = uHIDFacility.lookupByPhysician(uHIDs[uHIDs.length - 1]);
        assertEquals(2, actualPatients.size());
    }

    @Test
    public void testUHIDUpdatePhysician() {
        uHIDFacility.lookupByUHID(uHIDs[2]).updatePhysician(uHIDs[uHIDs.length - 1]);
        CurrentPatientGeneric<UHealthID> patient = uHIDFacility.lookupByUHID(uHIDs[2]);
        assertEquals(uHIDs[uHIDs.length - 1], patient.getPhysician());
    }

    @Test
    void testLookupByUHID() {
        FacilityGeneric<String> facility = new FacilityGeneric<>();
        CurrentPatientGeneric<String> testPatient = new CurrentPatientGeneric("Mi", "Zeng", new UHealthID("ABCD-1234"), 7, new GregorianCalendar(2025, 1, 20));

        facility.addPatient(testPatient);
        assertEquals(testPatient, facility.lookupByUHID(new UHealthID("ABCD-1234")));
        assertNull(facility.lookupByUHID(new UHealthID("BFCD-5678")));
    }

    // name facility tests -------------------------------------------------------------------------

    @Test
    public void testNameLookupPhysician() {
        Patient expectedPatient1 = new Patient(firstNames[1], lastNames[1], new UHealthID(uHIDs[1].toString()));
        Patient expectedPatient2 = new Patient(firstNames[9], lastNames[9], new UHealthID(uHIDs[9].toString()));

        ArrayList<CurrentPatientGeneric<String>> actualPatients = nameFacility.lookupByPhysician(physicianNames[1]);
        assertTrue(actualPatients.contains(expectedPatient1) && actualPatients.contains(expectedPatient2));
    }

    @Test
    public void testNameGetPhysicianList() {
        ArrayList<String> actual = nameFacility.getPhysicianList();
        assertEquals(8, actual.size());
    }

    @Test
    public void testNameAddAll() {
        FacilityGeneric<String> facility = new FacilityGeneric<>();
        ArrayList<CurrentPatientGeneric<String>> testPatients = new ArrayList<>();
        testPatients.add(new CurrentPatientGeneric("A", "B", new UHealthID("ABCC-1235"), "A", new GregorianCalendar(2025, 1, 20)));
        testPatients.add(new CurrentPatientGeneric("A", "B", new UHealthID("ABCC-1235"), "A", new GregorianCalendar(2025, 1, 20)));
        testPatients.add(new CurrentPatientGeneric("C", "G", new UHealthID("ABCG-1239"), "A", new GregorianCalendar(2025, 1, 22)));
        testPatients.add(new CurrentPatientGeneric("D", "H", new UHealthID("ABCH-1230"), "A", new GregorianCalendar(2025, 1, 23)));
        facility.addAll(testPatients);
        assertEquals(1, facility.getPhysicianList().size());
    }

    // phase 3 tests ---------------------------------------------------------------------------
    // Uncomment these when you get to phase 3

    @Test
    public void testOrderedByUHIDCount() {
        ArrayList<CurrentPatientGeneric<Integer>> actual = phase3Facility.getOrderedPatients(new OrderByUHealthID<Integer>());
        assertEquals(4, actual.size());
    }

    @Test
    public void testOrderedByUHIDOrder() {
        ArrayList<CurrentPatientGeneric<Integer>> actual = phase3Facility.getOrderedPatients(new OrderByUHealthID<Integer>());
        assertEquals(new CurrentPatientGeneric<Integer>("A", "B", p3id1, 7, p3date1), actual.get(3));
        assertEquals(new CurrentPatientGeneric<Integer>("A", "B", p3id2, 7, p3date2), actual.get(0));
        assertEquals(new CurrentPatientGeneric<Integer>("A", "C", p3id3, 7, p3date3), actual.get(2));
        assertEquals(new CurrentPatientGeneric<Integer>("R", "T", p3id4, 7, p3date4), actual.get(1));
    }


    @Test
    public void testOrderedByDateCount() {
        ArrayList<CurrentPatientGeneric<Integer>> actual = phase3Facility.getOrderedPatients(new OrderByDate<Integer>());
        assertEquals(4, actual.size());
    }

    @Test
    public void testOrderedByDateOrder() {
        ArrayList<CurrentPatientGeneric<Integer>> actual = phase3Facility.getOrderedPatients(new OrderByDate<Integer>());
        assertEquals(new CurrentPatientGeneric<Integer>("R", "T", p3id4, 7, p3date4), actual.get(0));
        assertEquals(new CurrentPatientGeneric<Integer>("A", "C", p3id3, 7, p3date3), actual.get(1));
        assertEquals(new CurrentPatientGeneric<Integer>("A", "B", p3id2, 7, p3date2), actual.get(2));
        assertEquals(new CurrentPatientGeneric<Integer>("A", "B", p3id1, 7, p3date1), actual.get(3));
    }


    // Private helper methods ---------------------------------------------------------------

    /**
     * A private helper to generate unique UHealthIDs.
     * Valid for up to 260,000 IDs.
     *
     * @param howMany - IDs to make
     * @return an array of UHealthIDs
     */
    private UHealthID[] generateUHIDs(int howMany) {
        UHealthID[] ids = new UHealthID[howMany];
        for (int i = 0; i < howMany; i++) {
            String prefix = "JKL" + (char) ('A' + (i / 10000) % 26);
            ids[i] = new UHealthID(prefix + "-" + String.format("%04d", i % 10000));
        }
        return ids;
    }

    /**
     * A private helper to generate dates.
     *
     * @param howMany - dates to generate
     * @return an array of dates
     */
    private GregorianCalendar[] generateDates(int howMany) {
        GregorianCalendar[] dates = new GregorianCalendar[howMany];
        for (int i = 0; i < howMany; i++)
            dates[i] = new GregorianCalendar(2000 + i % 22, i % 12, i % 28);
        return dates;
    }

    /**
     * A private helper to generate names.
     *
     * @param howMany - names to generate
     * @return an array of names
     */
    private String[] generateNames(int howMany) {
        String[] names = new String[howMany];
        Random rng = new Random();
        for (int i = 0; i < howMany; i++)
            names[i] = "" + (char) ('A' + rng.nextInt(26)) + (char) ('a' + rng.nextInt(26))
                    + (char) ('a' + rng.nextInt(26)) + (char) ('a' + rng.nextInt(26));
        return names;
    }
}