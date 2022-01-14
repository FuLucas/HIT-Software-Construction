package Interval;

import java.util.*;

public class CommonIntervalSet<L> implements IntervalSet<L> {

    protected final List<Interval<L>> intervals = new ArrayList<Interval<L>>();

    // Abstraction function:
    // AF(intervals) = the list of intervals in the interval set

    // Representation invariant:
    // each interval has a unique label
    // the start time of the interval should be <= the end time

    // Safety from rep exposure:
    // Check the rep invariant is true. That means setting 
    // list intervals unchangeable / private / final
    // and return value MUST be immutable

    // Constructor
    public CommonIntervalSet() {}

    // checkRep()
    private void checkRep() {
        assert intervals.size() == this.label().size();
        for (Interval<L> interval : intervals) {
            assert interval.start() < interval.end();
        }
    }

    @Override
    public boolean insert(long start, long end, L label) throws Exception {
        if (!label().contains(label) && start <= end) {
            Interval<L> newInterval = new Interval<L>(start, end, label);
            intervals.add(newInterval);
            checkRep();
            return true;
        }
        checkRep();
        return false;
    }

    @Override
    public Set<L> label() {
        Set<L> labels = new HashSet<L>();
        for (Interval<L> I : intervals) {
            labels.add(I.label());
        }
        return labels;
    }

    @Override
    public boolean remove(L label) {
        return intervals.removeIf(interval->interval.label().equals(label));
    }

    @Override
    public long start(L label) {
        for (Interval<L> I : intervals) {
            if (I.label().equals(label))
                return I.start();
        }
        return -1;
    }

    @Override
    public long end(L label) {
        for (Interval<L> I : intervals) {
            if (I.label().equals(label))
                return I.end();
        }
        return -1;
    }

    @Override
    public List<Interval<L>> getIntervals() {
        List<Interval<L>> ret = new ArrayList<Interval<L>>();
        for (Interval<L> I : intervals)
            ret.add(I);
        return ret;
    }

    @Override
    public boolean changeLabel(L oldlabel, L newlabel) throws Exception {
        if (!this.label().contains(oldlabel))
            return false;
        if (oldlabel.equals(newlabel))
            return true;
        long start = this.start(oldlabel);
        long end = this.end(oldlabel);
        this.remove(oldlabel);
        return this.insert(start, end, newlabel);
    }

    @Override
    public boolean isEmpty() {
        return intervals.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(intervals.toString());
        return sb.toString();
    }
}
