package assign02;

import java.util.Comparator;

/**
 * Comparator that defines an ordering among current patients using the last date they
 * have been to here.
 *
 * @author Zifan Zuo
 * @version 2025-01-20
 */
public class OrderByDate<Type> implements Comparator<CurrentPatientGeneric<Type>> {
    /**
     * Returns a negative value if lhs (left-hand side) is less than rhs (right-hand side).
     * Returns a positive value if lhs is greater than rhs.
     * Returns 0 if lhs and rhs are equal.
     *
     * @return an int as described herein
     */
    @Override
    public int compare(CurrentPatientGeneric<Type> lhs, CurrentPatientGeneric<Type> rhs) {
        if (lhs.getLastVisit().equals(rhs.getLastVisit())) {
            return lhs.getUHealthID().toString().compareTo(rhs.getUHealthID().toString());
        } else {
            return lhs.getLastVisit().compareTo(rhs.getLastVisit());
        }
    }
}