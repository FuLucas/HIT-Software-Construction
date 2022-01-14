package APPs.Process;

public class Process {

    // fields, process' ID, name, Shortest and Longest execution time
    private final int ID;
    private final String name;
    private final long shortestTime;
    private final long longestTime;


    // Abstraction function:
    // AF(ID) = Id of the process
    // AF(name) = name of the process
    // AF(shortestTime) = the Shortest execution time of the process
    // AF(longestTime) = the Longest execution time of the process

    // Representation invariant:
    // ID must > 0, and unique
    // the Longest execution time MUST be longer than the shortest
    
    // Safety from rep exposure:
    // Check the rep invariant is true
    // All fields MUST be private (all the String type are immutable)
    // So make defensive copies instead of just return mutable data 


    /**
     * constructor
     * @param ID int Id of the process
     * @param name string name of the process
     * @param shortestTime long Shortest execution time of the process
     * @param longestTime long Longest execution time of the process
     */
    public Process(int ID, String name, long shortestTime, long longestTime) {
        this.ID = ID;
        this.name = name;
        this.shortestTime = shortestTime;
        this.longestTime = longestTime;
    }

    /**
     * get the ID of the process
     * @return int ID of the process
     */
    public int getID() {
        return ID;
    }

    /**
     * get the name of the process
     * @return String name of the process
     */
    public String getName() {
        return name;
    }

    /**
     * get the Shortest execution time of the process
     * @return long the Shortest execution time of the process
     */
    public long getShortestTime() {
        return shortestTime;
    }

    /**
     * get the Longest execution time of the process
     * @return long the Longest execution time of the process
     */
    public long getLongestTime() {
        return longestTime;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o instanceof Process) {
            Process that = (Process) o;
            return that.getID() == this.ID;
        }
        else return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[" + ID + ", " + name + ", " + shortestTime + ", " + longestTime +"]");
        return sb.toString();
    }
}
