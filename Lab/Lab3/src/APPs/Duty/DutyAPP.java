package APPs.Duty;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

import Interval.DateFormat;

public class DutyAPP {

    
    private static void menu() {
        System.out.println("--------------------------------------------");
        System.out.println("--       0.Set start and end date         --");
        System.out.println("--            1.Add employees             --");
        System.out.println("--               2.Set duty               --");
        System.out.println("--          3.Set duty randomly           --");
        System.out.println("--           4.Show duty table            --");
        System.out.println("--          5.Delete Employees            --");
        System.out.println("--            6.Set from .txt             --");
        System.out.println("--              others.quit               --");
        System.out.println("--------------------------------------------");
        System.out.println("YOUR CHOICE: ");
    }

    private static void AddEmployees(DutyIntervalSet dutyApp, Scanner in) {
        boolean conti = true;
        do {
            System.out.println("Employ Name:");
            String name = in.next();
            System.out.println("Employ Duty:");
            String post = in.next();
            System.out.println("Employ Phone Number:");
            String number = in.next();
            dutyApp.addEmployee(name, post, number);
            System.out.println("Continue Add Employ? Y(y)/N(others)");
            String ch = in.next();
            if (ch.equals("Y") || ch.equals("y")) conti = true;
            else conti = false;
        } while(conti);
    }

