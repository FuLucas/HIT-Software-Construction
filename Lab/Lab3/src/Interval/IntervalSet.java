package Interval;

import java.util.*;

public interface IntervalSet<L> {

    /**
     * Create an empty object
     * @param <L> Label type, must be immutable
     * @return A collection of labeled time periods
     */
    public static <L> IntervalSet<L> empty() {
    	return new CommonIntervalSet<L>();
    }

    /**
     * Insert a new time period and label in the current object
     * @param start Starting time
     * @param end End Time
     * @param label Time period label
     * @throws Exception
     */
    public boolean insert(long start, long end, L label) throws Exception;

    
    /**
     * Get the label collection in the current object
     * @return Label collection
     */
    public Set<L> label();

    /**
     * Remove the time period associated with a label from the current object
     * @param label Related tags for the time period to be removed
     * @return If it is successful, return true, otherwise return false
     */
    public boolean remove(L label);

    /**
     * Returns the start time of the time period corresponding to a label
     * @param label Corresponding label
     * @return Return to start time
     */
    public long start(L label);

    /**
     * Returns the end time of the time period corresponding to a label
     * @param label Corresponding label
     * @return Return to end time
     */
    public long end(L label);

    /**
     * Return the entire list of stored contents
     * @return immutable
     */
    public List<Interval<L>> getIntervals();

    /**
     * Change an old label in the collection to a new label
     * @param oldlabel Old label
     * @param newlabel New label
     * @return Returns true if the change is successful, otherwise returns false
     * @throws Exception
     */
    public boolean changeLabel(L oldlabel, L newlabel) throws Exception;

    /**
     * check if the interval set contains any intervals
     * @return true if the interval contains nothing, otherwise returns false
     */
    public boolean isEmpty();

}