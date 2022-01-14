package API;

import static org.junit.Assert.*;
import org.junit.Test;

import Interval.CommonIntervalSet;
// import API.APIs;
import Interval.CommonMultiIntervalSet;
import Interval.IntervalSet;
import Interval.MultiIntervalSet;

public class APITest {

    @Test
    public void testMultipleSimilarity() throws Exception {
        MultiIntervalSet<String> mis1 = new CommonMultiIntervalSet<String>();
        MultiIntervalSet<String> mis2 = new CommonMultiIntervalSet<String>();
        mis1.insert(0, 5, "A");
        mis1.insert(10, 20, "B");
        mis1.insert(20, 25, "A");
        mis1.insert(25, 30, "B");
        mis2.insert(0, 5, "C");
        mis2.insert(10, 20, "B");
        mis2.insert(20, 35, "A");
        double res = APIs.Similarity(mis1, mis2);
        assertEquals(0.42857, res, 0.0001);
    }
    
    @Test
    public void testcalcConflictRatio() throws Exception {
        MultiIntervalSet<String> mis1 = new CommonMultiIntervalSet<String>();
        mis1.insert(0, 5, "A");
        mis1.insert(10, 20, "B");
        mis1.insert(20, 25, "A");
        mis1.insert(25, 30, "B");
        mis1.insert(0, 25, "A");
        assertEquals(0.666666, APIs.calcConflictRatio(mis1), 0.0001);
        IntervalSet<String> mis2 = new CommonIntervalSet<String>();
        mis2.insert(0, 5, "A");
        mis2.insert(10, 20, "B");
        mis2.insert(20, 25, "C");
        mis2.insert(25, 30, "D");
        mis2.insert(0, 25, "E");
        assertEquals(0.666666, APIs.calcConflictRatio(mis2), 0.0001);
    }

    @Test
    public void testcalcFreeTimeRatio() throws Exception {
        MultiIntervalSet<String> mis1 = new CommonMultiIntervalSet<String>();
        mis1.insert(0, 5, "A");
        mis1.insert(10, 20, "B");
        mis1.insert(20, 25, "A");
        mis1.insert(25, 30, "B");
        assertEquals(0.16666666, APIs.calcFreeTimeRatio(mis1), 0.0001);

        IntervalSet<String> mis2 = new CommonIntervalSet<String>();
        mis2.insert(0, 5, "A");
        mis2.insert(10, 20, "B");
        mis2.insert(20, 25, "C");
        mis2.insert(25, 30, "D");
        assertEquals(0.16666666, APIs.calcFreeTimeRatio(mis2), 0.0001);

    }
}
