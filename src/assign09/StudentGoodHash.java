package assign09;

import java.text.DecimalFormat;

/**
 * This class provides a simple representation for a University of Utah student.
 * Object's hashCode method is overridden with a correct hash function for this
 * object, but one that does a good job of distributing students in a hash
 * table.
 *
 * @author Zifan Zuo and Xinrui Ou
 * @version 2025-04-01
 */
public class StudentGoodHash {
    private int uid;
    private String firstName;
    private String lastName;

    /**
     * Creates a new student with the specified uid, firstName, and lastName.
     *
     * @param uid
     * @param firstName
     * @param lastName
     */
    public StudentGoodHash(int uid, String firstName, String lastName) {
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Getter for this Student's UID.
     *
     * @return the UID for this object
     */
    public int getUid() {
        return this.uid;
    }

    /**
     * Getter for this Student's first name.
     *
     * @return the first name for this object
     */

    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Getter for this Student's last name.
     *
     * @return the last name for this object
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Determines whether the given object is the same as this Student.
     *
     * @return true if both objects have the same UID, first name, and last name; false otherwise
     */
    public boolean equals(Object other) {
        // change to StudentMediumHash and StudentGoodHash for two new classes
        if(!(other instanceof StudentGoodHash))
            return false;

        StudentGoodHash rhs = (StudentGoodHash) other;

        return this.uid == rhs.uid && this.firstName.equals(rhs.firstName) && this.lastName.equals(rhs.lastName);
    }

    /**
     * Generates a textual representation of this Student.
     *
     * @return a textual representation of this object
     */
    public String toString() {
        DecimalFormat formatter = new DecimalFormat("0000000");
        return firstName + " " + lastName + " (u" + formatter.format(uid) + ")";
    }

    /**
     * The hashCode method for StudentGoodHash class, this will return a combination
     * of this student's uid, the hashCode of this student's first name, and the
     * hashCode of this student's last name. Although we should believe the student's
     * uid is unique, but this combination can tell us some wrong within the system, like
     * two different student accidentally get the same uid.
     *
     * @return a good quality of hashCode
     */
    public int hashCode() {
        int result = Integer.hashCode(uid);
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        return result;
    }
}
