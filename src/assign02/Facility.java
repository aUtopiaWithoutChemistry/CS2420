package assign02;

import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * This class represents a record of patients that have visited a UHealth
 * facility. It maintains a collection of CurrentPatients.
 *
 * @author CS 2420 course staff, Zifan Zuo and Mi Zeng
 * @version Jan 20, 2025
 */
public class Facility {

    private ArrayList<CurrentPatient> patientList;

    /**
     * Creates an empty facility record.
     */
    public Facility() {
        patientList = new ArrayList<CurrentPatient>();
    }

    /**
     * Adds the given patient to the list of patients, avoiding duplicates.
     * Using bisection algorithm to make adding faster.
     *
     * @param patient - patient to be added to this record
     * @return true if the patient was added,
     * false if the patient was not added because they already exist in the record
     */

    public boolean addPatient(CurrentPatient patient) {
        if (patientList.isEmpty()) {
            patientList.add(patient);
        } else {
            int startIndex = 0;
            int endIndex = patientList.size();
            while (startIndex < endIndex) {
                int middle = (startIndex + endIndex) / 2;
                CurrentPatient targetPatient = patientList.get(middle);
                if (patient.equals(targetPatient)) {
                    return false;
                } else if (patient.getLastVisit().equals(targetPatient.getLastVisit())) {
                    patientList.add(middle, patient);
                    return true;
                } else if (patient.getLastVisit().before(targetPatient.getLastVisit())) {
                    endIndex = middle - 1;
                } else if (patient.getLastVisit().after(targetPatient.getLastVisit())) {
                    startIndex = middle + 1;
                }
            }
            patientList.add(startIndex, patient);
        }
        return true;
    }

    /**
     * Adds all patients from the given list to the list of patients.
     *
     * @param patients - list of patients to be added to this record
     */
    public void addAll(ArrayList<CurrentPatient> patients) {
        for (CurrentPatient patient : patients) {
            addPatient(patient);
        }
    }

    /**
     * Retrieves the patient with the given UHealthID.
     *
     * @param patientID - ID of patient to be retrieved
     * @return the patient with the given ID, or null if no such patient
     * exists in the record
     */
    public CurrentPatient lookupByUHID(UHealthID patientID) {
        for (CurrentPatient currentPatient : this.patientList) {
            if (currentPatient.getUHealthID().equals(patientID)) {
                return currentPatient;
            }
        }
        return null;
    }

    /**
     * Retrieves the patient(s) with the given physician.
     *
     * @param physician - physician of patient(s) to be retrieved
     * @return a list of patient(s) with the given physician (in any order),
     * or an empty list if no such patients exist in the record
     */
    public ArrayList<CurrentPatient> lookupByPhysician(int physician) {
        ArrayList<CurrentPatient> patients = new ArrayList<>();
        for (CurrentPatient currentPatient : this.patientList) {
            if (currentPatient.getPhysician() == physician) {
                patients.add(currentPatient);
            }
        }
        return patients;
    }

    /**
     * Retrieves the patient(s) with last visits newer than a given date.
     * Note that GregorianCalendar includes a compareTo method that may be useful.
     * <p>
     * NOTE: If the last visit date equals this date, do not add the patient.
     *
     * @param date - cutoff date earlier than visit date of all returned patients.
     * @return a list of patient(s) with last visit date after cutoff (in any order),
     * or an empty list if no such patients exist in the record
     */
    public ArrayList<CurrentPatient> getRecentPatients(GregorianCalendar date) {
        ArrayList<CurrentPatient> patients = new ArrayList<>();
        for (CurrentPatient currentPatient : this.patientList) {
            if (currentPatient.getLastVisit().after(date)) {
                patients.add(currentPatient);
            }
        }
        return patients;
    }

    /**
     * Retrieves a list of physicians assigned to patients at this facility.
     * <p>
     * NOTE: Do not put duplicates in the list. Make sure each physician
     * is only added once.
     *
     * @return a list of physician(s) assigned to current patients,
     * or an empty list if no patients exist in the record
     */
    public ArrayList<Integer> getPhysicianList() {
        ArrayList<Integer> physicians = new ArrayList<>();
        for (CurrentPatient currentPatient : this.patientList) {
            Integer physician = currentPatient.getPhysician();
            if (!physicians.contains(physician)) {
                physicians.add(physician);
            }
        }
        return physicians;
    }

    /**
     * Sets the physician of a patient with the given UHealthID.
     * <p>
     * NOTE: If no patient with the ID exists in the collection, then this
     * method has no effect.
     *
     * @param patientID - UHealthID of patient to modify
     * @param physician - identifier of patient's new physician
     */
    public void setPhysician(UHealthID patientID, int physician) {
        for (CurrentPatient currentPatient : this.patientList) {
            if (currentPatient.getUHealthID().equals(patientID)) {
                currentPatient.updatePhysician(physician);
            }
        }
    }

    /**
     * Sets the last visit date of a patient with the given UHealthID.
     * <p>
     * NOTE: If no patient with the ID exists in the collection, then this
     * method has no effect.
     *
     * @param patientID - UHealthID of patient to modify
     * @param date      - date of last visit
     */
    public void setLastVisit(UHealthID patientID, GregorianCalendar date) {
        for (CurrentPatient currentPatient : this.patientList) {
            if (currentPatient.getUHealthID().equals(patientID)) {
                currentPatient.updateLastVisit(date);
            }
        }
    }
}
