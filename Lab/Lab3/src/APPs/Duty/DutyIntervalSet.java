package APPs.Duty;

import java.util.*;

import Interval.*;
import Interval.DateFormat;

// NoBlankIntervalSetImpl, NonOverlapIntervalSetImpl

public class DutyIntervalSet {

    private final IntervalSet<Employee> dutySet = IntervalSet.empty();
    // private final MultiIntervalSet<Employee> dutySet = MultiIntervalSet.empty();
    private long startTime = -1;
    private long endTime = -1;
    private final List<Employee> employees = new ArrayList<Employee>();

    // Abstraction function:
    // AF(dutySet) = the interval set in the duty APP
    // AF(startTime) = the start time of  the duty period
    // AF(endTime) = the end time of the duty period
    // AF(employees) = the list of employees in the duty set

    // Representation invariant:
    // each interval has a unique label
    // and each dont allow any blank period

    // Safety from rep exposure:
    // Check the rep invariant is true. That means setting 
    // dutySet/employees unchangeable / private / final
    // but the start and end time need be changable
    // and return value MUST be immutable

    /**
     * constructor
     */
    public DutyIntervalSet() { }

    /**
     * set the start and end time of this duty
     * @param startTime long the start data
     * @param endTime long the end data
     */
    public void SetStartEndTime(long startTime, long endTime) {
        if (startTime < endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }

    }

    /**
     * get the start data of this duty table
     * @return long the start data
     */
    public long GetStartTime() {
        return startTime;
    }

    /**
     * get the end data of this duty table
     * @return long the end data
     */
    public long GetEndTime() {
        return endTime;
    }

    /**
     * add an employee into the employee list
     * @param name String name of this employee
     * @param post String post of this employee
     * @param number long phone number of this employee
     * @return true (as specified by addEmployee), 
     * if employee already is in the list return false
     */
    public boolean addEmployee(String name, String post, String number) {
        for (Employee employee : employees) {
            if (employee.getName().equals(name)) return false;
        }
        Employee current = new Employee(name, post, number);
        return employees.add(current);
    }

    /**
     * get the list of employees
     * @return employee list
     */
    public List<Employee> employees() {
        List<Employee> ret = new ArrayList<Employee>();
        ret.addAll(employees);
        return ret;
    }

    /**
     * get the employee according to input name
     * @param name String type of an employee
     * @return the found employee, if there is not such employee, return null
     */
    public Employee getEmployee(String name) {
        for (Employee employee : employees)
            if (employee.getName().equals(name)) return employee;
        return null;
    }

    /**
     * remove the employee according to input name
     * @param name String name of an employee
     * @return true if the employee was in the list and was removed, false otherwise
     */
    public boolean removeEmployeeName(String name) {
        for (Employee I : dutySet.label()) {
            // for (Employee I : dutySet.label()) {
            if (I.getName().equals(name))
                return false;
        }
        int size = employees.size();
        employees.removeIf(e->e.getName().equals(name));
        if (employees.size() < size) return true;
        else return false;
    }

    /**
     * check if there is any time when no employee is on duty
     * @return true if no blank time between start and end time, false otherwise
     */
    public boolean checkNoBlank() {
        List<Interval<Employee>> intervals =  dutySet.getIntervals();
        // List<Interval<Employee>> intervals =  dutySet.getIntervals();
        Collections.sort(intervals);
        int size = intervals.size();
        int i = 0;
        // no duty set, or the first interval's start time is after startTime
        if (size == 0 || intervals.get(i).start() > startTime) return false;
        for (i = 0; i < size; i++) {
            if (intervals.get(i).end() >= endTime) return true;
            if (i > 0)
                if (intervals.get(i-1).end() < intervals.get(i).start()) 
                    return false;
        }
        return false;
    }

    /**
     * check if there has an interval that has repeated part with given interval
     * @param start long start time of the interval
     * @param end long end time of the interval
     * @return true if no overlap, false otherwise
     */
    private boolean checkNonOverlap (long start, long end) {
        List<Interval<Employee>> intervals =  dutySet.getIntervals();
        // List<Interval<Employee>> intervals =  dutySet.getIntervals();
        Collections.sort(intervals);
        int size = intervals.size();
        for (int i = 0; i < size; i++) {
            long curStart = intervals.get(i).start();
            long curEnd = intervals.get(i).end();
            if (!(curStart >= end || curEnd <= start)) return false;
        }
        return true;
    }

