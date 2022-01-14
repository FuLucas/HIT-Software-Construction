package Duty;

import static org.junit.Assert.*;
import org.junit.Test;

import APPs.Duty.Employee;

public class EmployeeTest {

    @Test
    public void test () throws Exception {
        String name = "LiHua";
        String post = "Manger";
        String number = "139-0451-0000";
        Employee employee = new Employee(name, post, number);
        assertEquals(name, employee.getName());
        assertEquals(post, employee.getPost());
        assertEquals(number, employee.getNumber());

        String name2 = "LiuMing";
        String post2 = "Professor";
        String number2 = "138-5654-4546";
        Employee employee2 = new Employee(name2, post2, number2);
        assertEquals(name2, employee2.getName());
        assertEquals(post2, employee2.getPost());
        assertEquals(number2, employee2.getNumber());
        Object obj = new Object();

        String name3 = "LiuMing";
        String post3 = "Manger";
        String number3 = "137-4444-0000";
        Employee employee3 = new Employee(name3, post3, number3);
        assertEquals(name3, employee3.getName());
        assertEquals(post3, employee3.getPost());
        assertEquals(number3, employee3.getNumber());

        assertFalse(employee.equals(employee2));
        assertFalse(employee2.equals(employee));
        assertTrue(employee2.equals(employee3));
        assertTrue(employee3.equals(employee2));
        assertTrue(employee3.equals(employee3));
        assertFalse(employee.equals(obj));

    }
    
}
