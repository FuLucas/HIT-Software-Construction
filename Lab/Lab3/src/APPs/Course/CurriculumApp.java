package APPs.Course;

import java.util.*;
import Interval.DateFormat;
import Interval.Interval;

public class CurriculumApp {

    /**
     * Unscheduled courses, current weekly free time ratio, repeat time ratio
     */
    private static void ShowNOTScheduleCourses(CourseIntervalSet CourseApp) {
        Map<Course,Integer> CourseLeft = CourseApp.GetLeftNOTSetCourse();
        System.out.println("Left NOT setted Courses:");
        for (Course c : CourseLeft.keySet()) {
            System.out.println(c.toString() + " " + CourseLeft.get(c).intValue());
        }
        System.out.println("Conflict Ratio: " + CourseApp.GetConflictRatio());
        System.out.println("Free Time Ratio: " + CourseApp.GetFreeTimeRatio());
    }
    
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        System.out.println("--- Input Start date (yyyy-MM-dd) and number of weeks of this semester ---");
        String StartDate = in.next();
        int weekNum = in.nextInt();
        CourseIntervalSet CourseApp = new CourseIntervalSet(DateFormat.StringToDateLong(StartDate), weekNum);
        System.out.println("--- Add a group of courses ---");
        String con;
        do {
            ShowNOTScheduleCourses(CourseApp);
            System.out.println("Course ID:");
            int id = in.nextInt();
            System.out.println("Course Name:");
            String name = in.next();
            System.out.println("Teacher's name:");
            String teacherName = in.next();
            System.out.println("Cource place:");
            String place = in.next();
            System.out.println("Hours per week:");
            int weekHours = in.nextInt();
            // no this course in the app, course hours per week is even
            if (CourseApp.GetCourse(id) == null && weekHours % 2 == 0)
                CourseApp.AddCourse(id, name, teacherName, place, weekHours);
            System.out.println("Add courses continuously? Y(y)/N(others)");
            con = in.next();
        } while(con.equals("y") || con.equals("Y"));
        System.out.println("Add courses to the curriculum.");
        do {
            ShowNOTScheduleCourses(CourseApp);
            System.out.println("Choose Course according to ID:");
            int id = in.nextInt();
            System.out.println("Choose weekday: 1-7");
            int weekday = in.nextInt();
            System.out.println("Choose a period: 0[8-10h], 1[10-12], 2[13-15], 3[15-17], 4[19-21]");
            int pr = in.nextInt();
            long semesterStart = CourseApp.GetSemesterStart();
            long table[] = {8, 10, 13, 15, 19};
            long start = semesterStart + (long)((weekday - 1) * 24 + table[pr]) * 3600 * 1000;
            long end = start + 2 * 3600 * 1000;
            CourseApp.InsertCurriculum(start, end, id);
        } while(!CourseApp.GetLeftNOTSetCourse().isEmpty());
        System.out.println("Watch the Curriculum of one day(yyyy-mm-dd):");
        Formatter f = new Formatter(System.out);
        do {
            System.out.println("Which day you want to watch");
            String date = in.next();
            List<Interval<Course>> temp = CourseApp.GetOneDayCourseList(date);
            for (Interval<Course> t : temp) {
                f.format("%-20s %-20s %-8s\n", "Start", "End", "Course");
                String s = DateFormat.HourDateLongToString(t.start());
                String e = DateFormat.HourDateLongToString(t.end());
                f.format("%-20s %-20s %-8s\n", s, e, t.label().toString());
            }
            System.out.println("Watch another day? Y(y)/N(others):");
            con = in.next();
        } while(con.equals("y") || con.equals("Y"));
        f.close();
        in.close();
    }
}
