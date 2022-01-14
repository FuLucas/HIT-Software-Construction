package Process;

import static org.junit.Assert.*;
import org.junit.Test;

import APPs.Process.Process;

public class ProcessTest {

    @Test
    public void test() throws Exception {
        int id = 56;
        String name = "test";
        long shortest = 3245;
        long longest = 657890;
        Process process = new Process(id, name, shortest, longest);
        assertEquals(id, process.getID());
        assertEquals(name, process.getName());
        assertEquals(shortest, process.getShortestTime());
        assertEquals(longest, process.getLongestTime());
        assertEquals("[56, test, 3245, 657890]", process.toString());

        int id2 = 45;
        String name2 = "test2";
        long shortest2 = 1565;
        long longest2 = 4989;
        Process process2 = new Process(id2, name2, shortest2, longest2);
        assertEquals(id2, process2.getID());
        assertEquals(name2, process2.getName());
        assertEquals(shortest2, process2.getShortestTime());
        assertEquals(longest2, process2.getLongestTime());
        assertEquals("[56, test, 3245, 657890]", process.toString());


        int id3 = 56;
        String name3 = "test3";
        long shortest3 = 3245;
        long longest3 = 657890;
        Process process3 = new Process(id3, name3, shortest3, longest3);
        assertEquals(id3, process3.getID());
        assertEquals(name3, process3.getName());
        assertEquals(shortest3, process3.getShortestTime());
        assertEquals(longest3, process3.getLongestTime());
        assertEquals("[56, test, 3245, 657890]", process.toString());

        Object o = new Object();

        assertTrue(process.equals(process));
        assertFalse(process.equals(process2));
        assertTrue(process.equals(process3));
        assertFalse(process.equals(o));
    }
    
}
