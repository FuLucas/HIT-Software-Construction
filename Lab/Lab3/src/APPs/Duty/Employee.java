package APPs.Duty;


public class Employee{
    // fields, employee's name, post and phone number
    private final String name;
    private final String post;
    private final String number;

    // Abstraction function:
    // AF(name) = name of the employee
    // AF(post) = post of the employee
    // AF(munber) = phone number of the employee

    // Representation invariant:
    // phone number consists of numbers
    
    // Safety from rep exposure:
    // Check the rep invariant is true
    // All fields MUST be private (all the String type are immutable)
    // So make defensive copies instead of just return mutable data 



    /**
     * constructor 
     * @param name String name of the employee
     * @param post String post of the employee
     * @param number String phone number of the employee
     */
    public Employee(String name, String post, String number) {
        this.name = name;
        this.post = post;
        this.number = number;
    }

    /**
     * get the employee's name
     * @return String name of the employee
     */
    public String getName() {
        return name;
    }

    /**
     * get the employee's post
     * @return String post of the employee
     */
    public String getPost() {
        return post;
    }

    /**
     * get the employee's phone number'
     * @return long phone number of the employee
     */
    public String getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Employee) {
            Employee that = (Employee) o;
            return that.getName().equals(this.name);
        }
        else return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[" + name + ", " + post + ", " + number + "]");
        return sb.toString();
    }
}