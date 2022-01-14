package Interval;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Test;

// import Interval.CommonIntervalSet;
// import Interval.IntervalSet;

public class CommonItervalSetTest extends IntervalSetTest {

    @Override
    public IntervalSet<String> emptySet() {
        return new CommonIntervalSet<String>();
    }

    @Test
    public void testEmptySet() {
        assertEquals(Collections.emptyList(), IntervalSet.empty().getIntervals());
    }
    
}
