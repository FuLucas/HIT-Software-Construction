package Course;

import static org.junit.Assert.*;
import org.junit.Test;

import APPs.Course.*;
import Interval.DateFormat;

public class CourseIntervalSetTest {

    @Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; 
        // make sure assertions are enabled with VM argument: -ea
	}

    @Test
    public void test() throws Exception {
        String semesterString = "2021-02-22";
        int weeknumber = 18;
        long semesterStart = DateFormat.StringToDateLong(semesterString);
        CourseIntervalSet curriculum = new CourseIntervalSet(semesterStart, weeknumber);
        assertEquals(semesterStart, curriculum.GetSemesterStart());
        String week1 = "2021-03-01";
        String week4 = "2021-05-12";
        String week2 = "2021-02-01";
        String week3 = "2021-12-01";
        assertEquals(-1, curriculum.GetWeekNumber(week2));
        assertEquals(-1, curriculum.GetWeekNumber(week3));
        assertEquals(2, curriculum.GetWeekNumber(week1));
        assertEquals(12, curriculum.GetWeekNumber(week4));

        assertEquals(-1, curriculum.GetDayFromStart("2021-02-01"));
        assertEquals(-1, curriculum.GetDayFromStart("2021-12-29"));
        assertEquals(1, curriculum.GetDayFromStart("2021-02-23"));
        assertEquals(68, curriculum.GetDayFromStart("2021-05-01"));
        assertEquals(49, curriculum.GetDayFromStart("2021-04-12"));

        assertTrue(curriculum.AddCourse(1, "a", "teacher1", "place1", 4));
        assertTrue(curriculum.AddCourse(2, "b", "teacher2", "place2", 6));
        assertFalse(curriculum.AddCourse(3, "c", "teacher3", "place3", 7));
        assertFalse(curriculum.AddCourse(4, "d", "teacher4", "place4", 0));
        assertFalse(curriculum.AddCourse(5, "e", "teacher5", "place5", -2));

        assertEquals("[ID:1, Name:a, Teacher:teacher1, Place:place1]", curriculum.GetCourse(1).toString());
        assertEquals("[ID:2, Name:b, Teacher:teacher2, Place:place2]", curriculum.GetCourse(2).toString());
        assertEquals(null, curriculum.GetCourse(3));
        assertEquals(null, curriculum.GetCourse(4));
        assertEquals(null, curriculum.GetCourse(5));

        assertEquals(2, curriculum.GetLeftNOTSetCourse().size());

        long start1 = DateFormat.StringToHourDateLong("2021-02-23 10");
        long end1 = DateFormat.StringToHourDateLong("2021-02-23 12");
        assertTrue(curriculum.InsertCurriculum(start1, end1, 1));
        assertTrue(curriculum.InsertCurriculum(start1, end1, 2));

        long start2 = DateFormat.StringToHourDateLong("2021-02-25 15");
        long end2 = DateFormat.StringToHourDateLong("2021-02-25 17");
        assertTrue(curriculum.InsertCurriculum(start2, end2, 1));

        long start3 = DateFormat.StringToHourDateLong("2021-02-24 10");
        long end3 = DateFormat.StringToHourDateLong("2021-02-24 12");
        assertTrue(curriculum.InsertCurriculum(start3, end3, 2));

        long start4 = DateFormat.StringToHourDateLong("2021-02-26 08");
        long end4 = DateFormat.StringToHourDateLong("2021-02-26 10");
        assertTrue(curriculum.InsertCurriculum(start4, end4, 2));

        assertEquals(2, curriculum.GetOneDayCourseList("2021-02-23").size());
        assertEquals(1 , curriculum.GetOneDayCourseList("2021-05-21").size());
        assertEquals(2, curriculum.GetOneDayCourseList("2021-04-20").size());

        assertEquals(0.028571, curriculum.GetConflictRatio(), 0.0001);
        assertEquals(0.885714, curriculum.GetFreeTimeRatio(), 0.0001);

    }
    
}


