package APPs.Process;

import java.util.*;
import java.util.Map.Entry;

import Interval.*;

// NonOverlapIntervalSetImpl

public class ProcessIntervalSet {
    
    private final MultiIntervalSet<Process> processSet = MultiIntervalSet.empty();
    // process and run time
    private final Map<Process,Long> processes = new HashMap<Process,Long>();
    // update by the methods in this class
    public long NowTime = 0;

    /**
     * add a process to the process list
     * @param id int ID of the process
     * @param name string the name of the process
     * @param shortest long Shortest execution time of the process
     * @param longest long longest execution time of the process
     * @return true if the process is added, false otherwise(the id already existes)
     */
    public boolean AddProcess(int id, String name, long shortest, long longest) {
        Process cur = new Process(id, name, shortest, longest);
        for (Process p : processes.keySet()) {
            if (p.getID() == id) return false;
        }
        // initialize run time to 0 
        processes.put(cur, (long) 0);
        return true;
    }

    /**
     * choose a process according to an ID
     * @param id int ID of a process
     * @return Process if the ID matches it, null no such process whose ID's this
     */
    public Process ChooseProcess(int id) {
        for (Process p : processes.keySet()) {
            if (p.getID() == id) return p;
        }
        return null;
    }

    /**
     * get a process randomly
     * @param num int a random number to get a process
     * @return Process
     */
    public Entry<Process, Long> GetProcess(int num) {
        if (num >= 0 && num < processes.size()) {
            Set<Entry<Process, Long>> s = processes.entrySet();
            List<Entry<Process, Long>> list = new ArrayList<Entry<Process, Long>>();
            list.addAll(s);
            return list.get(num);
        }
        else return null;
    }

    /**
     * check if there has an interval that has repeated part with given interval
     * @param start long start time of the interval
     * @param end long end time of the interval
     * @return true if no overlap, false otherwise
     */
    private boolean checkNonOverlap (long start, long end) {
        List<Interval<Process>> intervals =  processSet.allIntervals();
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
     * insert a process who execute for period time
     * @param period long the execution time
     * @param label Process the process who executes
     * @throws Exception
     */
    public void insert(long period, Process label) throws Exception {
        long already = processes.get(label);
        if (period > 0 && period + already <= label.getLongestTime()) {
            if (checkNonOverlap(NowTime, NowTime + period)) {
                processSet.insert(NowTime, NowTime + period, label);
                processes.put(label, already + period);
            }
            else System.out.println("Overlap!");
            this.NowTime += period;
        }
    }

    /**
     * don't execute any process
     * @param period long the stop time of the process
     */
    public void insertNull(long period) {
        if (period > 0) this.NowTime += period;
    }

    /**
     * get the run time of the process
     * @param id int ID of the process
     * @return long the run time of the process, -1 if no this process
     */
    public long getRunTime(int id) {
        Set<Process> pr = processes.keySet();
        long RunTime = -1;
        for (Process p : pr) {
            if (p.getID() == id) {
                RunTime = processes.get(p);
            }
        }
        return RunTime;
    }

    /**
     * remove the EOR processes from the process Map
     * run time is between shortest and longest
     */
    public void removeEORProcess() {
        List<Process> remList = new ArrayList<Process>();
        for (Process p : processes.keySet()) {
            if (processes.get(p) >= p.getShortestTime() && 
            processes.get(p) <= p.getLongestTime()) {
                //  processes.remove(p);
                remList.add(p);
            }
        }
        for (Process key : remList) {
            processes.remove(key);
        }
    }

    /**
     * get the processes that have NOT ended up
     * @return int the number of NOT ended processes
     */
    public int UnfinishedProcessNumber() {
        return processes.size();
    }

    /**
     * get the processes' schedule
     * @return list
     */
    public List<Interval<Process>> getSchedule() {
        return processSet.allIntervals();
    }

    /**
     * get process whosw time from the Langest execution time is the shortest 
     * @return Process found
     */
    public Entry<Process, Long> GetShortestProcess() {
        long min = Long.MAX_VALUE;
        Entry<Process, Long> temp = null;
        for (Entry<Process, Long> p : processes.entrySet()) {
            if (p.getKey().getLongestTime() - processes.get(p.getKey()) < min) {
                min = p.getKey().getLongestTime() - processes.get(p.getKey());
                temp = p;
            }
        }        
        return temp;
    }
}
