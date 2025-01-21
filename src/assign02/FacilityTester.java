package assign02;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains tests for Facility.
 *
 * @author CS 2420 course staff, Zifan Zuo and Mi Zeng
 * @version Jan 20, 2025
 */
public class FacilityTester {

    private Facility emptyFacility, verySmallFacility, smallFacility, largeFacility;
    private UHealthID uHID1, uHID2, uHID3;
    private GregorianCalendar date1, date2, date3;

    @BeforeEach
    void setUp() throws Exception {

        uHID1 = new UHealthID("AAAA-1111");
        uHID2 = new UHealthID("BCBC-2323");
        uHID3 = new UHealthID("HRHR-7654");

        date1 = new GregorianCalendar(2023, 0, 1);
        date2 = new GregorianCalendar(2023, 3, 17);
        date3 = new GregorianCalendar(2022, 8, 21);

        emptyFacility = new Facility();

        verySmallFacility = new Facility();
        verySmallFacility.addPatient(new CurrentPatient("Jane", "Doe", uHID1, 1010101, date1));
        verySmallFacility.addPatient(new CurrentPatient("Drew", "Hall", uHID2, 3232323, date2));
        verySmallFacility.addPatient(new CurrentPatient("Riley", "Nguyen", uHID3, 9879876, date3));

        smallFacility = new Facility();
        smallFacility.addAll(readFromFile("src/assign02/small_patient_list.txt"));

        // Extend this tester to add more tests for the facilities above,
        // as well as to create and test larger facilities.
        // (HINT: For a larger facility, use the helpers at the end of this file to
        //        generate names, IDs, and dates.)

        largeFacility = new Facility();
        int testSize = 1000;
        String[] names = generateNames(testSize * 2);
        UHealthID[] ids = generateUHIDs(testSize);
        Random physicians = new Random();
        GregorianCalendar[] dates = generateDates(testSize);
        for (int i = 0; i < testSize; i++) {
            largeFacility.addPatient(new CurrentPatient(names[i], names[testSize - 1 - i], ids[i], 100000 + physicians.nextInt(900000), dates[i]));
        }
    }

    // Empty Facility tests --------------------------------------------------------

    @Test
    public void testEmptyLookupUHID() {
        assertNull(emptyFacility.lookupByUHID(uHID1));
    }

    @Test
    public void testEmptyLookupPhysician() {
        ArrayList<CurrentPatient> patients = emptyFacility.lookupByPhysician(1010101);
        assertEquals(0, patients.size());
    }

    @Test
    public void testEmptySetVisit() {
        // ensure no exceptions thrown
        emptyFacility.setLastVisit(uHID2, date3);
    }

    @Test
    public void testEmptySetPhysician() {
        // ensure no exceptions thrown
        emptyFacility.setPhysician(uHID2, 1010101);
    }

    @Test
    public void testEmptyGetRecentPatients() {
        ArrayList<CurrentPatient> patients = emptyFacility.getRecentPatients(date3);
        assertEquals(0, patients.size());
    }

    // Very small facility tests ---------------------------------------------------

    @Test
    public void testVerySmallLookupUHID() {
        Patient expected = new Patient("Drew", "Hall", new UHealthID("BCBC-2323"));
        CurrentPatient actual = verySmallFacility.lookupByUHID(new UHealthID("BCBC-2323"));
        assertEquals(expected, actual);
    }

    @Test
    public void testVerySmallLookupPhysicianCount() {
        ArrayList<CurrentPatient> actualPatients = verySmallFacility.lookupByPhysician(9879876);
        assertEquals(1, actualPatients.size());
    }

    @Test
    public void testVerySmallLookupPhysicianPatient() {
        Patient expectedPatient = new Patient("Riley", "Nguyen", new UHealthID("HRHR-7654"));
        ArrayList<CurrentPatient> actualPatients = verySmallFacility.lookupByPhysician(9879876);
        assertEquals(expectedPatient, actualPatients.get(0));
    }

    @Test
    public void testVerySmallAddNewPatient() {
        assertTrue(verySmallFacility.addPatient(new CurrentPatient("Jane", "Doe", new UHealthID("BBBB-2222"), 1010101, date1)));
    }

    @Test
    public void testVerySmallUpdatePhysician() {
        verySmallFacility.lookupByUHID(uHID1).updatePhysician(9090909);
        CurrentPatient patient = verySmallFacility.lookupByUHID(uHID1);
        assertEquals(9090909, patient.getPhysician());
    }

    // Small facility tests -------------------------------------------------------------------------
    @Test
    public void testSmallGetRecentPatients() {
        ArrayList<CurrentPatient> actual = smallFacility.getRecentPatients(new GregorianCalendar(2020, 0, 0));
        assertEquals(2, actual.size());
    }

