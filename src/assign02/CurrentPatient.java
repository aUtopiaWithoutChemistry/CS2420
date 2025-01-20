package assign02;

import java.util.GregorianCalendar;

/**
 * This class is a subclass of Patient dealing with the current patient,
 * which contains an assigned physician and date of last visit.
 *
 * @author Zifan Zuo
 * @version 2025-01-20
 */
public class CurrentPatient extends Patient{

    private int physician;
    private GregorianCalendar lastVisit;

    /**
     * Creates a patient with a given name and ID.
     *
     * @param firstName - of the patient
     * @param lastName  - of the patient
     * @param uHealthID - of the patient
     * @param physician - the uid of the physician
     * @param lastVisit - the date of patient's last visiting
     */
    public CurrentPatient(String firstName, String lastName, UHealthID uHealthID, int physician, GregorianCalendar lastVisit) {
        super(firstName, lastName, uHealthID);
        this.physician = physician;
        this.lastVisit = lastVisit;
    }

    /**
     * Getter method for the physician's uid of CurrentPatient class.
     *
     * @return this CurrentPatient's physician's uid
     */
    public int getPhysician() {
        return this.physician;
    }

    /**
     * Getter method for the last visit time of CurrentPatient class.
     *
     * @return this CurrentPatient's last date of visiting.
     */
    public GregorianCalendar getLastVisit() {
        return this.lastVisit;
    }

    /**
     * Setter method for the physician's uid of CurrentPatient class.
     *
     * @param newPhysician the uid of new assigned physician
     */
    public void updatePhysician(int newPhysician) {
        this.physician = newPhysician;
    }

    /**
     * Setter method for the lastVisit of CurrentPatient class.
     *
     * @param date the new date for lastVisit.
     */
    public void updateLastVisit(GregorianCalendar date) {
        this.lastVisit = date;
    }
}