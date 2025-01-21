package assign02;

import java.util.Comparator;

/**
 * Comparator that defines an ordering among current patients using their Name.
 * More specifically, the comparison is based on the string representation of their
 * name. If their first name is equal, then compare their last name, if last names are
 * still equal, then compare UHealthID.
 *
 * @author Zifan Zuo and Mi Zeng
 * @version Jan 20, 2025
 */
public class OrderByName<Type> implements Comparator<CurrentPatientGeneric<Type>> {
    /**
     * Returns a negative value if lhs (left-hand side) is less than rhs (right-hand side).
     * Returns a positive value if lhs is greater than rhs.
     * Returns 0 if lhs and rhs are equal.
     *
     * @return an int as described herein
     */
    @Override
    public int compare(CurrentPatientGeneric<Type> lhs, CurrentPatientGeneric<Type> rhs) {
        if (lhs.getFirstName().equals(rhs.getFirstName())) {
            if (lhs.getLastVisit().equals(rhs.getLastVisit())) {
                return lhs.getUHealthID().toString().compareTo(rhs.getUHealthID().toString());
            } else {
                return lhs.getLastVisit().compareTo(rhs.getLastVisit());
            }
        } else {
            return lhs.getFirstName().compareTo(rhs.getFirstName());
        }
    }
}