    // Add more tests for small facility
    @Test
    public void testSmallLookupUHID() {
        Patient expected = new Patient("Blake", "Bird", new UHealthID("JHSD-7483"));
        CurrentPatient actual = smallFacility.lookupByUHID(new UHealthID("JHSD-7483"));
        assertEquals(expected, actual);
    }

    @Test
    public void testSmallLookupPhysicianCount() {
        ArrayList<CurrentPatient> actualPatients = smallFacility.lookupByPhysician(8888888);
        assertEquals(2, actualPatients.size());
    }

    @Test
    public void testSmallLookupPhysicianPatient() {
        Patient expectedPatient = new Patient("Blake", "Bird", new UHealthID("JHSD-7483"));
        ArrayList<CurrentPatient> actualPatients = smallFacility.lookupByPhysician(0000000);
        assertEquals(expectedPatient, actualPatients.get(0));
    }

    @Test
    public void testSmallAddNewPatient() {
        assertTrue(smallFacility.addPatient(new CurrentPatient("Jane", "Doe", new UHealthID("BBBB-2222"), 1010101, date1)));
    }

    @Test
    public void testSmallUpdatePhysician() {
        smallFacility.lookupByUHID(new UHealthID("JHSD-7483")).updatePhysician(9090909);
        CurrentPatient patient = smallFacility.lookupByUHID(new UHealthID("JHSD-7483"));
        assertEquals(9090909, patient.getPhysician());
    }

    // Large facility tests ------------------------------------------------------
    @Test
    public void testLargeAddPatient() {
        assertTrue(largeFacility.addPatient(new CurrentPatient("Jane", "Doe", new UHealthID("BBBB-2222"), 1010101, date1)));
    }

    @Test
    public void testLargeAddAll() {
        ArrayList<CurrentPatient> patients = smallFacility.getRecentPatients(new GregorianCalendar(1970, 1, 1));
        largeFacility.addAll(patients);
        ArrayList<CurrentPatient> largePatients = largeFacility.getRecentPatients(new GregorianCalendar(1970, 1, 1));
        assertEquals(1011, largePatients.size());
    }

    @Test
    public void testLargeLookupByUHID() {
        CurrentPatient expected = new CurrentPatient("Blake", "Bird", new UHealthID("JHSD-7483"), 0000000, date1);
        largeFacility.addPatient(expected);
        CurrentPatient actual = largeFacility.lookupByUHID(new UHealthID("JHSD-7483"));
        assertEquals(expected, actual);
    }

    @Test
    public void testLargeLookupByPhysician() {
        CurrentPatient expectedPatient = new CurrentPatient("Blake", "Bird", new UHealthID("JHSD-7483"), 0000000, date2);
        largeFacility.addPatient(expectedPatient);
        ArrayList<CurrentPatient> actualPatients = largeFacility.lookupByPhysician(0000000);
        assertEquals(expectedPatient, actualPatients.get(0));
    }

    @Test
    public void testLargeGetRecentPatients1970() {
        ArrayList<CurrentPatient> allPatients = largeFacility.getRecentPatients(new GregorianCalendar(1970, 1, 1));
        assertEquals(allPatients.size(), 1000);
    }

    @Test
    public void testLargeGetRecentPatients2025() {
        ArrayList<CurrentPatient> allPatients = largeFacility.getRecentPatients(new GregorianCalendar(2025, 1, 1));
        int totalAmount = allPatients.size();
        largeFacility.addPatient(new CurrentPatient("aa", "bb", uHID1, 0000000, new GregorianCalendar(2026, 2, 8)));
        ArrayList<CurrentPatient> newPatients = largeFacility.getRecentPatients(new GregorianCalendar(2025, 1, 1));
        assertEquals(newPatients.size(), totalAmount + 1);
    }

    @Test
    public void testLargeGetRecentPatientsNoneExist() {
        ArrayList<CurrentPatient> allPatients = largeFacility.getRecentPatients(new GregorianCalendar(1970, 1, 1));
        Collections.sort(allPatients, Comparator.comparing(CurrentPatient::getLastVisit));
        GregorianCalendar lastDate = allPatients.getLast().getLastVisit();
        assertEquals(largeFacility.getRecentPatients(lastDate).size(), 0);
    }

    @Test
    public void testLargeGetPhysicianList() {
        ArrayList<Integer> physicians = largeFacility.getPhysicianList();
        assertNotEquals(physicians.size(), 0);
    }

    @Test
    public void testLargeGetPhysicianListNoneDuplicates() {
        ArrayList<Integer> physicians = largeFacility.getPhysicianList();
        Collections.sort(physicians);
        for (int i = 0; i < physicians.size() - 2; i++) {
            assertNotEquals(physicians.get(i), physicians.get(i + 1));
        }
    }

