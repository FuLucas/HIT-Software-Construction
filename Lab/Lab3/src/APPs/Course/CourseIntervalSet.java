package APPs.Course;

import java.util.*;
import Interval.*;
import Interval.DateFormat;

// NonOverlapIntervalSetImpl, NoBlankIntervalSetImpl

public class CourseIntervalSet {

    // private final MultiIntervalSet
    private final long SemesterStart;
    private final int WeekNumber;
    private final long SemesterEnd;
    // Course, WeekHour(left hours)
    private final Map<Course,Integer> courses = new HashMap<>();
    private final MultiIntervalSet<Course> curriculum = MultiIntervalSet.empty();


    /**
     * constructor Set semester start date and total number of weeks
     * @param SemesterStart
     * @param WeekNumber
     */
    public CourseIntervalSet(long SemesterStart, int WeekNumber) {
        this.SemesterStart = SemesterStart;
        this.WeekNumber = WeekNumber;
        this.SemesterEnd = SemesterStart + (long)WeekNumber * 7 * 24 * 3600 * 1000;
    }



    /**
     * add a NOT setted course
     * @param id the id of the course
     * @param CourseName the name of the course
     * @param teacher the teacher's name of the course
     * @param place the place of the course
     * @param WeekHour how many hours per week of the course
     * @return
     */
    public boolean AddCourse(int id, String CourseName, String teacher, String place, int WeekHour) {
        if (WeekHour <= 0 || WeekHour % 2 == 1) return false; // not even
        Course cur = new Course(id, CourseName, teacher, place);
        for (Course c : courses.keySet()) {
            if (c.equals(cur)) return false;
        }
        courses.put(cur, WeekHour);
        return true;
    }

    /**
     * get when this semester start
     * @return the start date
     */
    public long GetSemesterStart() {
        return SemesterStart;
    }

    /**
     * the date input is in which week of the semester
     * @param date input date
     * @return the week number of the semester
     */
    public int GetWeekNumber(String date) {
        long s = DateFormat.StringToDateLong(date);
        int day = (int)((s - SemesterStart) / (24 * 3600 * 1000));
        int weekNum = day / 7 + 1;
        if (weekNum < 1 || weekNum > WeekNumber) {
            System.out.println("Out of Semester!");
            return -1;
        }
        else return weekNum;
    }

    /**
     * get one day's course list according to the date input
     * @param date the date wantted to get
     * @return the list of courses of this day
     * null if the day is not in the semester
     */
    public List<Interval<Course>> GetOneDayCourseList(String date) {
        long s = DateFormat.StringToDateLong(date);
        if (s > SemesterEnd || s < SemesterStart) {
            System.out.println("Out of Semester!");
            return null;
        }
        int day = (int)((s - SemesterStart) / (24 * 3600 * 1000));
        // from 0 - 6, symbol Monday to Sunday 
        int weekday = day % 7;
        // from 0 - WeekNumber-1, symbol which week
        // int weekNum = day / 7;
        long start = SemesterStart + weekday * 24 * 3600 * 1000;
        long end = SemesterStart + (weekday + 1) * 24 * 3600 * 1000;
        List<Interval<Course>> oneDayCourses = new ArrayList<>();
        for (Interval<Course> c : curriculum.allIntervals()) {
            if (c.start() >= start && c.end() <= end) {
                oneDayCourses.add(c);
            }
        }
        return oneDayCourses;
    }

    /**
     * choose a course according to its ID
     * @param id int the course ID
     * @return Course if it is found, null otherwise
     */
    public Course GetCourse(int id) {
        for (Course c : courses.keySet()) {
            if (c.getID() == id) return c;
        }
        return null;
    }

    /**
     * set curriculum
     * @param start the start time of the course
     * @param end the end time of the course
     * @param id the course ID
     * @return true if inserted successfully, false illegal input
     */
    public boolean InsertCurriculum(long start, long end, int id) {
        if (start > end || start >= SemesterEnd || end <= SemesterStart)
            return false;
        Course tmp = null;
        for (Course c : courses.keySet()) {
            if (c.getID() == id) tmp = c;
        }
        if (tmp == null) return false;
        curriculum.insert(start, end, tmp);
        if (courses.get(tmp) - 2 == 0)
            courses.remove(tmp);
        else if (courses.get(tmp) - 2 > 0)
            courses.put(tmp, courses.get(tmp) - 2);
        return true;
    }

    /**
     * not setted courses
     * @return map, can be a new one
     */
    public Map<Course,Integer> GetLeftNOTSetCourse() {
        Map<Course,Integer> ret = new HashMap<Course,Integer>();
        ret.putAll(courses);
        return ret;
    }

    /**
     * get the week day of the date
     * @param date the date that is waiting to be check
     * @return 0-6 symbol From Monday to Sunday, -1 if the date is not in semester
     */
    public int GetDayFromStart(String date) {
        long s = DateFormat.StringToDateLong(date);
        if (s > SemesterEnd || s < SemesterStart) {
            System.out.println("Out of Semester!");
            return -1;
        }
        else return (int)((s - SemesterStart) / (24 * 3600 * 1000));
    }

    /**
     * get conflict ratio
     * @return conflict ratio above 0 below 1
     */
    public double GetConflictRatio() {
        double allms = 7 * 5 * 2 * 3600 * 1000;
        List<Interval<Course>> list = curriculum.allIntervals();
        Collections.sort(list);
        long conf = 0;
        int size = list.size();
        if (size == 0 || size == 1) return 0;
        for (int i = 0; i < size; i++) {
            int temp = 1;
            while(i + temp < size && list.get(i+temp).start() < list.get(i).end()) {
                if (list.get(i).end() > list.get(i+temp).end()) 
                    conf += (list.get(i+temp).end() - list.get(i+temp).start());
                else conf += (list.get(i).end() - list.get(i+temp).start());
                temp++;
            }
        }
        return (double)conf / allms;
    }

    /**
     * get free time ratio
     * @return free time ratio above 0 below 1, the Total time is certain
     */
    public double GetFreeTimeRatio() {
        double allms = 7 * 5 * 2;
        List<Interval<Course>> list = curriculum.allIntervals();
        Collections.sort(list);
        long Notfree = 0;
        int size = list.size();
        if (size == 0) return 1;
        for (int i = 0; i < size; i++) {
            // if (i + 1 < size && list.get(i+1).start() > list.get(i).end()) {
            //     free += list.get(i+1).start() - list.get(i).end();
            // }
            while (i + 1 < size && list.get(i+1).start() == list.get(i).start()) {
                i++;
            }
            Notfree += 2;
        }
        return 1.0 - (double)Notfree / allms;
    }
}
