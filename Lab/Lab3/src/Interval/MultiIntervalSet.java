package Interval;

import java.util.*;

public interface MultiIntervalSet<L> {

    // create an empty ∂‘œÛ
    /**
     * Create an empty object
     * @param <L> type of labels
     * @return a new empty 
     */
    public static <L> MultiIntervalSet<L> empty() {
        return new CommonMultiIntervalSet<L>();
    }

    /**
     * Create a non-empty object
     * @param initial Use the data contained in initial to create a non-empty object
     * @throws Exception
     */
    public static <L> MultiIntervalSet<L> create(IntervalSet<L> initial) throws Exception {
        MultiIntervalSet<L> newIntervalSet = new CommonMultiIntervalSet<L>();
        for (L l : initial.label()) {
            long start = initial.start(l);
            long end = initial.end(l);
            newIntervalSet.insert(start, end, l);
        }
        return newIntervalSet;
    }

    /**
     * insert an interval into the interval list
     * @param start long the start time of the interval
     * @param end long the end time of the interval
     * @param label L the label of the interval
     * @return true if insert successfully(follow the rules), false otherwise
     */
    public boolean insert(long start, long end, L label);

    /**
     * Get the label collection in the current object
     * @return The label collection for each time period in the object
     */
    public Set<L> labels();

    /**
     * Remove all time periods associated with a label from the current object
     * @param label Label to remove
     * @return Returns true if the removal is successful, otherwise returns false
     */
    public boolean remove(L label);

    /**
     * Get all the time periods associated with a label from the current object
     * @param label The label of the time period to be obtained
     * @return Time period collection, where the label is the sort sequence number
     * @throws Exception
     */
    public IntervalSet<Integer> intervals(L label) throws Exception;

    /**
     * get all intervals in the MultiIntervalSet
     * @return list of intervals
     */
    public List<Interval<L>> allIntervals();
}