    /**
     * insert an interval into the list of intervals
     * if start < startTime or end > endTime, remind the client out of bounds
     * if there is no overlap after inserting the interval, just insert
     * otherwise, remind of the client overlap, and don't insert
     * @param start long the start time of the interval
     * @param end long the end time of the interval
     * @param label Employee who is on duty
     * @throws Exception
     */
    public void insert(long start, long end, Employee label) throws Exception {
        if (start < startTime || end > endTime)
            throw new Exception("Out of bounds");
        if (checkNonOverlap(start, end))
            dutySet.insert(start, end, label);
        else System.out.println(label.getName() + "Overlap!");
    }


    /**
     * show the table when there has employee on duty
     */
    public void DutyTable() {
        List<Interval<Employee>> intervals =  dutySet.getIntervals();
        // List<Interval<Employee>> intervals =  dutySet.getIntervals();
        Collections.sort(intervals);
        System.out.println("Duty Table:");
        for (int i = 0; i < intervals.size(); i++) {
            System.out.println(DateFormat.DateLongToString(intervals.get(i).start()) + " " + 
            DateFormat.DateLongToString(intervals.get(i).end() - 24 * 3600 * 1000) + " " + intervals.get(i).label().getName()
            + " " + intervals.get(i).label().getPost() + " " +  intervals.get(i).label().getNumber());
        }
        System.out.println("Free Ratio: " + getFreeTimeRatio());
    }

    /**
     * show the table when there are no employees on duty
     */
    public void EmptyDutyTable() {
        List<Interval<Employee>> intervals =  dutySet.getIntervals();
        // List<Interval<Employee>> intervals =  dutySet.getIntervals();
        Collections.sort(intervals);
        System.out.println("Empty Duty Table:");
        boolean haveEmpty = false;
        if (intervals.size() == 0) {
            System.out.println(DateFormat.DateLongToString(startTime) + " " + DateFormat.DateLongToString(endTime - 24 * 3600 * 1000));
        } else {
            if (intervals.get(0).start() > startTime) {
                System.out.println(DateFormat.DateLongToString(startTime) + "  " + DateFormat.DateLongToString(intervals.get(0).start() - 24 * 3600 * 1000));
                haveEmpty = true;
            }
            if (intervals.size() > 1)
                for (int i = 0; i < intervals.size() - 1; i++) {
                    if (intervals.get(i+1).start() > intervals.get(i).end()) {
                        System.out.println(DateFormat.DateLongToString(intervals.get(i).end()) 
                        + "  " + DateFormat.DateLongToString(intervals.get(i+1).start() - 24 * 3600 * 1000));
                        haveEmpty = true;
                    }
                }
            if (intervals.get(intervals.size() - 1).end() < endTime) {
                System.out.println(DateFormat.DateLongToString(intervals.get(intervals.size() - 1).end()) + "  " + DateFormat.DateLongToString(endTime - 24 * 3600 * 1000));
                haveEmpty = true;
            }
            if (!haveEmpty) System.out.println("FULL DUTY TABLE!!");
        }
        System.out.println("Free Ratio: " + getFreeTimeRatio());
    }

    public double getFreeTimeRatio() {
        List<Interval<Employee>> list = dutySet.getIntervals();
        Collections.sort(list);
        long free = 0;
        int i = 0;
        int size = list.size();
        if (size == 0) return 1;
        if (list.get(0).start() > startTime) 
            free += list.get(0).start() - startTime; 
        for (i = 0; i < size - 1; i++) {
            if (list.get(i+1).start() > list.get(i).end()) {
                free += list.get(i+1).start() - list.get(i).end();
            }
        }
        if (list.get(i).end() < endTime)
            free += endTime - list.get(i).end();
        return (double)free / (double)(endTime - startTime);
    }
}


