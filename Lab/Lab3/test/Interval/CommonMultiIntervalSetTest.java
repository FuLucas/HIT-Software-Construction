package Interval;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Test;

// import Interval.CommonIntervalSet;
// import Interval.CommonMultiIntervalSet;
// import Interval.IntervalSet;
// import Interval.MultiIntervalSet;


public class CommonMultiIntervalSetTest extends MultiItervalSetTest {

    @Override
    public MultiIntervalSet<String> emptyMulti() {
        return new CommonMultiIntervalSet<String>();
    }

    @Test
    public void testEmptyMulti() {
        assertEquals(Collections.emptyList(), MultiIntervalSet.empty().allIntervals());
    }

    @Test
    public void testNOTEmptyMulti() throws Exception {
        IntervalSet<String> temp = new CommonIntervalSet<String>();
        temp.insert(5, 10, "label1");
        temp.insert(15, 20, "label2");
        temp.insert(45, 62, "label3");
        assertEquals(3, MultiIntervalSet.create(temp).allIntervals().size());
    }
    
}
