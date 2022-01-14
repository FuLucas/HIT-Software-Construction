package Interval;

import static org.junit.Assert.*;
import org.junit.Test;

// import Interval.MultiIntervalSet;

import java.util.*;

public abstract class MultiItervalSetTest {

    public abstract MultiIntervalSet<String> emptyMulti();

    @Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; 
        // make sure assertions are enabled with VM argument: -ea
	}

    @Test
	public void testInitialVerticesEmpty() {
		// you may use, change, or remove this test
		assertEquals("expected new MultiIntervalSet to have no intervals",
         Collections.emptySet().size(), emptyMulti().allIntervals().size());
	}
    

    
    @Test
    public void testInsert() {
        long start1 = 2;
        long end1 = 5;
        String label1 = "a";
        long start2 = 6;
        long end2 = 10;
        String label2 = "b";
        long start3 = 23;
        long end3 = 34;
        String label3 = "b";
        long start4 = 7;
        long end4 = 3;
        String label4 = "c";
        MultiIntervalSet<String> intervals = emptyMulti();
        assertTrue(intervals.insert(start1, end1, label1));
        assertTrue(intervals.insert(start2, end2, label2));
        assertTrue(intervals.insert(start3, end3, label3));
        assertFalse(intervals.insert(start4, end4, label4));
    }

    @Test
    public void testRemove() {
        long start1 = 2;
        long end1 = 5;
        String label1 = "a";
        long start2 = 6;
        long end2 = 10;
        String label2 = "b";
        long start3 = 23;
        long end3 = 34;
        String label3 = "c";
        MultiIntervalSet<String> intervals = emptyMulti();
        assertTrue(intervals.insert(start1, end1, label1));
        assertTrue(intervals.insert(start2, end2, label2));
        assertTrue(intervals.insert(start3, end3, label3));

        assertTrue(intervals.remove("a"));
        assertTrue(intervals.remove("b"));
        assertFalse(intervals.remove("b"));
        assertTrue(intervals.remove("c"));
    }

    @Test
    public void testLabels() {
        long start1 = 2;
        long end1 = 5;
        String label1 = "a";
        long start2 = 6;
        long end2 = 10;
        String label2 = "b";
        long start3 = 23;
        long end3 = 34;
        String label3 = "b";
        long start4 = 7;
        long end4 = 3;
        String label4 = "c";
        MultiIntervalSet<String> intervals = emptyMulti();
        assertTrue(intervals.insert(start1, end1, label1));
        assertTrue(intervals.insert(start2, end2, label2));
        assertTrue(intervals.insert(start3, end3, label3));
        assertFalse(intervals.insert(start4, end4, label4));

        assertEquals("[a, b]", intervals.labels().toString());
    }

    @Test
    public void testIntervals() {
        long start1 = 2;
        long end1 = 5;
        String label1 = "a";
        long start2 = 6;
        long end2 = 10;
        String label2 = "b";
        long start3 = 23;
        long end3 = 34;
        String label3 = "b";
        long start4 = 7;
        long end4 = 3;
        String label4 = "c";
        long start5 = 11;
        long end5 = 15;
        String label5 = "b";
        MultiIntervalSet<String> intervals = emptyMulti();
        assertTrue(intervals.insert(start1, end1, label1));
        assertTrue(intervals.insert(start2, end2, label2));
        assertTrue(intervals.insert(start3, end3, label3));
        assertFalse(intervals.insert(start4, end4, label4));
        assertTrue(intervals.insert(start5, end5, label5));

        try {
            assertEquals("[[6, 10, 0], [23, 34, 2], [11, 15, 1]]", intervals.intervals("b").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
