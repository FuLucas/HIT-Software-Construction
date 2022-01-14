package Duty;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.*;

import APPs.Duty.DutyIntervalSet;

public class DutyIntervalSetTest {

    @Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; 
        // make sure assertions are enabled with VM argument: -ea
	}

    @Test
	public void testInitialDutyIntervalSetEmpty() {
		// you may use, change, or remove this test
        DutyIntervalSet emptyDuty = new DutyIntervalSet();
		assertEquals("expected new MultiIntervalSet to have no intervals",
         Collections.emptyList(), emptyDuty.employees());
	}

    @Test
    public void testSetStartEndTime() {
        long start = 10;
        long end = 20;
        DutyIntervalSet duty = new DutyIntervalSet();
        duty.SetStartEndTime(end, start);
        assertEquals(-1, duty.GetStartTime());
        assertEquals(-1, duty.GetEndTime());
        duty.SetStartEndTime(start, end);
        assertEquals(10, duty.GetStartTime());
        assertEquals(20, duty.GetEndTime());
    }

    @Test
    public void testEmployees() {
        DutyIntervalSet duty = new DutyIntervalSet();
        duty.SetStartEndTime(0, 100);

        assertEquals("[]", duty.employees().toString());

        assertTrue(duty.addEmployee("a", "test1", "123456"));
        assertTrue(duty.addEmployee("b", "test2", "9865"));
        assertFalse(duty.addEmployee("a", "test3", "345"));
        assertTrue(duty.addEmployee("c", "test1", "9884"));
        assertTrue(duty.addEmployee("d", "test4", "123456"));

        assertEquals("[[a, test1, 123456], [b, test2, 9865], [c, test1, 9884], [d, test4, 123456]]",
         duty.employees().toString());

        assertEquals("[a, test1, 123456]", duty.getEmployee("a").toString());
        assertEquals(null, duty.getEmployee("e"));
        assertEquals("[b, test2, 9865]", duty.getEmployee("b").toString());

        assertTrue(duty.removeEmployeeName("a"));
        assertEquals(null, duty.getEmployee("a"));
        assertTrue(duty.removeEmployeeName("b"));
        assertEquals(null, duty.getEmployee("b"));
        assertFalse(duty.removeEmployeeName("f"));

    }

    @Test
    public void testSetDuty() throws Exception {
        DutyIntervalSet duty = new DutyIntervalSet();
        duty.SetStartEndTime(0, 100);
        assertTrue(duty.addEmployee("a", "test1", "1"));
        assertTrue(duty.addEmployee("b", "test2", "2"));
        assertTrue(duty.addEmployee("c", "test3", "3"));
        assertTrue(duty.addEmployee("d", "test4", "4"));
        duty.insert(0, 20, duty.getEmployee("a"));
        duty.insert(20, 60, duty.getEmployee("b"));
        assertFalse(duty.checkNoBlank());
        duty.insert(60, 100, duty.getEmployee("c"));
        duty.insert(80, 100, duty.getEmployee("d"));
        assertTrue(duty.checkNoBlank());

        assertFalse(duty.removeEmployeeName("a"));
        assertFalse(duty.removeEmployeeName("b"));
        assertFalse(duty.removeEmployeeName("c"));
        assertTrue(duty.removeEmployeeName("d"));
    }

    @Test
    public void testTable() throws Exception {
        DutyIntervalSet duty = new DutyIntervalSet();
        duty.SetStartEndTime(0, 100);
        assertTrue(duty.addEmployee("a", "test1", "1"));
        assertTrue(duty.addEmployee("b", "test2", "2"));
        assertTrue(duty.addEmployee("c", "test3", "3"));
        assertTrue(duty.addEmployee("d", "test4", "4"));
        duty.EmptyDutyTable();
        duty.insert(20, 60, duty.getEmployee("b"));
        duty.EmptyDutyTable();
        duty.insert(0, 20, duty.getEmployee("a"));
        duty.DutyTable();
        duty.EmptyDutyTable();
        duty.insert(80, 100, duty.getEmployee("c"));
        duty.EmptyDutyTable();
        duty.insert(60, 80, duty.getEmployee("d"));
        duty.EmptyDutyTable();
    }
}
