package APPs.Course;

public class Course {

    // fields, process' ID, name, Shortest and Longest execution time
    private final int ID;
    private final String name;
    private final String teacherName;
    private final String place;


    // Abstraction function:
    // AF(ID) = the ID of the course
    // AF(name) = the name of the course
    // AF(teacherName) = the name of the course's teacher
    // AF(place) = the place of the course

    // Representation invariant:
    // ID must > 0, and unique
    // the name of different courses can be repeated
    
    // Safety from rep exposure:
    // Check the rep invariant is true
    // All fields MUST be private (all the String type are immutable)
    // So make defensive copies instead of just return mutable data 


    /**
     * constructor
     * @param ID int the ID of the course
     * @param name string the name of the course
     * @param teacherName String the name of the course's teacher
     * @param place string the place of the course
     */
    public Course(int ID, String name, String teacherName, String place) {
        this.ID = ID;
        this.name = name;
        this.teacherName = teacherName;
        this.place = place;
        // this.WeekHour = WeekHour;
        checkRep();
    }


    // checkRep
    private void checkRep() {
        // assert WeekHour % 2 == 0;
    }

    /**
     * get the ID of the course
     * @return int ID of the course
     */
    public int getID() {
        return ID;
    }

    /**
     * get the name of the course
     * @return String name of the course
     */
    public String getName() {
        return name;
    }

    /**
     * get the name of the course's teacher
     * @return String the name of the course's teacher
     */
    public String getTeacherName() {
        return teacherName;
    }

    /**
     * get the place of the course
     * @return String
     */
    public String getPlace() {
        return place;
    }

    /**
     * get the number of hours per week of the course
     * @return int 
     */
    // public int getWeekHour() {
    //     return WeekHour;
    // }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Course) {
            Course that = (Course) o;
            return that.getID() == this.ID;
        }
        else return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ID:" + ID + ", Name:" + name + ", Teacher:" + teacherName + ", Place:" + place + "]");
        return sb.toString();
    }
}
