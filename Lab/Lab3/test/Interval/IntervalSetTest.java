package Interval;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Test;

// import Interval.IntervalSet;

public abstract class IntervalSetTest {

    public abstract IntervalSet<String> emptySet();

    @Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; 
        // make sure assertions are enabled with VM argument: -ea
	}

    @Test
	public void testInitialVerticesEmpty() {
		// you may use, change, or remove this test
		assertEquals("expected new IntervalSet to have no intervals",
         Collections.emptyList(), emptySet().getIntervals());
	}
    
    @Test
    public void testInsert() throws Exception{
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
        IntervalSet<String> intervals = emptySet();
        assertTrue(intervals.insert(start1, end1, label1));
        assertTrue(intervals.insert(start2, end2, label2));
        assertFalse(intervals.insert(start3, end3, label3));
        assertFalse(intervals.insert(start4, end4, label4));
    }

    @Test
    public void testLabel() throws Exception {
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
        long end4 = 11;
        String label4 = "c";
        IntervalSet<String> intervals = emptySet();
        assertTrue(intervals.insert(start1, end1, label1));
        assertTrue(intervals.insert(start2, end2, label2));
        assertFalse(intervals.insert(start3, end3, label3));
        assertTrue(intervals.insert(start4, end4, label4));

        assertEquals(intervals.label().toString(), "[a, b, c]");
    }

    @Test
    public void testRemoveEmpty() throws Exception {
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
        long end4 = 11;
        String label4 = "c";
        IntervalSet<String> intervals = emptySet();
        assertTrue(intervals.isEmpty());
        assertTrue(intervals.insert(start1, end1, label1));
        assertTrue(intervals.insert(start2, end2, label2));
        assertFalse(intervals.insert(start3, end3, label3));
        assertTrue(intervals.insert(start4, end4, label4));
        assertFalse(intervals.isEmpty());
        assertTrue(intervals.remove("a"));
        assertFalse(intervals.remove("a"));
        assertTrue(intervals.remove("b"));
        assertTrue(intervals.remove("c"));
        assertFalse(intervals.remove("d"));
        assertTrue(intervals.isEmpty());
    }

    @Test
    public void testStartEnd() throws Exception {
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
        long end4 = 11;
        String label4 = "c";
        IntervalSet<String> intervals = emptySet();
        assertTrue(intervals.insert(start1, end1, label1));
        assertTrue(intervals.insert(start2, end2, label2));
        assertFalse(intervals.insert(start3, end3, label3));
        assertTrue(intervals.insert(start4, end4, label4));

        assertEquals(2, intervals.start("a"));
        assertEquals(5, intervals.end("a"));
        assertEquals(6, intervals.start("b"));
        assertEquals(10, intervals.end("b"));
        assertEquals(-1, intervals.start("d"));
        assertEquals(-1, intervals.end("d"));
    }

    @Test
    public void testGetIntervals() throws Exception {
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
        long end4 = 11;
        String label4 = "c";
        IntervalSet<String> intervals = emptySet();
        assertTrue(intervals.insert(start1, end1, label1));
        assertTrue(intervals.insert(start2, end2, label2));
        assertFalse(intervals.insert(start3, end3, label3));
        assertTrue(intervals.insert(start4, end4, label4));

        assertEquals("[[2, 5, a], [6, 10, b], [7, 11, c]]", intervals.getIntervals().toString());
    }


    @Test
    public void testChangeLabel() throws Exception {
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
        long end4 = 11;
        String label4 = "c";
        IntervalSet<String> intervals = emptySet();
        assertTrue(intervals.insert(start1, end1, label1));
        assertTrue(intervals.insert(start2, end2, label2));
        assertFalse(intervals.insert(start3, end3, label3));
        assertTrue(intervals.insert(start4, end4, label4));

        assertEquals("[[2, 5, a], [6, 10, b], [7, 11, c]]", intervals.getIntervals().toString());

        assertTrue(intervals.changeLabel("a", "newA"));
        assertTrue(intervals.changeLabel("b", "newB"));
        assertFalse(intervals.changeLabel("a", "newA"));
        assertFalse(intervals.changeLabel("d", "newD"));

        assertEquals("[[7, 11, c], [2, 5, newA], [6, 10, newB]]", intervals.getIntervals().toString());
    }

}