    @Test
    public void testLargeSetPhysician() {
        int targetPhysician = 1000000;
        ArrayList<Integer> physicians = largeFacility.getPhysicianList();
        while (physicians.contains(targetPhysician)) {
            targetPhysician += 1;
        }
        assertEquals(largeFacility.lookupByPhysician(targetPhysician).size(), 0);
        UHealthID patientID = new UHealthID("JHSD-7483");
        CurrentPatient expectedPatient = new CurrentPatient("Blake", "Bird", patientID, targetPhysician, date2);
        largeFacility.addPatient(expectedPatient);
        assertEquals(largeFacility.lookupByPhysician(targetPhysician).size(), 1);
    }

    @Test
    public void testLargeSetLastVisit() {
        ArrayList<CurrentPatient> allPatients = largeFacility.getRecentPatients(new GregorianCalendar(1970, 1, 1));
        Collections.sort(allPatients, Comparator.comparing(CurrentPatient::getLastVisit));
        CurrentPatient luckyDog = allPatients.getLast();
        GregorianCalendar lastDate = luckyDog.getLastVisit();
        GregorianCalendar newLastDate = (GregorianCalendar)lastDate.clone();
        newLastDate.add(Calendar.DATE, 1);
        largeFacility.setLastVisit(luckyDog.getUHealthID(), newLastDate);
        assertEquals(largeFacility.getRecentPatients(lastDate).size(), 1);
    }

    // Helper methods ------------------------------------------------------------

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

    /**
     * Adds the patients specified by the input file to a list.
     * <p>
     * Assumes a strict file format:
     * (each line): FirstName LastName ABCD-0123 u0123456 2023 05 16
     * <p>
     * Also assumes there are no duplicate patients in the file.
     *
     * @param filename - full or relative path to file containing patient data
     */
    public ArrayList<CurrentPatient> readFromFile(String filename) {
        ArrayList<CurrentPatient> patients = new ArrayList<CurrentPatient>();
        try {
            Scanner fileIn = new Scanner(new File(filename));
            int lineNumber = 0;

            while (fileIn.hasNextLine()) {
                String line = fileIn.nextLine();
                lineNumber++;
                patients.add(parsePatient(line, lineNumber));
            }
            fileIn.close();
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage() + "  Patient file couldn't be opened.");
        } catch (ParseException e) {
            System.err.println(e.getLocalizedMessage() + " formatted incorrectly at line " + e.getErrorOffset()
                    + ". Not all patients added to list.");
        }
        return patients;
    }

    /**
     * Helper method for parsing the information about a patient from file.
     *
     * @param line       - string to be parsed
     * @param lineNumber - line number in file, used for error reporting (see above)
     * @return the Patient constructed from the information
     * @throws ParseException if file containing line is not properly formatted (see above)
     */
    private CurrentPatient parsePatient(String line, int lineNumber) throws ParseException {
        Scanner lineIn = new Scanner(line);
        lineIn.useDelimiter(" ");

        if (!lineIn.hasNext()) {
            lineIn.close();
            throw new ParseException("First name", lineNumber);
        }
        String firstName = lineIn.next();

        if (!lineIn.hasNext()) {
            lineIn.close();
            throw new ParseException("Last name", lineNumber);
        }
        String lastName = lineIn.next();

        if (!lineIn.hasNext()) {
            lineIn.close();
            throw new ParseException("UHealth ID", lineNumber);
        }
        String patientIDString = lineIn.next();

        if (!lineIn.hasNext()) {
            lineIn.close();
            throw new ParseException("physician", lineNumber);
        }
        String physicianString = lineIn.next();
        int physician = Integer.parseInt(physicianString.substring(1, 8));

        if (!lineIn.hasNext()) {
            lineIn.close();
            throw new ParseException("year of last visit", lineNumber);
        }
        String yearString = lineIn.next();
        int year = Integer.parseInt(yearString);

        if (!lineIn.hasNext()) {
            lineIn.close();
            throw new ParseException("month of last visit", lineNumber);
        }
        String monthString = lineIn.next();
        int month = Integer.parseInt(monthString);

        if (!lineIn.hasNext()) {
            lineIn.close();
            throw new ParseException("day of last visit", lineNumber);
        }
        String dayString = lineIn.next();
        int day = Integer.parseInt(dayString);

        GregorianCalendar lastVisit = new GregorianCalendar(year, month, day);

        lineIn.close();

        return new CurrentPatient(firstName, lastName, new UHealthID(patientIDString),
                physician, lastVisit);
    }
}
