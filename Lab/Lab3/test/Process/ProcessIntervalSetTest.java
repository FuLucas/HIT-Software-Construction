package Process;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.*;
import APPs.Process.ProcessIntervalSet;

public class ProcessIntervalSetTest {

    @Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; 
        // make sure assertions are enabled with VM argument: -ea
	}

    @Test
	public void testInitialProcessIntervalSetEmpty() {
		// you may use, change, or remove this test
        ProcessIntervalSet emptyProcess = new ProcessIntervalSet();
		assertEquals("expected new MultiIntervalSet to have no intervals",
         Collections.emptyList(), emptyProcess.getSchedule());
	}

    @Test
    public void testProcess() {
        ProcessIntervalSet process = new ProcessIntervalSet();
        assertTrue(process.AddProcess(12, "name", 10, 18));
        assertEquals(1, process.UnfinishedProcessNumber());
        assertFalse(process.AddProcess(12, "name", 10, 18));
        assertEquals(1, process.UnfinishedProcessNumber());
        assertTrue(process.AddProcess(25, "name2", 20, 30));
        assertEquals(2, process.UnfinishedProcessNumber());
        assertTrue(process.AddProcess(36, "name3", 25, 35));
        assertEquals(3, process.UnfinishedProcessNumber());

        assertNotNull(process.GetProcess(0));
        assertNotNull(process.GetProcess(1));
        assertNotNull(process.GetProcess(2));
        assertNull(process.GetProcess(4));

        assertEquals("[12, name, 10, 18]", process.ChooseProcess(12).toString());     
        assertEquals("[25, name2, 20, 30]", process.ChooseProcess(25).toString());     
        assertEquals("[36, name3, 25, 35]", process.ChooseProcess(36).toString());     
        assertEquals(null, process.ChooseProcess(47));     
    }

    @Test
    public void testSet() throws Exception {
        ProcessIntervalSet process = new ProcessIntervalSet();
        assertTrue(process.AddProcess(12, "name", 10, 18));
        assertTrue(process.AddProcess(25, "name2", 20, 30));
        assertTrue(process.AddProcess(36, "name3", 25, 35));
        assertEquals("[12, name, 10, 18]=0", process.GetShortestProcess().toString());
        process.insert(8, process.ChooseProcess(12));
        assertEquals(8, process.getRunTime(12));
        process.insertNull(10);
        process.insert(16, process.ChooseProcess(36));
        process.insert(8, process.ChooseProcess(12));
        process.removeEORProcess();
        process.insert(14, process.ChooseProcess(36));
        assertEquals("[36, name3, 25, 35]=30", process.GetShortestProcess().toString());
        process.removeEORProcess();
        process.insert(25, process.ChooseProcess(25));
        process.removeEORProcess();
        assertEquals(0, process.UnfinishedProcessNumber());

    }

    
}
