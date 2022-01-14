package Course;

import static org.junit.Assert.*;
import org.junit.Test;

import APPs.Course.Course;

public class CourseTest {

    @Test
    public void test () throws Exception {
        int id = 1;
        String name = "Software Construction";
        String teacher = "LiuMing";
        String place = "Zhengxin";
        Course course = new Course(id, name, teacher, place);
        assertEquals(id, course.getID());
        assertEquals(name, course.getName());
        assertEquals(teacher, course.getTeacherName());
        assertEquals(place, course.getPlace());

        int id1 = 2;
        String name1 = "Software Construction";
        String teacher1 = "LiuMing";
        String place1 = "Zhengxin";
        Course course1 = new Course(id1, name1, teacher1, place1);
        assertEquals(id1, course1.getID());
        assertEquals(name1, course1.getName());
        assertEquals(teacher1, course1.getTeacherName());
        assertEquals(place1, course1.getPlace());

        assertFalse(course.equals(course1));
        assertTrue(course.equals(course));

        assertEquals("[ID:2, Name:Software Construction, Teacher:LiuMing, Place:Zhengxin]", course1.toString());
        assertEquals("[ID:1, Name:Software Construction, Teacher:LiuMing, Place:Zhengxin]", course.toString());
    }
    
}
