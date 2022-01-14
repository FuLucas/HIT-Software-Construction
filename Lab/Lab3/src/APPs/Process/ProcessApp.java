package APPs.Process;

import java.util.*;
import java.util.Map.Entry;

import Interval.Interval;

public class ProcessApp {
    static ProcessIntervalSet ProcessApp = new ProcessIntervalSet();

    /**
     * Add a group of processes, enter each process' ID, name, minimum and maximum execution times.
     * Once a process is configured, it cannot modify its information.
     */
    private static void SetGroupProcess(Scanner t) {
        System.out.println("Add a group of processes.");
        String con;
        do {
            System.out.println("ID:");
            int id = t.nextInt();
            System.out.println("Name:");
            String name = t.next();
            System.out.println("Shortest execution time:");
            long shortest = t.nextLong();
            System.out.println("Longest execution time:");
            long longest = t.nextLong();
            ProcessApp.AddProcess(id, name, shortest, longest);
            System.out.println("Add process continuously? Y(y)/N(others)");
            con = t.next();
        } while(con.equals("y") || con.equals("Y"));
    }

    /**
     * Randomly select and execute an unfinished process until all processes have reached 
     * the "end of execution" state. In each selection, you can also "do not execute any process", 
     * and select the process again at a randomly selected time point in the follow-up.
     * @throws Exception
     */
    private static void ScheduleRandom() throws Exception {
        int pro = ProcessApp.UnfinishedProcessNumber();
        Process tmp = null;
        while (pro > 0) {
            // choose a random process, 0 <= cho <= pro
            // when cho == pro, do not choose any process
            int cho = (int)(Math.random() * (pro + 1));
            long RandomTime;
            // choose one of the processes
            if (cho >= 0 && cho < pro) { 
                Entry<Process, Long> cur = ProcessApp.GetProcess(cho);
                Process chosen = cur.getKey();
                if (chosen.equals(tmp)) continue;
                long AlreadyTime = cur.getValue();
                long longest = chosen.getLongestTime();
                RandomTime = (long)(Math.random() * (longest - AlreadyTime + 1));
                ProcessApp.insert(RandomTime, chosen);
                tmp = chosen;
                // update run time in the insert method
            } else { // choose stop for a period
                RandomTime = (long)(1 + Math.random() * (2000));
                ProcessApp.insertNull(RandomTime);
                tmp = null;
            }
            // delete ended processes
            ProcessApp.removeEORProcess();
            pro = ProcessApp.UnfinishedProcessNumber();
        }
    }

    /**
     * The simulation strategy of "shortest process first": each time a process is selected, 
     * the process with the smallest difference in execution time from its maximum execution time is selected first
     */
    private static void ScheduleShortestProcessNext() throws Exception{
        int pro = ProcessApp.UnfinishedProcessNumber();
        while (pro > 0) {
            // choose the shortest process 
            // choose a random process, 0 <= cho <= pro
            // when cho == pro, do not choose any process
            int cho = (int)(Math.random() * (pro + 1));
            long RandomTime;
            // choose one of the processes
            if (cho >= 0 && cho < pro) { 
                // Entry<Process, Long> cur = ProcessApp.GetProcess(cho);
                Entry<Process, Long> cur = ProcessApp.GetShortestProcess();
                Process chosen = cur.getKey();
                long AlreadyTime = cur.getValue();
                long longest = chosen.getLongestTime();
                RandomTime = (long)(Math.random() * (longest - AlreadyTime + 1));
                ProcessApp.insert(RandomTime, chosen);
                // update run time in the insert method
            } else { // choose stop for a period
                RandomTime = (long)(1 + Math.random() * (2000));
                ProcessApp.insertNull(RandomTime);
            }
            // delete ended processes
            ProcessApp.removeEORProcess();
            pro = ProcessApp.UnfinishedProcessNumber();
        }
    }

    /**
     * Visually display the results of the process scheduling before the current moment, 
     * and the process being executed at the current moment.
     */
    private static void ProcessSchedulingResults() {
        System.out.println("Processes Schedule Results");
        List<Interval<Process>> intervals = ProcessApp.getSchedule();
        Formatter f = new Formatter(System.out);
        f.format("%-6s %-8s %-8s %-20s %-8s %-10s %-10s\n",
             "Num", "start", "end", "Name", "ID", "Shortest", "Longest");
        for (int i = 0; i < intervals.size(); i++) {
            Interval<Process> in = intervals.get(i);
            Process pr = in.label();
            f.format("%-6s %-8s %-8s %-20s %-8s %-10s %-10s\n",
             i, in.start(), in.end(), pr.getName(), pr.getID(), pr.getShortestTime(), pr.getLongestTime());
        }
        f.close();
    }

    /**
     * menu of ProcessApp
     */
    private static void menu() {
        System.out.println("----------------------------------------------------");
        System.out.println("--          0.Schedule Processes Randomly         --");
        System.out.println("--   1.Schedule Processes Shortest Process Next   --");
        System.out.println("----------------------------------------------------");
        System.out.println("YOUR CHOICE: ");
    }
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        SetGroupProcess(in);
        int choice;
        do {
            menu();
            choice = in.nextInt();
            try {
            if (choice == 0)
				// try {
					ScheduleRandom();
				// } catch (Exception e) {
				// 	e.printStackTrace();
				// }
			else if (choice == 1)
                ScheduleShortestProcessNext();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while(choice != 0 && choice != 1);
        ProcessSchedulingResults();
        in.close();
    }
}
