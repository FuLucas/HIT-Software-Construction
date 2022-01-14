package Interval;

import java.util.*;

public class CommonMultiIntervalSet<L> implements MultiIntervalSet<L> {
    
    private final List<Interval<L>> multiIntervalSets = new ArrayList<Interval<L>>();

    // Abstraction function:
    // AF(multiIntervalSets) = the list of intervals in the MiltiInterval set

    // Representation invariant:
    // intervals' labels can be repeated
    // the start time of the interval should be <= the end time

    // Safety from rep exposure:
    // Check the rep invariant is true. That means setting 
    // list intervals unchangeable / private / final
    // and return value MUST be immutable

    public CommonMultiIntervalSet() {}

    @Override
    public boolean insert(long start, long end, L label) {
        if (start <= end) {
            Interval<L> e = new Interval<L>(start, end, label);
            multiIntervalSets.add(e);
            return true;
        }
        else return false;
    }

    @Override
    public Set<L> labels() {
        Set<L> ret = new HashSet<L>();
        for (Interval<L> s : multiIntervalSets) {
            ret.add(s.label());
        }
        return ret;
    }

    @Override
    public boolean remove(L label) {
        int initSize = multiIntervalSets.size();
        multiIntervalSets.removeIf(s->s.label().equals(label));
        if (multiIntervalSets.size() < initSize) return true;
        return false;
    }

    @Override
    public IntervalSet<Integer> intervals(L label) throws Exception {
        IntervalSet<Integer> ret = new CommonIntervalSet<Integer>();
        for(Interval<L> s : multiIntervalSets) {
            if (s.label().equals(label)) {
                long start = s.start();
                long end = s.end();
                Integer IntegerLabel = 0;
                // count label which is Integer, Interval having bigger start's label ++
                for(Interval<Integer> r : ret.getIntervals()) {
                    if (r.start() > start)
                        ret.changeLabel(r.label(), r.label()+1);
                    else if (r.start() < start)
                        IntegerLabel ++;
                }
                ret.insert(start, end, IntegerLabel);
            }
        }
        return ret;
    }

    @Override
    public List<Interval<L>> allIntervals() {
        return multiIntervalSets;
    }
    
}
