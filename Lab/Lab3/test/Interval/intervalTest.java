package Interval;

import static org.junit.Assert.*;
import org.junit.Test;

// import Interval.Interval;

public class intervalTest {

    @Test
    public void testStartEndLabel() {
        long start = 10;
        long end = 20;
        String label = "test";
        Interval<String> interval = new Interval<String>(start, end, label);
        assertEquals(10, interval.start());
        assertEquals(20, interval.end());
        assertTrue(interval.label().equals(label));
    }
    
    @Test
    public void testRepeat() {
        long start = 10;
        long end = 20;
        String label = "test";
        Interval<String> interval = new Interval<String>(start, end, label);
        assertTrue(interval.hasRepeatedPeriod(15, 25));
        assertTrue(interval.hasRepeatedPeriod(5, 15));
        assertFalse(interval.hasRepeatedPeriod(20, 30));
        assertFalse(interval.hasRepeatedPeriod(5, 10));
        assertFalse(interval.hasRepeatedPeriod(25, 30));
        assertFalse(interval.hasRepeatedPeriod(5, 7));
        assertFalse(interval.hasRepeatedPeriod(15, 7));
        assertTrue(interval.hasRepeatedPeriod(15, 15));
        assertTrue(interval.hasRepeatedPeriod(15, 17));
        assertTrue(interval.hasRepeatedPeriod(5, 30));
    }

    @Test
    public void testCompareTo() {
        long start = 10;
        long end = 20;
        String label = "test";
        Interval<String> interval = new Interval<String>(start, end, label);
        long start1 = 5;
        long end1 = 20;
        String label1 = "test1";
        Interval<String> interval1 = new Interval<String>(start1, end1, label1);
        long start2 = 15;
        long end2 = 20;
        String label2 = "test2";
        Interval<String> interval2 = new Interval<String>(start2, end2, label2);
        long start3 = 10;
        long end3 = 30;
        String label3 = "test3";
        Interval<String> interval3 = new Interval<String>(start3, end3, label3);
        assertEquals(-1, interval.compareTo(interval2));
        assertEquals(1, interval.compareTo(interval1));
        assertEquals(0, interval.compareTo(interval3));
    }
}
