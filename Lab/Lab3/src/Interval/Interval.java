package Interval;

public class Interval<L> implements Comparable<Interval<L>>{
    // fields
    private final long start;
    private final long end;
    private final L label;

    // Abstraction function:
    // AF(start) = the start time of the interval
    // AF(end) = the end time of the interval
    // AF(label) = the label of the interval

    // Representation invariant:
    // the start time of the interval should be earlier than the end time
    // both the start and end time should be above 0  
    
    // Safety from rep exposure:
    // Check the rep invariant is true, but list and set are mutable
    // All fields MUST be private
    // So make defensive copies instead of just return mutable data 

    /**
     * constructor
     * @param start long start time of the interval
     * @param end long end time of the interval
     * @param label L label of the interval
     */
    public Interval(long start, long end, L label) {
        this.start = start;
        this.end = end;
        this.label = label;
        checkRep();
    }

    // checkRep()
    private void checkRep() {
        assert start < end;
        assert start >= 0;
    }

    /**
     * get the start time of the interval
     * @return long start
     */
    public long start() {
        checkRep();
        return start;
    }

    /**
     * get the end time of the interval
     * @return long end
     */
    public long end() {
        checkRep();
        return end;
    }

    /**
     * get the label of the interval
     * @return L label
     */
    public L label() {
        checkRep();
        return label;
    }

    /**
     * check if the interval has any repeated period with given one
     * @param start long start time of the unchecked interval
     * @param end long end time of the unchecked interval
     * @return true if the two intervales have repeated part, false otherwise
     */
    public boolean hasRepeatedPeriod(long start, long end) {
        checkRep();
        if (start > end) return false;
        return !(start >= this.end || end <= this.start);
    }

    @Override
    public int compareTo(Interval<L> o) {
        if (this.start < o.start) return -1;
        else if (this.start > o.start) return 1;
        else return 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[" + this.start + ", " + this.end + ", " + this.label.toString() + "]");
        return sb.toString();
    }

}