    private static void SetDuty(DutyIntervalSet dutyApp, Scanner in) {
        boolean conti = true;
        do {
            dutyApp.EmptyDutyTable();
            System.out.println("Choose an employee:");
            String name = in.next();
            Employee cur = dutyApp.getEmployee(name);
            if(cur == null) {
                System.out.println("have no this employee");
                break;
            }
            System.out.println("Date Form (yyyy-mm-dd):");
            String startS = in.next();
            long start = DateFormat.StringToDateLong(startS);
            String endS = in.next();
            long end = DateFormat.StringToDateLong(endS) + 24 * 3600 * 1000;
            try {
                dutyApp.insert(start, end, cur); // TODO
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Continue Set Duty? Y(y)/N(others)");
            String ch = in.next();
            if (ch.equals("Y") || ch.equals("y")) conti = true;
            else conti = false;
        } while (conti && !dutyApp.checkNoBlank());
        // when choose continue set, and there has blank 
    }

    private static void SetDutyRandom(DutyIntervalSet dutyApp) {
        long start = dutyApp.GetStartTime();
        long end = dutyApp.GetEndTime();
        long period =  end - start;
        long startTemp = start;
        long endTemp;
        List<Employee> em = dutyApp.employees();
        long rand;
        for (int i = 0; i < em.size(); i++) {
            if (i < em.size() - 1)
                rand = (long)(0 + Math.random() * (period + 1));
            else rand = period;
            endTemp = startTemp + rand + 24 * 3600 * 1000;
            try {
                dutyApp.insert(startTemp, endTemp, em.get(i)); // TODO
            } catch (Exception e) {
                e.printStackTrace();
            }
            period -= rand;
            startTemp = endTemp;
        }
    }

    private static void SetPeriod(DutyIntervalSet dutyApp, Scanner s) {
        String startTime, endTime;
        long startRe, endRe;
        do {
            System.out.println("Input start data (yyyy-mm-dd):");
            startTime = s.next();
            startRe = DateFormat.StringToDateLong(startTime);
            
            System.out.println("Input end data (yyyy-mm-dd):");
            endTime = s.next();
            endRe = DateFormat.StringToDateLong(endTime);
        } while(startRe >= endRe);
        dutyApp.SetStartEndTime(startRe, endRe + 24 * 3600 * 1000);
    }

    private static void DeleteEmployee(DutyIntervalSet dutyApp, Scanner in) {
        boolean conti;
        do {
            System.out.println("Input the employee's name:");
            String name = in.next();
            if(!dutyApp.removeEmployeeName(name))
                System.out.println("Have no this employee.");
            System.out.println("Continue delete? Y(y)/N(others)");
            String ch = in.next();
            if (ch.equals("Y") || ch.equals("y")) conti = true;
            else conti = false;
        } while (conti);
    }

    private static void SetFromTXT(DutyIntervalSet dutyApp, File test) throws Exception {
        FileReader fr = new FileReader(test);
        BufferedReader br = new BufferedReader(fr);
        String line = null;
        String regex1 = "Period\\{[0-9]{4}\\-[0-9]{2}\\-[0-9]{2}\\,[0-9]{4}\\-[0-9]{2}\\-[0-9]{2}\\}";
        String regex2 = "Employee\\{\s*";
        String regex30 = "[a-zA-Z]+\\{[a-zA-Z\s*]+\\,1[0-9]{2}\\-[0-9]{4}\\-[0-9]{4}\\}";
        String regex31 = "[a-zA-Z]+\\{[a-zA-Z\s*]+\\,1[0-9]{1}\\-[0-9]{5}\\-[0-9]{4}\\}";
        String regex32 = "[a-zA-Z]+\\{[a-zA-Z\s*]+\\,1[0-9]{2}\\-[0-9]{3}\\-[0-9]{5}\\}";
        String regex4 = "Roster\\{\s*";
        String regex5 = "[a-zA-Z]+\\{[0-9]{4}\\-[0-9]{2}\\-[0-9]{2}\\,[0-9]{4}\\-[0-9]{2}\\-[0-9]{2}\\}";
        String regex6 = "\\}";
        while ((line = br.readLine()) != null) {
            if (Pattern.matches(regex1, line)) {
                String[] str = line.split("[\\{|\\}|\\,]");
                // System.out.println(str[0] + "\n" + str[1]);
                dutyApp.SetStartEndTime(DateFormat.StringToDateLong(str[1]), DateFormat.StringToDateLong(str[2]) + 24 * 3600 * 1000);
            }
            else if (Pattern.matches(regex2, line)) {
                while (!Pattern.matches(regex6, (line = br.readLine()))) {
                    if (Pattern.matches(regex30, line) || Pattern.matches(regex31, line) || Pattern.matches(regex32, line)) {
                        String[] str = line.split("[\\{\\,\\}]");
                        // System.out.println(str[0] + "\n" + str[1] + "\n" + str[2]);
                        dutyApp.addEmployee(str[0], str[1], str[2]);
                    }
                }
            }
            else if (Pattern.matches(regex4, line)) {
                while (!Pattern.matches(regex6, (line = br.readLine()))) {
                    if (Pattern.matches(regex5, line)) {
                        String[] str = line.split("\\{|\\,|\\}");
                        // System.out.println(str[0]);
                        // System.out.println(str[0] + "\n" + str[1] + "\n" + str[2]);
                        Employee label = dutyApp.getEmployee(str[0]);
                        if (label != null)
                        dutyApp.insert(DateFormat.StringToDateLong(str[1]), 
                                DateFormat.StringToDateLong(str[2]) + 24 * 3600 * 1000, label); // TODO
                        else System.out.println("Employee " + str[0] + " does not exist!");
                    }
                }
            }
        }
        dutyApp.DutyTable();
        br.close();
    }


    public static void main(String[] args) throws Exception {
        final DutyIntervalSet dutyApp = new DutyIntervalSet();
        Scanner m = new Scanner(System.in);
        int choice;
        do {
            menu();
            choice = m.nextInt();
            switch(choice) {
                case 0:
                SetPeriod(dutyApp, m);
                break;
                // add some employees into the employee list
                case 1:
                AddEmployees(dutyApp, m);
                break;
                case 2:
                SetDuty(dutyApp, m);
                break;
                case 3:
                SetDutyRandom(dutyApp);
                dutyApp.checkNoBlank();
                break;
                case 4:
                dutyApp.DutyTable();
                break;
                case 5:
                DeleteEmployee(dutyApp, m);
                break;
                case 6:
                System.out.println("Choose a file(1-8, otherwise quit):");
                int myTXT = m.nextInt();
                File file = new File("src/APPs/Duty/TXT/test" + myTXT + ".txt");
                SetFromTXT(dutyApp, file);
                m.close();
                System.exit(0);
                break;
                default:
                break;
            }
        } while(choice >= 0 && choice < 7);
        m.close();
    }
    
}
